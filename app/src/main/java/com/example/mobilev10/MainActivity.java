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
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
        ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView txtVersaoapp = (TextView) findViewById(R.id.txtVersaoapp);
        EditText edtEmailLogin = (EditText) findViewById(R.id.edtEmailLogin);
        EditText edtSenhaLogin = (EditText) findViewById(R.id.edtSenhaLogin);
        txtVersaoapp.setTextColor(getResources().getColor(R.color.dark_texto));

        ConstrBackground.setBackgroundResource(R.color.dark_background);
        imgLogo.setImageResource(R.drawable.logodark);
        btnLogin.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_botao)));

        edtEmailLogin.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtEmailLogin.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtEmailLogin.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtSenhaLogin.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtSenhaLogin.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtSenhaLogin.setBackgroundResource(R.drawable.input_arredondado_dark);



    }

    // Redirecionamento sem verificação temporário
    public void abrirAcoesActivity(View view){
        Intent intent = new Intent(this, AcoesActivity.class);
        startActivity(intent);
        finish();
    }
}