package com.example.ppm2024_gr03;
import android.os.Bundle;
import android.view.View;
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
        }
    }