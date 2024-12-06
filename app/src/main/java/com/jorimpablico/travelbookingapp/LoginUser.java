package com.jorimpablico.travelbookingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jorimpablico.travelbookingapp.apiservice.UserApiService;
import com.jorimpablico.travelbookingapp.mainapp.MainActivity;
import com.jorimpablico.travelbookingapp.model.LoginRequest;
import com.jorimpablico.travelbookingapp.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginUser extends AppCompatActivity {

    TextView tvSignup;
    TextView tvForgotPassword;
    EditText etInputUsername, etInputPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        tvSignup = findViewById(R.id.tv_signup);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        btnLogin = findViewById(R.id.btn_login);
        etInputUsername = findViewById(R.id.et_input_username);
        etInputPassword = findViewById(R.id.et_input_password);

        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginUser.this, SignupUser.class);
            startActivity(intent);
        });

        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginUser.this, ForgotPassUser.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            loginUser();
        });


    }

    public void loginUser(){
        String username = etInputUsername.getText().toString().trim();
        String password = etInputPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        UserApiService apiService = RetrofitClient.getRetrofitInstance().create(UserApiService.class);
        LoginRequest loginRequest = new LoginRequest(username, password);

        Call<LoginResponse> call = apiService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    //Save the token using SharedPreferences for future authenticated requests.
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("auth_token", token);
                    editor.apply();

                    Toast.makeText(LoginUser.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginUser.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginUser.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                Toast.makeText(LoginUser.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}