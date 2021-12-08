package com.example.mobilev10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
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
import android.widget.Toast;

public class ServicosActivity extends AppCompatActivity implements SensorEventListener {
    // Banco de Dados
    private DatabaseHelper mydb ;
    int id_to_update = 0;

    TextView edtDescServico;
    Servicos servico;

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);

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

        // Banco de Dados
        edtDescServico = (TextView) findViewById(R.id.edtDescServico);

        mydb = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int value = extras.getInt("id");

            if (value > 0){
                // ou seja, se NÃO for para fazer um cadastro novo (visualizar)

                Cursor cursor = mydb.getDataServicos(value);
                id_to_update = value;
                cursor.moveToFirst();

                servico = new Servicos();
                servico.set_desc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESC_SERV)));

                if (!cursor.isClosed()){
                    cursor.close();
                }

                Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
                btnSalvar.setVisibility(View.GONE);

                edtDescServico.setText(servico.get_desc());
                edtDescServico.setEnabled(false);
                edtDescServico.setFocusable(false);
                edtDescServico.setClickable(false);
            } else {
                Button btnEditar = (Button) findViewById(R.id.btnEditar);
                Button btnExcluir = (Button) findViewById(R.id.btnExcluir);
                btnEditar.setVisibility(View.GONE);
                btnExcluir.setVisibility(View.GONE);
            }
        }
    }

    // Banco de Dados
    public void editar(View view){
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setVisibility(View.VISIBLE);

        edtDescServico.setEnabled(true);
        edtDescServico.setFocusableInTouchMode(true);
        edtDescServico.setClickable(true);
    }

    public void excluir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.excluir_registro)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb.deleteServico(id_to_update);
                        Toast.makeText(getApplicationContext(), R.string.delete_ok,
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ConServicosActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(R.string.delete_registro);
        alertDialog.show();
    }


    public void salvar(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int value = extras.getInt("id");

            if (value > 0){
                if(mydb.updateServico(
                        new Servicos(
                                id_to_update,
                                edtDescServico.getText().toString()
                        ))){
                    Toast.makeText(getApplicationContext(), "Atualizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ConServicosActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Não Atualizado", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(mydb.insertServico(
                        new Servicos(
                                edtDescServico.getText().toString()
                        ))){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), ConServicosActivity.class);
                startActivity(intent);
            }
        }
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

                Intent intent = new Intent(ServicosActivity.this, ServicosActivity.class);
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
        EditText edtDescServico = (EditText) findViewById(R.id.edtDescServico);


        ConstrBackground.setBackgroundResource(R.color.dark_background);
        linearCabecalho.setBackgroundResource(R.color.dark_cabecalho);
        imgbtnHome.setImageResource(R.drawable.logodark);
        imgbtnAcoes.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        imgbtnPerfil.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        imgAcao.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_texto)));
        btnSalvar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_botao)));
        txtTitulo.setTextColor(getResources().getColor(R.color.dark_texto));
        edtDescServico.setTextColor(getResources().getColor(R.color.dark_titulo));
        edtDescServico.setHintTextColor(getResources().getColor(R.color.dark_input_hint));
        edtDescServico.setBackgroundResource(R.drawable.input_arredondado_dark);
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