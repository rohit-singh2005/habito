package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_details extends AppCompatActivity {

    private ImageView hotelImageView;
    private TextView hotelTitle;
    private TextView hotelLocation;
    private TextView descriptionText;
    private TextView pricePerNight;
    private ImageButton backButton;
    private Button selectRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        // Initialize views
        initViews();

        // Set up data (you can get this from intent or a data source)
        setupHotelData();

        // Set up click listeners
        setupClickListeners();
    }

    private void initViews() {
        hotelImageView = findViewById(R.id.hotelImageView);
        hotelTitle = findViewById(R.id.hotelTitle);
        hotelLocation = findViewById(R.id.hotelLocation);
        descriptionText = findViewById(R.id.descriptionText);
        pricePerNight = findViewById(R.id.pricePerNight);
        backButton = findViewById(R.id.backButton);
        selectRoomButton = findViewById(R.id.selectRoomButton);
    }

    private void setupHotelData() {
        // If there's data passed from previous activity
        // Intent intent = getIntent();
        // String hotelName = intent.getStringExtra("HOTEL_NAME");
        // hotelTitle.setText(hotelName);

        // For now, we'll use the hardcoded values from the layout
    }

    private void setupClickListeners() {
        // Back button - return to previous activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simply finish the current activity to go back
                finish();
            }
        });

        // Select Room button - go to payment page
        selectRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to navigate to Payment page
                Intent paymentIntent = new Intent(activity_details.this,PaymentActivity.class);

                // You can pass hotel details to payment page if needed
                paymentIntent.putExtra("HOTEL_NAME", hotelTitle.getText().toString());
                paymentIntent.putExtra("PRICE_PER_NIGHT", pricePerNight.getText().toString());

                // Start the Payment Activity
                startActivity(paymentIntent);
            }
        });
    }
}