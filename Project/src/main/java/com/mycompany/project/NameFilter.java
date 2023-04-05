package com.mycompany.project;
import java.util.ArrayList;
import java.util.List;

public class NameFilter {
    public static List<String> apply(List<String> entries, String key) {
        List<String> filteredEntries = new ArrayList<>();
        for (String entry : entries) {
            if (entry.contains(key)) {
                filteredEntries.add(entry);
            }
        }
        return filteredEntries;
    }
}
