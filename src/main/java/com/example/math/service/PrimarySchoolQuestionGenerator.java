package com.example.math.service;

import java.util.ArrayList;
import java.util.List;

public class PrimarySchoolQuestionGenerator extends QuestionGenerator {

    public PrimarySchoolQuestionGenerator() {
        // 默认构造函数
    }

    @Override
    public String generateQuestion() {
        char[] operators = {'+', '-', '*', '/'};
        int numOperands = random.nextInt(4) + 2; // 2到5个操作数
        List<String> operands = new ArrayList<>();
        List<Character> ops = new ArrayList<>();

        for (int i = 0; i < numOperands; i++) {
            operands.add(String.valueOf(random.nextInt(100) + 1));
        }

        for (int i = 0; i < numOperands - 1; i++) {
            ops.add(operators[random.nextInt(4)]);
        }

        String question = buildExpression(operands, ops) + " = ";
        return question;
    }
}
