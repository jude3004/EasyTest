package com.example.easytest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.easytest.Classes.QuestionAnswer;
import com.example.easytest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submitButton, ansA, ansB, ansC, ansD;
    private TextView questionTextView, totalQuestionTextView;
    private ImageButton arrow;
    private BottomNavigationView nav;
    private int totalQuestions;
    private int currentQuestionIndex;
    private int score;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        attachComponents();
        nav=findViewById(R.id.bottomNavigationView1);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    private void attachComponents() {
        arrow = findViewById(R.id.imageButtonquiz);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainPageActivityIntent = new Intent(QuizActivity.this, HomePage.class);
                startActivity(mainPageActivityIntent);
            }
        });

        submitButton = findViewById(R.id.submit_btn);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        questionTextView = findViewById(R.id.question);
        totalQuestionTextView = findViewById(R.id.total_questions);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        totalQuestions = QuestionAnswer.question.length;
        currentQuestionIndex = 0;
        score = 0;

        totalQuestionTextView.setText("Total Questions: " + totalQuestions);
        loadNewQuestion();
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestions) {
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        // Reset the background color of all the answer buttons
        ansA.setBackgroundColor(Color.TRANSPARENT);
        ansB.setBackgroundColor(Color.TRANSPARENT);
        ansC.setBackgroundColor(Color.TRANSPARENT);
        ansD.setBackgroundColor(Color.TRANSPARENT);
    }

    private void finishQuiz() {
        String passStatus = (score > totalQuestions * 0.6) ? "passed" : "failed";
        String message = "Score: " + score + " out of " + totalQuestions;

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restartQuiz();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        currentQuestionIndex = 0;
        score = 0;
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
}






