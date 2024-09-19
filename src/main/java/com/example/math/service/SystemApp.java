//package com.example.math.service;
//
//import com.example.math.model.User;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class SystemApp {
//    private User user;
//    private Path execPath;
//    private String schoolType;
//    private boolean switchingType;
//    private boolean endGenerate;
//    private List<User> users;
//
//    public SystemApp() {
//        this.user = null;
//        getRunningPath();
//        readAccounts();
//    }
//
//    public void run() {
//        int opt;
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            if (!switchingType) {
//                clearConsole();
//                System.out.println("请输入你的选择: 1.登陆  2.退出");
//                opt = inputInt(scanner);
//
//                if (opt != 1 && opt != 2) {
//                    System.out.println("请输入 1 或 2");
//                    continue;
//                }
//
//                if (opt == 2) {
//                    System.out.println("系统退出...");
//                    return;
//                }
//
//                login(scanner);
//            }
//
//            switchingType = false;
//            endGenerate = false;
//            while (!endGenerate) {
//                configureQuestion(scanner);
//            }
//        }
//    }
//
//    private void login(Scanner scanner) {
//        String account, password;
//        while (true) {
//            clearConsole();
//            System.out.println("请输入账号和密码");
//            String[] input = scanner.nextLine().split(" ");
//            account = input[0];
//            password = input[1];
//
//            if (!checkAccount(account, password)) {
//                System.out.println("请输入正确的用户名、密码");
//                try {
//                    Thread.sleep(2000); // 等待2秒
//                } catch (InterruptedException e) {
//                    // 处理异常
//                }
//                continue;
//            }
//            break;
//        }
//
//        Map<User.Type, String> type2School = new HashMap<>();
//        type2School.put(User.Type.PrimarySchool, "小学");
//        type2School.put(User.Type.MiddleSchool, "初中");
//        type2School.put(User.Type.HighSchool, "高中");
//
//        this.schoolType = type2School.get(user.getType());
//
//        System.out.println(STR."当前选择为\{this.schoolType}出题");
//    }
//
//    private boolean checkAccount(String account, String password) {
//        for (User user_ : users) {
//            if (account.equals(user_.getAccount()) && password.equals(user_.getPassword())) {
//                this.user = user_;
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void configureQuestion(Scanner scanner) {
//        System.out.printf("准备生成%s数学题目，请输入生成题目数量(输入-1将退出当前用户，重新登录)\n", schoolType);
//        String input;
//        int questionNum;
//        while (true) {
//            input = scanner.nextLine();
//            input = input.trim();
//            if (checkSwitch(input)) {
//                switchingType = true;
//                endGenerate = true;
//                return;
//            }
//            if (input.equals("-1")) {
//                endGenerate = true;
//                return;
//            }
//            if (!isNumber(input)) {
//                System.out.println("输入格式不对，请重新输入");
//                continue;
//            }
//            questionNum = Integer.parseInt(input);
//            if (questionNum < 10 || questionNum > 30) {
//                System.out.println("输入数字不在范围，请重新输入");
//                continue;
//            }
//            break;
//        }
//        generateQuestion(questionNum);
//    }
//
//    private boolean checkSwitch(String input) {
//        String opt1 = "切换为小学";
//        String opt2 = "切换为初中";
//        String opt3 = "切换为高中";
//        if (input.equals(opt1)) {
//            schoolType = "小学";
//            user.type = User.Type.PrimarySchool;
//            return true;
//        } else if (input.equals(opt2)) {
//            schoolType = "初中";
//            user.type = User.Type.MiddleSchool;
//            return true;
//        } else if (input.equals(opt3)) {
//            schoolType = "高中";
//            user.type = User.Type.HighSchool;
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isNumber(String input) {
//        if (input.isEmpty()) return false;
//        for (char c : input.toCharArray()) {
//            if (!Character.isDigit(c)) return false;
//        }
//        return true;
//    }
//
//    private void generateQuestion(int num) {
//        String account = user.account;
//        Path saveDirPath = execPath.resolve("Paper").resolve(account);
//        PaperChecker checker = new PaperChecker();
//        checker.bindPath(saveDirPath);
//        QuestionGenerator generator = QuestionGeneratorFactory.createQuestionGenerator(user.type);
//
//        StringBuilder paper = new StringBuilder();
//        String question;
//        Set<String> jointedQuestions = new HashSet<>();
//        for (int i = 0; i < num; i++) {
//            while (true) {
//                question = generator.generateQuestion();
//
//                if (jointedQuestions.contains(question)) {
//                    continue;
//                }
//                if (checker.checkPaper(question)) {
//                    break;
//                }
//            }
//            jointedQuestions.add(question);
//            paper.append(i + 1).append(".").append(question).append("\n\n");
//        }
//        savePaper(paper.toString(), saveDirPath);
//        System.out.println("生成成功");
//        try {
//            Thread.sleep(2000); // 等待2秒
//        } catch (InterruptedException e) {
//            // 处理异常
//        }
//        clearConsole();
//    }
//
//    private void getRunningPath() {
//        execPath = Paths.get("").toAbsolutePath();
//    }
//
//    private void readAccounts() {
//        Path filePath = execPath.resolve("account.csv");
//
//        Map<String, User.Type> string2type = new HashMap<>();
//        string2type.put("小学", User.Type.PrimarySchool);
//        string2type.put("初中", User.Type.MiddleSchool);
//        string2type.put("高中", User.Type.HighSchool);
//
//        users = new ArrayList<>();
//
//        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 3) {
//                    String account = parts[0];
//                    String password = parts[1];
//                    String typeStr = parts[2];
//                    User.Type type = string2type.get(typeStr);
//                    if (type != null) {
//                        users.add(new User(account, password, type));
//                    }
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to open file", e);
//        }
//    }
//
//    private void saveAccounts() {
//        Path filePath = execPath.resolve("account.csv");
//
//        Map<User.Type, String> type2String = new HashMap<>();
//        type2String.put(User.Type.PrimarySchool, "小学");
//        type2String.put(User.Type.MiddleSchool, "初中");
//        type2String.put(User.Type.HighSchool, "高中");
//
//        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
//            for (User user : users) {
//                String typeStr = type2String.get(user.type);
//                writer.write(STR."\{user.account},\{user.password},\{typeStr}");
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to save accounts", e);
//        }
//    }
//
//    private String getCurrentTimeStamp() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
//        return LocalDateTime.now().format(formatter);
//    }
//
//    private void savePaper(String questions, Path saveDirPath) {
//        try {
//            if (!Files.exists(saveDirPath)) {
//                Files.createDirectories(saveDirPath);
//            }
//            Path saveFilePath = saveDirPath.resolve(STR."\{getCurrentTimeStamp()}.txt");
//
//            Files.write(saveFilePath, questions.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to write out paper", e);
//        }
//    }
//
//    private void clearConsole() {
//        // 清屏操作，可能在不同平台下行为不同
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//    }
//
//    private int inputInt(Scanner scanner) {
//        while (true) {
//            String input = scanner.nextLine().trim();
//            try {
//                return Integer.parseInt(input);
//            } catch (NumberFormatException e) {
//                System.out.println("格式错误，请重新输入");
//            }
//        }
//    }
//}
