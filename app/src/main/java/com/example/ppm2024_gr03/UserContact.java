package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_page);

        DB dbHelper = new DB(this);

        Button sendButton = findViewById(R.id.btn_send);
        EditText etName = findViewById(R.id.et_name);
        EditText etEmail = findViewById(R.id.et_email);
        EditText etMessage = findViewById(R.id.et_message);

        sendButton.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.insertMessage(name, email, message);
            if (result != -1) {
                Toast.makeText(this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etEmail.setText("");
                etMessage.setText("");
            } else {
                Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the buttons and their listeners
        Button homeButton = findViewById(R.id.Btn1);
        Button buyButton = findViewById(R.id.Btn2);
        Button contactButton = findViewById(R.id.Btn3);

        // Set onClick listeners for each button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the home activity
                // You can start a new activity or update the UI
                Intent intent = new Intent(UserContact.this, UserHomePage.class); // Or the appropriate activity
                startActivity(intent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserContact.this, UserMenu.class);
                startActivity(intent);
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the Contact activity
                Intent intent = new Intent(UserContact.this, UserContact.class);
                startActivity(intent);
            }
        });
    }
}
