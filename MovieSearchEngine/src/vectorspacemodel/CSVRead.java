/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectorspacemodel;

import java.io.*;
import java.util.Arrays;

/**
 *
 * @author Thomas
 */
public class CSVRead {
    
    public static String filePath = "src\\tfidf\\tf-idf-extended.csv";
    
    public static void main(String[] arg) throws Exception {

  BufferedReader CSVFile = 
        new BufferedReader(new FileReader(filePath));

  String dataRow = CSVFile.readLine(); // Read first line.
  // The while checks to see if the data is null. If 
  // it is, we've hit the end of the file. If not, 
  // process the data.

  while (dataRow != null){
   String[] dataArray = dataRow.split(",");
   for (String item:dataArray) { 
//      System.out.print(item + "\t"); 
   }
   System.out.println(); // Print the data line.
   dataRow = CSVFile.readLine(); // Read next line of data.
   System.out.print(dataRow);
  }
  // Close the file once all data has been read.
  CSVFile.close();

  // End the printout with a blank line.
  System.out.println();

 } //main()
} // CSVRead
    

