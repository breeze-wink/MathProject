package com.example.math.controller;
import com.example.math.service.EmailService;
import com.example.math.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MailController {
    private final EmailService emailService;
    @Autowired
    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }
    @PostMapping("/sendmail")
    public MailResponse sendMail(@RequestBody MailRequest request) throws MessagingException {
        String verifyCode = emailService.sendEmail(request.getEmail());

        return new MailResponse(verifyCode);
    }
}

class MailRequest{
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class MailResponse{
    public MailResponse(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}