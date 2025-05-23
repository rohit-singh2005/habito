package com.example.madproject;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class activity_listingform extends AppCompatActivity implements OnMapReadyCallback {

    // Property type selection cards
    private MaterialCardView cardHouse;
    private MaterialCardView cardApartment;
    private MaterialCardView cardGuestHouse;
    private MaterialCardView cardHotel;
    private MaterialCardView cardHouseboat;
    private MaterialCardView cardTent;

    // Place type selection cards
    private MaterialCardView cardEntirePlace;
    private MaterialCardView cardPrivateRoom;
    private MaterialCardView cardSharedRoom;

    // Buttons
    private Button btnSaveExit;
    private Button btnLooksGood;
    private Button btnSaveContinue;

    // Address inputs
    private EditText etAddress;
    private EditText etCountry;
    private EditText etFlatHouse;
    private EditText etStreet;
    private EditText etLandmark;
    private EditText etDistrict;
    private EditText etCity;
    private EditText etState;
    private EditText etPincode;

    // Photo containers
    private FrameLayout containerCoverPhoto;
    private FrameLayout containerRoomPhotos;

    // Price input
    private EditText etPricePerNight;

    // Facility checkboxes
    private CheckBox cbWifi;
    private CheckBox cbAc;
    private CheckBox cbKitchen;
    private CheckBox cbParking;
    private CheckBox cbPool;
    private CheckBox cbGym;

    // Selected property and place type trackers
    private MaterialCardView selectedPropertyTypeCard = null;
    private MaterialCardView selectedPlaceTypeCard = null;

    // Image URIs
    private Uri coverPhotoUri = null;
    private ArrayList<Uri> roomPhotoUris = new ArrayList<>();

    // Google Map
    private GoogleMap mMap;

    // Activity result launcher for cover photo
    private final ActivityResultLauncher<String> coverPhotoLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    coverPhotoUri = uri;
                    containerCoverPhoto.setBackgroundColor(Color.parseColor("#E0E0E0"));
                    Toast.makeText(this, "Cover photo selected", Toast.LENGTH_SHORT).show();
                }
            });

    // Activity result launcher for room photos
    private final ActivityResultLauncher<String> roomPhotoLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    roomPhotoUris.add(uri);
                    containerRoomPhotos.setBackgroundColor(Color.parseColor("#E0E0E0"));
                    Toast.makeText(this, roomPhotoUris.size() + " room photos selected", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listingform);

        // Initialize views
        initViews();

        // Setup map
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(this);
//        }

        // Setup click listeners
        setupClickListeners();
    }

    private void initViews() {
        // Property type cards
        cardHouse = findViewById(R.id.card_house);
        cardApartment = findViewById(R.id.card_apartment);
        cardGuestHouse = findViewById(R.id.card_guest_house);
        cardHotel = findViewById(R.id.card_hotel);
        cardHouseboat = findViewById(R.id.card_houseboat);
        cardTent = findViewById(R.id.card_tent);

        // Place type cards
        cardEntirePlace = findViewById(R.id.card_entire_place);
        cardPrivateRoom = findViewById(R.id.card_private_room);
        cardSharedRoom = findViewById(R.id.card_shared_room);

        // Buttons
        btnSaveExit = findViewById(R.id.btn_save_exit);
        btnLooksGood = findViewById(R.id.btn_looks_good);
        btnSaveContinue = findViewById(R.id.btn_save_continue);

        // Address inputs
        etAddress = findViewById(R.id.et_address);
        etCountry = findViewById(R.id.et_country);
        etFlatHouse = findViewById(R.id.et_flat_house);
        etStreet = findViewById(R.id.et_street);
        etLandmark = findViewById(R.id.et_landmark);
        etDistrict = findViewById(R.id.et_district);
        etCity = findViewById(R.id.et_city);
        etState = findViewById(R.id.et_state);
        etPincode = findViewById(R.id.et_pincode);

        // Photo containers
        containerCoverPhoto = findViewById(R.id.container_cover_photo);
        containerRoomPhotos = findViewById(R.id.container_room_photos);

        // Price input
        etPricePerNight = findViewById(R.id.et_price_per_night);

        // Facility checkboxes
        cbWifi = findViewById(R.id.cb_wifi);
        cbAc = findViewById(R.id.cb_ac);
        cbKitchen = findViewById(R.id.cb_kitchen);
        cbParking = findViewById(R.id.cb_parking);
        cbPool = findViewById(R.id.cb_pool);
        cbGym = findViewById(R.id.cb_gym);
    }

    private void setupClickListeners() {
        // Property type selection
        setupPropertyTypeSelection();

        // Place type selection
        setupPlaceTypeSelection();

        // Save & Exit button
        btnSaveExit.setOnClickListener(v -> {
            saveListingData();
            Intent intent = new Intent(activity_listingform.this, activity_details.class);
            startActivity(intent);
            finish();
        });

        // Looks Good button (Address confirmation)
        btnLooksGood.setOnClickListener(v -> {
            if (validateAddressInputs()) {
                Toast.makeText(this, "Address confirmed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill in all required address fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Photo selection
        containerCoverPhoto.setOnClickListener(v -> coverPhotoLauncher.launch("image/*"));
        containerRoomPhotos.setOnClickListener(v -> roomPhotoLauncher.launch("image/*"));

        // Save & Continue button
        btnSaveContinue.setOnClickListener(v -> {
            if (validateAllInputs()) {
                saveListingData();
                Intent intent = new Intent(activity_listingform.this, activity_profile.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPropertyTypeSelection() {
        View.OnClickListener propertyTypeClickListener = v -> {
            MaterialCardView selectedCard = (MaterialCardView) v;
            MaterialCardView[] propertyCards = {cardHouse, cardApartment, cardGuestHouse, cardHotel, cardHouseboat, cardTent};
            for (MaterialCardView card : propertyCards) {
                if (card == selectedCard) {
                    CardViewStrokeHelper.setCardSelected(card);
                    selectedPropertyTypeCard = card;
                } else {
                    CardViewStrokeHelper.setCardUnselected(card);
                }
            }
        };

        cardHouse.setOnClickListener(propertyTypeClickListener);
        cardApartment.setOnClickListener(propertyTypeClickListener);
        cardGuestHouse.setOnClickListener(propertyTypeClickListener);
        cardHotel.setOnClickListener(propertyTypeClickListener);
        cardHouseboat.setOnClickListener(propertyTypeClickListener);
        cardTent.setOnClickListener(propertyTypeClickListener);
    }

    private void setupPlaceTypeSelection() {
        View.OnClickListener placeTypeClickListener = v -> {
            MaterialCardView selectedCard = (MaterialCardView) v;
            MaterialCardView[] placeCards = {cardEntirePlace, cardPrivateRoom, cardSharedRoom};
            for (MaterialCardView card : placeCards) {
                if (card == selectedCard) {
                    CardViewStrokeHelper.setCardSelected(card);
                    selectedPlaceTypeCard = card;
                } else {
                    CardViewStrokeHelper.setCardUnselected(card);
                }
            }
        };

        cardEntirePlace.setOnClickListener(placeTypeClickListener);
        cardPrivateRoom.setOnClickListener(placeTypeClickListener);
        cardSharedRoom.setOnClickListener(placeTypeClickListener);
    }

    private boolean validateAddressInputs() {
        return !etAddress.getText().toString().isEmpty() &&
                !etCountry.getText().toString().isEmpty() &&
                !etCity.getText().toString().isEmpty() &&
                !etState.getText().toString().isEmpty() &&
                !etPincode.getText().toString().isEmpty();
    }

    private boolean validateAllInputs() {
        if (selectedPropertyTypeCard == null) {
            Toast.makeText(this, "Please select a property type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (selectedPlaceTypeCard == null) {
            Toast.makeText(this, "Please select a place type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validateAddressInputs()) {
            Toast.makeText(this, "Please complete your address information", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (coverPhotoUri == null) {
            Toast.makeText(this, "Please add a cover photo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (roomPhotoUris.isEmpty()) {
            Toast.makeText(this, "Please add at least one room photo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etPricePerNight.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please set a price per night", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveListingData() {
        // Implementation remains the same as before
    }

    private String getPropertyTypeSelection() {
        if (selectedPropertyTypeCard == cardHouse) return "House";
        if (selectedPropertyTypeCard == cardApartment) return "Flat/apartment";
        if (selectedPropertyTypeCard == cardGuestHouse) return "Guest house";
        if (selectedPropertyTypeCard == cardHotel) return "Hotel";
        if (selectedPropertyTypeCard == cardHouseboat) return "Houseboat";
        if (selectedPropertyTypeCard == cardTent) return "Tent";
        return "";
    }

    private String getPlaceTypeSelection() {
        if (selectedPlaceTypeCard == cardEntirePlace) return "An entire place";
        if (selectedPlaceTypeCard == cardPrivateRoom) return "A room";
        if (selectedPlaceTypeCard == cardSharedRoom) return "A shared room in a hostel";
        return "";
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng defaultLocation = new LatLng(28.6139, 77.2090);
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Default Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12));

        etAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !etAddress.getText().toString().isEmpty()) {
                Toast.makeText(activity_listingform.this,
                        "Address search would update map here", Toast.LENGTH_SHORT).show();
            }
        });
    }
}