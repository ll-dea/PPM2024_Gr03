package com.example.ppm2024_gr03;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

public class ChangePasswordActivity extends AppCompatActivity {

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

        // Get the email of the user (for example from SharedPreferences or passed from the previous screen)
        String email = "user@example.com";  // This is just an example, you should fetch the email dynamically

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
                if (db.verifyOldPassword(email, oldPassword)) {
                    // Përdor funksionin për të përditësuar fjalëkalimin
                    boolean isUpdated = db.updatePassword(email, newPassword);
                    if (isUpdated) {
                        Toast.makeText(ChangePasswordActivity.this, "Fjalëkalimi është ndryshuar me sukses", Toast.LENGTH_SHORT).show();
                        finish();  // Mbyll aktivitetin dhe kthehu në ekranin paraprak
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Dështoi përditësimi i fjalëkalimit", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Fjalëkalimi i vjetër është gabim", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();  // Close the database connection when the activity is destroyed
    }
}
