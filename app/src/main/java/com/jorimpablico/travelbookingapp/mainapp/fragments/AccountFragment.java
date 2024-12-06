package com.jorimpablico.travelbookingapp.mainapp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorimpablico.travelbookingapp.ChangePassword;
import com.jorimpablico.travelbookingapp.LoginUser;
import com.jorimpablico.travelbookingapp.R;

public class AccountFragment extends Fragment {

    CardView changePass, logOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        changePass = view.findViewById(R.id.change_pass);
        logOut = view.findViewById(R.id.log_out);

        changePass.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        });

        logOut.setOnClickListener(v -> logoutUser());

        return view;
    }

    private void logoutUser() {
        // Clear the token stored in SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("auth_token");  // Remove the auth token
        editor.apply();  // Save changes
        editor.remove("user_name");
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(getActivity(), LoginUser.class);
        startActivity(intent);
        getActivity().finish();
    }
}