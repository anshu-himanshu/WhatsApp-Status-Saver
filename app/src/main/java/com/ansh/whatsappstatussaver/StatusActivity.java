package com.ansh.whatsappstatussaver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StatusActivity extends AppCompatActivity {

    ViewpagerStatusAdapter viewpagerStatusAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private final String[] tabTitles = new String[]{"Images", "Videos", "Saved"};
    Toolbar toolbar;
    Context context;
    private boolean GRANTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPagerStatus);

        viewpagerStatusAdapter = new ViewpagerStatusAdapter(this);
        viewPager2.setAdapter(viewpagerStatusAdapter);



        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(tabTitles[position]))).attach();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("WhatsApp Status");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu, menu);

        menu.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.optRefresh) {
            recreate();
        } else if (itemId == R.id.optOpenWhatsApp) {
            openWhatsApp();

        } else if (itemId == android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    public void openWhatsApp() {
        Intent intent = StatusActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + "com.whatsapp"));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        StatusActivity.this.startActivity(intent);
    }
}