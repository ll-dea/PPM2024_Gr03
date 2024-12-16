package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ContactPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactpage);



        // Set up the buttons and their listeners
        Button homeButton = findViewById(R.id.btn_1);
        Button buyButton = findViewById(R.id.btn_2);
        Button contactButton = findViewById(R.id.btn_3);

        // Set onClick listeners for each button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the home activity
                // You can start a new activity or update the UI
                Intent intent = new Intent(ContactPage.this, HomePage.class); // Or the appropriate activity
                startActivity(intent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactPage.this, BuyPage.class);
                startActivity(intent);
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the Contact activity
                Intent intent = new Intent(ContactPage.this, ContactPage.class);
                startActivity(intent);
            }
        });
    }
}