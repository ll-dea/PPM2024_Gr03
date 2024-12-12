package com.example.ppm2024_gr03;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);

        String userName = getIntent().getStringExtra("adminName");

        Log.d("Admin-HomePage", "User name: " + userName);

        TextView welcomeText = findViewById(R.id.welcomeTextView);
        welcomeText.setText("Hello, " + userName + "!");
    }
}
