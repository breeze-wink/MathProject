package com.example.math.service;

import com.example.math.model.User;

public class QuestionGeneratorFactory {

    public QuestionGeneratorFactory() {
        // 默认构造函数
    }

    public static QuestionGenerator createQuestionGenerator(User.Type type) {
        return switch (type) {
            case PrimarySchool -> new PrimarySchoolQuestionGenerator();
            case MiddleSchool -> new MiddleSchoolQuestionGenerator();
            case HighSchool -> new HighSchoolQuestionGenerator();
            default -> null;
        };
    }
}
