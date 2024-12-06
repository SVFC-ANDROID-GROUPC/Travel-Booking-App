package com.jorimpablico.travelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jorimpablico.travelbookingapp.apiservice.UserApiService;
import com.jorimpablico.travelbookingapp.model.SignUpRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupUser extends AppCompatActivity {

    TextView tvLogin;
    EditText etName, etUsername, etEmail, etPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        tvLogin = findViewById(R.id.tv_login);
        etName = findViewById(R.id.et_name);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignup = findViewById(R.id.btn_signup);

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupUser.this, LoginUser.class);
            startActivity(intent);
        });

        btnSignup.setOnClickListener(v -> signUpUser());

    }

    private void signUpUser() {
        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        UserApiService apiService = RetrofitClient.getRetrofitInstance().create(UserApiService.class);
        SignUpRequest signUpRequest = new SignUpRequest(name, username, email, password);

        Call<Void> call = apiService.createUser(signUpRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupUser.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                    // Redirect user to LoginActivity
                    startActivity(new Intent(SignupUser.this, LoginUser.class));
                    finish();
                } else {
                    Toast.makeText(SignupUser.this, "Failed to create account. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(SignupUser.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}