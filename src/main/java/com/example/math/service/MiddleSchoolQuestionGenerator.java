package com.example.math.service;

import com.example.math.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MiddleSchoolQuestionGenerator extends QuestionGenerator {

    public MiddleSchoolQuestionGenerator() {
        // 默认构造函数
    }

    @Override
    public List<Question> generateQuestion(int num) {
        List<Question> questions = new ArrayList<>();
        for (int qNum = 1; qNum <= num; qNum++) {
            char[] operators = {'+', '-', '*', '/'};
            int numOperands = random.nextInt(4) + 2; // 至少两个操作数
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
            String questionContent = buildExpression(operands, ops) + " = ?";
            double correctAnswer;
            try {
                correctAnswer = evaluateExpression(questionContent.replace("= ?", ""));
            } catch (Exception e) {
                e.printStackTrace();
                qNum--; // 如果计算出错，重试当前题目编号
                continue;
            }
            List<String> choices = generateChoices(correctAnswer);
            Question question = new Question(qNum, questionContent, formatAnswer(correctAnswer), choices.toArray(new String[0]));
            questions.add(question);
        }
        return questions;
    }

    private List<String> generateChoices(double correctAnswer) {
        List<String> choices = new ArrayList<>();
        choices.add(formatAnswer(correctAnswer));
        while (choices.size() < 4) {
            double wrongAnswer = correctAnswer + random.nextInt(21) - 10;
            String formattedWrong = formatAnswer(wrongAnswer);
            if (!choices.contains(formattedWrong)) {
                choices.add(formattedWrong);
            }
        }
        Collections.shuffle(choices);
        return choices;
    }

    private String formatAnswer(double answer) {
        return String.format("%.2f", answer);
    }
}
