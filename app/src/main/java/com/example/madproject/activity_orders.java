package com.example.madproject; // Replace with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activity_orders extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order); // Make sure this matches your layout file name

        // Initialize bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNav);

        // Set Orders item as selected/active
        bottomNavigationView.setSelectedItemId(R.id.nav_orders);

        // Set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();

                        // Check which item was clicked and navigate accordingly
                        if (itemId == R.id.nav_home) {
                            // Navigate to Home activity
                            startActivity(new Intent(activity_orders.this, HomeActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        } else if (itemId == R.id.nav_orders) {
                            // Already on Orders activity
                            return true;
                        } else if (itemId == R.id.nav_profile) {
                            // Navigate to Profile activity
                            startActivity(new Intent(activity_orders.this, activity_profile.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        // If Orders is not the selected item, select it
        if (bottomNavigationView.getSelectedItemId() != R.id.nav_orders) {
            bottomNavigationView.setSelectedItemId(R.id.nav_orders);
        } else {
            super.onBackPressed();
        }
    }
}