package com.example;

import com.example.util.LineValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class FileHandler {

    // reading file and processing strings
    public Set<String> readFile(Path filePath) {
        try (BufferedReader reader =
                     filePath.toString().endsWith(".gz") ?
                             new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(filePath)))) :
                             Files.newBufferedReader(filePath)) {
            return reader.lines()
                    .filter(LineValidator::isValid)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    public void processFile(Path filePath) {

    }

    // writing groups to file
    private void writeToFile(List<List<String>> groups, Path outputPath) {

    }
}
