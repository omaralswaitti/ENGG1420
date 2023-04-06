/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;
import java.io.*;
import java.util.*;

/**
 *
 * @author SidZa
 */

public class Split {
  
    private int lines;

    public Split(int lines) // Constructor to initialize the number of lines per output file
    {
        this.lines = lines;
    }

    public List<String> split(File inputFile) throws IOException // Method to split a single file into multiple files
    {
        List<String> outputFiles = new ArrayList<>();
        // Ignore directories
        if (inputFile.isDirectory()) 
        {
            return outputFiles;
        }

        // Create a BufferedReader to read the input file line by line
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line = null;
        int lineCount = 0;
        int partNumber = 1;
        BufferedWriter writer = null;
        String outputFileName = null;

        while ((line = reader.readLine()) != null) 
        { 
            if (lineCount == 0) // If this is the first line of a new output file, create a new file and writer
            {            
                outputFileName = inputFile.getName() + ".part" + partNumber + ".txt"; // Generate the output file name         
                writer = new BufferedWriter(new FileWriter(outputFileName));// Create a new BufferedWriter to write to the output file               
                outputFiles.add(outputFileName);// Add the output file name to the list of output files
            }
     
            writer.write(line);// Write the current line to the output file
            writer.newLine();// Write a newline character after the line
            lineCount++; // Increment the line count
            
            if (lineCount == lines) // If we have reached the desired number of lines, close the current output file and move on to the next one
            {
                writer.close();
                writer = null; // Set writer to null to indicate that we need to create a new output file
                partNumber++;
                lineCount = 0;
            }
        }

        if (writer != null) // Close the last output file
        {
            writer.close();
        }
        reader.close();// Close the input file reader
       
        return outputFiles; // Return the list of output files that were generated
    }

    // Method to split a list of files into multiple files
    public List<String> split(List<File> inputFiles) throws IOException {
        List<String> outputFiles = new ArrayList<>();

        // Split each input file and add its output files to the list of output files
        for (File inputFile : inputFiles) {
            List<String> outputFileList = split(inputFile);
            outputFiles.addAll(outputFileList);
        }

        // Return the list of output files that were generated
        return outputFiles;
    }
}
