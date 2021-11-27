package com.example.mobilev10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserActivity extends AppCompatActivity {

    EditText edtNotes;
    Button btnSave;
    ImageView imgUser;
    Button btnAltPerfil;
    ImageView imgPerfil;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    private static final int RESULT_SELECT_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        edtNotes = (EditText) findViewById(R.id.edtNotes);
        btnSave= (Button) findViewById(R.id.btnSaveNotes);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        btnAltPerfil = (Button) findViewById(R.id.btnAltFoto);


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

    //IMAGE PICKER
    public void viewGallery(View view){
        //inicia uma intent com ação de Seleção de Imagens
        Intent intent =     new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //inicia uma activity que devolverá uma resposta
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), RESULT_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
           Uri imagemSelecionada = data.getData();
            //define como source da imagem
            imgUser.setImageURI(imagemSelecionada);
        }
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