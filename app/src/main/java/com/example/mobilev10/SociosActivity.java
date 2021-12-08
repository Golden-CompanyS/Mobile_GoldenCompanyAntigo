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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SociosActivity extends AppCompatActivity implements SensorEventListener {
    // Banco de Dados
    private DatabaseHelper mydb ;
    int id_to_update = 0;

    // Tabela Funcionario (sócio)
    TextView edtNomeSocio;
    TextView edtDtNascSocio;
    TextView edtCpfSocio;
    TextView edtCargoSocio;
    TextView edtSenhaSocio;
    TextView edtNumEndSocio;

    // Tabela Telefone
    TextView edtTelSocio;

    // Tabela Contato
    TextView edtEmailSocio;

    // Tabela Estado
    TextView edtUfSocio;

    // Tabela Cidade
    TextView edtCidadeSocio;

    // Tabela Bairro
    TextView edtBairroSocio;

    // Tabela Rua
    TextView edtLogradouroSocio;

    // Tabela  Endereco
    TextView edtComplementoSocio;

    Socios socio;


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

        // Banco de Dados
        edtNomeSocio = (TextView) findViewById(R.id.edtNomeSocio);
        edtDtNascSocio = (TextView) findViewById(R.id.edtDtNascSocio);
        edtCpfSocio = (TextView) findViewById(R.id.edtCpfSocio);
        edtCargoSocio = (TextView) findViewById(R.id.edtCargoSocio);
        edtSenhaSocio = (TextView) findViewById(R.id.edtSenhaSocio);
        edtTelSocio = (TextView) findViewById(R.id.edtTelSocio);
        edtEmailSocio = (TextView) findViewById(R.id.edtEmailSocio);
        edtUfSocio = (TextView) findViewById(R.id.edtUfSocio);
        edtCidadeSocio = (TextView) findViewById(R.id.edtCidadeSocio);
        edtBairroSocio = (TextView) findViewById(R.id.edtBairroSocio);
        edtLogradouroSocio = (TextView) findViewById(R.id.edtLogradouroSocio);
        edtComplementoSocio = (TextView) findViewById(R.id.edtComplementoSocio);
        edtNumEndSocio = (TextView) findViewById(R.id.edtNumEndSocio);

        mydb = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int value = extras.getInt("id");

            if (value > 0){
                // ou seja, se NÃO for para fazer um cadastro novo (visualizar)

                Cursor cursor = mydb.getDataSocios(value);
                id_to_update = value;
                cursor.moveToFirst();

                socio = new Socios();
                socio.set_nome(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_FUNC)));
                socio.set_dtnasc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DTNASC_FUNC)));
                socio.set_cpf(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CPF_FUNC)));
                socio.set_cargo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CARGO_FUNC)));
                socio.set_senha(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SENHA_FUNC)));
                socio.set_numend(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMEND_FUNC)));
                socio.set_telefone(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUM_TEL)));
                socio.set_email(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL_CNTT)));
                socio.set_estado(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UF_EST)));
                socio.set_cidade(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CID_NOME)));
                socio.set_bairro(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BAIRR_NOME)));
                socio.set_logradouro(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RUA_LOGR)));
                socio.set_complemento(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPL_END)));


                if (!cursor.isClosed()){
                    cursor.close();
                }

                Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
                btnSalvar.setVisibility(View.GONE);

                edtNomeSocio.setText(socio.get_nome());
                edtNomeSocio.setEnabled(false);
                edtNomeSocio.setFocusable(false);
                edtNomeSocio.setClickable(false);

                edtDtNascSocio.setText(socio.get_dtnasc());
                edtDtNascSocio.setEnabled(false);
                edtDtNascSocio.setFocusable(false);
                edtDtNascSocio.setClickable(false);

                edtCpfSocio.setText(socio.get_cpf());
                edtCpfSocio.setEnabled(false);
                edtCpfSocio.setFocusable(false);
                edtCpfSocio.setClickable(false);

                edtCargoSocio.setText(socio.get_cargo());
                edtCargoSocio.setEnabled(false);
                edtCargoSocio.setFocusable(false);
                edtCargoSocio.setClickable(false);

                // senha não pode ser vista por qualquer um
                edtSenhaSocio.setText(socio.get_senha());
                edtSenhaSocio.setVisibility(View.GONE);

                edtTelSocio.setText(socio.get_telefone());
                edtTelSocio.setEnabled(false);
                edtTelSocio.setFocusable(false);
                edtTelSocio.setClickable(false);

                edtEmailSocio.setText(socio.get_email());
                edtEmailSocio.setEnabled(false);
                edtEmailSocio.setFocusable(false);
                edtEmailSocio.setClickable(false);

                edtUfSocio.setText(socio.get_estado());
                edtUfSocio.setEnabled(false);
                edtUfSocio.setFocusable(false);
                edtUfSocio.setClickable(false);

                edtCidadeSocio.setText(socio.get_cidade());
                edtCidadeSocio.setEnabled(false);
                edtCidadeSocio.setFocusable(false);
                edtCidadeSocio.setClickable(false);

                edtBairroSocio.setText(socio.get_bairro());
                edtBairroSocio.setEnabled(false);
                edtBairroSocio.setFocusable(false);
                edtBairroSocio.setClickable(false);

                edtLogradouroSocio.setText(socio.get_logradouro());
                edtLogradouroSocio.setEnabled(false);
                edtLogradouroSocio.setFocusable(false);
                edtLogradouroSocio.setClickable(false);

                edtComplementoSocio.setText(socio.get_complemento());
                edtComplementoSocio.setEnabled(false);
                edtComplementoSocio.setFocusable(false);
                edtComplementoSocio.setClickable(false);

                edtNumEndSocio.setText(socio.get_numend());
                edtNumEndSocio.setEnabled(false);
                edtNumEndSocio.setFocusable(false);
                edtNumEndSocio.setClickable(false);

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

        edtNomeSocio.setEnabled(true);
        edtNomeSocio.setFocusableInTouchMode(true);
        edtNomeSocio.setClickable(true);

        edtDtNascSocio.setEnabled(true);
        edtDtNascSocio.setFocusableInTouchMode(true);
        edtDtNascSocio.setClickable(true);

        edtCpfSocio.setEnabled(true);
        edtCpfSocio.setFocusableInTouchMode(true);
        edtCpfSocio.setClickable(true);

        edtCargoSocio.setEnabled(true);
        edtCargoSocio.setFocusableInTouchMode(true);
        edtCargoSocio.setClickable(true);

        edtSenhaSocio.setEnabled(true);
        edtSenhaSocio.setFocusableInTouchMode(true);
        edtSenhaSocio.setClickable(true);

        edtTelSocio.setEnabled(true);
        edtTelSocio.setFocusableInTouchMode(true);
        edtTelSocio.setClickable(true);

        edtEmailSocio.setEnabled(true);
        edtEmailSocio.setFocusableInTouchMode(true);
        edtEmailSocio.setClickable(true);

        edtUfSocio.setEnabled(true);
        edtUfSocio.setFocusableInTouchMode(true);
        edtUfSocio.setClickable(true);

        edtCidadeSocio.setEnabled(true);
        edtCidadeSocio.setFocusableInTouchMode(true);
        edtCidadeSocio.setClickable(true);

        edtBairroSocio.setEnabled(true);
        edtBairroSocio.setFocusableInTouchMode(true);
        edtBairroSocio.setClickable(true);

        edtLogradouroSocio.setEnabled(true);
        edtLogradouroSocio.setFocusableInTouchMode(true);
        edtLogradouroSocio.setClickable(true);

        edtComplementoSocio.setEnabled(true);
        edtComplementoSocio.setFocusableInTouchMode(true);
        edtComplementoSocio.setClickable(true);

        edtNumEndSocio.setEnabled(true);
        edtNumEndSocio.setFocusableInTouchMode(true);
        edtNumEndSocio.setClickable(true);
    }

    public void excluir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.excluir_registro)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb.deleteSocio(id_to_update);
                        Toast.makeText(getApplicationContext(), R.string.delete_ok,
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ConSociosActivity.class);
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

            String nome = edtNomeSocio.getText().toString();
            String dtnasc = edtDtNascSocio.getText().toString();
            String cpf = edtCpfSocio.getText().toString();
            String cargo = edtCargoSocio.getText().toString();
            String senha = edtSenhaSocio.getText().toString();
            String numend = edtNumEndSocio.getText().toString();
            String tel = edtTelSocio.getText().toString();
            String email = edtEmailSocio.getText().toString();
            String uf = edtUfSocio.getText().toString();
            String cidade = edtCidadeSocio.getText().toString();
            String bairro = edtBairroSocio.getText().toString();
            String logradouro = edtLogradouroSocio.getText().toString();
            String complemento = edtComplementoSocio.getText().toString();

            // testar se os campos estão preenchidos
            if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(dtnasc) || TextUtils.isEmpty(cpf) ||
                    TextUtils.isEmpty(cargo) || TextUtils.isEmpty(senha) || TextUtils.isEmpty(numend) ||
                    TextUtils.isEmpty(tel) || TextUtils.isEmpty(email) || TextUtils.isEmpty(uf) ||
                    TextUtils.isEmpty(cidade) || TextUtils.isEmpty(bairro) || TextUtils.isEmpty(logradouro) ||
                    TextUtils.isEmpty(complemento)){

                Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show();
            } else {
                if (value > 0){
                    if(mydb.updateSocio(
                            new Socios(
                                    id_to_update,
                                    nome,
                                    dtnasc,
                                    cpf,
                                    cargo,
                                    senha,
                                    numend,
                                    tel,
                                    email,
                                    uf,
                                    cidade,
                                    bairro,
                                    logradouro,
                                    complemento
                            ))){
                        Toast.makeText(getApplicationContext(), "Atualizado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ConSociosActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(getApplicationContext(), "Não Atualizado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(mydb.insertSocio(
                            new Socios(
                                    edtNomeSocio.getText().toString(),
                                    edtDtNascSocio.getText().toString(),
                                    edtCpfSocio.getText().toString(),
                                    edtCargoSocio.getText().toString(),
                                    edtSenhaSocio.getText().toString(),
                                    edtNumEndSocio.getText().toString(),
                                    edtTelSocio.getText().toString(),
                                    edtEmailSocio.getText().toString(),
                                    edtUfSocio.getText().toString(),
                                    edtCidadeSocio.getText().toString(),
                                    edtBairroSocio.getText().toString(),
                                    edtLogradouroSocio.getText().toString(),
                                    edtComplementoSocio.getText().toString()
                            ))){
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getApplicationContext(), ConSociosActivity.class);
                    startActivity(intent);
                }
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