package com.example.math.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final JavaMailSender mailSender;

  @Value("${spring.mail.from}")
  private String fromEmail;

  @Autowired
  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  /**
   * 发送邮件的方法，返回生成的验证码。
   *
   * @param recipientEmail 接收邮件的邮箱地址
   * @return 生成的6位数验证码
   * @throws MessagingException 如果邮件发送失败
   */
  public String sendEmail(String recipientEmail) throws MessagingException {
    String verificationCode = generateVerificationCode();
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

    // 设置发件人、收件人、主题和内容
    helper.setFrom(fromEmail);
    helper.setTo(recipientEmail);
    helper.setSubject("您的验证码");
    String emailContent = "您的验证码是：" + verificationCode + "。请在5分钟内使用。";

    helper.setText(emailContent, false);

    // 发送邮件
    mailSender.send(message);
    return verificationCode;
  }

  /**
   * 生成6位数验证码。
   *
   * @return 6位数验证码字符串
   */
  private String generateVerificationCode() {
    Random random = new Random();
    int code = 100000 + random.nextInt(900000); // 生成100000到999999之间的随机数
    return String.valueOf(code);
  }
}
