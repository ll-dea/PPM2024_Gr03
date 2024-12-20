package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity_Admin extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView emailTextView;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);

        // Initialize UI components
        userNameTextView = findViewById(R.id.userNameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        // Assuming you are passing the user's name and email via Intent
        String userName = getIntent().getStringExtra("userName");
        String email = getIntent().getStringExtra("email");

        // Set the TextViews with the received data
        if (userName != null) {
            userNameTextView.setText(userName);
        }
        if (email != null) {
            emailTextView.setText(email);
        }

        // Set up the change password button click listener
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Change Password activity
                Intent intent = new Intent(ProfileActivity_Admin.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });


        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity_Admin.this, LogIn.class);
            startActivity(intent);
            finish();
        });
    }
}
