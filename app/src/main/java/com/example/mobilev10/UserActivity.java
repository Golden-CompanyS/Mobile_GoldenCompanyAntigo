package com.example.mobilev10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class UserActivity extends AppCompatActivity implements FetchAddressTask.OnTaskCompleted, SensorEventListener {
    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;


    EditText edtNotes;
    Button btnSave;
    ImageView imgUser;
    Button btnAltPerfil;
    ImageView imgPerfil;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    // Constantes
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String LASTADRESS_KEY = "adress";
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private static final String FILE_NAME = "NOTES.txt";


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

        // Luminosidade - Dark Mode
        SharedPreferences preferencias = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        Switch switchTema_Manual = (Switch) findViewById(R.id.switch1);
        Switch switchTema_Automatico = (Switch) findViewById(R.id.switch2);

        if (preferencias.getBoolean("Automatico", false)) {
            switchTema_Automatico.setChecked(true);
            switchTema_Manual.setClickable(false);
            if (preferencias.getBoolean("Dark", false)){
                ativarDarkMode();
            }
        } else if (preferencias.getBoolean("Dark", false)) {
            switchTema_Manual.setChecked(true);
            switchTema_Automatico.setChecked(false);
            ativarDarkMode();
        }


        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        switchTema_Manual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("Dark", true);
                } else {
                    editor.putBoolean("Dark", false);
                }
                editor.commit();

                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                startActivity(intent);
                UserActivity.this.overridePendingTransition(0, 0);
                finish();
            }
        });

        switchTema_Automatico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("Automatico", true);
                    switchTema_Manual.setClickable(false);
                } else {
                    editor.putBoolean("Automatico", false);
                    switchTema_Manual.setClickable(false);
                }
                editor.commit();

                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                startActivity(intent);
                UserActivity.this.overridePendingTransition(0, 0);
                finish();
            }
        });


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
        //mPreferences = getSharedPreferences(PREFERENCIAS_NAME, MODE_PRIVATE);
        //recupera as preferencias
        //recuperar();
    }

    // Luminosidade - Dark Mode
    @Override
    public void onSensorChanged(SensorEvent event) {
        SharedPreferences preferencias = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        SharedPreferences.Editor editor = preferencias.edit();

        if (preferencias.getBoolean("Automatico", false)) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                luminosidade = event.values[0];

                if (luminosidade < 20000) {
                    editor.putBoolean("Dark", true).apply();
                } else if (preferencias.getBoolean("Automatico", false) && luminosidade >= 20000) {
                    editor.putBoolean("Dark", false).apply();
                }

                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                startActivity(intent);
                this.overridePendingTransition(0, 0);
                finish();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void ativarDarkMode() {
        ConstraintLayout ConstrBackground = (ConstraintLayout) findViewById(R.id.background);
        LinearLayout linearCabecalho = (LinearLayout) findViewById(R.id.linearCabecalho);
        ImageButton imgbtnHome = (ImageButton) findViewById(R.id.imgbtnHome);
        ImageButton imgbtnAcoes = (ImageButton) findViewById(R.id.imgbtnAcoes);
        ImageButton imgbtnPerfil = (ImageButton) findViewById(R.id.imgbtnPerfil);
        TextView txtUserArea = (TextView) findViewById(R.id.txtUserArea);
        TextView txtAltSenha = (TextView) findViewById(R.id.txtAltSenha);
        TextView txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        Button btnAltFoto = (Button) findViewById(R.id.btnAltFoto);
        Button btnAltSenha = (Button) findViewById(R.id.btnAltSenha);
        Button btnSaveNotes = (Button) findViewById(R.id.btnSaveNotes);
        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        Switch swtDarkmodeManual = (Switch) findViewById(R.id.switch1);
        Switch swtDarkmodeAutomatico = (Switch) findViewById(R.id.switch2);
        EditText edtSenhaAtual = (EditText) findViewById(R.id.edttxtAltSenha);
        EditText edtSenhaNova = (EditText) findViewById(R.id.edttxtConfAltSenha);
        EditText edtNotes = (EditText) findViewById(R.id.edtNotes);


        ConstrBackground.setBackgroundResource(R.color.dark_background);
        linearCabecalho.setBackgroundResource(R.color.dark_cabecalho);
        imgbtnHome.setImageResource(R.drawable.logodark);
        imgbtnAcoes.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        imgbtnPerfil.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        txtUserArea.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtAltSenha.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtEndereco.setTextColor(getResources().getColor(R.color.dark_titulo));
        btnAltFoto.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_botao)));
        btnAltSenha.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_botao)));
        btnSaveNotes.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_botao)));
        btnLogout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_botao)));
        swtDarkmodeManual.setTextColor(getResources().getColor(R.color.dark_titulo));
        swtDarkmodeAutomatico.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtSenhaAtual.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtSenhaAtual.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtSenhaAtual.setBackgroundResource(R.drawable.input_arredondado_dark);
        edtSenhaNova.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtSenhaNova.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtSenhaNova.setBackgroundResource(R.drawable.input_arredondado_dark);
        edtNotes.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtNotes.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtNotes.setBackgroundResource(R.drawable.input_arredondado_dark);
    }

    //SALVAR AS ANOTAÇÕES - ARMAZENAMENTO EXTERNO
    public void savePublicly(View view){
        String text = edtNotes.getText().toString();
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            edtNotes.getText().clear();
            Toast.makeText(this, "Anotação salva!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException x){
            x.printStackTrace();
        } catch (IOException x){
            x.printStackTrace();
        } finally{
            if(fos != null){
                try{
                    fos.close();
                } catch (IOException x){
                    x.printStackTrace();
                }
            }
        }
    }

    public void loadNotes(View v){
        FileInputStream y = null;

        try {
            y = openFileInput(FILE_NAME);
            InputStreamReader a = new InputStreamReader(y);
            BufferedReader b = new BufferedReader(a);
            StringBuilder c = new StringBuilder();
            String text;
            while ((text = b.readLine()) != null) {
                c.append(text).append("\n");
                edtNotes.setText(c.toString());
            }
        } catch(FileNotFoundException x){
            x.printStackTrace();
        } catch(IOException x){
            x.printStackTrace();
        }finally{
            if(y != null){
                try {
                    y.close();
                } catch (IOException x) {
                    x.printStackTrace();
                }
            }
        }
        edtNotes.setClickable(false);
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
                                SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);

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
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("StringFormatMatches")
    private void recuperar() {
        SharedPreferences mPreferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        lastAdress = mPreferences.getString(LASTADRESS_KEY, "");
        Toast.makeText(this, getString(R.string.endereco_text), Toast.LENGTH_SHORT).show();
    }

}