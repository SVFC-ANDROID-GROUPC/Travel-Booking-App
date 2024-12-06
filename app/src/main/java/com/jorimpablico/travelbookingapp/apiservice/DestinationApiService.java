package com.jorimpablico.travelbookingapp.apiservice;

import com.jorimpablico.travelbookingapp.model.DestinationResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DestinationApiService {

    @GET("/destination")
    Call<List<DestinationResponseModel>> getDestination();

    @GET("/destination/{destination_id}")
    Call<DestinationResponseModel> getProductDetails(@Path("destination_id") String objectId);
}
