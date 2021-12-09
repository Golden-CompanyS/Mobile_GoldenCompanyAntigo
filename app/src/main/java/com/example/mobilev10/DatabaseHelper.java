package com.example.mobilev10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.mobilev10.Servicos;

import java.util.ArrayList;
import java.util.Date;

import static java.sql.Types.INTEGER;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbMobile.db";

    //TABELA SERVICO
    public static final String SERVICO_TABLE_NAME = "tbServico";
    public static final String COLUMN_ID_SERV = "IdServico";
    public static final String COLUMN_DESC_SERV = "descServ";

    //TABELA ESTADO
    public static final String ESTADO_TABLE_NAME = "tbEstado";
    public static final String COLUMN_ID_EST = "IdUF";
    public static final String COLUMN_UF_EST = "UF";

    //TABELA CIDADE
    public static final String CIDADE_TABLE_NAME = "tbCidade";
    public static final String COLUMN_ID_CID = "IdCidade";
    public static final String COLUMN_CID_NOME = "NomeCidade";

    //TABELA BAIRRO
    public static final String BAIRRO_TABLE_NAME = "tbBairro";
    public static final String COLUMN_ID_BAIRR ="IdBairro";
    public static final String COLUMN_BAIRR_NOME = "NomeBairro";

    //TABELA RUA
    public static final String RUA_TABLE_NAME = "tbRua";
    public static final String COLUMN_ID_RUA ="IdRua";
    public static final String COLUMN_RUA_LOGR = "Logradouro";

    //TABELA ENDERECO
    public static final String ENDERECO_TABLE_NAME = "tbEndereco";
    public static final String COLUMN_ID_END = "IdEndereco";
    public static final String COLUMN_COMPL_END = "Complemento";

    //TABELA TELEFONE
    public static final String TELEFONE_TABLE_NAME = "tbTelefone";
    public static final String COLUMN_ID_TEL = "IdTelefone";
    public static final String COLUMN_NUM_TEL = "Telefone";

    //TABELA CONTATO
    public static final String CONTATO_TABLE_NAME = "tbContato";
    public static final String COLUMN_ID_CNTT = "IdContato";
    public static final String COLUMN_EMAIL_CNTT = "Email";

    // TABELA FUNCIONARIO
    public static final String FUNCIONARIO_TABLE_NAME = "tbFuncionario";
    public static final String COLUMN_ID_FUNC= "IdFunc";
    public static final String COLUMN_NAME_FUNC = "NomeFunc";
    public static final String COLUMN_DTNASC_FUNC = "DtNasc";
    public static final String COLUMN_CPF_FUNC = "CPF";
    public static final String COLUMN_CARGO_FUNC = "Cargo";
    public static final String COLUMN_SENHA_FUNC = "Senha";
    public static final String COLUMN_NUMEND_FUNC = "NumEnd";

    // TABELA CLIENTE
    public static final String CLIENTE_TABLE_NAME = "tbCliente";
    public static final String COLUMN_ID_CLI = "IdCliente";
    public static final String COLUMN_NAME_CLI = "NomeCliente";
    public static final String COLUMN_CNPJ_CLI = "CNPJ";
    public static final String COLUMN_NUMEND_CLI = "Senha";

    //TABELA ATIVIDADE
    public static final String ATIVIDADE_TABLE_NAME = "tbAtividade";
    public static final String COLUMN_ID_ATV = "IdAtividade";
    public static final String COLUMN_DESC_ATV = "descricaoAtv";
    public static final String COLUMN_DTINICIO_ATV = "dtInicio";
    public static final String COLUMN_DTFIM_ATV = "dtFim";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_SERVICO_TABLE = "CREATE TABLE " + SERVICO_TABLE_NAME + "("
                + COLUMN_ID_SERV + " INTEGER PRIMARY KEY,"
                + COLUMN_DESC_SERV + " TEXT UNIQUE"
                + ")";

        String CREATE_ESTADO_TABLE = "CREATE TABLE " + ESTADO_TABLE_NAME + "("
                + COLUMN_ID_EST + " INTEGER PRIMARY KEY,"
                + COLUMN_UF_EST + " TEXT(2) UNIQUE"
                + ")";

        String CREATE_CIDADE_TABLE = "CREATE TABLE " + CIDADE_TABLE_NAME + "("
                + COLUMN_ID_CID + " INTEGER PRIMARY KEY,"
                + COLUMN_CID_NOME + " TEXT UNIQUE"
                + ")";

        String CREATE_BAIRRO_TABLE = "CREATE TABLE " + BAIRRO_TABLE_NAME + "("
                + COLUMN_ID_BAIRR + " INTEGER PRIMARY KEY,"
                + COLUMN_BAIRR_NOME + " TEXT UNIQUE"
                + ")";

        String CREATE_RUA_TABLE = "CREATE TABLE " + RUA_TABLE_NAME + "("
                + COLUMN_ID_RUA + " INTEGER PRIMARY KEY,"
                + COLUMN_RUA_LOGR + " TEXT UNIQUE"
                + ")";

        String CREATE_ENDERECO_TABLE = "CREATE TABLE " + ENDERECO_TABLE_NAME + "("
                + COLUMN_ID_END + " INTEGER PRIMARY KEY,"
                + COLUMN_ID_EST + " INTEGER,"
                + COLUMN_ID_CID + " INTEGER,"
                + COLUMN_ID_BAIRR + " INTEGER,"
                + COLUMN_ID_RUA + " INTEGER,"
                + COLUMN_COMPL_END + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_ID_EST + ") REFERENCES " + ESTADO_TABLE_NAME + "(" + COLUMN_ID_EST + "),"
                + "FOREIGN KEY (" + COLUMN_ID_CID + ") REFERENCES " + CIDADE_TABLE_NAME + "(" + COLUMN_ID_CID + "),"
                + "FOREIGN KEY (" + COLUMN_ID_BAIRR + ") REFERENCES " + BAIRRO_TABLE_NAME + "(" + COLUMN_ID_BAIRR + "),"
                + "FOREIGN KEY (" + COLUMN_ID_RUA + ") REFERENCES " + RUA_TABLE_NAME + "(" + COLUMN_ID_RUA + ")"
                + ")";

        String CREATE_TELEFONE_TABLE = "CREATE TABLE " + TELEFONE_TABLE_NAME + "("
                + COLUMN_ID_TEL + " INTEGER PRIMARY KEY,"
                + COLUMN_NUM_TEL + " TEXT UNIQUE"
                + ")";

        String CREATE_CONTATO_TABLE = "CREATE TABLE " + CONTATO_TABLE_NAME + "("
                + COLUMN_ID_CNTT + " INTEGER PRIMARY KEY,"
                + COLUMN_ID_END + " INTEGER,"
                + COLUMN_ID_TEL + " INTEGER,"
                + COLUMN_EMAIL_CNTT + " TEXT UNIQUE,"
                + "FOREIGN KEY (" + COLUMN_ID_END + ") REFERENCES " + ENDERECO_TABLE_NAME + "(" + COLUMN_ID_END + "),"
                + "FOREIGN KEY (" + COLUMN_ID_TEL + ") REFERENCES " + TELEFONE_TABLE_NAME + "(" + COLUMN_ID_TEL + ")"
                + ")";

        String CREATE_FUNCIONARIO_TABLE = "CREATE TABLE " + FUNCIONARIO_TABLE_NAME + "("
                + COLUMN_ID_FUNC + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_FUNC + " TEXT,"
                + COLUMN_DTNASC_FUNC + " DATE,"
                + COLUMN_CPF_FUNC + " TEXT UNIQUE,"
                + COLUMN_ID_CNTT + " INTEGER,"
                + COLUMN_CARGO_FUNC + " TEXT,"
                + COLUMN_SENHA_FUNC + " TEXT,"
                + COLUMN_NUMEND_FUNC + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_ID_CNTT + ") REFERENCES " + CONTATO_TABLE_NAME + " (" + COLUMN_ID_CNTT + ")"
                + ")";

        String CREATE_CLIENTE_TABLE = "CREATE TABLE " + CLIENTE_TABLE_NAME + "("
                + COLUMN_ID_CLI + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_CLI + " TEXT,"
                + COLUMN_CNPJ_CLI + " TEXT UNIQUE,"
                + COLUMN_ID_CNTT + " INTEGER,"
                + COLUMN_NUMEND_CLI + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_ID_CNTT + ") REFERENCES "+ CONTATO_TABLE_NAME +" (" + COLUMN_ID_CNTT + ")"
                + ")";

        String CREATE_ATIVIDADE_TABLE = "CREATE TABLE " + ATIVIDADE_TABLE_NAME + "("
                + COLUMN_ID_ATV + " INTEGER PRIMARY KEY,"
                + COLUMN_DESC_ATV + " TEXT,"
                + COLUMN_DTINICIO_ATV + " DATE,"
                + COLUMN_DTFIM_ATV + " DATE,"
                + COLUMN_ID_FUNC + " INTEGER,"
                + COLUMN_ID_SERV + " INTEGER,"
                + COLUMN_CNPJ_CLI + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_ID_FUNC + ") REFERENCES " + FUNCIONARIO_TABLE_NAME + " (" + COLUMN_ID_FUNC + "),"
                + "FOREIGN KEY (" + COLUMN_CNPJ_CLI + ") REFERENCES " + CLIENTE_TABLE_NAME + " (" + COLUMN_CNPJ_CLI + "),"
                + "FOREIGN KEY (" + COLUMN_ID_SERV + ") REFERENCES " + SERVICO_TABLE_NAME + " (" + COLUMN_ID_SERV + ")"
                +")";

        db.execSQL(CREATE_SERVICO_TABLE);
        db.execSQL(CREATE_ESTADO_TABLE);
        db.execSQL(CREATE_CIDADE_TABLE);
        db.execSQL(CREATE_BAIRRO_TABLE);
        db.execSQL(CREATE_RUA_TABLE);
        db.execSQL(CREATE_ENDERECO_TABLE);
        db.execSQL(CREATE_TELEFONE_TABLE);
        db.execSQL(CREATE_CONTATO_TABLE);
        db.execSQL(CREATE_FUNCIONARIO_TABLE);
        db.execSQL(CREATE_CLIENTE_TABLE);
        db.execSQL(CREATE_ATIVIDADE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + SERVICO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ESTADO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CIDADE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BAIRRO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RUA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ENDERECO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TELEFONE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CONTATO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FUNCIONARIO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CLIENTE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ATIVIDADE_TABLE_NAME);
        onCreate(db);
    }

    // Operações Login
    public Boolean validarLogin(String email, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CONTATO_TABLE_NAME + " AS c"
                + " INNER JOIN " + FUNCIONARIO_TABLE_NAME + " AS f ON c." + COLUMN_ID_CNTT + " = f." + COLUMN_ID_CNTT
                + " WHERE c." + COLUMN_EMAIL_CNTT + " = '" + email + "' AND f." + COLUMN_SENHA_FUNC + " = '" + senha + "'", null);

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public int getFuncIdSession(String email, String senha){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT f." + COLUMN_ID_FUNC + " FROM " + CONTATO_TABLE_NAME + " AS c"
                + " INNER JOIN " + FUNCIONARIO_TABLE_NAME + " AS f ON c." + COLUMN_ID_CNTT + " = f." + COLUMN_ID_CNTT
                + " WHERE c." + COLUMN_EMAIL_CNTT + " = '" + email + "' AND f." + COLUMN_SENHA_FUNC + " = '" + senha + "'", null);
        cursor.moveToFirst();
        int func_id_session = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_FUNC));

        if (cursor.getCount() > 0){
            return func_id_session;
        } else {
            return 0;
        }
    }

    public boolean checarSenha(int id, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + FUNCIONARIO_TABLE_NAME
                + " WHERE " + COLUMN_ID_FUNC + " = " + id
                + " AND " + COLUMN_SENHA_FUNC + " = '" + senha + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateSenha(int id, String senhaNova){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SENHA_FUNC, senhaNova);

        db.update(FUNCIONARIO_TABLE_NAME, contentValues,
                COLUMN_ID_FUNC + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    // Operações Serviços
    public boolean insertServico(Servicos serv) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT OR IGNORE INTO " + SERVICO_TABLE_NAME
                + "("
                + COLUMN_DESC_SERV
                + ") VALUES('"
                + serv.get_desc()
                + "')"
        );

        return true;
    }

    public Cursor getDataServicos(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM "+ SERVICO_TABLE_NAME + " WHERE " + COLUMN_ID_SERV + " = " + id, null);
        return cursor;
    }

    public boolean updateServico(Servicos serv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESC_SERV, serv.get_desc());

        db.update(SERVICO_TABLE_NAME, contentValues,
                COLUMN_ID_SERV + " = ? ", new String[] { Integer.toString(serv.get_id()) } );
        return true;
    }

    public Integer deleteServico(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SERVICO_TABLE_NAME,
                COLUMN_ID_SERV + " = ? ", new String[] { Integer.toString(id) } );
    }

    public ArrayList<String> getAllServicos() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + SERVICO_TABLE_NAME, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            array_list.add(cursor.getString(cursor.getColumnIndex(COLUMN_DESC_SERV)));
            cursor.moveToNext();
        }
        return array_list;
    }

    // Operações Clientes
    public boolean insertClientes(Clientes cliente) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT OR IGNORE INTO " + ESTADO_TABLE_NAME
                + "("
                + COLUMN_UF_EST
                + ") VALUES('"
                + cliente.get_estado()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + CIDADE_TABLE_NAME
                + "("
                + COLUMN_CID_NOME
                + ") VALUES('"
                + cliente.get_cidade()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + BAIRRO_TABLE_NAME
                + "("
                + COLUMN_BAIRR_NOME
                + ") VALUES('"
                + cliente.get_bairro()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + RUA_TABLE_NAME
                + "("
                + COLUMN_RUA_LOGR
                + ") VALUES('"
                + cliente.get_logradouro()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + ENDERECO_TABLE_NAME
                + "("
                + COLUMN_ID_EST + ","
                + COLUMN_ID_CID + ","
                + COLUMN_ID_BAIRR + ","
                + COLUMN_ID_RUA + ","
                + COLUMN_COMPL_END
                + ") VALUES("
                + "(SELECT " + COLUMN_ID_EST + " FROM " + ESTADO_TABLE_NAME
                + " WHERE " + COLUMN_UF_EST + " = '" + cliente.get_estado() + "'),"
                + "(SELECT " + COLUMN_ID_CID + " FROM " + CIDADE_TABLE_NAME
                + " WHERE " + COLUMN_CID_NOME + " = '" + cliente.get_cidade() + "'),"
                + "(SELECT " + COLUMN_ID_BAIRR + " FROM " + BAIRRO_TABLE_NAME
                + " WHERE " + COLUMN_BAIRR_NOME + " = '" + cliente.get_bairro() + "'),"
                + "(SELECT " + COLUMN_ID_RUA + " FROM " + RUA_TABLE_NAME
                + " WHERE " + COLUMN_RUA_LOGR + " = '" + cliente.get_logradouro() + "'),'"
                + cliente.get_complemento()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + TELEFONE_TABLE_NAME
                + "("
                + COLUMN_NUM_TEL
                + ") VALUES('"
                + cliente.get_telefone()
                + "')"
        );

        db.execSQL("INSERT INTO " + CONTATO_TABLE_NAME
                + "("
                + COLUMN_ID_END + ","
                + COLUMN_ID_TEL + ","
                + COLUMN_EMAIL_CNTT
                + ") VALUES("
                + "(SELECT " + COLUMN_ID_END + " FROM " + ENDERECO_TABLE_NAME + " ORDER BY " + COLUMN_ID_END + " DESC LIMIT 1),"
                + "(SELECT " + COLUMN_ID_TEL + " FROM " + TELEFONE_TABLE_NAME
                + " WHERE " + COLUMN_NUM_TEL + " = '" + cliente.get_telefone() + "'),'"
                + cliente.get_email()
                + "')"
        );

        db.execSQL("INSERT INTO " + CLIENTE_TABLE_NAME
                + "("
                + COLUMN_NAME_CLI + ","
                + COLUMN_CNPJ_CLI + ","
                + COLUMN_ID_CNTT + ","
                + COLUMN_NUMEND_CLI
                + ") VALUES('"
                + cliente.get_nome() + "','"
                + cliente.get_cnpj() + "',"
                + "(SELECT " + COLUMN_ID_CNTT + " FROM " + CONTATO_TABLE_NAME + " ORDER BY " + COLUMN_ID_CNTT + " DESC LIMIT 1),"
                + cliente.get_numend()
                + ")"
        );

        return true;
    }

    public Cursor getDataClientes(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + CLIENTE_TABLE_NAME + " AS cli"
                + " INNER JOIN " + CONTATO_TABLE_NAME + " AS c ON cli." + COLUMN_ID_CNTT + " = c." + COLUMN_ID_CNTT
                + " INNER JOIN " + TELEFONE_TABLE_NAME + " AS t ON c." + COLUMN_ID_TEL + " = t." + COLUMN_ID_TEL
                + " INNER JOIN " + ENDERECO_TABLE_NAME + " AS e ON c." + COLUMN_ID_END + " = e." + COLUMN_ID_END
                + " INNER JOIN " + ESTADO_TABLE_NAME + " AS u ON e." + COLUMN_ID_EST + " = u." + COLUMN_ID_EST
                + " INNER JOIN " + CIDADE_TABLE_NAME + " AS ci ON e." + COLUMN_ID_CID + " = ci." + COLUMN_ID_CID
                + " INNER JOIN " + BAIRRO_TABLE_NAME + " AS b ON e." + COLUMN_ID_BAIRR + " = b." + COLUMN_ID_BAIRR
                + " INNER JOIN " + RUA_TABLE_NAME + " AS r ON e." + COLUMN_ID_RUA + " = r." + COLUMN_ID_RUA
                + " WHERE " + COLUMN_ID_CLI + " = " + id, null);
        return cursor;
    }

    public boolean updateCliente(Clientes clientes) {
        SQLiteDatabase db = this.getWritableDatabase();

        // update na tbCliente
        ContentValues contentValuesFunc = new ContentValues();
        contentValuesFunc.put(COLUMN_NAME_CLI, clientes.get_nome());
        contentValuesFunc.put(COLUMN_CNPJ_CLI, clientes.get_cnpj());
        contentValuesFunc.put(COLUMN_NUMEND_CLI, clientes.get_numend());
        db.update(CLIENTE_TABLE_NAME, contentValuesFunc,
                COLUMN_ID_CLI + " = ? ", new String[] { Integer.toString(clientes.get_id()) } );

        // pegar ID da tbContato
        Cursor cursor_id_cntt = db.rawQuery("SELECT " + COLUMN_ID_CNTT + " FROM " + CLIENTE_TABLE_NAME
                + " WHERE " + COLUMN_ID_CLI + " = " + clientes.get_id(), null);
        cursor_id_cntt.moveToNext();
        int id_cntt = cursor_id_cntt.getInt(cursor_id_cntt.getColumnIndex(COLUMN_ID_CNTT));

        // pegar ID da tbTelefone
        Cursor cursor_id_tel = db.rawQuery("SELECT " + COLUMN_ID_TEL + " FROM " + CONTATO_TABLE_NAME
                + " WHERE " + COLUMN_ID_CNTT + " = " + id_cntt, null);
        cursor_id_tel.moveToNext();
        int id_tel = cursor_id_tel.getInt(cursor_id_tel.getColumnIndex(COLUMN_ID_TEL));

        // update na tbTelefone
        ContentValues contentValuesTel = new ContentValues();
        contentValuesTel.put(COLUMN_NUM_TEL, clientes.get_telefone());
        db.update(TELEFONE_TABLE_NAME, contentValuesTel,
                COLUMN_ID_TEL + " = ? ", new String[] { Integer.toString(id_tel) } );

        // insert UF caso não exista
        db.execSQL("INSERT OR IGNORE INTO " + ESTADO_TABLE_NAME
                + "("
                + COLUMN_UF_EST
                + ") VALUES('"
                + clientes.get_estado()
                + "')"
        );

        // insert CIDADE caso não exista
        db.execSQL("INSERT OR IGNORE INTO " + CIDADE_TABLE_NAME
                + "("
                + COLUMN_CID_NOME
                + ") VALUES('"
                + clientes.get_cidade()
                + "')"
        );

        // insert BAIRRO caso não exista
        db.execSQL("INSERT OR IGNORE INTO " + BAIRRO_TABLE_NAME
                + "("
                + COLUMN_BAIRR_NOME
                + ") VALUES('"
                + clientes.get_bairro()
                + "')"
        );

        // insert LOGRADOURO caso não exista
        db.execSQL("INSERT OR IGNORE INTO " + RUA_TABLE_NAME
                + "("
                + COLUMN_RUA_LOGR
                + ") VALUES('"
                + clientes.get_logradouro()
                + "')"
        );

        // insert ENDERECO caso não exista
        db.execSQL("INSERT INTO " + ENDERECO_TABLE_NAME
                + "("
                + COLUMN_ID_EST + ","
                + COLUMN_ID_CID + ","
                + COLUMN_ID_BAIRR + ","
                + COLUMN_ID_RUA + ","
                + COLUMN_COMPL_END
                + ") VALUES("
                + "(SELECT " + COLUMN_ID_EST + " FROM " + ESTADO_TABLE_NAME
                + " WHERE " + COLUMN_UF_EST + " = '" + clientes.get_estado() + "'),"
                + "(SELECT " + COLUMN_ID_CID + " FROM " + CIDADE_TABLE_NAME
                + " WHERE " + COLUMN_CID_NOME + " = '" + clientes.get_cidade() + "'),"
                + "(SELECT " + COLUMN_ID_BAIRR + " FROM " + BAIRRO_TABLE_NAME
                + " WHERE " + COLUMN_BAIRR_NOME + " = '" + clientes.get_bairro() + "'),"
                + "(SELECT " + COLUMN_ID_RUA + " FROM " + RUA_TABLE_NAME
                + " WHERE " + COLUMN_RUA_LOGR + " = '" + clientes.get_logradouro() + "'),'"
                + clientes.get_complemento()
                + "')"
        );

        // pegar ID do recém adicionado endereco
        Cursor cursor_id_end = db.rawQuery("SELECT " + COLUMN_ID_END + " FROM " + ENDERECO_TABLE_NAME
                + " ORDER BY " + COLUMN_ID_END + " DESC LIMIT 1", null);
        cursor_id_end.moveToNext();
        int id_end = cursor_id_end.getInt(cursor_id_end.getColumnIndex(COLUMN_ID_END));

        // update na tbContato
        ContentValues contentValuesCntt = new ContentValues();
        contentValuesCntt.put(COLUMN_ID_END, id_end);
        contentValuesCntt.put(COLUMN_EMAIL_CNTT, clientes.get_email());
        db.update(CONTATO_TABLE_NAME, contentValuesCntt,
                COLUMN_ID_CNTT + " = ? ", new String[] { Integer.toString(id_cntt) } );

        return true;
    }

    public boolean deleteCliente(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_id_cntt = db.rawQuery("SELECT " + COLUMN_ID_CNTT + " FROM " + CLIENTE_TABLE_NAME
                + " WHERE " + COLUMN_ID_CLI + " = " + id, null);
        cursor_id_cntt.moveToNext();
        int id_cntt = cursor_id_cntt.getInt(cursor_id_cntt.getColumnIndex(COLUMN_ID_CNTT));

        db.execSQL("DELETE FROM " + CLIENTE_TABLE_NAME
                + " WHERE " + COLUMN_ID_CLI + " = " + id);
        db.execSQL("DELETE FROM " + CONTATO_TABLE_NAME
                + " WHERE " + COLUMN_ID_CNTT + " = " + id_cntt);
        return true;
    }

    public ArrayList<String> getAllClientes() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT " + COLUMN_NAME_CLI + " FROM " + CLIENTE_TABLE_NAME, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            array_list.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CLI)));
            cursor.moveToNext();
        }
        return array_list;
    }


    // Operações Atividades
    public boolean insertAtividade(Atividades atividades) {
        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("INSERT INTO " + ATIVIDADE_TABLE_NAME
                + "("
                + COLUMN_DESC_ATV + ","
                + COLUMN_DTINICIO_ATV + ","
                + COLUMN_DTFIM_ATV + ","
                + COLUMN_ID_FUNC + ","
                + COLUMN_CNPJ_CLI + ","
                + COLUMN_ID_SERV
                + ") VALUES('"
                + atividades.get_desc() + "','"
                + atividades.get_dtInicio() + "','"
                + atividades.get_dtFim() + "',"
                +"(SELECT " + COLUMN_ID_FUNC + " FROM " + FUNCIONARIO_TABLE_NAME
                + " WHERE " + COLUMN_ID_FUNC + " = '" + atividades.get_func() + "'),"
                + "(SELECT " + COLUMN_CNPJ_CLI + " FROM " + CLIENTE_TABLE_NAME
                + " WHERE " + COLUMN_CNPJ_CLI + " = '" + atividades.get_cnpj() + "'),"
                + "(SELECT " + COLUMN_ID_SERV + " FROM " + SERVICO_TABLE_NAME
                + " WHERE " + COLUMN_ID_SERV + " = '" + atividades.get_serv() + "')"
                + ")"
        );

        return true;
    }

    public Cursor getDataAtividades(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + ATIVIDADE_TABLE_NAME
                + " WHERE " + COLUMN_ID_ATV + " = " + id, null);
        return cursor;
    }

    public boolean updateAtividade(Atividades atividades) {
        SQLiteDatabase db = this.getWritableDatabase();

        // update na tbFuncionario
        ContentValues contentValuesAtv = new ContentValues();
        contentValuesAtv.put(COLUMN_DESC_ATV, atividades.get_desc());
        contentValuesAtv.put(COLUMN_DTINICIO_ATV, atividades.get_dtInicio());
        contentValuesAtv.put(COLUMN_DTFIM_ATV, atividades.get_dtFim());
        contentValuesAtv.put(COLUMN_ID_FUNC, atividades.get_func());
        contentValuesAtv.put(COLUMN_CNPJ_CLI, atividades.get_cnpj());
        contentValuesAtv.put(COLUMN_ID_SERV, atividades.get_serv());

        db.update(ATIVIDADE_TABLE_NAME, contentValuesAtv,
                COLUMN_ID_ATV + " = ? ", new String[] { Integer.toString(atividades.get_id()) } );
        return true;
    }

    public boolean deleteAtividade(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_id_cntt = db.rawQuery("SELECT " + COLUMN_ID_ATV + " FROM " + ATIVIDADE_TABLE_NAME
                + " WHERE " + COLUMN_ID_ATV + " = " + id, null);
        cursor_id_cntt.moveToNext();
        int id_cntt = cursor_id_cntt.getInt(cursor_id_cntt.getColumnIndex(COLUMN_ID_ATV));

        db.execSQL("DELETE FROM " + ATIVIDADE_TABLE_NAME
                + " WHERE " + COLUMN_ID_ATV + " = " + id);

        return true;
    }

    public ArrayList<String> getAllAtividades() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT " + COLUMN_DESC_ATV + " FROM " + ATIVIDADE_TABLE_NAME, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            array_list.add(cursor.getString(cursor.getColumnIndex(COLUMN_DESC_ATV)));
            cursor.moveToNext();
        }
        return array_list;
    }

    // Operações Sócios
    public boolean insertSocio(Socios socio) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT OR IGNORE INTO " + ESTADO_TABLE_NAME
                + "("
                + COLUMN_UF_EST
                + ") VALUES('"
                + socio.get_estado()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + CIDADE_TABLE_NAME
                + "("
                + COLUMN_CID_NOME
                + ") VALUES('"
                + socio.get_cidade()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + BAIRRO_TABLE_NAME
                + "("
                + COLUMN_BAIRR_NOME
                + ") VALUES('"
                + socio.get_bairro()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + RUA_TABLE_NAME
                + "("
                + COLUMN_RUA_LOGR
                + ") VALUES('"
                + socio.get_logradouro()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + ENDERECO_TABLE_NAME
                + "("
                + COLUMN_ID_EST + ","
                + COLUMN_ID_CID + ","
                + COLUMN_ID_BAIRR + ","
                + COLUMN_ID_RUA + ","
                + COLUMN_COMPL_END
                + ") VALUES("
                + "(SELECT " + COLUMN_ID_EST + " FROM " + ESTADO_TABLE_NAME
                + " WHERE " + COLUMN_UF_EST + " = '" + socio.get_estado() + "'),"
                + "(SELECT " + COLUMN_ID_CID + " FROM " + CIDADE_TABLE_NAME
                + " WHERE " + COLUMN_CID_NOME + " = '" + socio.get_cidade() + "'),"
                + "(SELECT " + COLUMN_ID_BAIRR + " FROM " + BAIRRO_TABLE_NAME
                + " WHERE " + COLUMN_BAIRR_NOME + " = '" + socio.get_bairro() + "'),"
                + "(SELECT " + COLUMN_ID_RUA + " FROM " + RUA_TABLE_NAME
                + " WHERE " + COLUMN_RUA_LOGR + " = '" + socio.get_logradouro() + "'),'"
                + socio.get_complemento()
                + "')"
        );

        db.execSQL("INSERT OR IGNORE INTO " + TELEFONE_TABLE_NAME
                + "("
                + COLUMN_NUM_TEL
                + ") VALUES('"
                + socio.get_telefone()
                + "')"
        );

        db.execSQL("INSERT INTO " + CONTATO_TABLE_NAME
                + "("
                + COLUMN_ID_END + ","
                + COLUMN_ID_TEL + ","
                + COLUMN_EMAIL_CNTT
                + ") VALUES("
                + "(SELECT " + COLUMN_ID_END + " FROM " + ENDERECO_TABLE_NAME + " ORDER BY " + COLUMN_ID_END + " DESC LIMIT 1),"
                + "(SELECT " + COLUMN_ID_TEL + " FROM " + TELEFONE_TABLE_NAME
                + " WHERE " + COLUMN_NUM_TEL + " = '" + socio.get_telefone() + "'),'"
                + socio.get_email()
                + "')"
        );

        db.execSQL("INSERT INTO " + FUNCIONARIO_TABLE_NAME
                + "("
                + COLUMN_NAME_FUNC + ","
                + COLUMN_DTNASC_FUNC + ","
                + COLUMN_CPF_FUNC + ","
                + COLUMN_ID_CNTT + ","
                + COLUMN_CARGO_FUNC + ","
                + COLUMN_SENHA_FUNC + ","
                + COLUMN_NUMEND_FUNC
                + ") VALUES('"
                + socio.get_nome() + "','"
                + socio.get_dtnasc() + "','"
                + socio.get_cpf() + "',"
                + "(SELECT " + COLUMN_ID_CNTT + " FROM " + CONTATO_TABLE_NAME + " ORDER BY " + COLUMN_ID_CNTT + " DESC LIMIT 1),'"
                + socio.get_cargo() + "','"
                + socio.get_senha() + "',"
                + socio.get_numend()
                + ")"
        );

        return true;
    }

    public Cursor getDataSocios(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + FUNCIONARIO_TABLE_NAME + " AS f"
                + " INNER JOIN " + CONTATO_TABLE_NAME + " AS c ON f." + COLUMN_ID_CNTT + " = c." + COLUMN_ID_CNTT
                + " INNER JOIN " + TELEFONE_TABLE_NAME + " AS t ON c." + COLUMN_ID_TEL + " = t." + COLUMN_ID_TEL
                + " INNER JOIN " + ENDERECO_TABLE_NAME + " AS e ON c." + COLUMN_ID_END + " = e." + COLUMN_ID_END
                + " INNER JOIN " + ESTADO_TABLE_NAME + " AS u ON e." + COLUMN_ID_EST + " = u." + COLUMN_ID_EST
                + " INNER JOIN " + CIDADE_TABLE_NAME + " AS ci ON e." + COLUMN_ID_CID + " = ci." + COLUMN_ID_CID
                + " INNER JOIN " + BAIRRO_TABLE_NAME + " AS b ON e." + COLUMN_ID_BAIRR + " = b." + COLUMN_ID_BAIRR
                + " INNER JOIN " + RUA_TABLE_NAME + " AS r ON e." + COLUMN_ID_RUA + " = r." + COLUMN_ID_RUA
                + " WHERE " + COLUMN_ID_FUNC + " = " + id, null);
        return cursor;
    }

    public boolean updateSocio(Socios socio) {
        SQLiteDatabase db = this.getWritableDatabase();

        // update na tbFuncionario
        ContentValues contentValuesFunc = new ContentValues();
        contentValuesFunc.put(COLUMN_NAME_FUNC, socio.get_nome());
        contentValuesFunc.put(COLUMN_DTNASC_FUNC, socio.get_dtnasc());
        contentValuesFunc.put(COLUMN_CPF_FUNC, socio.get_cpf());
        contentValuesFunc.put(COLUMN_CARGO_FUNC, socio.get_cargo());
        contentValuesFunc.put(COLUMN_SENHA_FUNC, socio.get_senha());
        contentValuesFunc.put(COLUMN_NUMEND_FUNC, socio.get_numend());
        db.update(FUNCIONARIO_TABLE_NAME, contentValuesFunc,
                COLUMN_ID_FUNC + " = ? ", new String[] { Integer.toString(socio.get_id()) } );

        // pegar ID da tbContato
        Cursor cursor_id_cntt = db.rawQuery("SELECT " + COLUMN_ID_CNTT + " FROM " + FUNCIONARIO_TABLE_NAME
                + " WHERE " + COLUMN_ID_FUNC + " = " + socio.get_id(), null);
        cursor_id_cntt.moveToNext();
        int id_cntt = cursor_id_cntt.getInt(cursor_id_cntt.getColumnIndex(COLUMN_ID_CNTT));

        // pegar ID da tbTelefone
        Cursor cursor_id_tel = db.rawQuery("SELECT " + COLUMN_ID_TEL + " FROM " + CONTATO_TABLE_NAME
                + " WHERE " + COLUMN_ID_CNTT + " = " + id_cntt, null);
        cursor_id_tel.moveToNext();
        int id_tel = cursor_id_tel.getInt(cursor_id_tel.getColumnIndex(COLUMN_ID_TEL));

        // update na tbTelefone
        ContentValues contentValuesTel = new ContentValues();
        contentValuesTel.put(COLUMN_NUM_TEL, socio.get_telefone());
        db.update(TELEFONE_TABLE_NAME, contentValuesTel,
                COLUMN_ID_TEL + " = ? ", new String[] { Integer.toString(id_tel) } );

        // insert UF caso não exista
        db.execSQL("INSERT OR IGNORE INTO " + ESTADO_TABLE_NAME
                + "("
                + COLUMN_UF_EST
                + ") VALUES('"
                + socio.get_estado()
                + "')"
        );

        // insert CIDADE caso não exista
        db.execSQL("INSERT OR IGNORE INTO " + CIDADE_TABLE_NAME
                + "("
                + COLUMN_CID_NOME
                + ") VALUES('"
                + socio.get_cidade()
                + "')"
        );

        // insert BAIRRO caso não exista
        db.execSQL("INSERT OR IGNORE INTO " + BAIRRO_TABLE_NAME
                + "("
                + COLUMN_BAIRR_NOME
                + ") VALUES('"
                + socio.get_bairro()
                + "')"
        );

        // insert LOGRADOURO caso não exista
        db.execSQL("INSERT OR IGNORE INTO " + RUA_TABLE_NAME
                + "("
                + COLUMN_RUA_LOGR
                + ") VALUES('"
                + socio.get_logradouro()
                + "')"
        );

        // insert ENDERECO caso não exista
        db.execSQL("INSERT INTO " + ENDERECO_TABLE_NAME
                + "("
                + COLUMN_ID_EST + ","
                + COLUMN_ID_CID + ","
                + COLUMN_ID_BAIRR + ","
                + COLUMN_ID_RUA + ","
                + COLUMN_COMPL_END
                + ") VALUES("
                + "(SELECT " + COLUMN_ID_EST + " FROM " + ESTADO_TABLE_NAME
                + " WHERE " + COLUMN_UF_EST + " = '" + socio.get_estado() + "'),"
                + "(SELECT " + COLUMN_ID_CID + " FROM " + CIDADE_TABLE_NAME
                + " WHERE " + COLUMN_CID_NOME + " = '" + socio.get_cidade() + "'),"
                + "(SELECT " + COLUMN_ID_BAIRR + " FROM " + BAIRRO_TABLE_NAME
                + " WHERE " + COLUMN_BAIRR_NOME + " = '" + socio.get_bairro() + "'),"
                + "(SELECT " + COLUMN_ID_RUA + " FROM " + RUA_TABLE_NAME
                + " WHERE " + COLUMN_RUA_LOGR + " = '" + socio.get_logradouro() + "'),'"
                + socio.get_complemento()
                + "')"
        );

        // pegar ID do recém adicionado endereco
        Cursor cursor_id_end = db.rawQuery("SELECT " + COLUMN_ID_END + " FROM " + ENDERECO_TABLE_NAME
                + " ORDER BY " + COLUMN_ID_END + " DESC LIMIT 1", null);
        cursor_id_end.moveToNext();
        int id_end = cursor_id_end.getInt(cursor_id_end.getColumnIndex(COLUMN_ID_END));

        // update na tbContato
        ContentValues contentValuesCntt = new ContentValues();
        contentValuesCntt.put(COLUMN_ID_END, id_end);
        contentValuesCntt.put(COLUMN_EMAIL_CNTT, socio.get_email());
        db.update(CONTATO_TABLE_NAME, contentValuesCntt,
                COLUMN_ID_CNTT + " = ? ", new String[] { Integer.toString(id_cntt) } );

        return true;
    }

    public boolean deleteSocio(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_id_cntt = db.rawQuery("SELECT " + COLUMN_ID_CNTT + " FROM " + FUNCIONARIO_TABLE_NAME
                + " WHERE " + COLUMN_ID_FUNC + " = " + id, null);
        cursor_id_cntt.moveToNext();
        int id_cntt = cursor_id_cntt.getInt(cursor_id_cntt.getColumnIndex(COLUMN_ID_CNTT));

        db.execSQL("DELETE FROM " + FUNCIONARIO_TABLE_NAME
                + " WHERE " + COLUMN_ID_FUNC + " = " + id);
        db.execSQL("DELETE FROM " + CONTATO_TABLE_NAME
                + " WHERE " + COLUMN_ID_CNTT + " = " + id_cntt);
        return true;
    }

    public ArrayList<String> getAllSocios() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT " + COLUMN_NAME_FUNC + " FROM " + FUNCIONARIO_TABLE_NAME, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            array_list.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FUNC)));
            cursor.moveToNext();
        }
        return array_list;
    }
}
