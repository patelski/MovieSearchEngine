package SentimentClassification;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Stefan
 *
 * TODO:
 * find out which other Weka algorithms might be useful to test
 * Implement my own classification algorithm
 * get rottentomatoes.com test set
 */
public class SentimentClassification {

    static Classifier classifier = new Classifier();
    static double certainty;
    static Document dom;

    static void test() {
        System.out.println("Classifier constructed.");

        // String to be classified (these strings are not in the training data):
        String negativereview = "The climax is the biggest letdown, a giant hash of crosscutting and unremarkable (in an era in which we've seen everything) CGI.";

        // get the likelyhood that the string belongs to this class
        double certainty = classifier.getLikelyhood(negativereview);

        // Get the predicted class
        String classify = classifier.getClass(negativereview);
        System.out.println("Review text: " + negativereview);
        System.out.println("Predicted class: " + classify + " (" + certainty + "% certainty)");

        // Evaluate the classifier
        classifier.evaluate();
    }

    private static String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    private static Element getChildByTagName(Element ele, String tagName) {
        Element el = ele;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            el = (Element) nl.item(0);
            //textVal = el.getFirstChild().getNodeValue();
            return el;
        } else {
            return null;
        }

    }

    static void classifyMovies() {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            dom = db.parse("../movies_test.xml");
        } catch (ParserConfigurationException | SAXException | IOException pce) {
            pce.printStackTrace();
        }
        Element root = dom.getDocumentElement(); //get the root element
        NodeList nl = root.getElementsByTagName("movie");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) { // for each movie
                Element movie = (Element) nl.item(i);
                String name = getTextValue(movie, "name");
                System.out.println("Movie name: " + name);
                Element reviewsEL = getChildByTagName(movie, "reviews");
                // it works at least up to this point
                NodeList reviews = reviewsEL.getElementsByTagName("review");
                System.out.println(reviews.getLength());
                int counter = 0;
                int total = 0;
                int score = 0;
                if (reviews != null && reviews.getLength() > 0) {
                    for (int j = 0; j < reviews.getLength(); j++) {
                        Element reviewEL = (Element) reviews.item(j);
                        if (reviewEL.getFirstChild() != null) {
                            String review = reviewEL.getFirstChild().getNodeValue().split("$$")[0];
                            String pnpolarity = classifier.getClass(review);
                            int likelyhood = (int) Math.round(100 * classifier.getLikelyhood(review));
                            System.out.println("Review: " + review + " (" + likelyhood + "% " + pnpolarity + ")");
                            reviewEL.getFirstChild().setNodeValue(review + "$$" + pnpolarity + "$$" + likelyhood);
                            counter++;
                            total += Math.round(100 * classifier.getLikelyhoods(review)[0]);
                        }
                    }
                }
                if (counter > 0) {
                    score = total / counter;
                }
                Element classifiedscore = getChildByTagName(movie, "classifiedscore");
                if (classifiedscore == null) {
                    classifiedscore = dom.createElement("classifiedscore");
                    Node textnode = dom.createTextNode(Integer.toString(score));
                    classifiedscore.appendChild(textnode);
                    movie.appendChild(classifiedscore);
                } else {
                    classifiedscore.getFirstChild().setNodeValue(Integer.toString(score));
                }
                System.out.println("Movie parsed");
            }
            
            Transformer transformer = null;
            try {
                transformer = TransformerFactory.newInstance().newTransformer();
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(SentimentClassification.class.getName()).log(Level.SEVERE, null, ex);
            }
            Result output = new StreamResult(new File("../output.xml"));
            Source input = new DOMSource(dom);
            try {
                transformer.transform(input, output);
            } catch (TransformerException ex) {
                Logger.getLogger(SentimentClassification.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("All movies parsed");
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting...");
        //test();
        classifyMovies();
    }
}
