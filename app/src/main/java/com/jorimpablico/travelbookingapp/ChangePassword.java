package com.jorimpablico.travelbookingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.jorimpablico.travelbookingapp.apiservice.UserApiService;
import com.jorimpablico.travelbookingapp.model.ChangePasswordRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    CardView submitChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etCurrentPassword = findViewById(R.id.current_password);
        etNewPassword = findViewById(R.id.new_password);
        etConfirmPassword = findViewById(R.id.confirm_password);
        submitChangePass = findViewById(R.id.submit_change_pass);

        submitChangePass.setOnClickListener(v -> changePassword());

    }

    private void changePassword() {
        String currentPassword = etCurrentPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validate inputs
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New password and confirm password must match", Toast.LENGTH_SHORT).show();
            return;
        }

        sendChangePasswordRequest(currentPassword, newPassword);
    }

    private void sendChangePasswordRequest(String currentPassword, String newPassword) {
        // Get the token from SharedPreferences or another source
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", "");

        if (!token.isEmpty()) {
            // Create the request body
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(currentPassword, newPassword);

            UserApiService apiService = RetrofitClient.getRetrofitInstance().create(UserApiService.class);
            Call<Void> call = apiService.changePassword("Bearer " + token, changePasswordRequest);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ChangePassword.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                        // Optionally, log the user out or navigate them to the login screen
                    } else {
                        Toast.makeText(ChangePassword.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ChangePassword.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}