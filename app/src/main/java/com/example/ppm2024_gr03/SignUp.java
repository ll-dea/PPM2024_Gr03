package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private EditText nameField, surnameField, emailField, phoneField, passwordField;
    DB DB;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize fields
        nameField = findViewById(R.id.signupName);
        surnameField = findViewById(R.id.signupSurname);
        emailField = findViewById(R.id.signupEmail);
        phoneField = findViewById(R.id.signupPhone);
        passwordField = findViewById(R.id.signupPassword);
        Button signUpButton = findViewById(R.id.signup);
        loginButton=findViewById(R.id.loginButton);


        DB=new DB(this);
        // Set onClick listener for the SignUp button
        signUpButton.setOnClickListener(v -> {

            if (validateFields()) {

                String name = nameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                String surname = surnameField.getText().toString().trim();
                String phone = phoneField.getText().toString().trim();

                // Proceed with signup logic
                if (DB.checkAdminEmail(email)) {
                    Toast.makeText(this, "This user already exists.", Toast.LENGTH_SHORT).show();
                } else if (DB.insertAdminUser(email,password,name,surname,phone)) {
                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to register.", Toast.LENGTH_SHORT).show();
                }
                Log.d("SignUp", "Button clicked");
                Log.d("SignUp", "Email: " + email);
            }
        });
        loginButton.setOnClickListener(view->{
            Intent intent=new Intent(SignUp.this, LogIn.class);
            startActivity(intent);
            overridePendingTransition(R.xml.slide_right, R.xml.slide_left);
        });


        // Handle system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validateFields() {
        // Retrieve values
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String surname = surnameField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();

        // Validate Name (only alphabets, not empty)
        if (TextUtils.isEmpty(name) || !name.matches("[a-zA-Z]+")) {
            nameField.setError("Name must contain only letters and not be empty.");
            return false;
        }

        // Validate Surname (only alphabets, not empty)
        if (TextUtils.isEmpty(surname) || !surname.matches("[a-zA-Z]+")) {
            surnameField.setError("Surname must contain only letters and not be empty.");
            return false;
        }

        // Validate Email (non-empty and valid format)
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("Enter a valid email address.");
            return false;
        }

        // Validate Phone (non-empty, valid format)
        if (TextUtils.isEmpty(phone) || !phone.matches("\\d{10,15}")) {
            phoneField.setError("Enter a valid phone number (10-15 digits).");
            return false;
        }

        // Validate Password
        // At least 1 lowercase, 1 uppercase, 1 digit, 1 special character, 6-20 characters, no spaces
        if (TextUtils.isEmpty(password) ||
                !Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{6,20}$").matcher(password).matches()) {
            passwordField.setError("Password must contain 1 lowercase, 1 uppercase, 1 digit, 1 special character, and be 6-20 characters long.");
            return false;
        }

        // If all validations pass
        return true;
    }
}