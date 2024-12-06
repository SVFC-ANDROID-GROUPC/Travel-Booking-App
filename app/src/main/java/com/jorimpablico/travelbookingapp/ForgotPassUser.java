package com.jorimpablico.travelbookingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jorimpablico.travelbookingapp.apiservice.UserApiService;
import com.jorimpablico.travelbookingapp.model.ForgotPasswordRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassUser extends AppCompatActivity {

    EditText etEmail;
    Button btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_user);

        etEmail = findViewById(R.id.email_address);
        btnEmail = findViewById(R.id.btn_email);

        btnEmail.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            requestPasswordReset(email);
        });

    }

    private void requestPasswordReset(String email) {
        // Call the API to request password reset
        UserApiService apiService = RetrofitClient.getRetrofitInstance().create(UserApiService.class);
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(email);

        Call<Void> call = apiService.forgotPassword(forgotPasswordRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                } else {
                    Toast.makeText(ForgotPassUser.this, "Failed to send email. Try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(ForgotPassUser.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}