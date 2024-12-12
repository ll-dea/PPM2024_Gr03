package com.example.ppm2024_gr03;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        String userName = getIntent().getStringExtra("userName");

        Log.d("HomePage", "User name: " + userName);

        TextView welcomeText = findViewById(R.id.welcomeTextView);
        welcomeText.setText("Hello, " + userName + "!");
    }


}
