package com.example.easytest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.easytest.Classes.QuestionAnswer;
import com.example.easytest.R;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submitbtn,ansA,ansB,ansC,ansD;
    private TextView questionTextView, totalQuestonTextView;
    int score=0;
    private ImageButton arrow;
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
        arrow=findViewById(R.id.imageButtonquiz);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainPageActivityIntent = new Intent(QuizActivity.this, HomePage.class);
                startActivity(mainPageActivityIntent);
            }
        });
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


    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;

        // Check if the clicked button is one of the answer buttons
        if (clickedButton.getId() == R.id.ans_A ||
                clickedButton.getId() == R.id.ans_B ||
                clickedButton.getId() == R.id.ans_C ||
                clickedButton.getId() == R.id.ans_D) {

            // Check if the selected answer is correct
            String selectedAnswer = clickedButton.getText().toString();
            String correctAnswer = QuestionAnswer.correctAnswers[currentQuestionIndex];
            boolean isCorrect = selectedAnswer.equals(correctAnswer);

            // Set the background color of the clicked button based on correctness
            int backgroundColor = isCorrect ? Color.GREEN : Color.RED;
            clickedButton.setBackgroundColor(backgroundColor);

            // Increment the score if the answer is correct
            if (isCorrect) {
                score++;
            }
        }

        // Handle submit button click
        if (clickedButton.getId() == R.id.submit_btn) {
            currentQuestionIndex++;
            loadNewQuestion();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}






