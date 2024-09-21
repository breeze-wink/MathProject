package com.example.math.dto;

import com.example.math.model.Question;

public class QuestionGenerateResponse {
    public QuestionGenerateResponse(Question[] questions){
        this.questions = questions;
    }
    Question[] questions;

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }
}
