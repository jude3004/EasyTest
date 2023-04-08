package com.example.easytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimedQuizBegins extends AppCompatActivity {
private List<QuestionModel> questionsList;
private TextView tvQuestion,tvScore,tvQuestionNo,tvTimer;
private RadioGroup radiogroup;
private RadioButton r1,r2,r3,r4;
private Button btnnext;
int totalQuestions;
int qCounter=0;
private QuestionModel currentQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_quiz_begins);
    }
    public void attatchcomponents()
    {
        tvQuestion=findViewById(R.id.textQuestion);
        tvScore=findViewById(R.id.textScore);
        tvQuestionNo=findViewById(R.id.textQuestionNo);
        tvTimer=findViewById(R.id.textTimer);
        radiogroup=findViewById(R.id.radioGroup);
        r1=findViewById(R.id.rb1);
        r2=findViewById(R.id.rb2);
        r3=findViewById(R.id.rb3);
        r4=findViewById(R.id.rb4);
        btnnext=findViewById(R.id.btnNext);
        questionsList= new ArrayList<>();
        addQuestions();
        totalQuestions=questionsList.size();
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (qCounter < totalQuestions){
currentQuestion= questionsList.get(qCounter);
tvQuestion.setText(currentQuestion.getQuestion());
r1.setText(currentQuestion.getOption1());
            r2.setText(currentQuestion.getOption2());
            r3.setText(currentQuestion.getOption3());
            r4.setText(currentQuestion.getOption4());
            qCounter++;
        }
        else {
            finish();
        }
    }

    private void addQuestions() {
        questionsList.add(new QuestionModel("A is correct?","A","B","C","D",1));
        questionsList.add(new QuestionModel("A is correct?","A","B","C","D",2));
        questionsList.add(new QuestionModel("A is correct?","A","B","C","D",3));
        questionsList.add(new QuestionModel("A is correct?","A","B","C","D",4));
    }
}