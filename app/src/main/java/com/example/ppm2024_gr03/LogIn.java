package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class LogIn extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private  Button signUpButton;

    DB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_main);

        emailEditText=findViewById(R.id.loginEmail);
        passwordEditText=findViewById(R.id.loginPassword);
        loginButton=findViewById(R.id.loginButton);
        signUpButton=findViewById(R.id.signup);


        DB=new DB(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton.setOnClickListener(view->validateFields());

        signUpButton.setOnClickListener(view->{
            Intent intent=new Intent(LogIn.this, SignUp.class);
            startActivity(intent);
            overridePendingTransition(R.xml.slide_right, R.xml.slide_left);
        });


    }
    private void validateFields() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
        } else {
            // Verifiko përdoruesin
            Boolean isValidUser = DB.validateUser(email, password);
            if (isValidUser) {
                boolean isAdmin = DB.checkAdminEmail(email); // Kontrollo nëse është admin
                if (isAdmin) {
                    // Hap faqen e admin-it
                    String userName = DB.getUserName(email);
                    Toast.makeText(this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogIn.this,TableSelectionActivity.class);
                    intent.putExtra("adminEmail", email);
                    intent.putExtra("adminName", userName);

                    startActivity(intent);
                } else {
                    // Hap faqen e përdoruesit të thjeshtë
                    String userName = DB.getUserName(email); // Merr emrin e përdoruesit
                    Toast.makeText(this, "Welcome " + userName + "!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogIn.this, UserHomePage.class);
                    intent.putExtra("userEmail", email);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Wrong Credentials!", Toast.LENGTH_SHORT).show();
            }
        }
}
}