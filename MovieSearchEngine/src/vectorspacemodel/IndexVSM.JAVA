/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexvsm;

import java.io.*;
import java.util.*;
import java.math.*;

/**
 *
 * @author Thomas
 */
public class IndexVSM {

    public static String filePath = "tf-idf-extended.csv";
    public static String newFile = "C:\\Users\\Thomas\\Dropbox\\Vakken\\Web information retrieval and data mining\\Project\\IndexVSM.txt";
    static int[] docID = new int[5000]; // for fast check 100
    static String[] tfidf = new String[5000]; // for fast check 100
    static String[][] docIDtfidf = new String[docID.length][tfidf.length];
    static int sizeToIncrement = 1;
    static int sID;
    static String sTfIdf;
//    static int counter;
//    static HashMap hm = new HashMap<Integer, ArrayList<String>>();

    public static void main(String[] arg) throws Exception {

        BufferedReader CSVFile
                = new BufferedReader(new FileReader(filePath));

        String dataRow = CSVFile.readLine(); // Read first line.
        dataRow = CSVFile.readLine(); // Read second line.
        // The while checks to see if the data is null. If 
        // it is, we've hit the end of the file. If not, 
        // process the data.

//        counter = 0;
        while (dataRow != null && !dataRow.isEmpty()) { // add && counter<=100 for fast check
            String[] dataArray = dataRow.split(",");
            for (String item : dataArray) {
//                VSMimplement(dataArray);// int dataArray[1]; double dataArray[6]);
//                System.out.println(dataArray[1]);
//                System.out.println("next Array");
                System.out.print(item + "\t");
            }
            System.out.println(); // Print the data line.

            System.out.println("dataArray[1] = " + dataArray[1] + " ");
            System.out.println("dataArray[5] = " + dataArray[5] + " ");
            VSMimplement(dataArray);

            System.out.println("next Array");
            dataRow = CSVFile.readLine(); // Read next line of data.
//            counter = counter + 1;

        }
        // Close the file once all data has been read.
        CSVFile.close();

        // End the printout with a blank line.
        System.out.println();

        PrintWriter writer = new PrintWriter(newFile);
        System.out.println("Start writer");

        for (int i = 0; i < docID.length; i++) {
            if (docID[i] == i) {
                writer.print(docID[i] + " ");
                System.out.println(docID);
                for (int j = 0; j < tfidf.length; j++) {
                    if (docIDtfidf[i][j] != null) {
                        writer.print(docIDtfidf[i][j] + " ");
                        System.out.println("Next tfidf no " + j);
                    } else {
                        j = tfidf.length;
                        System.out.println("Next docID");
                    }
                }
                writer.println(" ");
            }
        }
        writer.close();

    } //main()

    static void VSMimplement(String[] dataArray) {
        System.out.println("start implementation");
        sID = (int) Math.round(Double.parseDouble(dataArray[1]));
        System.out.println("sID = " + sID);
        sTfIdf = dataArray[0] + "(" + dataArray[5] + ")";
        System.out.println("sTfIdf = " + sTfIdf);

        if (docID[sID] != sID) {
            docID[sID] = sID;
            docIDtfidf[sID][0] = sTfIdf;
//                    docID = Arrays.copyOf(docID, docID.length + sizeToIncrement);
            System.out.println("new term: ");
            System.out.println("docID = " + docID[sID]);
            System.out.println("tfidf = " + docIDtfidf[sID][0]);
            System.out.println();

        } else {
            for (int j = 0; j < tfidf.length + 1; j++) {
                System.out.println("start j LOOP " + j);
                if (docIDtfidf[sID][j] == null) {
                    docIDtfidf[sID][j] = sTfIdf;
                    System.out.println("new tfidf: ");
                    System.out.println("docID = " + docID[sID]);
                    System.out.println("tfidf = " + docIDtfidf[sID][j]);
                    System.out.println();
                    j = tfidf.length + 1;

                }
            }
        }
    }
}
