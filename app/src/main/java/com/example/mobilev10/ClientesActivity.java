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

public class ClientesActivity extends AppCompatActivity implements SensorEventListener {

    // Luminosidade - Dark Mode
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";
    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    // Banco de Dados
    private DatabaseHelper mydb ;
    int id_to_update = 0;

    // Tabela Cliente
    TextView edtNomeCli;
    TextView edtCnpjCli;
    TextView edtNumEndCli;

    // Tabela Telefone
    TextView edtTelCli;

    // Tabela Contato
    TextView edtEmailCli;

    // Tabela Estado
    TextView edtUfCli;

    // Tabela Cidade
    TextView edtCidadeCli;

    // Tabela Bairro
    TextView edtBairroCli;

    // Tabela Rua
    TextView edtLogradouroCli;

    // Tabela  Endereco
    TextView edtComplementoCli;

    Clientes clientes;


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

        // Banco de Dados
        edtNomeCli = (TextView) findViewById(R.id.edtNomeSocio);
        edtCnpjCli = (TextView) findViewById(R.id.edtCpfSocio);
        edtTelCli = (TextView) findViewById(R.id.edtTelSocio);
        edtEmailCli = (TextView) findViewById(R.id.edtEmailSocio);
        edtUfCli = (TextView) findViewById(R.id.edtUfSocio);
        edtCidadeCli = (TextView) findViewById(R.id.edtCidadeSocio);
        edtBairroCli = (TextView) findViewById(R.id.edtBairroSocio);
        edtLogradouroCli = (TextView) findViewById(R.id.edtLogradouroSocio);
        edtComplementoCli = (TextView) findViewById(R.id.edtComplementoSocio);
        edtNumEndCli = (TextView) findViewById(R.id.edtNumEndSocio);

        mydb = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int value = extras.getInt("id");

