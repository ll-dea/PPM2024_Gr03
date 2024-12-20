package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

public class ChangePassword_Admin extends AppCompatActivity {

    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button changePasswordButton;

    private DB db;  // Create an instance of the DB class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Initialize EditText fields and DB helper
        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        db = new DB(this);  // Initialize DB

        // Set up the Change Password button click listener
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Verifikimi i inputeve
                if (TextUtils.isEmpty(oldPassword)) {
                    oldPasswordEditText.setError("Fjalëkalimi i vjetër është i kërkuar");
                    return;
                }

                if (TextUtils.isEmpty(newPassword)) {
                    newPasswordEditText.setError("Fjalëkalimi i ri është i kërkuar");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    confirmPasswordEditText.setError("Ju lutem konfirmoni fjalëkalimin e ri");
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("Fjalëkalimet nuk përputhen");
                    return;
                }

                // Merr emailin e përdoruesit të loguar
                String email = db.getUserEmail();

                // Verifikimi i fjalëkalimit të vjetër
                if (db.verifyOldPassword_Admin(email, oldPassword)) {
                    // Përdor funksionin për të përditësuar fjalëkalimin
                    boolean isUpdated = db.updatePassword_Admin(email, newPassword);
                    if (isUpdated) {
                        Toast.makeText(ChangePassword_Admin.this, "Fjalëkalimi është ndryshuar me sukses", Toast.LENGTH_SHORT).show();
                        finish();  // Mbyll aktivitetin dhe kthehu në ekranin paraprak
                    } else {
                        Toast.makeText(ChangePassword_Admin.this, "Dështoi përditësimi i fjalëkalimit", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePassword_Admin.this, "Fjalëkalimi i vjetër është gabim", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePassword_Admin.this, ProfileActivity_User.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();  // Close the database connection when the activity is destroyed
    }
}
