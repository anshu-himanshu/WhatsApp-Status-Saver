package com.ansh.whatsappstatussaver;

import android.content.Intent;
import android.content.SharedPreferences;
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

            SharedPreferences sp = getSharedPreferences("DATA",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("IS_LAUNCHED_BEFORE",true);
            editor.apply();

            Intent intent = new Intent(ConsentActivity.this,PermissionActivity.class);
            startActivity(intent);
            finish();
        });


    }
}