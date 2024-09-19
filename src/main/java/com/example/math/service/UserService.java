package com.example.math.service;

import com.example.math.model.User;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserService {

    private List<User> users;
    private Path execPath;

    public UserService() {
        getRunningPath();
        readAccounts();
    }

    // 校验用户的账号和密码
    public User validateUser(String account, String password) {
        for (User user : users) {
            if (account.equals(user.getAccount()) && password.equals(user.getPassword())) {
                return user;  // 返回 User 对象
            }
        }
        return null;  // 如果用户不存在或密码不匹配，返回 null
    }


    // 从文件中读取账号信息
    private void readAccounts() {
        Path filePath = execPath.resolve("account.csv");

        Map<String, User.Type> string2type = new HashMap<>();
        string2type.put("小学", User.Type.PrimarySchool);
        string2type.put("初中", User.Type.MiddleSchool);
        string2type.put("高中", User.Type.HighSchool);

        users = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String account = parts[0];
                    String password = parts[1];
                    String typeStr = parts[2];
                    User.Type type = string2type.get(typeStr);
                    if (type != null) {
                        users.add(new User(account, password, type));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to open file", e);
        }
    }

    private void getRunningPath() {
        execPath = Paths.get("").toAbsolutePath();
    }
}
