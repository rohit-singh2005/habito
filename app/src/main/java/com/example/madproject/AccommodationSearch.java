package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AccommodationSearch extends AppCompatActivity implements AccommodationAdapter.OnAccommodationClickListener {

    private static final String TAG = "AccommodationSearch";

    private RecyclerView recyclerView;
    private AccommodationAdapter adapter;
    private List<Accommodation> accommodationList;
    private BottomNavigationView bottomNavigationView;
    private EditText searchBar;

    private Button btnPrice;
    private Button btnRooms;
    private Button btnRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        recyclerView = findViewById(R.id.recycler_view_accommodations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();

        searchBar = findViewById(R.id.search_bar);
        btnPrice = findViewById(R.id.btn_price);
        btnRooms = findViewById(R.id.btn_rooms);
        btnRatings = findViewById(R.id.btn_ratings);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "Search text changed: " + s.toString());
                // Implement filtering here if needed
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPricePopupMenu(v);
            }
        });

        btnRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoomsPopupMenu(v);
            }
        });

        btnRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingsPopupMenu(v);
            }
        });

        // Sample data
        accommodationList = new ArrayList<>();
        accommodationList.add(new Accommodation(R.drawable.homestay_five, "Taj Mahal", "Taj East Gate Road", "₹2,500 to ₹3,000 per night"));
        accommodationList.add(new Accommodation(R.drawable.homestay_six, "Oberoi Villa", "Near Shilp Gram, Taj Mahal Eastern Gate", "₹3,000 to ₹3,500 per night"));
        accommodationList.add(new Accommodation(R.drawable.homestay_three, "Atulyaa Homes Taj", "2/183 West Gate Taj Mahal", "₹1,800 to ₹2,500 per night"));
        accommodationList.add(new Accommodation(R.drawable.homestay_four, "Hotel Siddharth Homestay", "Taj Mahal Eastern Gate, Shilp Gram VIP Road", "₹1,500 to ₹1,800 per night"));

        adapter = new AccommodationAdapter(accommodationList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupBottomNavigation() {
//        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(AccommodationSearch.this, HomeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_orders) {
                    startActivity(new Intent(AccommodationSearch.this, activity_orders.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    startActivity(new Intent(AccommodationSearch.this, activity_profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(Accommodation accommodation) {
        Intent intent = new Intent(this, RoomPreview.class);
        intent.putExtra("title", accommodation.getTitle());
        intent.putExtra("location", accommodation.getLocation());
        intent.putExtra("price", accommodation.getPriceRange());
        intent.putExtra("image_resource", accommodation.getImageResource());
        startActivity(intent);

        Toast.makeText(this, "Clicked: " + accommodation.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void showPricePopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.price_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handlePriceFilterSelection(item.getItemId());
                return true;
            }
        });

        popupMenu.show();
    }

    private void showRoomsPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.rooms_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleRoomsFilterSelection(item.getItemId());
                return true;
            }
        });

        popupMenu.show();
    }

    private void showRatingsPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.ratings_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleRatingsFilterSelection(item.getItemId());
                return true;
            }
        });

        popupMenu.show();
    }

    private void handlePriceFilterSelection(int itemId) {
        String selectedFilter = "";
        if (itemId == R.id.price_low_to_high) {
            selectedFilter = "Price: Low to High";
        } else if (itemId == R.id.price_high_to_low) {
            selectedFilter = "Price: High to Low";
        } else if (itemId == R.id.price_range_1) {
            selectedFilter = "Price: ₹0 - ₹2000";
        } else if (itemId == R.id.price_range_2) {
            selectedFilter = "Price: ₹2000 - ₹5000";
        }
        Toast.makeText(this, "Selected: " + selectedFilter, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Price Filter Selected: " + selectedFilter);
    }

    private void handleRoomsFilterSelection(int itemId) {
        String selectedFilter = "";
        if (itemId == R.id.rooms_any) {
            selectedFilter = "Rooms: Any";
        } else if (itemId == R.id.rooms_1) {
            selectedFilter = "Rooms: 1";
        } else if (itemId == R.id.rooms_2) {
            selectedFilter = "Rooms: 2";
        } else if (itemId == R.id.rooms_3_plus) {
            selectedFilter = "Rooms: 3+";
        }
        Toast.makeText(this, "Selected: " + selectedFilter, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Rooms Filter Selected: " + selectedFilter);
    }

    private void handleRatingsFilterSelection(int itemId) {
        String selectedFilter = "";
        if (itemId == R.id.rating_4_plus) {
            selectedFilter = "Ratings: 4+ Stars";
        } else if (itemId == R.id.rating_3_plus) {
            selectedFilter = "Ratings: 3+ Stars";
        } else if (itemId == R.id.rating_any) {
            selectedFilter = "Ratings: Any";
        }
        Toast.makeText(this, "Selected: " + selectedFilter, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Ratings Filter Selected: " + selectedFilter);
    }
}
