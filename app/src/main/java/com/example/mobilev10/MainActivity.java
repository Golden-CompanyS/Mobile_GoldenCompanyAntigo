package com.example.mobilev10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Redirecionamento sem verificação temporário
    public void abrirAcoesActivity(View view){
        Intent intent = new Intent(this, AcoesActivity.class);
        startActivity(intent);
        finish();
    }

}