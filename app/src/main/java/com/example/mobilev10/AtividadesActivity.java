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

public class AtividadesActivity extends AppCompatActivity implements SensorEventListener {

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    // Banco de Dados
    private DatabaseHelper mydb ;
    int id_to_update = 0;

    // Tabela Atividades
    TextView edtDesc;
    TextView edtDtInicio;
    TextView edtDtFim;

    // Tabela Cliente
    TextView edtCnpj;

    // Tabela Funcionario
    TextView edtFunc;

    // Tabela Estado
    TextView edtServico;

    Atividades atividades;

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

        // Banco de Dados
        edtDesc = (TextView) findViewById(R.id.edtDescAtividade);
        edtDtInicio = (TextView) findViewById(R.id.edtDtInicioAtividade);
        edtDtFim = (TextView) findViewById(R.id.edtDtFimAtividade);
        edtFunc = (TextView) findViewById(R.id.edtFuncionarioAtividade);
        edtCnpj = (TextView) findViewById(R.id.edtClienteAtividade);
        edtServico = (TextView) findViewById(R.id.edtServicoAtividade);

        mydb = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int value = extras.getInt("id");

            if (value > 0){
                // ou seja, se NÃO for para fazer um cadastro novo (visualizar)

                Cursor cursor = mydb.getDataAtividades(value);
                id_to_update = value;
                cursor.moveToFirst();

                atividades = new Atividades();
                atividades.set_desc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESC_ATV)));
                atividades.set_dtInicio(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DTINICIO_ATV)));
                atividades.set_dtFim(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DTFIM_ATV)));
                atividades.set_func(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_FUNC)));
                atividades.set_cnpj(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CNPJ_CLI)));
                atividades.set_serv(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_SERV)));


                if (!cursor.isClosed()){
                    cursor.close();
                }

                Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
                btnSalvar.setVisibility(View.GONE);

                edtDesc.setText(atividades.get_desc());
                edtDesc.setEnabled(false);
                edtDesc.setFocusable(false);
                edtDesc.setClickable(false);

                edtDtInicio.setText(atividades.get_dtInicio());
                edtDtInicio.setEnabled(false);
                edtDtInicio.setFocusable(false);
                edtDtInicio.setClickable(false);

                edtDtFim.setText(atividades.get_dtFim());
                edtDtFim.setEnabled(false);
                edtDtFim.setFocusable(false);
                edtDtFim.setClickable(false);

                edtFunc.setText(atividades.get_func());
                edtFunc.setEnabled(false);
                edtFunc.setFocusable(false);
                edtFunc.setClickable(false);

                edtCnpj.setText(atividades.get_cnpj());
                edtCnpj.setEnabled(false);
                edtCnpj.setFocusable(false);
                edtCnpj.setClickable(false);

                edtServico.setText(atividades.get_serv());
                edtServico.setEnabled(false);
                edtServico.setFocusable(false);
                edtServico.setClickable(false);

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

        edtDesc.setText(atividades.get_desc());
        edtDesc.setEnabled(true);
        edtDesc.setFocusableInTouchMode(true);
        edtDesc.setClickable(true);


        edtDtInicio.setText(atividades.get_dtInicio());
        edtDtInicio.setEnabled(true);
        edtDtInicio.setFocusableInTouchMode(true);
        edtDtInicio.setClickable(true);

        edtDtFim.setText(atividades.get_dtFim());
        edtDtFim.setEnabled(true);
        edtDtFim.setFocusableInTouchMode(true);
        edtDtFim.setClickable(true);

        edtFunc.setText(atividades.get_func());
        edtFunc.setEnabled(true);
        edtFunc.setFocusableInTouchMode(true);
        edtFunc.setClickable(true);

        edtCnpj.setText(atividades.get_cnpj());
        edtCnpj.setEnabled(true);
        edtCnpj.setFocusableInTouchMode(true);
        edtCnpj.setClickable(true);

        edtServico.setText(atividades.get_serv());
        edtServico.setEnabled(true);
        edtServico.setFocusableInTouchMode(true);
        edtServico.setClickable(true);
    }

    public void excluir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.excluir_registro)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb.deleteAtividade(id_to_update);
                        Toast.makeText(getApplicationContext(), R.string.delete_ok,
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ConAtividadesActivity.class);
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
                if(mydb.updateAtividade(
                        new Atividades(
                                id_to_update,
                                edtDesc.getText().toString(),
                                edtDtInicio.getText().toString(),
                                edtDtFim.getText().toString(),
                                edtFunc.getText().toString(),
                                edtCnpj.getText().toString(),
                                edtServico.getText().toString()
                        ))){
                    Toast.makeText(getApplicationContext(), "Atualizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ConAtividadesActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Não Atualizado", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(mydb.insertAtividade(
                        new Atividades(
                                edtDesc.getText().toString(),
                                edtDtInicio.getText().toString(),
                                edtDtFim.getText().toString(),
                                edtFunc.getText().toString(),
                                edtCnpj.getText().toString(),
                                edtServico.getText().toString()
                        ))){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), ConAtividadesActivity.class);
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