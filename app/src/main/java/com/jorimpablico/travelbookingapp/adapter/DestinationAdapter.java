package com.jorimpablico.travelbookingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jorimpablico.travelbookingapp.R;
import com.jorimpablico.travelbookingapp.bookingactivity.CreateBookingNow;
import com.jorimpablico.travelbookingapp.model.DestinationResponseModel;

import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {

    Context context;
    List<DestinationResponseModel> destinationList;

    public DestinationAdapter(List<DestinationResponseModel> destinationList) {
        this.destinationList = destinationList;
    }

    @NonNull
    @Override
    public DestinationAdapter.DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_travel_card, parent, false);
        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationAdapter.DestinationViewHolder holder, int position) {
        DestinationResponseModel destinationResponseModel = destinationList.get(position);

        holder.tvName.setText(destinationResponseModel.getName());
        holder.tvLocation.setText(destinationResponseModel.getLocation());
        holder.tvPrice.setText(String.format("₱", destinationResponseModel.getPrice()));
        holder.tvDescription.setText(destinationResponseModel.getDescription());
        Glide.with(holder.itemView.getContext())
                .load(destinationResponseModel.getImageUrl())
                .into(holder.imgCard);

        holder.btnBookNow.setOnClickListener(v -> {
            Intent intent = new Intent(context, CreateBookingNow.class);
            intent.putExtra("destination_id", destinationResponseModel.getDestination_id()); // Pass product ID to detail activity
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public void updateData(List<DestinationResponseModel> newData) {
        destinationList.clear();
        destinationList.addAll(newData);
        this.destinationList = newData;
        notifyDataSetChanged();
    }

    public static class DestinationViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvLocation, tvPrice, tvDescription;
        Button btnBookNow;
        ImageView imgCard;

        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgCard = itemView.findViewById(R.id.img_card);

            btnBookNow = itemView.findViewById(R.id.btn_book_now);
        }
    }
}
