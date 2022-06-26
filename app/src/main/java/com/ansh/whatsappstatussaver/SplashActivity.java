package com.ansh.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(() -> {
            SharedPreferences sp = getSharedPreferences("DATA",MODE_PRIVATE);
            Boolean isLaunchedBefore = sp.getBoolean("IS_LAUNCHED_BEFORE",false);

            Intent intent;
            if (!isLaunchedBefore){
                intent = new Intent(SplashActivity.this, ConsentActivity.class);
            }else{
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();

        },1000);





    }
}