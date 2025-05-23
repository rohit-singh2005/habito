package com.example.madproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    EditText locationInput, dateInput, guestsInput;
    Button searchButton;

    RecyclerView popularCitiesRecycler, favoritePlacesRecycler;
    CityAdapter cityAdapter;
    PlaceAdapter placeAdapter;
    List<City> cityList;
    List<Place> placeList;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inputs and search
        locationInput = findViewById(R.id.locationInput);
        dateInput = findViewById(R.id.dateInput);
        guestsInput = findViewById(R.id.guestsInput);
        searchButton = findViewById(R.id.searchButton);

        // Date Picker Dialog
        dateInput.setFocusable(false);
        dateInput.setClickable(true);
        dateInput.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    HomeActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateInput.setText(selectedDate);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        searchButton.setOnClickListener(v -> {
            String location = locationInput.getText().toString();
            String date = dateInput.getText().toString();
            String guests = guestsInput.getText().toString();
            Intent intent = new Intent(HomeActivity.this, AccommodationSearch.class);
            startActivity(intent);
            finish();
        });

        // RecyclerViews
        popularCitiesRecycler = findViewById(R.id.popularCitiesRecycler);
        favoritePlacesRecycler = findViewById(R.id.favoritePlacesRecyclerView);

        // Setup Popular Cities
        cityList = new ArrayList<>();
        cityList.add(new City(R.drawable.kolkata, "Kolkata"));
        cityList.add(new City(R.drawable.gulmarg, "Jammu&Kashmir"));
        cityList.add(new City(R.drawable.jaipur, "Jaipur"));
        cityList.add(new City(R.drawable.srinagar, "Jammu&Kashmir"));

        cityAdapter = new CityAdapter(cityList);
        popularCitiesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularCitiesRecycler.setAdapter(cityAdapter);

        // Setup Favorite Places
        placeList = new ArrayList<>();
        placeList.add(new Place(R.drawable.gulmarg, "Gulmarg"));
        placeList.add(new Place(R.drawable.jaipur, "Amber Palace"));
        placeList.add(new Place(R.drawable.kolkata, "Victoria Memorial"));
        placeList.add(new Place(R.drawable.dal_lake, "Dal Lake"));

        placeAdapter = new PlaceAdapter(placeList);
        favoritePlacesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        favoritePlacesRecycler.setAdapter(placeAdapter);

        // Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    // Already on Home
                    return true;
                } else if (itemId == R.id.nav_orders) {
                    startActivity(new Intent(HomeActivity.this, activity_orders.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    startActivity(new Intent(HomeActivity.this, activity_profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }
}
