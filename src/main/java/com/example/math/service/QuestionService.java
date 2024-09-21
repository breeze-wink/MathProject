package com.example.math.service;

import com.example.math.model.Question;
import com.example.math.model.User;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    public QuestionService() {
        // 默认构造函数
    }
    public static QuestionGenerator createQuestionGenerator(String type) {
        return switch (type) {
            case "小学" -> new PrimarySchoolQuestionGenerator();
            case "初中" -> new MiddleSchoolQuestionGenerator();
            case "高中" -> new HighSchoolQuestionGenerator();
            default -> null;
        };
    }

    public static Question[] generateQuestions(String type, int num){
        QuestionGenerator generator = createQuestionGenerator(type);

        return generator.generateQuestion(num).toArray(new Question[0]);
    }
}
