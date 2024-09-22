package com.example.math.service;

import com.example.math.model.Question;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class QuestionGenerator {
    protected Random random = new Random();

    public QuestionGenerator() {
        // 默认构造函数
    }

    public abstract java.util.List<Question> generateQuestion(int num);

    public String buildExpression(List<String> operands, List<Character> ops) {
        if (operands.isEmpty()) {
            return "";
        }

        // 用于存储操作数和操作符的栈
        List<String> expressionParts = new ArrayList<>();
        expressionParts.add(operands.get(0));

        for (int i = 1; i < operands.size(); i++) {
            String operator = String.valueOf(ops.get(i - 1));
            String operand = operands.get(i);

            boolean addParentheses = random.nextBoolean();

            if (addParentheses && expressionParts.size() >= 2) {
                // 从栈中移除最近的操作数和操作符
                String lastOperand = expressionParts.remove(expressionParts.size() - 1);
                String lastOperator = expressionParts.remove(expressionParts.size() - 1);
                String secondLastOperand = expressionParts.remove(expressionParts.size() - 1);

                // 创建只包含最近两个操作数和操作符的子表达式，并用括号包围
                String subExpression = "(" + secondLastOperand + lastOperator + lastOperand + ")";

                // 将子表达式添加回栈中
                expressionParts.add(subExpression);

                // 添加当前的操作符和操作数
                expressionParts.add(operator);
                expressionParts.add(operand);
            } else {
                // 简单地添加操作符和操作数
                expressionParts.add(operator);
                expressionParts.add(operand);
            }
        }

        // 组合所有的表达式部分
        StringBuilder expression = new StringBuilder();
        for (String part : expressionParts) {
            expression.append(part);
        }

        return expression.toString();
    }



    public String generateOperand(boolean isSpecial, int level) {
        int operand = random.nextInt(100) + 1;
        if (isSpecial) {
            if (level == 2) {
                // 生成 1 到 100 的平方数
                int[] squares = new int[10];
                for (int i = 1; i <= 10; i++) {
                    squares[i - 1] = i * i;
                }
                int sqrtOperand = squares[random.nextInt(squares.length)];
                if (random.nextBoolean()) {
                    return operand + "^2";
                } else {
                    return "sqrt(" + sqrtOperand + ")";
                }
            } else if (level == 3) {
                String[] trigFunctions = {"sin", "cos", "tan"};
                String trigFunction = trigFunctions[random.nextInt(3)];
                int[] angles;
                if ("tan".equals(trigFunction)) {
                    // tan 函数避免 90 度
                    angles = new int[]{30, 45, 60};
                } else {
                    angles = new int[]{30, 45, 60, 90};
                }
                int angle = angles[random.nextInt(angles.length)];
                // 添加度数符号 °
                return trigFunction + "(" + angle + "°)";
            }
        }
        return String.valueOf(operand);
    }


    protected double evaluateExpression(String expression) {
        // 将角度转换为弧度，并替换表达式中的角度值
        Pattern pattern = Pattern.compile("(sin|cos|tan)\\((\\d+)°\\)");
        Matcher matcher = pattern.matcher(expression);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String func = matcher.group(1);
            double degrees = Double.parseDouble(matcher.group(2));
            double radians = Math.toRadians(degrees);
            String replacement = func + "(" + radians + ")";
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        expression = sb.toString();

        // 创建表达式并计算结果
        Expression exp = new ExpressionBuilder(expression)
                .build();
        return exp.evaluate();
    }

}
