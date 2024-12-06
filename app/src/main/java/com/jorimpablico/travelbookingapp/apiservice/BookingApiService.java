package com.jorimpablico.travelbookingapp.apiservice;

import com.jorimpablico.travelbookingapp.model.BookingRequestModel;
import com.jorimpablico.travelbookingapp.model.BookingResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookingApiService {

    @GET("/user/booking")
    Call<List<BookingResponseModel>> getUserBookings(@Header("Authorization") String token);

    @DELETE("/bookings")
    Call<Void> cancelBookings(@Query("booking_uuid") String booking_uuid);

    @POST("/bookings/")
    Call<BookingRequestModel> submitBooking(@Body BookingRequestModel bookingRequestModel);
}
