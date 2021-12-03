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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SociosActivity extends AppCompatActivity implements SensorEventListener {

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socios);

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

                Intent intent = new Intent(SociosActivity.this, SociosActivity.class);
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
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        ImageView imgAcao = (ImageView) findViewById(R.id.imgAcao);

        TextView txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        TextView txtContato = (TextView) findViewById(R.id.txtContato);
        TextView txtEndereco = (TextView) findViewById(R.id.txtEndereco);

        EditText edtNomeSocio = (EditText) findViewById(R.id.edtNomeSocio);
        EditText edtDtNascSocio = (EditText) findViewById(R.id.edtDtNascSocio);
        EditText edtCpfSocio = (EditText) findViewById(R.id.edtCpfSocio);
        EditText edtCargoSocio = (EditText) findViewById(R.id.edtCargoSocio);
        EditText edtSenhaSocio = (EditText) findViewById(R.id.edtSenhaSocio);
        EditText edtTelSocio = (EditText) findViewById(R.id.edtTelSocio);
        EditText edtEmailSocio = (EditText) findViewById(R.id.edtEmailSocio);
        EditText edtUfSocio = (EditText) findViewById(R.id.edtUfSocio);
        EditText edtCidadeSocio = (EditText) findViewById(R.id.edtCidadeSocio);
        EditText edtBairroSocio = (EditText) findViewById(R.id.edtBairroSocio);
        EditText edtLogradouroSocio = (EditText) findViewById(R.id.edtLogradouroSocio);
        EditText edtComplementoSocio = (EditText) findViewById(R.id.edtComplementoSocio);
        EditText edtNumEndSocio = (EditText) findViewById(R.id.edtNumEndSocio);


        ConstrBackground.setBackgroundResource(R.color.dark_background);
        linearCabecalho.setBackgroundResource(R.color.dark_cabecalho);
        imgbtnHome.setImageResource(R.drawable.logodark);
        imgbtnAcoes.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        imgbtnPerfil.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        imgAcao.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        btnSalvar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_botao)));

        txtTitulo.setTextColor(getResources().getColor(R.color.dark_texto));
        txtContato.setTextColor(getResources().getColor(R.color.dark_texto));
        txtEndereco.setTextColor(getResources().getColor(R.color.dark_texto));

        edtNomeSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtNomeSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtNomeSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtDtNascSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtDtNascSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtDtNascSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtCpfSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtCpfSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtCpfSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtCargoSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtCargoSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtCargoSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtSenhaSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtSenhaSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtSenhaSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtTelSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtTelSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtTelSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtEmailSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtEmailSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtEmailSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtUfSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtUfSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtUfSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtCidadeSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtCidadeSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtCidadeSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtBairroSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtBairroSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtBairroSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtLogradouroSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtLogradouroSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtLogradouroSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtComplementoSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtComplementoSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtComplementoSocio.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtNumEndSocio.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtNumEndSocio.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtNumEndSocio.setBackgroundResource(R.drawable.input_arredondado_dark);
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