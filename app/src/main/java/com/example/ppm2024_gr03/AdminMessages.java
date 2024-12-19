package com.example.ppm2024_gr03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminMessages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_admin_page);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DB db = new DB(this);
        List<UserMessage> messageList = db.getAllMessages();

        // Set adapter
        MessageAdapter adapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(adapter);



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
                Intent intent = new Intent(AdminMessages.this, AdminPage.class); // Or the appropriate activity
                startActivity(intent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMessages.this, NewItem.class);
                startActivity(intent);
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the Contact activity
                Intent intent = new Intent(AdminMessages.this, AdminMessages.class);
                startActivity(intent);
            }
        });
    }
}
