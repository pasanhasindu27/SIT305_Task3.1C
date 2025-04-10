package com.example.quiztutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class NameEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry);

        EditText nameEditText = findViewById(R.id.nameEditText);
        Button startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            if (!name.isEmpty()) {
                SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
                prefs.edit().putString("userName", name).apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                nameEditText.setError("Please enter your name");
            }
        });
    }
}