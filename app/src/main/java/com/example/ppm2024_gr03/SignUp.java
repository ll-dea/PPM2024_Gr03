package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private EditText nameField, surnameField, emailField, phoneField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize fields
        nameField = findViewById(R.id.editTextText);
        surnameField = findViewById(R.id.editTextText2);
        emailField = findViewById(R.id.editTextTextEmailAddress3);
        phoneField = findViewById(R.id.editTextPhone);
        passwordField = findViewById(R.id.editTextTextPassword2);
        Button signUpButton = findViewById(R.id.button3);
        Button backToLoginButton = findViewById(R.id.buttonBackToLogin);

        // Set onClick listener for the SignUp button
        signUpButton.setOnClickListener(v -> {
            Log.d("SignUp", "SignUp button clicked");
            if (validateFields()) {
                // Proceed with signup logic
                Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Signup failed! Please check the fields.", Toast.LENGTH_SHORT).show();
            }
        });

        backToLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LogIn.class); // Replace with your login activity class
            overridePendingTransition(R.xml.slide_right, R.xml.slide_left);

            startActivity(intent);

        });
    }

    private boolean validateFields() {
        String name = nameField.getText().toString().trim();
        String surname = surnameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (TextUtils.isEmpty(name) || !name.matches("[a-zA-Z]+")) {
            nameField.setError("Name must contain only letters and not be empty.");
            return false;
        }

        if (TextUtils.isEmpty(surname) || !surname.matches("[a-zA-Z]+")) {
            surnameField.setError("Surname must contain only letters and not be empty.");
            return false;
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("Enter a valid email address.");
            return false;
        }

        if (TextUtils.isEmpty(phone) || !phone.matches("\\d{10,15}")) {
            phoneField.setError("Enter a valid phone number (10-15 digits).");
            return false;
        }

        if (TextUtils.isEmpty(password) ||
                !Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{6,20}$").matcher(password).matches()) {
            passwordField.setError("Password must contain 1 lowercase, 1 uppercase, 1 digit, 1 special character, and be 6-20 characters long.");
            return false;
        }

        return true;
    }
}
