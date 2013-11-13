/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.tfidf;

import moviesearchengine.util.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author flavius
 */
public class TfIdf {

//    static private int termFrequency(String term, String docId) {
//        term = term.trim();
//        if ("".equals(term)) {
//            return 0;
//        }
//
//        ArrayList<String> content = docIdToContent.get(docId);
//        int tf = 0;
//
//        for (String t : content) {
//            if (term.equals(t)) {
//                tf++;
//            }
//        }
//
//        return tf;
//    }
//    static private boolean belongsTo(String term, ArrayList<String> content) {
//        for (String t : content) {
//            if (term.equals(t)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//    static private int documentFrequency(String term) {
//        term = term.trim();
//        if ("".equals(term)) {
//            return 0;
//        }
//
//        int df = 0;
//
//        for (ArrayList<String> content : docIdToContent.values()) {
//            if (belongsTo(term, content)) {
//                df++;
//            }
//        }
//
//        return df;
//    }
    public static HashMap<String, IndexEntry> buildIndex(HashMap<String, ArrayList<String>> docIdToContent) {
        if (docIdToContent == null || docIdToContent.isEmpty()) {
            return new HashMap<String, IndexEntry>();
        }
        
        
        System.out.println("Starting to build the index...");
        HashMap<String, IndexEntry> termToIndexEntry = new HashMap<String, IndexEntry>();
        // for each document in the movie collection
        for (Map.Entry<String, ArrayList<String>> entry : docIdToContent.entrySet()) {
            // for each term in the current document
            for (String t : entry.getValue()) {
                IndexEntry ie = termToIndexEntry.get(t);
                // if term is not indexed
                if (ie == null) {
                    // add term to index
                    ie = new IndexEntry(entry.getKey());
                    termToIndexEntry.put(t, ie);
                } else {
                    // update index
                    ie.update(entry.getKey());
                }
            }
            System.out.println("Processed movie " + entry.getKey());
        }
        System.out.println("Finished building the index.");
        return termToIndexEntry;
    }

//    static private void printIndex() {
//        for (Map.Entry<String, IndexEntry> entry : termToIndexEntry.entrySet()) {
//            System.out.print("term=" + entry.getKey());
//            IndexEntry ie = entry.getValue();
//            System.out.println(" df=" + ie.getDf());
//            for (Map.Entry<String, Integer> e : ie.getDocIdToTf().entrySet()) {
//                System.out.println("  docId=" + e.getKey() + " tf=" + e.getValue());
//            }
//        }
//    }
    private static void writeTfIdfToFile(int N, HashMap<String, IndexEntry> termToIndexEntry) throws Exception {
        if (termToIndexEntry == null || termToIndexEntry.isEmpty()) {
            System.out.println("Index is null or empty.");
            return;
        }
        
        
        System.out.println("Starting to write to file...");
        
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.OUTPUT_TFIDF_FILENAME));
//        bw.write("term;docId;tf-idf");
        bw.write("term;docId;tf;df;idf;tf-idf");
        bw.newLine();

        for (Map.Entry<String, IndexEntry> entry : termToIndexEntry.entrySet()) {
            IndexEntry ie = entry.getValue();
            int df = ie.getDf();
            double idf = Math.log10(N / df);
            for (Map.Entry<String, Integer> e : ie.getDocIdToTf().entrySet()) {
                int tf = e.getValue();
                StringBuilder builder = new StringBuilder();
//                builder.append(entry.getKey()).append(';').append(e.getKey()).append(';').append(tf * idf);
                builder.append(entry.getKey()).append(';').append(e.getKey()).append(';').append(tf).append(';').append(df).append(';').append(idf).append(';').append(tf * idf);
                bw.write(builder.toString());
//                bw.write(entry.getKey() + ";" + e.getKey() + ";" + (tf * idf));
//                bw.write(entry.getKey() + ";" + e.getKey() + ";" + tf + ";" + df + ";" + idf + ";" + (tf * idf));
                bw.newLine();
            }
            System.out.println("Processed term " + entry.getKey());
        }

        bw.flush();
        bw.close();

        System.out.println("Finished writing to file " + Constants.OUTPUT_TFIDF_FILENAME + ".");
    }

    /*public static HashMap<String, IndexEntry> tokenizeAndIndexDocs(HashMap<String, ArrayList<String>> docIdToContent) throws Exception {
        if (docIdToContent == null || docIdToContent.isEmpty()) {
            return new HashMap<String, IndexEntry>();
        }
        
        
        System.out.println("Starting to tokenize...");
        for (Map.Entry<String, ArrayList<String>> entry : docIdToContent.entrySet()) {
            System.out.println("Processing movie " + entry.getKey());
            entry.setValue(TextAnalyzer.tokenizeText(entry.getValue().get(0), Constants.SIMPLE_ANALYZER));
        }
        System.out.println("Finished tokenizing.");
        
        
        return buildIndex(docIdToContent);
    }*/
    
    public static HashMap<String, ArrayList<String>> tokenizeDocs(HashMap<String, ArrayList<String>> docIdToContent, String analyzerType) throws Exception {
        if (docIdToContent == null || docIdToContent.isEmpty()) {
            return new HashMap<String, ArrayList<String>>();
        }
        
        System.out.println("Starting to tokenize...");
        for (Map.Entry<String, ArrayList<String>> entry : docIdToContent.entrySet()) {
            System.out.println("Processing movie " + entry.getKey());
            entry.setValue(TextAnalyzer.tokenizeText(entry.getValue().get(0), analyzerType));
        }
        System.out.println("Finished tokenizing.");
        return docIdToContent;
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("maximum amount of memory: " + Runtime.getRuntime().maxMemory() + " bytes");

        
        System.out.println("Starting to parse the XML file...");
        HashMap<String, ArrayList<String>> docIdToContent = SAXDocExtraction.extractDocs(Constants.INPUT_XML_FILENAME);
        System.out.println("Finished parsing the XML file.");
        
        
        docIdToContent = tokenizeDocs(docIdToContent, Constants.STANDARD_ANALYZER);

        
        writeTfIdfToFile(docIdToContent.size(), buildIndex(docIdToContent));
    }
}
