package com.jorimpablico.travelbookingapp.bookingactivity;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.jorimpablico.travelbookingapp.R;
import com.jorimpablico.travelbookingapp.RetrofitClient;
import com.jorimpablico.travelbookingapp.apiservice.BookingApiService;
import com.jorimpablico.travelbookingapp.apiservice.DestinationApiService;
import com.jorimpablico.travelbookingapp.apiservice.UserApiService;
import com.jorimpablico.travelbookingapp.model.BookingRequestModel;
import com.jorimpablico.travelbookingapp.model.DestinationResponseModel;
import com.jorimpablico.travelbookingapp.model.UserProfile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBookingNow extends AppCompatActivity {

    TextView tvDate, tvPrice, tvPlaceName, tvLocation;
    Button btnShowDatePicker, btnBook;
    Spinner numberOfPeople;
    String objectId;
    int basePrice = 0;
    int selectedNumber, updatedPrice;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book_now);

        objectId = getIntent().getStringExtra("destination_id");

        tvDate = findViewById(R.id.tv_selected_date);
        tvPrice = findViewById(R.id.tv_price);
        tvPlaceName = findViewById(R.id.tv_place_name);
        tvLocation = findViewById(R.id.tv_location);
        btnShowDatePicker = findViewById(R.id.btn_show_date_picker);
        btnBook = findViewById(R.id.btn_book);
        numberOfPeople = findViewById(R.id.number_of_people_spinner);

        btnShowDatePicker.setOnClickListener(v -> showDatePicker());

        btnBook.setEnabled(false);

        if (objectId != null) {
            fetchProductDetails(objectId);
        } else {
            showToast("Invalid object ID!");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", "");

        if (!token.isEmpty()) {
            fetchUserProfile(token);
        } else {
            // Handle if token is not found (e.g., prompt user to log in again)
        }

        btnBook.setOnClickListener(v -> {

            if (tvDate.getText().toString().isEmpty()) {
                showToast("Please select a date.");
                return; // Don't proceed with booking if the date is not selected
            }

            if (userName != null) { // Ensure the userName is fetched
                submitBookings(userName);
            } else {
                showToast("User name not available.");
            }
        });

        numberOfPeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNumber = Integer.parseInt(parent.getItemAtPosition(position).toString());
                updatedPrice = basePrice * selectedNumber;
                tvPrice.setText("₱" + updatedPrice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });

    }

    private void fetchProductDetails(String objectId) {
        DestinationApiService apiService = RetrofitClient.getRetrofitInstance().create(DestinationApiService.class);
        Call<DestinationResponseModel> getDetails = apiService.getProductDetails(objectId);

        getDetails.enqueue(new Callback<DestinationResponseModel>() {
            @Override
            public void onResponse(Call<DestinationResponseModel> call, Response<DestinationResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayProductDetails(response.body());
                } else {
                    showToast("Failed to load product details.");
                }
            }

            @Override
            public void onFailure(Call<DestinationResponseModel> call, Throwable throwable) {
                showToast("Network error: " + throwable.getMessage());
            }
        });
    }

    private void submitBookings(String userName) {
        BookingRequestModel bookingRequestModel = new BookingRequestModel(
                tvPlaceName.getText().toString(),
                tvLocation.getText().toString(),
                tvDate.getText().toString(),
                selectedNumber, userName,
                updatedPrice);

        BookingApiService apiService = RetrofitClient.getRetrofitInstance().create(BookingApiService.class);
        Call<BookingRequestModel> call = apiService.submitBooking(bookingRequestModel);

        call.enqueue(new Callback<BookingRequestModel>() {
            @Override
            public void onResponse(Call<BookingRequestModel> call, Response<BookingRequestModel> response) {
                if (response.isSuccessful()) {
                    showToast("Booking submitted successfully! Go to Manage Bookings to check the booking");
                } else {
                    showToast("Failed to submit booking.");
                }
            }

            @Override
            public void onFailure(Call<BookingRequestModel> call, Throwable throwable) {
                showToast("Network error: " + throwable.getMessage());
            }
        });
    }

    private void displayProductDetails(DestinationResponseModel destinationResponseModel) {
        ImageView ivBook = findViewById(R.id.iv_book);

        tvPlaceName.setText(destinationResponseModel.getName());
        tvLocation.setText(destinationResponseModel.getLocation());

        basePrice = destinationResponseModel.getPrice();
        tvPrice.setText("₱" + basePrice);

        Glide.with(this)
                .load(destinationResponseModel.getImageUrl())
                .into(ivBook);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void fetchUserProfile(String token) {
        UserApiService apiService = RetrofitClient.getRetrofitInstance().create(UserApiService.class);

        Call<UserProfile> call = apiService.getUserProfile("Bearer " + token);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                userName = response.body().getName();
                btnBook.setEnabled(true);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable throwable) {

            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(calendar.getTime());

                tvDate.setText("Selected Date: " + formattedDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

}