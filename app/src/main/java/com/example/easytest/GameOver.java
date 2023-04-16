package com.example.easytest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GameOver extends AppCompatActivity {
    TextView tvpoints, tvpersonalBest;
    SharedPreferences sharedPreferences;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        attachcomponents();
    }

    private void attachcomponents() {
        int points=getIntent().getExtras().getInt("points");
        tvpoints=findViewById(R.id.tvPoints);
        tvpersonalBest=findViewById(R.id.tvPersonalBest);
        tvpoints.setText(""+ points);
        sharedPreferences=getSharedPreferences("pref",0);
        int pointsSP= sharedPreferences.getInt("pointsSp",0);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        if (points> pointsSP){
            pointsSP=points;
            editor.putInt("pointsSP",pointsSP);
            editor.commit();

        }
        tvpersonalBest.setText(""+ pointsSP);
    }
    public void restart(View view){
        Intent intent= new Intent(GameOver.this,StartGame.class);
        startActivity(intent);
        finish();
    }
    public void exit(View view){
        Homepagefragment homepageFragment = new Homepagefragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutMain, homepageFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }
}
