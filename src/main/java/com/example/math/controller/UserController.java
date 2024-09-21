package com.example.math.controller;

import com.example.math.dto.*;
import com.example.math.model.User;
import com.example.math.service.EmailService;
import com.example.math.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
    }

    // 处理登录请求
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userService.validateUser(request.getAccount(), request.getPassword());
        if (user != null) {  // 如果 user 不为空，表示验证成功
            return new LoginResponse(true, "登录成功");
        } else {  // 否则返回登录失败的响应
            return new LoginResponse(false, "用户名或密码错误");
        }
    }

    @PostMapping("/confirmRegister")
    public ConfirmRegisterResponse confirmRegister(@RequestBody ConfirmRegisterRequest request){
        if (userService.userExist(request.getUserName())) {
            return new ConfirmRegisterResponse(false, "用户名已注册");
        }
        if (userService.checkPasswordIllegal(request.getPassword())) {
            return new ConfirmRegisterResponse(false, "密码不合法");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())){
            return new ConfirmRegisterResponse(false, "两次输入密码不同");
        }

        userService.putUser(request.getUserName(), request.getPassword(), request.getEmail());
        return new ConfirmRegisterResponse(true, "注册成功");
    }
    @PostMapping("/resetPassword")
    public ResetPasswordResponse resetPassword(@RequestBody ResetPasswordRequest request){
        if (!userService.checkPasswordCorrect(request.getUserName(), request.getOldPassword())) {
            return new ResetPasswordResponse(false, "旧密码不正确");
        }
        if (userService.checkPasswordIllegal(request.getNewPassword())){
            return new ResetPasswordResponse(false, "密码不合法");
        }
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())){
            return new ResetPasswordResponse(false, "两次新密码输入不同");
        }

        userService.modifyPassword(request.getUserName(), request.getNewPassword());
        return new ResetPasswordResponse(true, "修改成功");
    }
}

