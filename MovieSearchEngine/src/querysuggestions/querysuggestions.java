/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package querysuggestions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;


/**
 *
 * @author Stefan
 */

public class querysuggestions {
    static Document dom;

    private static String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    public static void createVocabulary() throws ParserConfigurationException, SAXException, IOException {
        Set<String> words = new HashSet<>();
        String[] descwords;
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //Using factory get an instance of document builder
        DocumentBuilder db = dbf.newDocumentBuilder();
        //parse using builder to get DOM representation of the XML file
        dom = db.parse("../movies.xml");
        Element root = dom.getDocumentElement(); //get the root element
        NodeList nl = root.getElementsByTagName("movie");
        for (int i = 0; i < nl.getLength(); i++) { // for each movie
            Element movie = (Element) nl.item(i);
            String name = getTextValue(movie, "name");
            String desc = getTextValue(movie, "MovieInfo");
            if (desc != null){
                descwords = desc.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
            } else {
                descwords = new String[0];
            }
            if (name != null){
                words.add(name.toLowerCase());
            }
            words.addAll(Arrays.asList(descwords));
        }
        try (PrintWriter printWriter = new PrintWriter(
                                 "words.txt",
                                 "UTF-8")) {
            for (String word : words){
                if (word.length() > 0){
                    printWriter.println(word);
                }
            }

        } catch (FileNotFoundException | UnsupportedEncodingException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
    
    public static int levenshteinDistance(String s, String t) {
        int m = s.length();
        int n = t.length();
        int[][] d = new int[m+1][n+1];

        for (int i = 1; i <= m; i++){
            d[i][0] = i;
        }
        
        for (int j = 1; j <= n; j++){
            d[0][j] = j;
        }
        
        for (int j = 1; j <= n; j++){
            for (int i = 1; i <= m; i++){
                if (s.charAt(i-1) == t.charAt(j-1)){
                    d[i][j] = d[i-1][j-1];
                } else {
                    int minimum = 999;
                    if ((d[i-1][j] + 1) < minimum) {minimum = (d[i-1][j] + 1);}
                    if ((d[i][j-1] + 1) < minimum) {minimum = (d[i][j-1] + 1);}
                    if ((d[i-1][j-1] + 1) < minimum) {minimum = (d[i-1][j-1] + 1);}
                    d[i][j] = minimum;
                }
            }
        }
        /* debugging
        for (int i = 0; i <= m; i++){
            for (int j = 0; j <= n; j++){
                System.out.print("|"+ d[i][j]);
            }
            System.out.println();
        }
        */
        
        return d[m][n];
    }
    
    public static Set<String> regexFinder(String regex, String word, int size) {
        Set<String> found = new HashSet<>();
        for (int i = 0; i <= word.length()-size; i++){
            String piece = word.substring(i, i+size);
            if (piece.matches(regex)){
                found.add(piece);
            }
        }
        return found;
    }
    
    public static String regexReplacer(String word, String regexmatch, String regexreplace, String replacement, int size) {
        for (String piece : regexFinder(regexmatch,word,size)){
            String piece2 = piece.replaceFirst(regexreplace, replacement);
            word = word.replaceAll(piece, piece2);
        }
        return word;
    }

    public static String Metaphone(String word){
        word = word.toUpperCase();
        word = word.replaceFirst("^KN","N");
        word = word.replaceFirst("^GN","N");
        word = word.replaceFirst("^PN","N");
        word = word.replaceFirst("^AE","E");
        word = word.replaceFirst("^WR","R");
        word = word.replaceFirst("^PF","F");
        word = word.replaceFirst("^WH","W");
        word = word.replaceFirst("^X","S");
        word = word.replaceFirst("MB$","M");
        word = word.replaceAll("SCH","SKH");
        word = word.replaceAll("CIA","XIA");
        word = word.replaceAll("CH","XH");
        word = word.replaceAll("CI","SI");
        word = word.replaceAll("CE","SE");
        word = word.replaceAll("CY","SY");
        word = word.replaceAll("C","K");
        word = word.replaceAll("DGE","JGE");
        word = word.replaceAll("DGY","JGY");
        word = word.replaceAll("DGI","JGI");
        word = word.replaceAll("D","T");
        word = regexReplacer(word, "GH[^AEIOU]", "G", "", 3);
        word = word.replaceAll("GN$","N");
        word = word.replaceAll("GNED$","NED");
        word = regexReplacer(word, "[^G]G[IEY]", "G", "J", 3);
        word = word.replaceAll("G","K");
        word = regexReplacer(word, "[AEIOU]H[^AEIOU]", "H", "", 3);
        word = word.replaceAll("CK","K");
        word = word.replaceAll("PH","F");
        word = word.replaceAll("Q","K");
        word = word.replaceAll("SH","XH");
        word = word.replaceAll("SIO","XIO");
        word = word.replaceAll("SIA","XIA");
        word = word.replaceAll("TIA","XIA");
        word = word.replaceAll("TIO","XIO");
        word = word.replaceAll("TH","0");
        word = word.replaceAll("TCH","CH");
        word = word.replaceAll("V","F");
        word = word.replaceAll("^WH","W");
        word = regexReplacer(word, "W[^AEIOU]", "W", "", 2);
        return word;
    }
    
    public static int Metaphone(String one, String two) {
        int metaphone = 0;
        one = one.replaceAll("[^\\w\\s]", "");
        levenshteinDistance(Metaphone(one),Metaphone(two));
        return metaphone;
    }
    
    public static Set<String> vocabulary() throws FileNotFoundException, IOException{
        Set<String> vocabulary = new HashSet<>();
        FileInputStream fstream = new FileInputStream("words.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
            String line;
            while ((line = br.readLine()) != null) {
                vocabulary.add(line);
            }
        }
        return vocabulary;
    }

    public static String[] suggestions(String query) throws FileNotFoundException, IOException {
        query = query.toLowerCase();
        String[] suggestions = new String[2];
        String[] levenshteinword = new String[2];
        String[] metaphoneword = new String[2];
        levenshteinword[0] = "none";
        levenshteinword[1] = "none";
        metaphoneword[0] = "none";
        metaphoneword[1] = "none";
        Set<String> vocabulary = vocabulary();
        int levenshtein = 999;
        int levenshtein2 = 999;
        int Metaphone = 999;
        int Metaphone2 = 999;
        for (String word : vocabulary){
            if (!word.equals(query)){
                int levenshteinDistance = levenshteinDistance(word, query);
                int MetaphoneD = Metaphone(word, query);
                if (levenshteinDistance < levenshtein && levenshteinDistance != 0){
                    levenshtein = levenshteinDistance;
                    levenshteinword[0] = word;
                } else if (levenshteinDistance < levenshtein2 && levenshteinDistance != 0) {
                    levenshtein2 = levenshteinDistance;
                    levenshteinword[1] = word;
                }
                if (MetaphoneD < Metaphone){
                    Metaphone = MetaphoneD;
                    metaphoneword[0] = word;
                } else if (MetaphoneD < Metaphone2){
                    Metaphone2 = MetaphoneD;
                    metaphoneword[1] = word;
                }
            }
        }
        if (!levenshteinword[0].equals(metaphoneword[0])){
            suggestions[0] = levenshteinword[0];
            suggestions[1] = metaphoneword[0];
        } else {
            suggestions[0] = levenshteinword[0];
            suggestions[1] = metaphoneword[1];
        }
        return suggestions;
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        System.out.println("Testing query suggestions...");
        //createVocabulary();
        System.out.println(suggestions("corriolanus")[1]);
        //System.out.println(levenshteinDistance("sunday","saturday"));
    }
}
