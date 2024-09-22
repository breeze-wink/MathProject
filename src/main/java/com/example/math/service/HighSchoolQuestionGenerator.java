package com.example.math.service;

import com.example.math.model.Question;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighSchoolQuestionGenerator extends QuestionGenerator {

  public HighSchoolQuestionGenerator() {
    // 默认构造函数
  }

  @Override
  public List<Question> generateQuestion(int num) {
    List<Question> questions = new ArrayList<>();
    int qNum = 1;
    while (questions.size() < num) {
      char[] operators = {'+', '-', '*', '/'};
      int numOperands = random.nextInt(4) + 2; // 至少两个操作数
      List<String> operands = new ArrayList<>();
      List<Character> ops = new ArrayList<>();
      int specialIndex = random.nextInt(numOperands);

      // 首先生成第一个操作数
      boolean isSpecial = (0 == specialIndex);
      operands.add(generateOperand(isSpecial, 3));

      for (int i = 0; i < numOperands - 1; i++) {
        char operator = operators[random.nextInt(4)];
        ops.add(operator);

        // 根据操作符生成下一个操作数
        isSpecial = ((i + 1) == specialIndex);
        String operand;
        if (operator == '/') {
          // 为了避免除数为零，确保操作数不为零
          do {
            operand = generateOperand(isSpecial, 3);
          } while (operand.equals("0"));
        } else {
          operand = generateOperand(isSpecial, 3);
        }
        operands.add(operand);
      }

      String questionContent = buildExpression(operands, ops) + " = ?";
      double correctAnswer;
      try {
        correctAnswer = evaluateExpression(questionContent.replace("= ?", ""));
        if (Double.isInfinite(correctAnswer) || Double.isNaN(correctAnswer)) {
          qNum--; // 重试当前题目编号
          continue;
        }
      } catch (Exception e) {
        e.printStackTrace();
        qNum--; // 如果计算出错，重试当前题目编号
        continue;
      }
      List<String> choices = generateChoices(correctAnswer);
      Question question =
          new Question(
              qNum++, questionContent, formatAnswer(correctAnswer), choices.toArray(new String[0]));
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
    // 处理接近零的负数
    if (Math.abs(answer) < 1e-8) {
      answer = 0.0;
    }

    DecimalFormat df;
    if (Math.abs(answer) >= 0.01) {
      df = new DecimalFormat("#.##");
    } else {
      df = new DecimalFormat("#.######");
    }

    return df.format(answer);
  }
}
