package com.example.math.service;

import java.util.List;
import java.util.Random;

public abstract class QuestionGenerator {
    protected Random random = new Random();

    public QuestionGenerator() {
        // 默认构造函数
    }

    public abstract String generateQuestion();

    public String buildExpression(List<String> operands, List<Character> ops) {
        StringBuilder expression = new StringBuilder();
        int openParentheses = 0;

        for (int i = 0; i < operands.size(); i++) {
            if (random.nextInt(3) == 0) {
                expression.append("(");
                openParentheses++;
            }

            expression.append(operands.get(i));

            if (openParentheses > 0 && random.nextInt(3) == 0) {
                expression.append(")");
                openParentheses--;
            }

            if (i < ops.size()) {
                expression.append(" ").append(ops.get(i)).append(" ");
            }
        }

        while (openParentheses > 0) {
            expression.append(")");
            openParentheses--;
        }

        return expression.toString();
    }

    public String generateOperand(boolean isSpecial, int level) {
        int operand = random.nextInt(100) + 1;
        if (isSpecial) {
            if (level == 2) {
                return random.nextBoolean() ? "(" + operand + "^2)" : "sqrt(" + operand + ")";
            } else if (level == 3) {
                String[] trigFunctions = {"sin", "cos", "tan"};
                String trigFunction = trigFunctions[random.nextInt(3)];
                return trigFunction + "(" + operand + ")";
            }
        }
        return String.valueOf(operand);
    }
}
