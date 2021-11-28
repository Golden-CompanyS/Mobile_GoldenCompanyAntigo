package com.example.mobilev10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserActivity extends AppCompatActivity implements FetchAddressTask.OnTaskCompleted {

    EditText edtNotes;
    Button btnSave;
    ImageView imgUser;
    Button btnAltPerfil;
    ImageView imgPerfil;

    public static final String PREFERENCIAS_NAME = "com.example.android.localizacao";
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    // Constantes
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String LASTADRESS_KEY = "adress";
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";

    // Shared preferences
    private SharedPreferences mPreferences;
    private String lastLatitude = "";
    private String lastLongitude = "";
    private String lastAdress = "";


    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private TextView mLocationTextView;

    private boolean mTrackingLocation;

    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    private static final int RESULT_SELECT_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        edtNotes = (EditText) findViewById(R.id.edtNotes);
        btnSave= (Button) findViewById(R.id.btnSaveNotes);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        btnAltPerfil = (Button) findViewById(R.id.btnAltFoto);
        mLocationTextView = (TextView) findViewById(R.id.txtEndereco);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);

        // Recupera o estado da aplicação quando é recriado
        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }
        mLocationCallback = new LocationCallback() {
            /**
             * This is the callback that is triggered when the
             * FusedLocationClient atualiza a localização.
             * @param locationResult The result containing the device location.
             */
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // If tracking is turned on, reverse geocode into an address
                if (mTrackingLocation) {
                    new FetchAddressTask(UserActivity.this, UserActivity.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };



        //inicializa as preferências do usuário
        mPreferences = getSharedPreferences(PREFERENCIAS_NAME, MODE_PRIVATE);
        //recupera as preferencias
        //recuperar();
    }
//SALVAR AS ANOTAÇÕES - ARMAZENAMENTO EXTERNO
    public void savePublicly(View view){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
        }, EXTERNAL_STORAGE_PERMISSION_CODE);
        String editTextNotes = edtNotes.getText().toString();
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, "notes.txt");
        writeTextNotes(file, editTextNotes);

        String data = getdata(file);
        if (data != null) {
            edtNotes.setText(data);
        } else {
            edtNotes.setText("No Data Found");
        }
    }

    private void writeTextNotes(File file, String data){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Anotações Salvas" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //IMAGE PICKER
    public void viewGallery(View view){
        //inicia uma intent com ação de Seleção de Imagens
        Intent intent =     new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //inicia uma activity que devolverá uma resposta
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), RESULT_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
           Uri imagemSelecionada = data.getData();
            //define como source da imagem
            imgUser.setImageURI(imagemSelecionada);
        }
    }



    // NAVEGAÇÃO

    public void abrirHomeActivity(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    public void abrirUserActivity(View view){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    public void abrirAcoesActivity(View view){
        Intent intent = new Intent(this, AcoesActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    //LOCALIZAÇÃO
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // Permissão garantida
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    iniciarLocal();
                } else {
                    Toast.makeText(this,
                            R.string.permissao_negada,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        return locationRequest;
    }
    @SuppressLint("StringFormatMatches")
    private void iniciarLocal() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null /* Looper */);
            mFusedLocationClient.getLastLocation().addOnCompleteListener(
                    task -> {
                        Location location = task.getResult();
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(UserActivity.this,
                                    Locale.getDefault());
                            try {
                                List<Address> adresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1);
                                adresses.get(0).getCountryName();
                                SharedPreferences preferences = getSharedPreferences(PREFERENCIAS_NAME, 0);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });
            mLocationTextView.setText(getString(R.string.endereco_text,
                    getString(R.string.loading), null, null));
        }
    }
    @SuppressLint("StringFormatMatches")
    @Override
    public void onTaskCompleted(String[] result) {
        if (mTrackingLocation) {
            // Update the UI
            lastAdress = result[0];
            lastLatitude = result[1];
            lastLongitude = result[2];
            mLocationTextView.setText(getString(R.string.endereco_text));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation);
        super.onSaveInstanceState(outState);
    }

    //Armazena as preferencias do usuário
    //na aplicação será armazenada a última localização

    private void armazenar(String latitude, String longitude, String lastAdress) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(LASTADRESS_KEY, lastAdress);
        preferencesEditor.apply();
    }


    @Override
    protected void onResume() {
        if (mTrackingLocation) {
            iniciarLocal();
        }
        recuperar();
        super.onResume();
    }

    @SuppressLint("StringFormatMatches")
    private void recuperar() {
        SharedPreferences mPreferences = getSharedPreferences(PREFERENCIAS_NAME, 0);
        lastAdress = mPreferences.getString(LASTADRESS_KEY, "");
        Toast.makeText(this, getString(R.string.endereco_text), Toast.LENGTH_SHORT).show();
    }



}