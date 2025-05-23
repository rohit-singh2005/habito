package com.example.madproject;

import android.content.Context;
import android.net.Uri;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Helper class for validating property listing inputs
 */
public class ListingValidator {

    private Context context;

    public ListingValidator(Context context) {
        this.context = context;
    }

    /**
     * Validates all required inputs for a property listing
     * @return true if all required fields are filled, false otherwise
     */
    public boolean validateAllInputs(
            String propertyType,
            String placeType,
            EditText etAddress,
            EditText etCity,
            EditText etState,
            EditText etPincode,
            Uri coverPhotoUri,
            ArrayList<Uri> roomPhotoUris,
            EditText etPricePerNight) {

        // Check property type
        if (propertyType == null || propertyType.isEmpty()) {
            showToast("Please select a property type");
            return false;
        }

        // Check place type
        if (placeType == null || placeType.isEmpty()) {
            showToast("Please select a place type");
            return false;
        }

        // Check address inputs
        if (!validateAddressInputs(etAddress, etCity, etState, etPincode)) {
            showToast("Please complete your address information");
            return false;
        }

        // Check cover photo
        if (coverPhotoUri == null) {
            showToast("Please add a cover photo");
            return false;
        }

        // Check room photos
        if (roomPhotoUris.isEmpty()) {
            showToast("Please add at least one room photo");
            return false;
        }

        // Check price per night
        if (etPricePerNight.getText().toString().isEmpty() ||
                Integer.parseInt(etPricePerNight.getText().toString()) <= 0) {
            showToast("Please set a valid price per night");
            return false;
        }

        return true;
    }

    /**
     * Validates required address fields
     * @return true if all required address fields are filled, false otherwise
     */
    public boolean validateAddressInputs(
            EditText etAddress,
            EditText etCity,
            EditText etState,
            EditText etPincode) {

        // Check address
        if (etAddress.getText().toString().isEmpty()) {
            showToast("Please enter your address");
            return false;
        }

        // Check city
        if (etCity.getText().toString().isEmpty()) {
            showToast("Please enter your city");
            return false;
        }

        // Check state
        if (etState.getText().toString().isEmpty()) {
            showToast("Please enter your state");
            return false;
        }

        // Check pincode
        if (etPincode.getText().toString().isEmpty()) {
            showToast("Please enter your PIN code");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}