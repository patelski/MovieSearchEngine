package SentimentClassification;

/**
 *
 * @author Stefan
 * 
 * TODO:
 * classify movies.xml
 * get test data from rotten tomatoes reviews
 * Implement my own classification algorithm
 */
public class SentimentClassification {
    
    public static void main(String[] args) {
        double certainty;
        System.out.println("Starting...");
        Classifier classifier = new Classifier();
        System.out.println("Classifier constructed.");
        
        // String to be classified (these strings are not in the training data):
        String negativereview = "The climax is the biggest letdown, a giant hash of crosscutting and unremarkable (in an era in which we've seen everything) CGI.";
       
        // get the likelyhood that the string belongs to this class
        double[] likelyhoods = classifier.getLikelyhoods(negativereview);
        if (likelyhoods[0] < likelyhoods[1]){
            certainty = 100*likelyhoods[1];
        } else {
            certainty = 100*likelyhoods[0];
        }
        
        // Get the predicted class
        String classify = classifier.getClass(negativereview);
        System.out.println("Review text: " + negativereview);
        System.out.println("Predicted class: " + classify + " (" + certainty + "% certainty)");
        
        // Evaluate the classifier
        classifier.evaluate();
    }
}
