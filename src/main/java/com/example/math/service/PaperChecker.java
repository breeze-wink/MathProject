package com.example.math.service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PaperChecker {

    private List<String> existedQuestions = new ArrayList<>();
    private Path paperFolder;

    public PaperChecker() {
        // 默认构造函数
    }

    public boolean checkPaper(String question) {
        getExistedQuestions();

        return !existedQuestions.contains(question);
    }

    public void bindPath(Path checkFolderPath) {
        this.paperFolder = checkFolderPath;
    }

    private void getExistedQuestions() {
        existedQuestions.clear();

        try {
            if (!Files.exists(paperFolder)) {
                Files.createDirectories(paperFolder);
            }

            DirectoryStream<Path> stream = Files.newDirectoryStream(paperFolder);
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    List<String> lines = Files.readAllLines(entry);
                    existedQuestions.addAll(lines);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read files", e);
        }
    }
}
