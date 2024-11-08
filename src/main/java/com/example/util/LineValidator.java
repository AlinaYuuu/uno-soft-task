package com.example.util;

import java.util.regex.Pattern;

public class LineValidator {
    private static final Pattern VALID_LINE_PATTERN = Pattern.compile("^\"[^\"]*\"(\\s*;\\s*\"[^\"]*\")*$");

    public static boolean isValid(String line) {
        return VALID_LINE_PATTERN.matcher(line).matches();
    }
}
