package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ppm2024_gr03.AdminPage;
import com.example.ppm2024_gr03.R;

public class TableSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_selection);


        // Retrieve the username from Intent extras
        String userName = getIntent().getStringExtra("userName");
        Log.d("UserHomePage", "User name: " + userName);

    }
    // Method to handle table selection
    public void onTableSelected(View view) {
        int tableId = Integer.parseInt(view.getTag().toString());

        Intent intent = new Intent(this, AdminPage.class);
        intent.putExtra("TABLE_ID", tableId);
        startActivity(intent);
    }

    public void onProfileImageClick(View view) {
        // Handle the click action, e.g., navigate to the profile page
        Intent intent = new Intent(this, ProfileActivity_User.class);
        startActivity(intent);
    }
}
