package com.example.easytest.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.easytest.Fragments.TimedQuizFragment;
import com.example.easytest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StartGame extends AppCompatActivity {
    TextView tvtimer;
    TextView tvresult;
    ImageView ivshowImage;
    HashMap<String, Integer> map = new HashMap<>();
    ArrayList<String> signList = new ArrayList<>();
    int index;
    Button bt1, bt2, bt3, bt4;
    TextView tvpoints;
    int points;
    CountDownTimer countDownTimer;
    long millisUntilFinished;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);
        attachComponents();
        startGame();
    }

    private void attachComponents() {
        tvtimer = findViewById(R.id.TvTimer);
        tvresult = findViewById(R.id.TvResult);
        tvpoints = findViewById(R.id.TvPoints);
        bt1 = findViewById(R.id.btn1);
        bt2 = findViewById(R.id.btn2);
        bt3 = findViewById(R.id.btn3);
        bt4 = findViewById(R.id.btn4);
        ivshowImage = findViewById(R.id.ivShowImage);
        index = 0;
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
        map.put(signList.get(0), R.drawable.highwayahead);
        map.put(signList.get(1), R.drawable.onlyleft);
        map.put(signList.get(2), R.drawable.deadend);
        map.put(signList.get(3), R.drawable.peoplecrossing);
        map.put(signList.get(4), R.drawable.speedlimit);
        map.put(signList.get(5), R.drawable.roadnarrowing);
        map.put(signList.get(6), R.drawable.steep);
        map.put(signList.get(7), R.drawable.whirly);
        map.put(signList.get(8), R.drawable.trainahead);
        map.put(signList.get(9), R.drawable.trafficlightahead);
        Collections.shuffle(signList);
        millisUntilFinished = 10000;
        points = 0;
    }

    private void startGame() {
        millisUntilFinished = 10000;
        tvtimer.setText("" + (millisUntilFinished / 1000) + "s");
        tvpoints.setText(points + "/" + signList.size());
        generateQuestions(index);
        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvtimer.setText("" + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: entered");
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StartGame.this);
                    builder.setTitle("Game Over")
                            .setMessage("Your score: " + points + "/" + signList.size())
                            .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    restartQuiz();
                                }
                            })
                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    exitGame();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }catch (Exception e){
                    Log.e(TAG, "Exception: "+e );

                }finally{
                    Log.d(TAG, "finally: entered");
                }

            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Paused");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Destroy");
    }

    private void restartQuiz() {
        finish();
        Intent i = new Intent(StartGame.this,StartGame.class);
        startActivity(i);
    }

    private void exitGame() {
        Intent homePageActivityIntent = new Intent(StartGame.this, HomePage.class);
        startActivity(homePageActivityIntent);
        finish();
    }

    private void generateQuestions(int index) {
        ArrayList<String> techListTemp = (ArrayList<String>) signList.clone();
        String correctAnswer = signList.get(index);
        techListTemp.remove(correctAnswer);
        Collections.shuffle(techListTemp);
        ArrayList<String> newList = new ArrayList<>();
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

    public void nextQuestion(View view) {
        countDownTimer.cancel();
        index++;
        if (index > signList.size() - 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(StartGame.this);
            builder.setTitle("Game Over")
                    .setMessage("Your score: " + points + "/" + signList.size())
                    .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            restartQuiz();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            exitGame();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            startGame();
            tvresult.setText(null);
        }
    }

    public void answerSelected(View view) {
        countDownTimer.cancel();
        String answer = ((Button) view).getText().toString().trim();
        String correctAnswer = signList.get(index);
        if (answer.equals(correctAnswer)) {
            points++;
            tvpoints.setText(points + "/" + signList.size());
            tvresult.setText("Correct");
        } else {
            tvresult.setText("Wrong");
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}

