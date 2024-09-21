package com.example.math.dto;

public class ConfirmRegisterResponse {
    public ConfirmRegisterResponse(boolean legal, String message) {
        this.legal = legal;
        this.message = message;
    }

    private boolean legal;
    private String message;

    public boolean isLegal() {
        return legal;
    }

    public void setLegal(boolean legal) {
        this.legal = legal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
