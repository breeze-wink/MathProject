package com.example.math.controller;

import com.example.math.dto.MailRequest;
import com.example.math.dto.MailResponse;
import com.example.math.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailController {

  private final EmailService emailService;

  @Autowired
  public MailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping("/send")
  public MailResponse sendMail(@RequestBody MailRequest request) throws MessagingException {
    String verifyCode = emailService.sendEmail(request.getEmail());

    return new MailResponse(verifyCode);
  }
}
