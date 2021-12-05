package com.example.mobilev10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mobilev10.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener {

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    private GoogleMap mMap;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Luminosidade - Dark Mode
        SharedPreferences preferencias = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        if (preferencias.getBoolean("Automatico", false)) {
            if (preferencias.getBoolean("Dark", false)){
                ativarDarkMode();
            }
        } else if (preferencias.getBoolean("Dark", false)) {
            ativarDarkMode();
        }

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Mudar para o endereço do galpão!!
        LatLng goldencompany = new LatLng(-23.5206185,-46.7306523);

        mMap.addMarker(new MarkerOptions().position(goldencompany).title("Golden Company"));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(goldencompany, 14f)
        );
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
                    finish();
                } else if (preferencias.getBoolean("Automatico", false) && luminosidade >= 20000) {
                    editor.putBoolean("Dark", false).apply();
                }

                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void ativarDarkMode() {
        ConstraintLayout ConstrBackground = (ConstraintLayout) findViewById(R.id.background);
        LinearLayout linearCabecalho = (LinearLayout) findViewById(R.id.linearCabecalho);
        ImageButton imgbtnHome = (ImageButton) findViewById(R.id.imgbtnHome);
        ImageButton imgbtnAcoes = (ImageButton) findViewById(R.id.imgbtnAcoes);
        ImageButton imgbtnPerfil = (ImageButton) findViewById(R.id.imgbtnPerfil);
        TextView txtBemvindo = (TextView) findViewById(R.id.txtBemvindo);
        TextView txtNossahistoriaTitulo = (TextView) findViewById(R.id.txtNossahistoriaTitulo);
        TextView txtNossahistoriaTexto = (TextView) findViewById(R.id.txtNossahistoriaTexto);
        TextView txtOquefazemosTitulo = (TextView) findViewById(R.id.txtOquefazemosTitulo);
        TextView txtOquefazemosTexto = (TextView) findViewById(R.id.txtOquefazemosTexto);
        TextView txtMapa = (TextView) findViewById(R.id.txtMapa);
        TextView txtVersaoApp = (TextView) findViewById(R.id.txtVersaoapp);


        ConstrBackground.setBackgroundResource(R.color.dark_background);
        linearCabecalho.setBackgroundResource(R.color.dark_cabecalho);
        imgbtnHome.setImageResource(R.drawable.logodark);
        imgbtnAcoes.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        imgbtnPerfil.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        txtBemvindo.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtNossahistoriaTitulo.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtNossahistoriaTexto.setTextColor(getResources().getColor(R.color.dark_texto));
        txtOquefazemosTitulo.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtOquefazemosTexto.setTextColor(getResources().getColor(R.color.dark_texto));
        txtMapa.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtVersaoApp.setTextColor(getResources().getColor(R.color.dark_titulo));
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

    public void abrirGit(View v){
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Golden-CompanyS/Mobile"));
        startActivity(Intent.createChooser(it, "Abrir browser"));
    }
}