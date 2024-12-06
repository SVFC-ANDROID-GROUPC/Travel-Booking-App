package com.jorimpablico.travelbookingapp.model;

public class LoginResponse extends LoginRequest {
    private String token;
    private String message;

    public LoginResponse(String username, String password, String token, String message) {
        super(username, password);
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
