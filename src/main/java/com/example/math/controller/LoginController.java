package com.example.math.controller;

import com.example.math.model.User;
import com.example.math.service.UserService;
import com.example.math.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;
    @Autowired
    public LoginController(UserService userService) {
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

}

// 登录请求的数据结构
class LoginRequest {
    private String account;
    private String password;

    // getters 和 setters

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

// 登录响应的数据结构
class LoginResponse {
    private final boolean success;
    private final String message;

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
