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

public class ClientesActivity extends AppCompatActivity implements SensorEventListener {

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

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

                Intent intent = new Intent(ClientesActivity.this, ClientesActivity.class);
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

        EditText edtNomeCliente = (EditText) findViewById(R.id.edtNomeCliente);
        EditText edtCnpjCliente = (EditText) findViewById(R.id.edtCnpjCliente);
        EditText edtTelCliente = (EditText) findViewById(R.id.edtTelCliente);
        EditText edtEmailCliente = (EditText) findViewById(R.id.edtEmailCliente);
        EditText edtUfCliente = (EditText) findViewById(R.id.edtUfCliente);
        EditText edtCidadeCliente = (EditText) findViewById(R.id.edtCidadeCliente);
        EditText edtBairroCliente = (EditText) findViewById(R.id.edtBairroCliente);
        EditText edtLogradouroCliente = (EditText) findViewById(R.id.edtLogradouroCliente);
        EditText edtComplementoCliente = (EditText) findViewById(R.id.edtComplementoCliente);
        EditText edtNumEndCliente = (EditText) findViewById(R.id.edtNumEndCliente);


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

        edtNomeCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtNomeCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtNomeCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtCnpjCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtCnpjCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtCnpjCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtTelCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtTelCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtTelCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtEmailCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtEmailCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtEmailCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtUfCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtUfCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtUfCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtCidadeCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtCidadeCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtCidadeCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtBairroCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtBairroCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtBairroCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtLogradouroCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtLogradouroCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtLogradouroCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtComplementoCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtComplementoCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtComplementoCliente.setBackgroundResource(R.drawable.input_arredondado_dark);

        edtNumEndCliente.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtNumEndCliente.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtNumEndCliente.setBackgroundResource(R.drawable.input_arredondado_dark);
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