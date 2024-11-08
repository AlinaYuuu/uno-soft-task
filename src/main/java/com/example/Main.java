package com.example;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("File not found");
            return;
        }
        String filePath = args[0];
        FileHandler fileHandler = new FileHandler();
        fileHandler.processFile(Paths.get(filePath));
    }
}
