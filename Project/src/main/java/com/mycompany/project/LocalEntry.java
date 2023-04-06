package com.mycompany.project;

import java.io.File;

public class LocalEntry extends Entry {
    private File file;
    private String filePath;

    public LocalEntry(String filePath) {
        setFilePath(filePath);
        location = "local";
        setType();
        name = file.getName();
    }

    public File getFile() {
        return file;
    }

    public void setFilePath(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null.");
        }
        this.filePath = filePath;
        setFile(new File(filePath));
    }

    private void setFile(File file) {
        try {
            this.file = file;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setType() {
        type = file.isFile() ? "file" : "directory";
    }
}
