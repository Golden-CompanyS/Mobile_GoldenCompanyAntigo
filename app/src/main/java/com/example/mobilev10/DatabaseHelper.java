package com.example.mobilev10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobilev10.Servicos;

import java.util.ArrayList;

import static java.sql.Types.INTEGER;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbMobile.db";

    //SERVIÇO
    public static final String SERVICO_TABLE_NAME = "tbServico";
    public static final String SERVICO_COLUMN_ID = "IdServico";
    public static final String SERVICO_COLUMN_DESC = "descServ";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_SERVICO_TABLE = "CREATE TABLE " + SERVICO_TABLE_NAME + "("
                + SERVICO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SERVICO_COLUMN_DESC + " TEXT" + ")";

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
