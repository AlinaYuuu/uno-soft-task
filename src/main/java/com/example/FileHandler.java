package com.example;

import com.example.util.LineValidator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            groups.sort((group1, group2) -> {
                int totalElements1 = getTotalElementsInGroup(group1);
                int totalElements2 = getTotalElementsInGroup(group2);
                return Integer.compare(totalElements2, totalElements1);
            });

            writer.write(groups.size() + "\n\n");

            AtomicInteger groupNumber = new AtomicInteger(1); // COUNTER
            for (List<String> group : groups) {
                writer.write("Группа " + groupNumber.getAndIncrement() + "\n");

                for (String line : group) {
                    writer.write(line + "\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getTotalElementsInGroup(List<String> group) {
        return group.stream()
                .mapToInt(line -> (int) line.chars().filter(c -> c == ';').count() + 1)
                .sum();
    }
}
