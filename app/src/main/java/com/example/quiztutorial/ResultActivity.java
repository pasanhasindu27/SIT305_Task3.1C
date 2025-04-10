package com.example.quiztutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        TextView scoreTV = findViewById(R.id.scoreTextView);
        EditText nameET = findViewById(R.id.nameEditText);
        Button newQuizBtn = findViewById(R.id.newQuizButton);
        Button finishBtn = findViewById(R.id.finishButton);

        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 0);
        String userName = getIntent().getStringExtra("USER_NAME");

        scoreTV.setText(userName + "'s Score: " + score + "/" + total);

        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        nameET.setText(prefs.getString("userName", ""));

        newQuizBtn.setOnClickListener(v -> {
            prefs.edit().putString("userName", nameET.getText().toString()).apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        finishBtn.setOnClickListener(v -> finishAffinity());
    }
}