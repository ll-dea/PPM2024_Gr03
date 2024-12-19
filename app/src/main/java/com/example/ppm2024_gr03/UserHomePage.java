package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserHomePage extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Retrieve the username from Intent extras
        String userName = getIntent().getStringExtra("userName");
        Log.d("UserHomePage", "User name: " + userName);

        // Update the greeting text
        TextView greetingText = findViewById(R.id.greetingTextView);
        if (userName != null && !userName.isEmpty()) {
            greetingText.setText("Hello, " + userName + "!");
        } else {
            greetingText.setText("Hello, Guest!");
        }

        // Initialize the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Set up the buttons and their listeners
        Button homeButton = findViewById(R.id.button1);
        Button buyButton = findViewById(R.id.button2);
        Button contactButton = findViewById(R.id.button3);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomePage.this, UserHomePage.class);
            startActivity(intent);
        });

        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomePage.this, UserMenu.class);
            startActivity(intent);
        });

        contactButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomePage.this, UserContact.class);
            startActivity(intent);
        });


    }
    public void onProfileImageClick(View view) {
        // Handle the click action, e.g., navigate to the profile page
        Intent intent = new Intent(this, ProfileActivity_User.class);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set initial map location to Prishtina
        LatLng prishtina = new LatLng(42.6629, 21.1655);
        mMap.addMarker(new MarkerOptions().position(prishtina).title("Marker in Prishtina"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(prishtina, 15));
    }
}
