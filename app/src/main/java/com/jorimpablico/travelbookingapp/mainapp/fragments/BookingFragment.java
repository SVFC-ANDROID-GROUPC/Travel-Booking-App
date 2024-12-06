package com.jorimpablico.travelbookingapp.mainapp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jorimpablico.travelbookingapp.LoginUser;
import com.jorimpablico.travelbookingapp.R;
import com.jorimpablico.travelbookingapp.RetrofitClient;
import com.jorimpablico.travelbookingapp.adapter.UpcomingBookingsAdapter;
import com.jorimpablico.travelbookingapp.apiservice.BookingApiService;
import com.jorimpablico.travelbookingapp.model.BookingResponseModel;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragment extends Fragment {
    RecyclerView rvUpcomingBookings;
    UpcomingBookingsAdapter upcomingBookingsAdapter;
    List<BookingResponseModel> bookingResponseModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        rvUpcomingBookings = view.findViewById(R.id.rv_upcoming_bookings);
        rvUpcomingBookings.setLayoutManager(new LinearLayoutManager(getContext()));

        upcomingBookingsAdapter = new UpcomingBookingsAdapter(bookingResponseModels, this::cancelBooking);
        rvUpcomingBookings.setAdapter(upcomingBookingsAdapter);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", "");

        if (!token.isEmpty()) {
            fetchBookings(token);
        } else {
            // Handle case where token is missing or expired
            Toast.makeText(requireContext(), "Please log in first." , Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void fetchBookings(String token) {

        BookingApiService apiService = RetrofitClient.getRetrofitInstance().create(BookingApiService.class);
        Call<List<BookingResponseModel>> getData = apiService.getUserBookings("Bearer " + token);

        getData.enqueue(new Callback<List<BookingResponseModel>>() {
            @Override
            public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookingResponseModels.clear();
                    bookingResponseModels.addAll(response.body());
                    upcomingBookingsAdapter.notifyDataSetChanged();
                } else if (response.code() == 401) {
                    handleSessionExpired();
                } else if (response.body() != null && response.body().isEmpty()) {
                    Toast.makeText(requireContext(), "No upcoming bookings.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to load bookings. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.e("BookingFragment", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<BookingResponseModel>> call, Throwable throwable) {
                Toast.makeText(requireContext(), "Error fetching bookings: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSessionExpired() {
        Toast.makeText(requireContext(), "Session expired. Please log in again.", Toast.LENGTH_SHORT).show();

        // Clear saved token
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("auth_token"); // Remove the saved token
        editor.apply();

        // Redirect to login screen
        Intent intent = new Intent(requireContext(), LoginUser.class);
        startActivity(intent);
        requireActivity().finish(); // Close the current activity/fragment
    }

    private void cancelBooking(BookingResponseModel bookingResponseModel) {
        BookingApiService apiService = RetrofitClient.getRetrofitInstance().create(BookingApiService.class);
        Call<Void> cancelData = apiService.cancelBookings(bookingResponseModel.getBooking_uuid());

        cancelData.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    upcomingBookingsAdapter.removeBooking(bookingResponseModel);
                    Toast.makeText(requireContext(), "Booking canceled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to cancel booking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to cancel booking", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
