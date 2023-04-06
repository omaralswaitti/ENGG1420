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
        if (inputFile.isDirectory()) 
        {
            return outputFiles;
        }

        BufferedReader reader = new BufferedReader(new FileReader(inputFile)); // Create a BufferedReader to read the input file line by line
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
    
    public List<String> split(List<File> inputFiles) throws IOException // Method to split a list of files into multiple files
    {
        List<String> outputFiles = new ArrayList<>();
        for (File inputFile : inputFiles) // Split each input file and add its output files to the list of output files
        {
            List<String> outputFileList = split(inputFile);
            outputFiles.addAll(outputFileList);
        }  
        return outputFiles; // Return the list of output files that were generated
    }



    //CALL IN MAIN
    public static void main(String[] args) 
    {
        Split splitter = new Split(10);// Create a new instance of the SplitFile class with the desired number of lines per output file    
        List<File> inputFiles = new ArrayList<>();// Create a list of input files to be split
        inputFiles.add(new File("File1.txt"));
        inputFiles.add(new File("File2.txt"));

        try 
        {      
            List<String> outputFiles = splitter.split(inputFiles); // Call the split() method to split the input files into multiple output files
            // Do something with the list of output file names
            // For example, you could print them out to the console:
            for (String fileName : outputFiles) 
            {
                System.out.println(fileName);
            }
        } 
        catch (IOException e) 
        {
            // Handle any exceptions that might occur during the file splitting process
            e.printStackTrace();
        }
    }
}