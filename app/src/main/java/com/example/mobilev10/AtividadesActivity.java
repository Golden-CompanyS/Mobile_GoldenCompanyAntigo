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

public class AtividadesActivity extends AppCompatActivity implements SensorEventListener {

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);

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

                Intent intent = new Intent(AtividadesActivity.this, AtividadesActivity.class);
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
        TextView txtAtribuicoes = (TextView) findViewById(R.id.txtAtribuicoes);

        EditText edtDescAtividade = (EditText) findViewById(R.id.edtDescAtividade);
        EditText edtDtInicioAtividade = (EditText) findViewById(R.id.edtDtInicioAtividade);
        EditText edtDtFimAtividade = (EditText) findViewById(R.id.edtDtFimAtividade);
        EditText edtFuncionarioAtividade = (EditText) findViewById(R.id.edtFuncionarioAtividade);
        EditText edtClienteAtividade = (EditText) findViewById(R.id.edtClienteAtividade);
        EditText edtServicoAtividade = (EditText) findViewById(R.id.edtServicoAtividade);


        ConstrBackground.setBackgroundResource(R.color.dark_background);
        linearCabecalho.setBackgroundResource(R.color.dark_cabecalho);
        imgbtnHome.setImageResource(R.drawable.logodark);
        imgbtnAcoes.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        imgbtnPerfil.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        imgAcao.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        btnSalvar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_botao)));

        txtTitulo.setTextColor(getResources().getColor(R.color.dark_texto));
        txtAtribuicoes.setTextColor(getResources().getColor(R.color.dark_texto));

        edtDescAtividade.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtDescAtividade.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtDescAtividade.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtDtInicioAtividade.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtDtInicioAtividade.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtDtInicioAtividade.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtDtFimAtividade.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtDtFimAtividade.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtDtFimAtividade.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtFuncionarioAtividade.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtFuncionarioAtividade.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtFuncionarioAtividade.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtClienteAtividade.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtClienteAtividade.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtClienteAtividade.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtServicoAtividade.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtServicoAtividade.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtServicoAtividade.setBackgroundResource(R.drawable.input_arredondado_dark);
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