package com.ansh.whatsappstatussaver;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ConsentActivity extends AppCompatActivity {

    private AppCompatButton startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);

        startBtn = findViewById(R.id.idStartBtn);

        startBtn.setOnClickListener(v ->{
            Intent intent = new Intent(ConsentActivity.this,StatusActivity.class);
            startActivity(intent);
            finish();
        });


    }
}