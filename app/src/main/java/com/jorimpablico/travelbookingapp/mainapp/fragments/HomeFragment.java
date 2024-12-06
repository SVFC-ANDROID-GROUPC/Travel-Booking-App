package com.jorimpablico.travelbookingapp.mainapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.jorimpablico.travelbookingapp.R;
import com.jorimpablico.travelbookingapp.RetrofitClient;
import com.jorimpablico.travelbookingapp.adapter.DestinationAdapter;
import com.jorimpablico.travelbookingapp.apiservice.DestinationApiService;
import com.jorimpablico.travelbookingapp.model.DestinationResponseModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    SearchView searchView;
    RecyclerView rvHome;
    List<DestinationResponseModel> destinationList = new ArrayList<>();
    DestinationAdapter destinationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.search_view);
        rvHome = view.findViewById(R.id.rv_home);
        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));

        destinationAdapter = new DestinationAdapter(new ArrayList<>());
        rvHome.setAdapter(destinationAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterResults(newText);
                return true;
            }
        });

        getAllDestination();

        return view;
    }

    private void getAllDestination() {
        DestinationApiService apiService = RetrofitClient.getRetrofitInstance().create(DestinationApiService.class);
        Call<List<DestinationResponseModel>> getData = apiService.getDestination();

        getData.enqueue(new Callback<List<DestinationResponseModel>>() {
            @Override
            public void onResponse(Call<List<DestinationResponseModel>> call, Response<List<DestinationResponseModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    destinationAdapter.updateData(response.body());
                } else {
                    Toast.makeText(getContext(), "Failed to load item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DestinationResponseModel>> call, Throwable throwable) {
                if (throwable instanceof IOException) {
                    Toast.makeText(getContext(), "Network error, please check your connection.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Unexpected error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void filterResults(String query) {
        List<DestinationResponseModel> filteredList = new ArrayList<>();
        for (DestinationResponseModel item : destinationList) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        destinationAdapter.updateData(filteredList);
    }
}

