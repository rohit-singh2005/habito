package com.example.madproject; // Replace with your package name

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RoomPreview extends AppCompatActivity {

    private ImageView roomImageView;
    private TextView titleTextView;
    private TextView locationTextView;
    private FloatingActionButton fabNext;
    // You might also want references to the indicator views if you make them interactive

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_preview);

        FirebaseApp.initializeApp(this);
        // Get references to views
        roomImageView = findViewById(R.id.image_room);
        titleTextView = findViewById(R.id.text_view_title);
        locationTextView = findViewById(R.id.text_view_location);
        fabNext = findViewById(R.id.fab_next);

        // --- Retrieve data passed from AccommodationSearch ---
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            String location = extras.getString("location");
            String price = extras.getString("price"); // You might use price elsewhere in preview
            int imageResource = extras.getInt("image_resource", 0); // 0 is a default value if not found

            if (title != null) {
                titleTextView.setText(title);
            }
            if (location != null) {
                locationTextView.setText(location);
            }
            if (imageResource != 0) {
                roomImageView.setImageResource(imageResource);
            } else {
                // Set a default image or hide the ImageView if no image resource is passed
                roomImageView.setImageResource(R.drawable.room_previewone);
            }

            // You could use the price data here as well if your room_preview.xml had a TextView for price
        }
        // --- End retrieve data ---


        // Set click listener for the Floating Action Button
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the action when the button is clicked
                // e.g., navigate to a booking screen
                Intent intent = new Intent(RoomPreview.this, activity_details.class);
                startActivity(intent);
                finish();
            }
        });

        // You would typically handle indicator updates here if you have multiple images
    }
}