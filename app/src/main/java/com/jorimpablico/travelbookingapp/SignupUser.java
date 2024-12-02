package com.jorimpablico.travelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupUser extends AppCompatActivity {

    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        tvLogin = findViewById(R.id.tv_login);

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupUser.this, LoginUser.class);
        });

    }
}