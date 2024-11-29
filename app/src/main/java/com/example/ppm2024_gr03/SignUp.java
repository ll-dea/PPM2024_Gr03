package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private EditText nameField, surnameField, emailField, phoneField, passwordField;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize fields and connection
        nameField = findViewById(R.id.editTextText);
        surnameField = findViewById(R.id.editTextText2);
        emailField = findViewById(R.id.editTextTextEmailAddress3);
        phoneField = findViewById(R.id.editTextPhone);
        passwordField = findViewById(R.id.editTextTextPassword2);
        Button signUpButton = findViewById(R.id.button3);
        Button backToLoginButton = findViewById(R.id.buttonBackToLogin);

        connectionClass = new ConnectionClass(); // Initialize database connection class

        // SignUp button listener
        signUpButton.setOnClickListener(v -> {
            if (validateFields()) {
                if (registerUser()) {
                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, LogIn.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Signup failed! Try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Back to Login button listener
        backToLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LogIn.class);
            startActivity(intent);
        });
    }

    // Validate user input fields
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
    private boolean registerUser() {
        String name = nameField.getText().toString().trim();
        String surname = surnameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        try {
            // Connect to the database
            Connection con = connectionClass.CONN();
            if (con != null) {
                // Check if user already exists in the database
                String checkQuery = "SELECT * FROM Users WHERE email = ?";
                PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // User exists
                    Log.d("DB_REGISTER", "User with this email already exists!");
                    Toast.makeText(this, "User with this email already exists!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                // Hash the password before storing it
                String hashedPassword = hashPassword(password);

                // Insert new user into the database
                String query = "INSERT INTO Users (username, email, phone, password) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, name + " " + surname); // Combine name and surname
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, hashedPassword); // Store hashed password

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    Log.d("DB_REGISTER", "User successfully registered!");
                    return true; // Signup successful
                } else {
                    Log.d("DB_REGISTER", "Failed to insert user.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB_REGISTER", "Error during user registration: " + e.getMessage());
        }
        return false;
    }

    // Hash the password using BCrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
