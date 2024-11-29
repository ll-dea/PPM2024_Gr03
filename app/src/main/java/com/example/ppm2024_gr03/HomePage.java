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

        // Merrni email-in e përdoruesit nga intent
        String userEmail = getIntent().getStringExtra("userEmail");

        // Log për të parë nëse kalimi po ndodh
        Log.d("HomePage", "User email: " + userEmail);

        TextView welcomeText = findViewById(R.id.welcomeTextView);
        welcomeText.setText("Welcome, " + userEmail + "!");
    }

}
