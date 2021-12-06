package com.example.mobilev10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobilev10.Servicos;

import java.util.ArrayList;
import java.util.Date;

import static java.sql.Types.INTEGER;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbMobile.db";

    //SERVIÇO
    public static final String SERVICO_TABLE_NAME = "tbServico";
    public static final String SERVICO_COLUMN_ID = "IdServico";
    public static final String SERVICO_COLUMN_DESC = "descServ";


    //TABELA CONTATO
    public static final String CONTACTS_TABLE_NAME = "tbContato";
    public static final String CONTACTS_ID = "IdContato";
    public static final String CONTACTS_EMAIL = "Email";

    //TELEFONE
    public static final String TABLE_TELEFONE = "tbBairro";
    public static final String COLUMN_ID_TEL = "IdTelefone";
    public static final String COLUMN_NAME_TELEPHONE = "Telefone";

    //ATIVIDADE
    public static final String TABLE_ATIVIDADE = "tbAtividade";
    public static final String COLUMN_ID_ATV = "IdAtividade";
    public static final String PROFILE_COLUMN_DESC_ATV = "descricaoAtv";
    public static final String COLUMN_DATE_INICIO = "dtInicio";
    public static final String COLUMN_DATE_FIM = "dtFim";
    public static final String COLUMN_ID_SERV = "FK_IdServico";

    //ESTADO
    public static final String TABLE_ESTADO = "tbEstado";
    public static final String COLUMN_ID_EST = "IdUF";
    public static final String COLUMN_UF_EST = "UF";

    //CIDADE
    public static final String TABLE_CIDADE = "tbCidade";
    public static final String COLUMN_ID_CID = "IdCidade";
    public static final String COLUMN_CID_NOME = "NomeCidade";

    public static final String TABLE_BAIRRO = "tbBairro";
    public static final String COLUMN_ID_BAIRR ="IdBairro";
    public static final String COLUMN_BAIRR_NOME = "NomeBairro";

    public static final String ADRESS_TABLE_NAME = "tbEndereco";
    public static final String COLUMN_ID_END = "IdEndereco";
    public static final String COLUMN_COMPL_EST = "Complemento";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_SERVICO_TABLE = "CREATE TABLE " + SERVICO_TABLE_NAME + "("
                + SERVICO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SERVICO_COLUMN_DESC + " TEXT" + ")";
        String CREATE_ACTIVITIES_TABLE = "CREATE TABLE " +TABLE_ATIVIDADE + "("
                + COLUMN_ID_ATV + " INTEGER PRIMARY KEY," + PROFILE_COLUMN_DESC_ATV + " TEXT,"
                + COLUMN_DATE_INICIO + " DATE" + COLUMN_DATE_FIM + "DATE" + COLUMN_ID_SERV +
                "INTEGER" +
                "FOREIGN KEY ('+ATV_SERV+') REFERENCES '+SERVICO_TABLE_NAME+'('+SERVICO_COLUMN_ID+')"+")";


        String CREATE_TELEPHONE_TABLE = "CREATE TABLE" + "("
                + COLUMN_ID_TEL + "INTEGER PRIMARY KEY"
                + COLUMN_NAME_TELEPHONE + "TEXT" + ")";


        String CREATE_STATES_TABLE = "CREATE TABLE" + TABLE_ESTADO + "("
                + COLUMN_ID_EST + "INTEGER PRIMARY KEY," + COLUMN_UF_EST + " TEXT(2)" + ")";

        String CREATE_CITIES_TABLE = "CREATE TABLE" + TABLE_CIDADE + "("
                + COLUMN_ID_CID + "INTEGER PRIMARY KEY," + COLUMN_CID_NOME + " TEXT" + ")";

        String CREATE_NEIGHBORH_TABLE = "CREATE TABLE" + TABLE_BAIRRO + "("
                + COLUMN_ID_BAIRR + "INTEGER PRIMARY KEY," + COLUMN_BAIRR_NOME + " TEXT" + ")";

        String CREATE_ADDRESS_TABLE = "CREATE TABLE " + ADRESS_TABLE_NAME + "("
                + COLUMN_ID_END + " INTEGER PRIMARY KEY," + COLUMN_ID_EST + " INTEGER PRIMARY KEY,"
                + COLUMN_ID_CID + " INTEGER PRIMARY KEY," + COLUMN_ID_BAIRR + " INTEGER PRIMARY KEY,"
                + COLUMN_COMPL_EST + "TEXT" +
                "FOREIGN KEY ('+COLUMN_ID_EST+') REFERENCES "+TABLE_ESTADO+"('+COLUMN_ID_EST+'),"+
        "FOREIGN KEY ('+COLUMN_ID_CID+') REFERENCES '+TABLE_CIDADE+'('+COLUMN_ID_CID+'),"+
        "FOREIGN KEY ('+COLUMN_ID_BAIRR+') REFERENCES '+TABLE_BAIRRO+'('+COLUMN_ID_BAIRR+')"+")";

        String CREATE_CONTATO_TABLE = "CREATE TABLE" + CONTACTS_TABLE_NAME + "("
                + CONTACTS_ID + "INTEGER PRIMARY KEY"
                + COLUMN_ID_END + "INTEGER"
                + "FOREIGN KEY ("+COLUMN_ID_END+") REFERENCES "+ADRESS_TABLE_NAME+" ("+COLUMN_ID_END+")"
                + COLUMN_ID_TEL + "INTEGER"
                + "FOREIGN KEY ("+COLUMN_ID_TEL+") REFERENCES '+TABLE_TELEFONE+' ('+COLUMN_ID_TEL+')"
                + CONTACTS_EMAIL + "TEXT"
                +")";



        db.execSQL(CREATE_SERVICO_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + SERVICO_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertServico (Servicos serv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVICO_COLUMN_DESC, serv.get_desc());
        db.insert(SERVICO_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getDataServicos(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM "+ SERVICO_TABLE_NAME + " WHERE " + SERVICO_COLUMN_ID + " = " + id + "", null);
        return cursor;
    }

    public int numberOfRowsServicos(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SERVICO_TABLE_NAME);
        return numRows;
    }

    public boolean updateServico (Servicos serv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVICO_COLUMN_DESC, serv.get_desc());

        db.update(SERVICO_TABLE_NAME, contentValues,
                SERVICO_COLUMN_ID + " = ? ", new String[] { Integer.toString(serv.get_id()) } );
        return true;
    }

    public Integer deleteServico(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SERVICO_TABLE_NAME,
                SERVICO_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
    }

    public ArrayList<String> getAllServicos() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + SERVICO_TABLE_NAME, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            array_list.add(cursor.getString(cursor.getColumnIndex(SERVICO_COLUMN_DESC)));
            cursor.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Servicos> getServicosList() {
        ArrayList<Servicos> list = new ArrayList<Servicos>() ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT * FROM " + SERVICO_TABLE_NAME, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Servicos serv = new Servicos();
            serv.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SERVICO_COLUMN_ID))));
            serv.set_desc(cursor.getString(cursor.getColumnIndex(SERVICO_COLUMN_DESC)));

            list.add(serv);
            cursor.moveToNext();
        }

        return list;
    }
}
