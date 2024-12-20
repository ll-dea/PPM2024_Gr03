package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity_Admin extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView userSurnameTextView;
    private TextView userPhoneTextView;
    private TextView emailTextView;
    private Button changePasswordButton;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_admin);

        // Initialize UI elements
        userNameTextView = findViewById(R.id.userNameTextView);
        userSurnameTextView = findViewById(R.id.userSurnameTextView);
        userPhoneTextView = findViewById(R.id.userPhoneTextView);
        emailTextView = findViewById(R.id.emailTextView);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        db = new DB(this);

        // Retrieve the logged-in admin's email from the intent
        String email = getIntent().getStringExtra("email");

        if (email != null) {
            Log.d("ProfileActivity_Admin", "Fetching data for logged-in admin: " + email);
            Admin admin = db.getAdminDetails(email);

            if (admin != null) {
                // Populate the UI with the admin's data
                userNameTextView.setText(admin.getName());
                userSurnameTextView.setText(admin.getSurname());
                userPhoneTextView.setText(admin.getPhone());
                emailTextView.setText(admin.getEmail());
            } else {
                Log.e("ProfileActivity_Admin", "Admin not found in the database for email: " + email);
            }
        } else {
            Log.e("ProfileActivity_Admin", "No email provided. Ensure the email is passed from the login activity.");
        }

        // Set up navigation buttons
        setupButtons(email);
    }

    private void setupButtons(String email) {
        // Home button setup
        Button homeButton = findViewById(R.id.buttonHome);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity_Admin.this, AdminPage.class);
            intent.putExtra("email", email); // Pass the email to the home page
            startActivity(intent);
        });

        // Logout button setup
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity_Admin.this, LogIn.class);
            startActivity(intent);
            finish(); // Clear the activity stack
        });

        // Change password button setup
        changePasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity_Admin.this, ChangePassword_Admin.class);
            intent.putExtra("email", email); // Pass the email to the change password activity
            startActivity(intent);
        });
    }
}
