package com.example.math.model;


public class User {
    private String account;
    private String password;
    private Type type;

    public User(String account, String password, Type type) {
        this.account = account;
        this.password = password;
        this.type = type;
    }

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        PrimarySchool,
        MiddleSchool,
        HighSchool
    }
}
