package com.jorimpablico.travelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoginUser extends AppCompatActivity {

    TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        tvSignup = findViewById(R.id.tv_signup);

        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginUser.this, SignupUser.class);
            startActivity(intent);
        });
    }
}