package com.example.mobilev10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class User extends AppCompatActivity {

    EditText edtNotes;
    Button btnSave;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        edtNotes = (EditText) findViewById(R.id.edtNotes);
        btnSave= (Button) findViewById(R.id.btnSaveNotes);
    }
//SALVAR AS ANOTAÇÕES - ARMAZENAMENTO EXTERNO
    public void savePublicly(View view){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
        }, EXTERNAL_STORAGE_PERMISSION_CODE);
        String editTextNotes = edtNotes.getText().toString();
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, "notes.txt");
        writeTextNotes(file, editTextNotes);

        String data = getdata(file);
        if (data != null) {
            edtNotes.setText(data);
        } else {
            edtNotes.setText("No Data Found");
        }
    }

    private void writeTextNotes(File file, String data){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Anotações Salvas" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}