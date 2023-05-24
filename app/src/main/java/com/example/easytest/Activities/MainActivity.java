package com.example.easytest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.easytest.Classes.FirebaseServices;
import com.example.easytest.R;
import com.example.easytest.UserManagement.LogInFragment;

public class MainActivity extends AppCompatActivity {

    FirebaseServices fs=new FirebaseServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new LogInFragment());
        ft.commit();
    }

}