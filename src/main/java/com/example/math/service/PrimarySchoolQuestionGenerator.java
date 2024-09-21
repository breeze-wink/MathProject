package com.example.math.service;

import com.example.math.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimarySchoolQuestionGenerator extends QuestionGenerator {

    public PrimarySchoolQuestionGenerator() {
        // 默认构造函数
    }

    @Override
    public List<Question> generateQuestion(int num) {
        List<Question> questions = new ArrayList<>();
        int qNum = 1;
        while (questions.size() < num) {
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

            String questionContent = buildExpression(operands, ops) + " = ?";
            double correctAnswer;

            try {
                correctAnswer = evaluateExpression(questionContent.replace("= ?", ""));
                if (Double.isInfinite(correctAnswer) || Double.isNaN(correctAnswer)) {
                    continue; // 跳过无效的表达式
                }
            } catch (Exception e) {
                continue; // 计算出错，跳过此题
            }

            List<String> choices = generateChoices(correctAnswer);
            Question question = new Question(qNum++, questionContent, formatAnswer(correctAnswer), choices.toArray(new String[0]));
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
