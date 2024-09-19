package com.example.math.service;

import java.util.ArrayList;
import java.util.List;

public class MiddleSchoolQuestionGenerator extends QuestionGenerator {

    public MiddleSchoolQuestionGenerator() {
        // 默认构造函数
    }

    @Override
    public String generateQuestion() {
        char[] operators = {'+', '-', '*', '/'};
        int numOperands = random.nextInt(5) + 1; // 1到5个操作数
        List<String> operands = new ArrayList<>();
        List<Character> ops = new ArrayList<>();
        int specialIndex = random.nextInt(numOperands);

        for (int i = 0; i < numOperands; i++) {
            boolean isSpecial = (i == specialIndex);
            operands.add(generateOperand(isSpecial, 2));
        }

        for (int i = 0; i < numOperands - 1; i++) {
            ops.add(operators[random.nextInt(4)]);
        }

        String question = buildExpression(operands, ops) + " = ";
        return question;
    }
}
