package com.example.quiztutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private TextView optionA,optionB,optionC,optionD;
    private TextView questionnumber,question,score;
    private TextView chechkout1,checkout2;
    int currentIndex;
    int mscore=0;
    int qn=1;
    ProgressBar progressBar;
    private String userName;

    private Button submitButton;
    private TextView selectedOptionView;
    private int selectedAnswerId;

    int CurrentQuestion,CurrentOptionA,CurrentOptionB,CurrentOptionC,CurrentOptionD;

    private answerclass[] questionBank = new answerclass[]
            {
                    new answerclass(R.string.question_1, R.string.question1_A, R.string.question1_B, R.string.question1_C, R.string.question1_D, R.string.question1_C), // Answer is option C
                    new answerclass(R.string.question_2, R.string.question2_A, R.string.question2_B, R.string.question2_C, R.string.question2_D, R.string.question2_D), // Answer is option D
                    new answerclass(R.string.question_3, R.string.question3_A, R.string.question3_B, R.string.question3_C, R.string.question3_D, R.string.question3_C), // Answer is option C
                    new answerclass(R.string.question_4, R.string.question4_A, R.string.question4_B, R.string.question4_C, R.string.question4_D, R.string.question4_A), // Answer is option A
                    new answerclass(R.string.question_5, R.string.question5_A, R.string.question5_B, R.string.question5_C, R.string.question5_D, R.string.question5_C), // Answer is option C
                    new answerclass(R.string.question_6, R.string.question6_A, R.string.question6_B, R.string.question6_C, R.string.question6_D, R.string.question6_D), // Answer is option D
                    new answerclass(R.string.question_7, R.string.question7_A, R.string.question7_B, R.string.question7_C, R.string.question7_D, R.string.question7_C), // Answer is option C
                    new answerclass(R.string.question_8, R.string.question8_A, R.string.question8_B, R.string.question8_C, R.string.question8_D, R.string.question8_A), // Answer is option A
            };

    final int PROGRESS_BAR = (int) Math.ceil(100/questionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        userName = prefs.getString("userName", "Anonymous");

        optionA=findViewById(R.id.optionA);
        optionB=findViewById(R.id.optionB);
        optionC=findViewById(R.id.optionC);
        optionD=findViewById(R.id.optionD);

        question = findViewById(R.id.question);
        score=findViewById(R.id.score);
        questionnumber=findViewById(R.id.QuestionNumber);

        chechkout1=findViewById(R.id.selectoption);
        checkout2=findViewById(R.id.CorrectAnswer);
        progressBar=findViewById(R.id.progress_bar);

        CurrentQuestion=questionBank[currentIndex].getQuestionid();
        question.setText(CurrentQuestion);
        CurrentOptionA=questionBank[currentIndex].getOptionA();
        optionA.setText(CurrentOptionA);
        CurrentOptionB=questionBank[currentIndex].getOptionB();
        optionB.setText(CurrentOptionB);
        CurrentOptionC=questionBank[currentIndex].getOptionC();
        optionC.setText(CurrentOptionC);
        CurrentOptionD=questionBank[currentIndex].getOptionD();
        optionD.setText(CurrentOptionD);

        submitButton = findViewById(R.id.submitButton);
        progressBar.setMax(questionBank.length);
        progressBar.setProgress(0);

        // Change all option click listeners in onCreate() to:
        optionA.setOnClickListener(v -> handleOptionClick(optionA, questionBank[currentIndex].getOptionA()));
        optionB.setOnClickListener(v -> handleOptionClick(optionB, questionBank[currentIndex].getOptionB()));
        optionC.setOnClickListener(v -> handleOptionClick(optionC, questionBank[currentIndex].getOptionC()));
        optionD.setOnClickListener(v -> handleOptionClick(optionD, questionBank[currentIndex].getOptionD()));

        submitButton.setOnClickListener(v -> {
            if (selectedOptionView == null) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }
            checkAnswer();
            new Handler().postDelayed(() -> {
                resetOptions();
                updateQuestion();
            }, 1000);
        });

        updateUI();

    }

    private void handleOptionClick(TextView optionView, int answerId) {
        resetOptions();
        selectedOptionView = optionView;
        selectedAnswerId = answerId;
        optionView.setBackgroundColor(Color.LTGRAY);
    }

    private void checkAnswer() {
        int correctAnswerId = questionBank[currentIndex].getAnswerid();
        TextView correctOption = findCorrectOption(correctAnswerId);

        if (selectedAnswerId == correctAnswerId) {
            selectedOptionView.setBackgroundColor(Color.GREEN);
            mscore++;
        } else {
            selectedOptionView.setBackgroundColor(Color.RED);
            if (correctOption != null) correctOption.setBackgroundColor(Color.GREEN);
        }
    }

    private TextView findCorrectOption(int correctAnswerId) {
        String correctText = getString(correctAnswerId);
        if (optionA.getText().toString().equals(correctText)) return optionA;
        if (optionB.getText().toString().equals(correctText)) return optionB;
        if (optionC.getText().toString().equals(correctText)) return optionC;
        if (optionD.getText().toString().equals(correctText)) return optionD;
        return null;
    }

    private void resetOptions() {
        for (TextView option : new TextView[]{optionA, optionB, optionC, optionD}) {
            option.setBackgroundColor(Color.WHITE);
            option.setTextColor(Color.BLACK);
        }
        selectedOptionView = null;
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        question.setText(questionBank[currentIndex].getQuestionid());
        optionA.setText(questionBank[currentIndex].getOptionA());
        optionB.setText(questionBank[currentIndex].getOptionB());
        optionC.setText(questionBank[currentIndex].getOptionC());
        optionD.setText(questionBank[currentIndex].getOptionD());
        progressBar.setProgress(currentIndex + 1);  // Update this line
        score.setText("Score: " + mscore + "/" + questionBank.length);
        questionnumber.setText((currentIndex + 1) + "/" + questionBank.length + " Question");
    }

    private void updateQuestion() {
        currentIndex++;
        if (currentIndex >= questionBank.length) {
            startActivity(new Intent(this, ResultActivity.class)
                    .putExtra("SCORE", mscore)
                    .putExtra("TOTAL", questionBank.length)
                    .putExtra("USER_NAME", userName));
            finish();
        } else {
            updateUI();
        }
    }

}