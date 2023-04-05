package com.mycompany.project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class CountFilter {
    
    public static List<String> apply(List<String> entries, String key, int min) throws IOException {
        List<String> output = new ArrayList<>();
        for (String entry : entries) {
            if (isFile(entry) && countOccurrences(entry, key) >= min) {
                output.add(entry);
            }
        }
        return output;
    }
    
    private static boolean isFile(String entry) {
        // Check if entry is a file (not directory)
        File file = new File(entry);
        return file.isFile();
    }
    
    private static int countOccurrences(String filePath, String key) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int count = 0;
        String line = reader.readLine();
        while (line != null) {
            count += countOccurrencesInLine(line, key);
            line = reader.readLine();
        }
        reader.close();
        return count;
    }
    
    private static int countOccurrencesInLine(String line, String key) {
        int count = 0;
        int index = line.indexOf(key);
        while (index != -1) {
            count++;
            index = line.indexOf(key, index + 1);
        }
        return count;
    }
}
