package com.ansh.whatsappstatussaver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ansh.whatsappstatussaver.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




        binding.reWhatsAppNormal.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatusActivity.class);
            startActivity(intent);
        });
        binding.reWhatsAppBusiness.setOnClickListener(v -> {

           Toast.makeText(MainActivity.this, "Function un Implementd",Toast.LENGTH_SHORT).show();
        });
        binding.relAbout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Function un Implementd",Toast.LENGTH_SHORT).show();
        });
       binding.relMoreApps.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Function un Implementd",Toast.LENGTH_SHORT).show();
        });
       binding.relPrivacy.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Function un Implementd",Toast.LENGTH_SHORT).show();
        });
       binding.relRatings.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Function un Implementd",Toast.LENGTH_SHORT).show();
        });
      binding.relTermsAndCondition.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Function un Implementd",Toast.LENGTH_SHORT).show();
        });

    }
}