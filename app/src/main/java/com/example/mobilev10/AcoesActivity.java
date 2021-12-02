package com.example.mobilev10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AcoesActivity extends AppCompatActivity implements SensorEventListener {

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoes);

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

                Intent intent = new Intent(AcoesActivity.this, AcoesActivity.class);
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
        ImageButton imgbtnSocios = (ImageButton) findViewById(R.id.imgbtnSocios);
        ImageButton imgbtnServicos = (ImageButton) findViewById(R.id.imgbtnServicos);
        ImageButton imgbtnClientes = (ImageButton) findViewById(R.id.imgbtnClientes);
        ImageButton imgbtnAtividades = (ImageButton) findViewById(R.id.imgbtnAtividades);
        TextView txtSocios = (TextView) findViewById(R.id.txtbtnSocios);
        TextView txtServicos = (TextView) findViewById(R.id.txtbtnServicos);
        TextView txtClientes = (TextView) findViewById(R.id.txtbtnClientes);
        TextView txtAtividades = (TextView) findViewById(R.id.txtbtnAtividades);


        ConstrBackground.setBackgroundResource(R.color.dark_background);
        linearCabecalho.setBackgroundResource(R.color.dark_cabecalho);
        /*imgbtnLogo.setImageResource(R.drawable.logoDark);
        imgbtnHome.setImageResource(R.drawable.homeDark);
        imgbtnPerfil.setImageResource(R.drawable.perfilDark);*/
        imgbtnSocios.setBackgroundResource(R.drawable.imgbtn_arredondado_dark);
        imgbtnServicos.setBackgroundResource(R.drawable.imgbtn_arredondado_dark);
        imgbtnClientes.setBackgroundResource(R.drawable.imgbtn_arredondado_dark);
        imgbtnAtividades.setBackgroundResource(R.drawable.imgbtn_arredondado_dark);
        txtSocios.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtServicos.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtClientes.setTextColor(getResources().getColor(R.color.dark_titulo));
        txtAtividades.setTextColor(getResources().getColor(R.color.dark_titulo));
    }


    public void abrirSociosActivity(View view){
        Intent intent = new Intent(this, SociosActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    public void abrirServicosActivity(View view){
        Intent intent = new Intent(this, ServicosActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    public void abrirClientesActivity(View view){
        Intent intent = new Intent(this, ClientesActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    public void abrirAtividadesActivity(View view){
        Intent intent = new Intent(this, AtividadesActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
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
}