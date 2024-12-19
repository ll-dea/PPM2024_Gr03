package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private Button savePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Initialize views
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        savePasswordButton = findViewById(R.id.savePasswordButton);

        // Handle save password button click
        savePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if passwords match and save new password (logic for saving the password is omitted)
                String currentPassword = currentPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the new password (you can add your logic here)
                // For now, we show a success message
                Toast.makeText(ChangePasswordActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Button backButton = findViewById(R.id.BackButton);

        // Set onClick listeners for each button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the home activity
                // You can start a new activity or update the UI
                Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity_User.class); // Or the appropriate activity
                startActivity(intent);
            }
        });

    }
}
