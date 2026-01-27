package com.example.locationtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<LocationEntity> locations = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault());

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationEntity location = locations.get(position);

        // Update to show only numeric values (no "Latitude:" text)
        holder.tvLatitude.setText(String.format("%.6f", location.getLatitude()));
        holder.tvLongitude.setText(String.format("%.6f", location.getLongitude()));
        holder.tvTimestamp.setText(dateFormat.format(new Date(location.getTimestamp())));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setLocations(List<LocationEntity> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLatitude, tvLongitude, tvTimestamp;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLatitude = itemView.findViewById(R.id.tvLatitude);
            tvLongitude = itemView.findViewById(R.id.tvLongitude);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
        }
    }
}