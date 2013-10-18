/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vectorspacemodel;

import static java.lang.Math.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;
import org.apache.lucene.util.*;
import static vectorspacemodel.CSVRead.filePath;

/**
 * http://lucene.apache.org/core/4_5_0/core/org/apache/lucene/analysis/package-summary.html
 *
 * @author Thomas
 */
public class VectorSpaceModel {

    private int docId;
    private int df;
    private int tf;
    private int idf;
    private double TfIdf;
    static String termq;
    static String termd;
    static double[] docVector;

    public static String filePath = "src\\tfidf\\tf-idf-extended.csv";
    
    Scanner user_input = new Scanner( System.in );
    
    public static void main(String[] args) throws Exception {
        Scanner user_input = new Scanner( System.in );
        termq= user_input.next( );
        
        CSVRead read = new CSVRead();
        if(read.dataArray[2] == termq){
            
        }
        
        
        
               
        int[] q;
        int[] d;
        
        q = new int[10];
        d = new int[10];
        
        CalcutateSimilatiry(q, d);
        } 
     

        // TODO code application logic here 

   
   public static double dotproduct(int[] q, int[] d){
       int n = q.length;
       int sum = 0;
       for(int i=1; i<n; i++){
              sum = sum + (q[i] * d[i]);
         }
       return sum;      
   }
   
   public static double norm(int[] q){
       int n = q.length;
       int sum = 0;
       for(int i=1; i<n; i++){
              sum = sum + (q[i] * q[i]);
         }
       return sqrt(sum);
   }
   
      public static double CalcutateSimilatiry(int[] q, int[] d){
       return dotproduct(q,d) / (norm(q) * norm(d));    
    }
}

