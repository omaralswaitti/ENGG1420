package com.mycompany.project;

import java.util.List;

public class Print {

    public void printEntries(List<Entry> entries) {
        for (Entry entry : entries) {
            if (entry.location.equals("local")) {
                System.out.println("Name: " + entry.name);
                System.out.println("Length: " + entry.file.length());
                System.out.println("Absolute Path: " + entry.file.getAbsolutePath());
            } else if (entry.location.equals("remote")) {
                System.out.println("Entry Id: " + ((RemoteEntry) entries).getEntryId());
                System.out.println("Name: " + entry.name);
                System.out.println("Length: " + entry.file.length());
                System.out.println("Absolute Path: " + ((RemoteEntry) entries).getFullPath());
            }
        }

    }
}