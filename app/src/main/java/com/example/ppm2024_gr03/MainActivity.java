package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializimi i ConnectionClass


        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.icon);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, LogIn.class));
            overridePendingTransition(R.xml.slide_bottom, R.xml.fade_out);
            finish();
        }, 4000);
    }


}
