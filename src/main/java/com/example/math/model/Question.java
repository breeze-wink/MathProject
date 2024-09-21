package com.example.math.model;

public class Question {
    public Question(int number, String content, String answer, String[] choices) {
        this.number = number;
        this.content = content;
        this.answer = answer;
        this.choices = choices;
    }
    private int number;
    private String content;
    private String answer;
    String[] choices;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }
}
