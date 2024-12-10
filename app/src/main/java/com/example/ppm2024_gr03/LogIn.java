package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

public class LogIn extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton, signUpButton;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button);
        signUpButton = findViewById(R.id.signUpButton);

        db = new DB(this);

        loginButton.setOnClickListener(view -> validateFields());

        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });
    }

    private void validateFields() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
        } else {
            // Retrieve the stored password hash from the database
            String storedPassword = db.getPasswordByEmail(email);

            // Debugging logs
            Log.d("Debug", "Entered Email: " + email);
            Log.d("Debug", "Stored Hash: " + (storedPassword != null ? storedPassword : "null"));
            Log.d("Debug", "Entered Password: " + password);

            if (storedPassword != null) {
                boolean passwordMatch = BCrypt.checkpw(password, storedPassword);
                Log.d("Debug", "Password Match: " + passwordMatch);

                if (passwordMatch) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomePage.class);
                    intent.putExtra("userEmail", email);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                    Log.d("LogIn", "Password mismatch for email: " + email);
                }
            } else {
                Toast.makeText(this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                Log.d("LogIn", "User not found for email: " + email);
            }
        }
    }
}
