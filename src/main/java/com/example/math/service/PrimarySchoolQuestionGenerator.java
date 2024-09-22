package com.example.math.service;

import com.example.math.model.Question;
import java.text.DecimalFormat;
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

      // 首先生成第一个操作数
      operands.add(String.valueOf(random.nextInt(100) + 1));

      for (int i = 0; i < numOperands - 1; i++) {
        char operator = operators[random.nextInt(4)];
        ops.add(operator);

        // 根据操作符生成下一个操作数
        int operand;
        if (operator == '/') {
          // 为了避免除数为零，确保操作数不为零
          operand = random.nextInt(99) + 1; // 1到99
        } else {
          operand = random.nextInt(100) + 1; // 1到100
        }
        operands.add(String.valueOf(operand));
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
