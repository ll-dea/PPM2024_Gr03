package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

public class UserHomePage extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        String userName = getIntent().getStringExtra("userName");

        Log.d("UserHomePage", "User name: " + userName);

        TextView greetingText = findViewById(R.id.greetingTextView);
        if (userName != null && !userName.isEmpty()) {
            greetingText.setText("Hello, " + userName + "!");
        } else {
            greetingText.setText("Hello, Guest!");
        }

        // Initialize the MapView
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Set the center of the map to a specific location (e.g., coordinates for London)
        GeoPoint startPoint = new GeoPoint(51.5074, -0.1278);
        IMapController mapController = mapView.getController();
        mapController.setZoom(10);
        mapController.setCenter(startPoint);

        // Add a marker to the map
        Marker marker = new Marker(mapView);
        marker.setPosition(startPoint);
        marker.setTitle("Marker in Prishtina");
        mapView.getOverlays().add(marker);

        // Set up the buttons and their listeners
        Button homeButton = findViewById(R.id.button1);
        Button buyButton = findViewById(R.id.button2);
        Button contactButton = findViewById(R.id.button3);
        Button logoutButton = findViewById(R.id.logoutButton);

        // Set onClick listeners for each button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the home activity
                // You can start a new activity or update the UI
                Intent intent = new Intent(UserHomePage.this, UserHomePage.class); // Or the appropriate activity
                startActivity(intent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomePage.this, UserMenu.class);
                startActivity(intent);
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the Contact activity
                Intent intent = new Intent(UserHomePage.this, UserContact.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log out the user and possibly go back to a login screen
                Intent intent = new Intent(UserHomePage.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDetach();
    }
}
