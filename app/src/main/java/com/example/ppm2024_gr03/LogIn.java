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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogIn extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton, signUpButton;
    private ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        connectionClass = new ConnectionClass();

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button);
        signUpButton = findViewById(R.id.signUpButton);

        connectToDatabase();

        loginButton.setOnClickListener(view -> validateFields());

        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUp.class);
            overridePendingTransition(R.xml.slide_left, R.xml.slide_right);
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
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    // Kontrolloni lidhjen me bazën e të dhënave
                    Connection connection = connectionClass.CONN();
                    if (connection != null) {
                        String query = "SELECT password FROM users WHERE email = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, email);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            String storedPassword = resultSet.getString("password");

                            // Kontrolloni fjalëkalimin
                            if (BCrypt.checkpw(password, storedPassword)) {
                                // Hyni me sukses, hapni HomePage
                                runOnUiThread(() -> {
                                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(this, HomePage.class);
                                    intent.putExtra("userEmail", email);
                                    startActivity(intent);
                                    overridePendingTransition(R.xml.slide_bottom, R.xml.fade_out);
                                    finish();
                                });
                            } else {
                                runOnUiThread(() -> {
                                    Toast.makeText(this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                                    Log.d("LogIn", "Invalid credentials for email: " + email);
                                });
                            }
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
                                Log.d("LogIn", "User not found for email: " + email);
                            });
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(this, "MySQL connection failed!", Toast.LENGTH_SHORT).show();
                            Log.d("LogIn", "MySQL connection failed.");
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Connection error!", Toast.LENGTH_SHORT).show());
                    Log.e("LogIn", "Error during database connection: " + e.getMessage());
                }
            });
        }
    }

    private void connectToDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                // Lidhja me MySQL
                Connection connection = connectionClass.CONN();
                if (connection != null) {
                    Log.d("LogIn", "Successfully connected to MySQL!");
                    runOnUiThread(() -> Toast.makeText(this, "Connected to MySQL!", Toast.LENGTH_SHORT).show());
                } else {
                    Log.d("LogIn", "MySQL connection failed.");
                    runOnUiThread(() -> Toast.makeText(this, "MySQL connection failed!", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                Log.e("LogIn", "Error during connection: " + e.getMessage());
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Connection failed!", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
