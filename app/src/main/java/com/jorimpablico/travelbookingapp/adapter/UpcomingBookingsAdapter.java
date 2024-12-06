package com.jorimpablico.travelbookingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jorimpablico.travelbookingapp.R;
import com.jorimpablico.travelbookingapp.model.BookingResponseModel;

import java.util.List;

public class UpcomingBookingsAdapter extends RecyclerView.Adapter<UpcomingBookingsAdapter.UpcomingBookingViewHolder> {

    List<BookingResponseModel> bookingList;
    OnCancelBookingListener cancelListener;

    public interface OnCancelBookingListener {
        void onCancelBooking(BookingResponseModel bookingResponseModel);
    }

    public UpcomingBookingsAdapter(List<BookingResponseModel> bookingList, OnCancelBookingListener listener) {
        this.bookingList = bookingList;
        this.cancelListener = listener;
    }

    public void removeBooking(BookingResponseModel booking) {
        int position = bookingList.indexOf(booking);
        if (position != -1) {
            bookingList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    @NonNull
    @Override
    public UpcomingBookingsAdapter.UpcomingBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_booking_card, parent, false);
        return new UpcomingBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingBookingsAdapter.UpcomingBookingViewHolder holder, int position) {
        BookingResponseModel bookingResponseModel = bookingList.get(position);

        holder.tvPlaceName.setText(bookingResponseModel.getName_of_place());
        holder.tvLocation.setText(bookingResponseModel.getLocation());
        holder.tvDate.setText(bookingResponseModel.getDate());
        holder.tvNumberPeople.setText(String.valueOf(bookingResponseModel.getNumber_of_people()));
        holder.tvUserName.setText(bookingResponseModel.getName_of_user());
        holder.tvPrice.setText(String.format("₱", bookingResponseModel.getPrice()));

        holder.cancelBookingButton.setOnClickListener(v -> cancelListener.onCancelBooking(bookingResponseModel));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class UpcomingBookingViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlaceName, tvLocation, tvDate, tvNumberPeople, tvUserName, tvPrice;
        Button cancelBookingButton;

        public UpcomingBookingViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlaceName = itemView.findViewById(R.id.place_name);
            tvLocation = itemView.findViewById(R.id.location);
            tvDate = itemView.findViewById(R.id.date);
            tvNumberPeople = itemView.findViewById(R.id.number_of_people);
            tvUserName = itemView.findViewById(R.id.user_name);
            tvPrice = itemView.findViewById(R.id.price);
            cancelBookingButton = itemView.findViewById(R.id.cancel_booking_button);
        }
    }
}
