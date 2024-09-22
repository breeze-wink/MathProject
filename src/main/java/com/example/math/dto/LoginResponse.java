package com.example.math.dto;

// 登录响应的数据结构
public class LoginResponse {
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
