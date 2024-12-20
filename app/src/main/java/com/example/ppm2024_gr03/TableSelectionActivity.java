package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TableSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_selection);

        // Retrieve the username from Intent extras
        String userName = getIntent().getStringExtra("userName");
        Log.d("UserHomePage", "User name: " + userName);

        // Set a greeting message dynamically based on the username
        TextView greetingTextView = findViewById(R.id.greetingTextView);
        if (userName != null) {
            greetingTextView.setText("Hello, " + userName + "!");
        } else {
            greetingTextView.setText("Hello, User!");
        }
    }

    // Method to handle table selection
    public void onTableSelected(View view) {
        int tableId = Integer.parseInt(view.getTag().toString());

        // Log the table selection
        Log.d("TableSelection", "Selected Table ID: " + tableId);

        // Create an Intent to navigate to the AdminPage
        Intent intent = new Intent(this, AdminPage.class);
        intent.putExtra("TABLE_ID", tableId);
        startActivity(intent);
    }

    public void onProfileImageClick(View view) {
        // Navigate to the profile page when the profile image is clicked
        Intent intent = new Intent(this, ProfileActivity_Admin.class);
        startActivity(intent);
    }
}
