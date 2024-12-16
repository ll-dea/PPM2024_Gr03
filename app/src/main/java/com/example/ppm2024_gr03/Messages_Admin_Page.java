package com.example.ppm2024_gr03;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Messages_Admin_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_admin_page);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DB db = new DB(this);
        List<UserMessage> messageList = db.getAllMessages();

        // Set adapter
        MessageAdapter adapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(adapter);
    }
}
