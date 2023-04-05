package com.mycompany.project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContentFilter {

    public static List<String> apply(List<String> entries, String key) {
        List<String> output = new ArrayList<>();
        for (String entry : entries) {
            if (containsKey(entry, key)) {
                output.add(entry);
            }
        }
        return output;
    }

    private static boolean containsKey(String entry, String key) {
        if (entry == null || key == null) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(entry))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(key)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            // handle exception
            return false;
        }
    }
}
