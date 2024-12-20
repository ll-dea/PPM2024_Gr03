package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity_User extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView userSurnameTextView;
    private TextView userPhoneTextView;
    private TextView emailTextView;
    private Button changePasswordButton;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);

        // Initialize UI components
        userNameTextView = findViewById(R.id.userNameTextView);
        userSurnameTextView = findViewById(R.id.userSurnameTextView);
        userPhoneTextView = findViewById(R.id.userPhoneTextView);
        emailTextView = findViewById(R.id.emailTextView);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        // Initialize the database helper
        db = new DB(this);

        // Assuming you are passing the admin email via Intent
        String email = getIntent().getStringExtra("email");

        if (email != null) {
            // Query admin details from the database
            User user = db.getUserDetails(email);

            if (user != null) {
                // Set the TextViews with the retrieved data
                userNameTextView.setText(user.getName());
                userSurnameTextView.setText(user.getSurname());
                userPhoneTextView.setText(user.getPhone());
                emailTextView.setText(user.getEmail());
            }
        }


        Button homeButton = findViewById(R.id.buttonHome);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity_User.this, UserHomePage.class);
            startActivity(intent);
        });

        // Set up the change password button click listener
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Change Password activity
                Intent intent = new Intent(ProfileActivity_User.this, ChangePasswordActivity.class);
                intent.putExtra("email", email); // Pass email to the next activity
                startActivity(intent);
            }
        });


        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity_User.this, LogIn.class);
            startActivity(intent);
            finish();
        });
    }
}
