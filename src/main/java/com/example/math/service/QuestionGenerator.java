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

    public String buildExpression(java.util.List<String> operands, java.util.List<Character> ops) {
        if (operands.isEmpty()) {
            return "";
        }

        List<String> expressionParts = new ArrayList<>();
        expressionParts.add(operands.get(0));

        for (int i = 1; i < operands.size(); i++) {
            String operator = String.valueOf(ops.get(i - 1));
            String operand = operands.get(i);

            // 决定是否在上一个操作数、操作符和当前操作数之间添加括号
            boolean addParentheses = random.nextBoolean();

            if (addParentheses) {
                // 移除最后一个操作数
                String lastOperand = expressionParts.remove(expressionParts.size() - 1);
                // 将上一个操作数、操作符和当前操作数组合并，并用括号包裹
                String subExpression = "(" + lastOperand + operator + operand + ")";
                // 将新的子表达式添加回列表
                expressionParts.add(subExpression);
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
                    return "(" + operand + ")^2";
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
                return trigFunction + "(" + angle + ")";
            }
        }
        return String.valueOf(operand);
    }

    protected double evaluateExpression(String expression) {
        // 将角度转换为弧度，并替换表达式中的角度值
        Pattern pattern = Pattern.compile("(sin|cos|tan)\\((\\d+)\\)");
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
