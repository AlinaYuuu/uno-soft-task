package com.example;

import java.util.*;

public class LineGrouper {
    private final int[] dsu;
    private final List<Map<String, Integer>> positionToGroup;

    public LineGrouper(int size) {
        dsu = new int[size];
        positionToGroup = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dsu[i] = i;
        }
    }

    // path compression search
    private int find(int x) {
        if (dsu[x] != x) {
            dsu[x] = find(dsu[x]);
        }
        return dsu[x];
    }

    // combining groups
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            dsu[rootY] = rootX;
        }
    }

    public List<List<String>> groupLines(Set<String> lines) {
        List<String> lineList = new ArrayList<>(lines);

        for (int i = 0; i < lineList.size(); i++) {
            String[] elements = lineList.get(i).split(";");
            for (int position = 0; position < elements.length; position++) {
                String value = elements[position].trim();
                if (value.isEmpty()) continue;
                if (position >= positionToGroup.size()) {
                    positionToGroup.add(new HashMap<>());
                }
                Map<String, Integer> columnGroups = positionToGroup.get(position);
                if (columnGroups.containsKey(value)) {
                    int previousIndex = columnGroups.get(value);
                    union(i, previousIndex);
                } else {
                    columnGroups.put(value, i);
                }
            }
        }
        Map<Integer, List<String>> groupedLines = new HashMap<>();

        for (int i = 0; i < lineList.size(); i++) {
            int root = find(i);
            groupedLines.computeIfAbsent(root, k -> new ArrayList<>()).add(lineList.get(i));
        }
        List<List<String>> result = new ArrayList<>(groupedLines.values());
        result.sort((a, b) -> Integer.compare(b.size(), a.size()));

        return result;
    }

}
