package com.example.ppm2024_gr03;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BuyPage extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.buypage);

            // Example: Set an onClickListener for one of the buttons
            findViewById(R.id.buyCoffeeButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BuyPage.this, "Coffee purchased!", Toast.LENGTH_SHORT).show();
                }
            });

            findViewById(R.id.buyPieButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BuyPage.this, "Pie purchased!", Toast.LENGTH_SHORT).show();
                }
            });



            // Set up the buttons and their listeners
            Button homeButton = findViewById(R.id.buttonHome);
            Button buyButton = findViewById(R.id.buttonMenu);
            Button contactButton = findViewById(R.id.buttonContact);

            // Set onClick listeners for each button
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // For example, navigate to the home activity
                    // You can start a new activity or update the UI
                    Intent intent = new Intent(BuyPage.this, HomePage.class); // Or the appropriate activity
                    startActivity(intent);
                }
            });

            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BuyPage.this, BuyPage.class);
                    startActivity(intent);
                }
            });

            contactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // For example, navigate to the Contact activity
                    Intent intent = new Intent(BuyPage.this, HomePage.class);
                    startActivity(intent);
                }
            });
        }
    }