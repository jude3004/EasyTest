package com.example.easytest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.FrameLayout, new Homepagefragment());
        transaction.commit();
        attachComponents();
    }

    private void attachComponents(){
        bottomNavigationView=findViewById(R.id.bottomNavigationView1);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.settings:
                        transaction.replace(R.id.frameLayoutMain, new SettingsFragment());
                        transaction.commit();
                        return true;
                    case R.id.profile:
                        transaction.replace(R.id.frameLayoutMain, new ProfileFragment());
                        transaction.commit();
                        return true;
                    case R.id.home:
                        transaction.replace(R.id.FrameLayout, new Homepagefragment());
                        transaction.commit();
                        return true;
                }
                return true;
            }
        });
    }
}
