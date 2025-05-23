package com.example.madproject; // Replace with your package name

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.AccommodationViewHolder> {

    private List<Accommodation> accommodationList;
    private OnAccommodationClickListener listener; // Member variable for the listener

    // Interface for click events
    public interface OnAccommodationClickListener {
        void onItemClick(Accommodation accommodation);
    }

    // Constructor that accepts the listener
    public AccommodationAdapter(List<Accommodation> accommodationList, OnAccommodationClickListener listener) {
        this.accommodationList = accommodationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccommodationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodation, parent, false);
        return new AccommodationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationViewHolder holder, int position) {
        Accommodation accommodation = accommodationList.get(position);
        holder.imageView.setImageResource(accommodation.getImageResource());
        holder.textViewTitle.setText(accommodation.getTitle());
        holder.textViewLocation.setText(accommodation.getLocation());
        holder.textViewPrice.setText(accommodation.getPriceRange());

        // Set the click listener on the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the listener is set and trigger the callback
                if (listener != null) {
                    listener.onItemClick(accommodation); // Pass the clicked accommodation object
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return accommodationList.size();
    }

    static class AccommodationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewLocation;
        TextView textViewPrice;

        AccommodationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_accommodation);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            textViewPrice = itemView.findViewById(R.id.text_view_price);

            // No need to set click listener here if setting in onBindViewHolder
        }
    }
}