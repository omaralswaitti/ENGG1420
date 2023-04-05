package com.mycompany.project;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LengthFilter {
    public static List<File> filter(List<File> entries, long length, String operator) {
        List<File> output = new ArrayList<>();
        for (File entry : entries) {
            if (entry.isFile()) {
                long entryLength = entry.length();
                switch (operator) {
                    case "EQ":
                        if (entryLength == length) {
                            output.add(entry);
                        }
                        break;
                    case "NEQ":
                        if (entryLength != length) {
                            output.add(entry);
                        }
                        break;
                    case "GT":
                        if (entryLength > length) {
                            output.add(entry);
                        }
                        break;
                    case "GTE":
                        if (entryLength >= length) {
                            output.add(entry);
                        }
                        break;
                    case "LT":
                        if (entryLength < length) {
                            output.add(entry);
                        }
                        break;
                    case "LTE":
                        if (entryLength <= length) {
                            output.add(entry);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + operator);
                }
            }
        }
        return output;
    }
}
