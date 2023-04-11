package com.example.easytest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submitbtn,ansA,ansB,ansC,ansD;
    private TextView questionTextView, totalQuestonTextView;
    int score=0;
    int totalQuestions= QuestionAnswer.question.length;
    int currentQuestionIndex=0;
    String selectedAnswer="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        attachcomponents();
    }
    public void attachcomponents()
    {
        submitbtn=findViewById(R.id.submit_btn);
        ansA=findViewById(R.id.ans_A);
        ansB=findViewById(R.id.ans_B);
        ansC=findViewById(R.id.ans_C);
        ansD=findViewById(R.id.ans_D);
        questionTextView=findViewById(R.id.question);
        totalQuestonTextView=findViewById(R.id.total_questions);
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitbtn.setOnClickListener(this);
        totalQuestonTextView.setText("total questions:"+totalQuestions);
        loadNewQuestion();
    }

     void loadNewQuestion() {
        if (currentQuestionIndex==totalQuestions){
            finishQuiz();
            return;
        }
questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
         ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
         ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
         ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
    }

     void finishQuiz() {
        String passStatus="";
        if (score>totalQuestions*0.6){
            passStatus="passed";
        }
        else{
            passStatus="failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("score is:"+score+"out of"+ totalQuestions)
                .setPositiveButton("Restart",((dialogInterface, i) -> restartQuiz()))
                .setCancelable(false)
                .show();
    }

     void restartQuiz() {
        score=0;
        currentQuestionIndex=0;
        loadNewQuestion();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(android.R.color.white);
        ansB.setBackgroundColor(android.R.color.white);
        ansC.setBackgroundColor(android.R.color.white);
        ansD.setBackgroundColor(android.R.color.white);

Button clickedButton= (Button) view;
        if (clickedButton.getId()==R.id.submit_btn) {
            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                score++;
            }

            currentQuestionIndex++;
            loadNewQuestion();
        }



else {
selectedAnswer=clickedButton.getText().toString();
clickedButton.setBackgroundColor(android.R.color.holo_red_dark);
}

}


}