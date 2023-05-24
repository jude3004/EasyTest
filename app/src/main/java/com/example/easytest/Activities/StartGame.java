package com.example.easytest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.easytest.Fragments.GameOverFragment;
import com.example.easytest.R;
import com.example.easytest.UserManagement.ForgotPasswordFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StartGame extends AppCompatActivity {
    TextView tvtimer;
    TextView tvresult;
    ImageView ivshowImage;
    HashMap <String,Integer> map= new HashMap<>();
    ArrayList <String> signList=new ArrayList<>();
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
        signList.add("highway ahead");
        signList.add("turn left is mandatory");
        signList.add("dead end");
        signList.add("people crossing");
        signList.add("speed limit");
        signList.add("road narrowing from both sides");
        signList.add("steep cliff ahead");
        signList.add("zig zag road ahead");
        signList.add("train ahead");
        signList.add("traffic light ahead");
        map.put(signList.get(0),R.drawable.highwayahead);
        map.put(signList.get(1),R.drawable.onlyleft);
        map.put(signList.get(2),R.drawable.deadend);
        map.put(signList.get(3),R.drawable.peoplecrossing);
        map.put(signList.get(4),R.drawable.speedlimit);
        map.put(signList.get(5),R.drawable.roadnarrowing);
        map.put(signList.get(6),R.drawable.steep);
        map.put(signList.get(7),R.drawable.whirly);
        map.put(signList.get(8),R.drawable.trainahead);
        map.put(signList.get(9),R.drawable.trafficlightahead);
        Collections.shuffle(signList);
        millisUntilFinished=10000;
        points=0;


    }

    private void startGame() {
        millisUntilFinished=10000;
        tvtimer.setText(""+(millisUntilFinished/1000)+"s");
        tvpoints.setText(points+"/"+signList.size());
        generateQuestions(index);
        countDownTimer = new CountDownTimer(millisUntilFinished,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
tvtimer.setText(""+(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
index++;
if (index>signList.size()-1) {
    ivshowImage.setVisibility(View.GONE);
    bt1.setVisibility(View.GONE);
    bt2.setVisibility(View.GONE);
    bt3.setVisibility(View.GONE);
    bt4.setVisibility(View.GONE);
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.frameLayoutMain, new GameOverFragment());
    fragmentTransaction.commit();

// Create an instance of the GameOverFragment
    GameOverFragment gameOverFragment = new GameOverFragment();

// Pass the player's score to the GameOverFragment using arguments
    Bundle bundle = new Bundle();
    bundle.putInt("points", points);
    gameOverFragment.setArguments(bundle);
    finish();
}
    else {
        startGame();
    }

            }
        }.start();
    }

    private void generateQuestions(int index) {
        ArrayList<String> techListTemp= (ArrayList<String>) signList.clone();
        String correctAnswer= signList.get(index);
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
        ivshowImage.setImageResource(map.get(signList.get(index)));

    }


    public void nextQuestion (View view)
    {countDownTimer.cancel();
        index++;
        if (index>signList.size()-1){
            ivshowImage.setVisibility(View.GONE);
            bt1.setVisibility(View.GONE);
            bt2.setVisibility(View.GONE);
            bt3.setVisibility(View.GONE);
            bt4.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.startgamelayout, new GameOverFragment());
            fragmentTransaction.commit();
            finish();
        } else {
            startGame();
        }

    }

    public void answerSelected (View view)
    {
        countDownTimer.cancel();
        String answer= ((Button) view).getText().toString().trim();
        String correctAnswer= signList.get(index);
        if (answer.equals(correctAnswer)){
            points++;
            tvpoints.setText(points+"/"+signList.size());
            tvresult.setText("correct");

        } else {
            tvresult.setText("wrong");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
