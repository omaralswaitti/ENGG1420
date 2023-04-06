package com.mycompany.project;

import java.io.File;
import java.util.ArrayList;

public class Rename {

    
    public void renameFiles(ArrayList<File> fileList, String suffix) {
        for (File file : fileList) {
            // Get the name and extension of the file
            String name = file.getName();
            String extension = "";
            if (name.contains(".")) {
                int dotIndex = name.lastIndexOf(".");
                extension = name.substring(dotIndex);
                name = name.substring(0, dotIndex);
            }

            // Create the new name for the file by appending the suffix
            String newName = name + suffix + extension;

            // Rename the file
            File newNameFile = new File(file.getParent(), newName);
            boolean success = file.renameTo(newNameFile);
            if (!success) {
                System.out.println("Failed to rename file: " + file.getAbsolutePath());
            }
        }
    }
}
