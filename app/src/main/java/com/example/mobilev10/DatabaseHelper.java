package com.example.mobilev10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobilev10.Servicos;

import java.util.ArrayList;

import static java.sql.Types.INTEGER;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbMobile.db";

    //SERVIÇO
    public static final String PROFILE_TABLE_NAME = "tbServico";
    public static final String PROFILE_COLUMN_ID_SERV = "IdServico";
    public static final String PROFILE_COLUMN_NAME = "descServ";

    //ESTADO
    public static final String PROFILE_TABLE_ESTADO = "tbEstado";
    public static final String PROFILE_COLUMN_ID_UF = "IdUF";
    public static final String PROFILE_COLUMN_UF = "UF";

    //CIDADE
    public static final String PROFILE_TABLE_CITY = "tbCidade";
    public static final String PROFILE_COLUMN_ID_CITY = "IdCidade";
    public static final String PROFILE_COLUMN_NAMECITY = "NomeCidade";

    //BAIRRO
    public static final String PROFILE_TABLE_BAIRRO = "tbBairro";
    public static final String PROFILE_COLUMN_ID_BAIRRO = "IdBairro";
    public static final String PROFILE_COLUMN_NAMEBAIRRO = "NomeBairro";

    //ENDEREÇO
    public static final String PROFILE_TABLE_ENDERECO = "tbEndereco";
    public static final String PROFILE_COLUMN_ID_CEP = "CEP";
    public static final String PROFILE_COLUMN_LOGRADOURO= "logradouro";
    public static final String PROFILE_COLUMN_COMPLEMENTO= "Complemento";

    //TELEFONE
    public static final String PROFILE_TABLE_TELEFONE = "tbBairro";
    public static final String PROFILE_COLUMN_ID_TEL = "IdTelefone";
    public static final String PROFILE_COLUMN_NAME_Telefone = "Telefone";

    //CONTATO
    public static final String PROFILE_TABLE_CONTATO = "tbContato";
    public static final String PROFILE_COLUMN_ID_CONTATO = "IdContato";
    public static final String PROFILE_COLUMN_NAME_EMAIL = "Email";

    //CLIENTE
    public static final String PROFILE_TABLE_CLIENTE = "tbCliente";
    public static final String PROFILE_COLUMN_ID_CLIENTE = "IdCli";
    public static final String PROFILE_COLUMN_NAME_CLIENTE= "NomeCli";
    public static final String PROFILE_COLUMN_NAME_CNPJ= "CNPJ";
    public static final String PROFILE_COLUMN_NUM_ENDERECO= "NumeroEnde";

    //FUNCIONARIO
    public static final String PROFILE_TABLE_FUNC = "tbFuncionario";
    public static final String PROFILE_COLUMN_ID_FUNC = "IdFunc";
    public static final String PROFILE_COLUMN_NAME_FUNC= "NomeFunc";
    public static final String PROFILE_COLUMN_DATE_NASC= "DtNascFunc";
    public static final String PROFILE_COLUMN_CPF= "CPF";
    public static final String PROFILE_COLUMN_CARGO= "Cargo";
    public static final String PROFILE_COLUMN_PASSWORD= "Senha";
    public static final String PROFILE_COLUMN_NUM_ENDERECO_FUNC= "NumeroEnde";

    //ATIVIDADE
    public static final String PROFILE_TABLE_ATIVIDADE= "tbAtividade";
    public static final String PROFILE_COLUMN_ID_ATV= "IdAtv";
    public static final String PROFILE_COLUMN_DESC_ATV= "AtvDescricao";
    public static final String PROFILE_COLUMN_DATE_INICIO= "DtInicio";
    public static final String PROFILE_COLUMN_DATE_FIM= "DtFim";


    public static final String PROFILE_COLUMN_DESCR = "descr";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_ACTIVITIES_TABLE = "CREATE TABLE " + PROFILE_TABLE_ATIVIDADE + "("
                + PROFILE_COLUMN_ID_ATV + " INTEGER PRIMARY KEY," + PROFILE_COLUMN_DESC_ATV + " TEXT,"
                + PROFILE_COLUMN_DATE_INICIO + " DATE" + PROFILE_COLUMN_DATE_FIM + "DATE" +")";
        String CREATE_SERVICO_TABLE = "CREATE TABLE " + PROFILE_TABLE_NAME + "("
                + PROFILE_COLUMN_ID_SERV + " INTEGER PRIMARY KEY autoincrement," + PROFILE_COLUMN_NAME + " TEXT" + ")";
        db.execSQL(CREATE_SERVICO_TABLE
                /*"create table tbEstado " +
                        "(IdUF smallint primary key auto_increment, UF char(2) not null)"

        "create table tbEstado" +
                "(IdUF smallint primary key auto_increment, UF char(2) not null)"

        "create table tbCidade" +
                "(IdCidade smallint primary key auto_increment,NomeCidade varchar(100) not null)",

        "create table tbBairro" +
                "(IdBairro smallint primary key auto_increment, NomeBairro varchar(100) not null)",

        "create table tbEndereco" +
                "(CEP char(12) primary key auto_increment, IdUF smallint, foreign key IdUf(IdUF) references tbEstado(IdUF),IdCidade smallint, " +
                "foreign key IdCidade(IdCidade) references tbCidade(IdCidade), IdBairro smallint, " +
                "foreign key IdBairro(IdBairro) references tbBairro(IdBairro),logradouro varchar(150) not null,Complemento varchar(100))",

        "create table tbTelefone" +
                "(IdTelefone smallint primary key auto_increment, Telefone char(9) not null)",

        "create table tbContato" +
                "(IdContato smallint primary key auto_increment,FK_IdEndereco char(12) not null, foreign key (FK_IdEndereco) references tbEndereco(CEP), " +
                "FK_IdTelefone smallint not null, foreign key (FK_IdTelefone) references tbTelefone(IdTelefone), Email varchar(150) not null)",

        "create table tbCliente" +
                "(IdCli smallint primary key auto_increment, NomeCli varchar(150) not null, FK_IdContato smallint not null," +
                "foreign key (FK_IdContato) references tbContato(IdContato), CNPJ char(20) not null, NumeroEnde int not null)",

        "create TABLE tbFuncionario" +
                "(IdFunc smallint primary key auto_increment, NomeFunc varchar(150) not null," +
                "DtNascFunc date not null, CPF char(14) not null, FK_IdContato smallint not null," +
                "foreign key (FK_IdContato) references tbContato(IdContato), Cargo varchar(80) not null," +
                "Senha varchar(20) not null, NumeroEnde int not null)",

        "create table tbAtividade" +
                "(IdAtv smallint primary key auto_increment, AtvDescricao varchar(200) not null,"+
                "DtInicio date not null, DtFim date, FK_IdCli smallint not null," +
                "foreign key (FK_IdCli) references tbCliente(IdCli), FK_IdFunc smallint not null, foreign key (FK_IdFunc) references tbFuncionario(IdFunc),"+
                "FK_IdServico int not null, foreign key (FK_IdServico) references tbServico(IdServico))"*/
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME);
        db.execSQL("drop table if exists tbEstado");
        db.execSQL("drop table if exists tbCidade");
        db.execSQL("drop table if exists tbBairro");
        db.execSQL("drop table if exists tbContato");
        db.execSQL("drop table if exists tbTelefone");
        db.execSQL("drop table if exists tbCliente");
        db.execSQL("drop table if exists tbFuncionario");
        db.execSQL("drop table if exists tbAtividade");
        db.execSQL("drop table if exists tbEndereco");
        onCreate(db);
    }

    public boolean insertServico (Servicos s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_COLUMN_NAME, s.get_desc());
        contentValues.put(PROFILE_COLUMN_ID_SERV, s.get_id());
        db.insert(PROFILE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from tbprofiles where id="+id+"", null );
    }

    public void deleteAll () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROFILE_TABLE_NAME,
                null,
                null);
    }

    public boolean updateContact (Servicos c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_COLUMN_NAME, c.get_desc());

        db.update(PROFILE_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(c.get_id()) } );
        return true;
    }
    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tbServico", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Servicos> getServicosList() {
        ArrayList<Servicos> list = new ArrayList<>() ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tbServico", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            Servicos pfm = new Servicos();
            pfm.set_id(Integer.parseInt(res.getString(res.getColumnIndex(PROFILE_COLUMN_ID_SERV))));
            pfm.set_desc(res.getString(res.getColumnIndex(PROFILE_COLUMN_NAME)));

            list.add(pfm);
            res.moveToNext();
        }

        return list;
    }
}
