
package com.mycompany.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SidZa
 */
public class Lister 
{
    public static List<File> selectEntries(List<File> inputList, int max) 
    {
        List<File> outputList = new ArrayList<>(); // Create an empty list to store the selected entries
        for (File entry : inputList) // Loop over each entry in the input list
        { 
            if (entry.isDirectory()) // Check if the entry is a directory
            { 
                File[] innerEntries = entry.listFiles(); // Get all the inner entries in the directory
                int numInnerEntries = innerEntries.length; // Count the number of inner entries
                for (int i = 0; i < numInnerEntries && i < max; i++) // Loop over the inner entries and select up to max entries
                { 
                    outputList.add(innerEntries[i]); // Add the selected entry to the output list
                }
            }
        }
        return outputList; // Return the list of selected entries
    }
    

    //WILL GO IN MAIN CLASS
    public static void main(String[] args) 
    {
        List<File> inputList = new ArrayList<>(); // Create a new empty list to store the input directory entries
        inputList.add(new File("/path/to/directory1")); // Add a directory entry to the input list
        inputList.add(new File("/path/to/directory2")); // Add another directory entry to the input list
        
        int max = 20; // Set the maximum number of entries to select
        
        List<File> outputList = selectEntries(inputList, max); // Call the selectEntries() method to select the entries
        
        for (File file : outputList) // Loop over the selected entries and print their names
        { 
            System.out.println(file.getName());
        }
    }
}