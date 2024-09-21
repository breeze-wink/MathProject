package com.example.math.dto;

public class MailResponse {
    public MailResponse(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
