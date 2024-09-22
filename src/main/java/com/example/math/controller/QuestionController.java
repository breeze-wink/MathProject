package com.example.math.controller;

import com.example.math.dto.QuestionGenerateRequest;
import com.example.math.dto.QuestionGenerateResponse;
import com.example.math.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
  private QuestionService questionService;

  @Autowired
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping("/generate")
  public QuestionGenerateResponse generateQuestion(@RequestBody QuestionGenerateRequest request) {
    return new QuestionGenerateResponse(
        QuestionService.generateQuestions(request.getType(), request.getNumber()));
  }
}
