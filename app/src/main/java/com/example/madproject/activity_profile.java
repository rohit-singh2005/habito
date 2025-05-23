package com.example.madproject; // Replace with your actual package name

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_profile extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private CircleImageView ivProfilePic;
    private SwitchCompat switchNotification;
    private Button btnLogOut;
    private ConstraintLayout passwordOption, notificationOption, aboutUsOption, listingOption, hostingOption;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile); // Make sure this matches your layout file name

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        // Initialize views
        initializeViews();

        // Set up bottom navigation
        setupBottomNavigation();

        // Set up click listeners
        setupClickListeners();

        // Set notification switch state from saved preferences
        boolean notificationEnabled = sharedPreferences.getBoolean("notification_enabled", true);
        switchNotification.setChecked(notificationEnabled);

        // Set up notification switch listener
        setupNotificationSwitch();
    }

    private void initializeViews() {
        bottomNavigationView = findViewById(R.id.bottomNav);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        switchNotification = findViewById(R.id.switchNotification);
        btnLogOut = findViewById(R.id.btnLogOut);
        passwordOption = findViewById(R.id.passwordOption);
        notificationOption = findViewById(R.id.notificationOption);
        aboutUsOption = findViewById(R.id.aboutUsOption);
        listingOption = findViewById(R.id.ListingOption);
        hostingOption = findViewById(R.id.Hosting);
    }

    private void setupBottomNavigation() {
        // Set Profile item as selected/active
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        // Set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                // Check which item was clicked and navigate accordingly
                if (itemId == R.id.nav_home) {
                    // Navigate to Home activity
                    startActivity(new Intent(activity_profile.this, HomeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_orders) {
                    // Navigate to Orders activity
                    startActivity(new Intent(activity_profile.this,activity_orders.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    // Already on Profile activity
                    return true;
                }
                return false;
            }
        });
    }

    private void setupClickListeners() {
        // Password option click
        passwordOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Password change activity
                // TODO: Replace with your actual password activity
                Toast.makeText(activity_profile.this, "Password change option clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // About us option click
        aboutUsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to About us activity
                // TODO: Replace with your actual about us activity
                Toast.makeText(activity_profile.this, "About us option clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Listings option click
        listingOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Listings activity
                // TODO: Replace with your actual listings activity
                Toast.makeText(activity_profile.this, "Your listings option clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Hosting option click - Open ListingFormActivity
        hostingOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Listing Form activity
                Intent intent = new Intent(activity_profile.this, activity_listingform.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                // Clear any saved login info if necessary
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Navigate to LoginActivity
                Intent intent = new Intent(activity_profile.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear backstack
                startActivity(intent);
                finish(); // Finish current activity
            }
        });

        // Log out button click
    }

    private void setupNotificationSwitch() {
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save notification preference
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notification_enabled", isChecked);
                editor.apply();

                // Show feedback to user
                String message = isChecked ? "Notifications enabled" : "Notifications disabled";
                Toast.makeText(activity_profile.this, message, Toast.LENGTH_SHORT).show();

                // TODO: Update notification settings in your app
            }
        });
    }


    @Override
    public void onBackPressed() {
        // If Profile is not the selected item, select it
        if (bottomNavigationView.getSelectedItemId() != R.id.nav_profile) {
            bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        } else {
            super.onBackPressed();
        }
    }
}