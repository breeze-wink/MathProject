package com.example.math.dto;

public class ResetPasswordResponse {

    public ResetPasswordResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }
    private boolean success;
    private String message;
}
