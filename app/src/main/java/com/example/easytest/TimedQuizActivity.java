package com.example.easytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TimedQuizActivity extends AppCompatActivity {
Button btnstart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_quiz);
    }
    public void attatchcomponents()
    {
        btnstart=findViewById(R.id.btnStart);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TimedQuizActivity.this,StartGame.class);
                startActivity(intent);

            }
        });
    }
}