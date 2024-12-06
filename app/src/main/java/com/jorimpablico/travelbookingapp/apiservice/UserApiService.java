package com.jorimpablico.travelbookingapp.apiservice;

import com.jorimpablico.travelbookingapp.model.ChangePasswordRequest;
import com.jorimpablico.travelbookingapp.model.ForgotPasswordRequest;
import com.jorimpablico.travelbookingapp.model.LoginRequest;
import com.jorimpablico.travelbookingapp.model.LoginResponse;
import com.jorimpablico.travelbookingapp.model.SignUpRequest;
import com.jorimpablico.travelbookingapp.model.UserProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApiService {

    @POST ("/user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET ("/user")
    Call<UserProfile> getUserProfile(@Header("Authorization") String token);

    @POST ("/user/")
    Call<Void> createUser(@Body SignUpRequest signUpRequest);

    @POST("/user/change_password")
    Call<Void> changePassword(@Header("Authorization") String token, @Body ChangePasswordRequest changePasswordRequest);

    @POST("/user/forgot_password")
    Call<Void> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);


}
