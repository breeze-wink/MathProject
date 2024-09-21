package com.example.math.dto;

public class QuestionGenerateRequest {
    private String type;
    private int number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
