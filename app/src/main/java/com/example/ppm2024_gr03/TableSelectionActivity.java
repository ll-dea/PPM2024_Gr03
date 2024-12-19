package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ppm2024_gr03.AdminPage;
import com.example.ppm2024_gr03.R;

public class TableSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_selection);
    }

    // Method to handle table selection
    public void onTableSelected(View view) {
        int tableId = Integer.parseInt(view.getTag().toString());

        Intent intent = new Intent(this, AdminPage.class);
        intent.putExtra("TABLE_ID", tableId);
        startActivity(intent);
    }
}
