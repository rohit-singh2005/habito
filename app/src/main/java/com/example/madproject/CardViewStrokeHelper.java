package com.example.madproject;

import android.graphics.Color;
import com.google.android.material.card.MaterialCardView;

public class CardViewStrokeHelper {

    private static final int SELECTED_STROKE_WIDTH = 4;
    private static final String SELECTED_STROKE_COLOR = "#2196F3";  // Blue color
    private static final String SELECTED_BACKGROUND_COLOR = "#E3F2FD";  // Light blue background
    private static final String UNSELECTED_BACKGROUND_COLOR = "#FFFFFF";  // White background

    public static void setCardSelected(MaterialCardView cardView) {
        cardView.setStrokeWidth(SELECTED_STROKE_WIDTH);
        cardView.setStrokeColor(Color.parseColor(SELECTED_STROKE_COLOR));
        cardView.setCardBackgroundColor(Color.parseColor(SELECTED_BACKGROUND_COLOR));
    }

    public static void setCardUnselected(MaterialCardView cardView) {
        cardView.setStrokeWidth(0);
        cardView.setCardBackgroundColor(Color.parseColor(UNSELECTED_BACKGROUND_COLOR));
    }
}