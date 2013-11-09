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
                descwords = desc.replaceAll("[^a-zA-Z ]", "").split("\\s+");
            } else {
                descwords = new String[0];
            }
            if (name != null){
                words.add(name);
            }
            words.addAll(Arrays.asList(descwords));
        }
        try (PrintWriter printWriter = new PrintWriter(
                                 "C:/Desktop/words.txt",
                                 "UTF-8")) {
            for (String word : words){      
                printWriter.println(word);
            }

        } catch (FileNotFoundException | UnsupportedEncodingException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
    

    public int levenshteinDistance(String one, String two) {
        int distance = 0;
        return distance;
    }

    public int Metaphone(String one, String two) {
        int metaphone = 0;
        return metaphone;
    }
    
    public static Set<String> vocabulary() throws FileNotFoundException, IOException{
        Set<String> vocabulary = new HashSet<>();
        FileInputStream fstream = new FileInputStream("C:/Desktop/words.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
            String line;
            while ((line = br.readLine()) != null) {
                vocabulary.add(line);
            }
        }
        return vocabulary;
    }

    public String[] suggestions(String query) throws FileNotFoundException, IOException {
        String[] suggestions = new String[2];
        String[] levenshteinword = new String[2];
        String[] metaphoneword = new String[2];
        Set<String> vocabulary = vocabulary();
        int levenshtein = 999;
        int levenshtein2 = 999;
        int Metaphone = 999;
        int Metaphone2 = 999;
        for (String word : vocabulary){  
            int levenshteinDistance = levenshteinDistance(word, query);
            int MetaphoneD = Metaphone(word, query);
            if (levenshteinDistance < levenshtein && levenshteinDistance != 0){
                levenshtein = levenshteinDistance;
                levenshteinword[0] = query;
            } else if (levenshteinDistance < levenshtein2 && levenshteinDistance != 0) {
                levenshtein2 = levenshteinDistance;
                levenshteinword[1] = query;
            }
            if (MetaphoneD < Metaphone && MetaphoneD != 0){
                Metaphone = MetaphoneD;
                metaphoneword[0] = query;
            } else if (MetaphoneD < Metaphone2 && MetaphoneD != 0){
                Metaphone2 = MetaphoneD;
                metaphoneword[1] = query;
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
    }
}
