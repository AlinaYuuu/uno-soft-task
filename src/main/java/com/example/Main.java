package com.example;

import java.nio.file.Paths;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("File not found");
            return;
        }
        String filePath = args[0];
        FileHandler fileHandler = new FileHandler();
        Set<String> uniqueLines = fileHandler.readFile(Paths.get(filePath));
        uniqueLines.stream().limit(50).forEach(System.out::println);
        System.out.println(uniqueLines.size());
    }
}
