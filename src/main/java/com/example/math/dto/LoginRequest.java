package com.example.math.dto;

// 登录请求的数据结构
public class LoginRequest {
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
