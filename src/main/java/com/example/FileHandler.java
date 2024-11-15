package com.example;

import com.example.util.LineValidator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Set<String> uniqueLines = readFile(filePath);
        LineGrouper lineGrouper = new LineGrouper(uniqueLines.size());
        List<List<String>> groups = lineGrouper.groupLines(uniqueLines);
        List<List<String>> filteredGroups = groups.stream()
                .filter(group -> group.size() > 1)
                .sorted((g1, g2) -> Integer.compare(g2.size(), g1.size()))
                .collect(Collectors.toList());
        writeToFile(filteredGroups, Paths.get("output.txt"));
    }

    // writing groups to file
    private void writeToFile(List<List<String>> groups, Path outputPath) {
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
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
}