            if (value > 0){
                // ou seja, se NÃO for para fazer um cadastro novo (visualizar)

                Cursor cursor = mydb.getDataSocios(value);
                id_to_update = value;
                cursor.moveToFirst();

                clientes = new Clientes();
                clientes.set_nome(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_CLI)));
                clientes.set_cnpj(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CNPJ_CLI)));
                clientes.set_numend(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMEND_CLI)));
                clientes.set_telefone(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUM_TEL)));
                clientes.set_email(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL_CNTT)));
                clientes.set_estado(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UF_EST)));
                clientes.set_cidade(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CID_NOME)));
                clientes.set_bairro(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BAIRR_NOME)));
                clientes.set_logradouro(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RUA_LOGR)));
                clientes.set_complemento(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPL_END)));


                if (!cursor.isClosed()){
                    cursor.close();
                }

                Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
                btnSalvar.setVisibility(View.GONE);

                edtNomeCli.setText(clientes.get_nome());
                edtNomeCli.setEnabled(false);
                edtNomeCli.setFocusable(false);
                edtNomeCli.setClickable(false);

                edtCnpjCli.setText(clientes.get_cnpj());
                edtCnpjCli.setEnabled(false);
                edtCnpjCli.setFocusable(false);
                edtCnpjCli.setClickable(false);

                edtTelCli.setText(clientes.get_telefone());
                edtTelCli.setEnabled(false);
                edtTelCli.setFocusable(false);
                edtTelCli.setClickable(false);

                edtEmailCli.setText(clientes.get_email());
                edtEmailCli.setEnabled(false);
                edtEmailCli.setFocusable(false);
                edtEmailCli.setClickable(false);

                edtUfCli.setText(clientes.get_estado());
                edtUfCli.setEnabled(false);
                edtUfCli.setFocusable(false);
                edtUfCli.setClickable(false);

                edtCidadeCli.setText(clientes.get_cidade());
                edtCidadeCli.setEnabled(false);
                edtCidadeCli.setFocusable(false);
                edtCidadeCli.setClickable(false);

                edtBairroCli.setText(clientes.get_bairro());
                edtBairroCli.setEnabled(false);
                edtBairroCli.setFocusable(false);
                edtBairroCli.setClickable(false);

                edtLogradouroCli.setText(clientes.get_logradouro());
                edtLogradouroCli.setEnabled(false);
                edtLogradouroCli.setFocusable(false);
                edtLogradouroCli.setClickable(false);

                edtComplementoCli.setText(clientes.get_complemento());
                edtComplementoCli.setEnabled(false);
                edtComplementoCli.setFocusable(false);
                edtComplementoCli.setClickable(false);

                edtNumEndCli.setText(clientes.get_numend());
                edtNumEndCli.setEnabled(false);
                edtNumEndCli.setFocusable(false);
                edtNumEndCli.setClickable(false);

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

        edtNomeCli.setText(clientes.get_nome());
        edtNomeCli.setEnabled(true);
        edtNomeCli.setFocusableInTouchMode(true);
        edtNomeCli.setClickable(true);

        edtCnpjCli.setText(clientes.get_cnpj());
        edtCnpjCli.setEnabled(true);
        edtCnpjCli.setFocusableInTouchMode(true);
        edtCnpjCli.setClickable(true);

        edtTelCli.setText(clientes.get_telefone());
        edtTelCli.setEnabled(true);
        edtTelCli.setFocusableInTouchMode(true);
        edtTelCli.setClickable(true);

        edtEmailCli.setText(clientes.get_email());
        edtEmailCli.setEnabled(true);
        edtEmailCli.setFocusableInTouchMode(true);
        edtEmailCli.setClickable(true);

        edtUfCli.setText(clientes.get_estado());
        edtUfCli.setEnabled(true);
        edtUfCli.setFocusableInTouchMode(true);
        edtUfCli.setClickable(true);

        edtCidadeCli.setText(clientes.get_cidade());
        edtCidadeCli.setEnabled(true);
        edtCidadeCli.setFocusableInTouchMode(true);
        edtCidadeCli.setClickable(true);

        edtBairroCli.setText(clientes.get_bairro());
        edtBairroCli.setEnabled(true);
        edtBairroCli.setFocusableInTouchMode(true);
        edtBairroCli.setClickable(true);

        edtLogradouroCli.setText(clientes.get_logradouro());
        edtLogradouroCli.setEnabled(true);
        edtLogradouroCli.setFocusableInTouchMode(true);
        edtLogradouroCli.setClickable(true);

        edtComplementoCli.setText(clientes.get_complemento());
        edtComplementoCli.setEnabled(true);
        edtComplementoCli.setFocusableInTouchMode(true);
        edtComplementoCli.setClickable(true);

        edtNumEndCli.setText(clientes.get_numend());
        edtNumEndCli.setEnabled(true);
        edtNumEndCli.setFocusableInTouchMode(true);
        edtNumEndCli.setClickable(true);
    }

    public void excluir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.excluir_registro)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb.deleteCliente(id_to_update);
                        Toast.makeText(getApplicationContext(), R.string.delete_ok,
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ConClientesActivity.class);
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
                if(mydb.updateCliente(
                        new Clientes(
                                id_to_update,
                                edtNomeCli.getText().toString(),
                                edtCnpjCli.getText().toString(),
                                edtNumEndCli.getText().toString(),
                                edtTelCli.getText().toString(),
                                edtEmailCli.getText().toString(),
                                edtUfCli.getText().toString(),
                                edtCidadeCli.getText().toString(),
                                edtBairroCli.getText().toString(),
                                edtLogradouroCli.getText().toString(),
                                edtComplementoCli.getText().toString()
                        ))){
                    Toast.makeText(getApplicationContext(), "Atualizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ConClientesActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Não Atualizado", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(mydb.insertClientes(
                        new Clientes(
                                edtNomeCli.getText().toString(),
                                edtCnpjCli.getText().toString(),
                                edtNumEndCli.getText().toString(),
                                edtTelCli.getText().toString(),
                                edtEmailCli.getText().toString(),
                                edtUfCli.getText().toString(),
                                edtCidadeCli.getText().toString(),
                                edtBairroCli.getText().toString(),
                                edtLogradouroCli.getText().toString(),
                                edtComplementoCli.getText().toString()
                        ))){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), ConClientesActivity.class);
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