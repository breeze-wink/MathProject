package com.example.math.service;

import com.example.math.model.User;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserService {

    private Map<String, User> users;
    private Path execPath;

    public UserService() {
        getRunningPath();
        readAccounts();
    }

    @PreDestroy
    public void onDestroy(){
        saveAccount();
    }
    public void saveAccount()
    {
        Path filePath = execPath.resolve("account.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (User user : users.values()) {
                writer.write(String.format("%s,%s,%s\n", user.getAccount(), user.getPassword(), user.getEmail()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file", e);
        }
    }

    // 校验用户的账号和密码
    public User validateUser(String account, String password) {
        User user = users.get(account);

        if (user != null && user.getPassword().equals(password))
        {
            return user;
        }

        return null;
    }


    // 从文件中读取账号信息
    private void readAccounts() {
        Path filePath = execPath.resolve("account.csv");

        users = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String account = parts[0];
                    String password = parts[1];
                    String email = parts[2];

                    users.put(account, new User(account, password, email));

                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to open file", e);
        }
    }

    private void getRunningPath() {
        execPath = Paths.get("").toAbsolutePath();
    }

    public boolean userExist(String userName){
        return users.get(userName) == null;
    }

    public boolean checkPasswordLegal(String password) {
        if (password == null || password.length() < 6 || password.length() > 10) {
            return false;
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,10}$";

        return password.matches(regex);
    }

    public void putUser(String userName, String password, String email){
        users.put(userName, new User(userName, password, email));
    }

    public boolean checkPasswordCorrect(String userName, String password) {
        return users.get(userName).getPassword().equals(password);
    }

    public void modifyPassword(String userName, String newPassword) {
        users.get(userName).setPassword(newPassword);
    }
}
