package com.example.easytest;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StartGame extends AppCompatActivity {
    TextView tvtimer;
    TextView tvresult;
    ImageView ivshowImage;
    HashMap <String,Integer> map= new HashMap<>();
    ArrayList <String> techList=new ArrayList<>();
    int index;
    Button bt1,bt2,bt3,bt4;
    TextView tvpoints;
    int points;
    CountDownTimer countDownTimer;
    long millisUntilFinished;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);
        attachcomponents();
        startGame();
    }

    private void attachcomponents() {
        tvtimer=findViewById(R.id.TvTimer);
        tvresult=findViewById(R.id.TvResult);
        tvpoints=findViewById(R.id.TvPoints);
        bt1=findViewById(R.id.btn1);
        bt2=findViewById(R.id.btn2);
        bt3=findViewById(R.id.btn3);
        bt4=findViewById(R.id.btn4);
        ivshowImage=findViewById(R.id.ivShowImage);
        index=0;
        techList.add("highway ahead");
        techList.add("turn left is mandatory");
        techList.add("who has priority");
        techList.add("who has priority 2");
        techList.add("who has priority 3");
        techList.add("road narrowing from both sides");
        techList.add("steep cliff ahead");
        techList.add("zig zag road ahead");
        techList.add("what lane to transfer to");
        techList.add("what lane to transfer to 2");
        map.put(techList.get(0),R.drawable.highwayahead);
        map.put(techList.get(1),R.drawable.onlyleft);
        map.put(techList.get(2),R.drawable.priority);
        map.put(techList.get(3),R.drawable.priority2);
        map.put(techList.get(4),R.drawable.priority3);
        map.put(techList.get(5),R.drawable.roadnarrowing);
        map.put(techList.get(6),R.drawable.steep);
        map.put(techList.get(7),R.drawable.whirly);
        map.put(techList.get(8),R.drawable.whatntev);
        map.put(techList.get(9),R.drawable.whatntev1);
        Collections.shuffle(techList);
        millisUntilFinished=10000;
        points=0;


    }

    private void startGame() {
        millisUntilFinished=10000;
        tvtimer.setText(""+(millisUntilFinished/1000)+"s");
        tvpoints.setText(points+"/"+techList.size());
        generateQuestions(index);
        countDownTimer = new CountDownTimer(millisUntilFinished,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
tvtimer.setText(""+(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
index++;
if (index>techList.size()-1) {
    ivshowImage.setVisibility(View.GONE);
    bt1.setVisibility(View.GONE);
    bt2.setVisibility(View.GONE);
    bt3.setVisibility(View.GONE);
    bt4.setVisibility(View.GONE);
    Intent intent = new Intent(StartGame.this, GameOver.class);
    intent.putExtra("points", points);
    startActivity(intent);
    finish();
}
    else {
        startGame();
    }

            }
        }.start();
    }

    private void generateQuestions(int index) {
        ArrayList<String> techListTemp= (ArrayList<String>) techList.clone();
        String correctAnswer= techList.get(index);
        techListTemp.remove(correctAnswer);
        Collections.shuffle(techListTemp);
ArrayList<String> newList= new ArrayList<>();
newList.add(techListTemp.get(0));
        newList.add(techListTemp.get(1));
        newList.add(techListTemp.get(2));
        newList.add(correctAnswer);
        Collections.shuffle(newList);
bt1.setText(newList.get(0));
        bt2.setText(newList.get(1));
        bt3.setText(newList.get(2));
        bt4.setText(newList.get(3));
        ivshowImage.setImageResource(map.get(techList.get(index)));

    }


    public void nextQuestion (View view)
    {countDownTimer.cancel();
        index++;
        if (index>techList.size()-1){
            ivshowImage.setVisibility(View.GONE);
            bt1.setVisibility(View.GONE);
            bt2.setVisibility(View.GONE);
            bt3.setVisibility(View.GONE);
            bt4.setVisibility(View.GONE);
            Intent intent = new Intent(StartGame.this, GameOver.class);
            intent.putExtra("points", points);
            startActivity(intent);
            finish();
        } else {
            startGame();
        }

    }
    public void answerSelected (View view)
    {
        countDownTimer.cancel();
        String answer= ((Button) view).getText().toString().trim();
        String correctAnswer= techList.get(index);
        if (answer.equals(correctAnswer)){
            points++;
            tvpoints.setText(points+"/"+techList.size());
            tvresult.setText("correct");

        } else {
            tvresult.setText("wrong");
        }
    }
}
