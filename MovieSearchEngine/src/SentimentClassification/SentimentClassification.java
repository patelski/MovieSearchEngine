package SentimentClassification;

/**
 *
 * @author Stefan
 */
public class SentimentClassification {
    
    public static void main(String[] args) {
        System.out.println("Starting...");
        Classifier classifier = new Classifier();
        System.out.println("Classifier constructed.");
        
        // String to be classified (these strings are not in the training data):
        String negativereview = "how do you judge a film that is so bad , but intentionally so in spiceworld , the highly popular singing group the spice girls accomplish their major goal : mocking themselves with a purposely cheezy film and having a lot of fun doing it . if that was their goal , they did a fantastic job . so is it fair to give it such a low grade when it wasn't really meant to be much better than this ? honestly , i'd rather see this film before many others i gave higher grades , so does that mean i graded it inaccurately ? truth be known , i don't really think i can answer this question . to understand spiceworld , you have to understand the spice girls . unless you're very * very * young , or fairly older , you probably have at least heard of them. they're a group of five busty ";
        String positivereview = "in 1987 the stock market crashed , and oliver stone's wall street was released to critical acclaim and packed movie houses . wall street lucked out in its timing ; the recent crash gave the film a resonation it might not of had , and though its kill-or-be-killed approach to business was exaggerated , that very ideology was keyed into the mind set of the quintessential reagan-era businessman . stone constructed his film as a mythical good vs . evil tale relayed in the fast-paced milieu of the burgeoning stock exchange . critics and audiences lauded wall street , elevating its status to that of a contemporary classic . i'm proud to say that i will never be counted as one of them . i found wall street to be just as axiomatic and pandering as the majority of stone's output with its thin caricatures , obvious sentiments , and a charisma barren performance by the young charlie sheen . stone goes for broke in every scene ( the same could be said for nearly all of his works ) .";
        
        // For each class, get the likelyhood that the string belongs to this class
        double[] likelyhoods = classifier.getLikelyhoods(positivereview);
        System.out.println("Positive: " + likelyhoods[0] + " - Negative: " + likelyhoods[1]);
        
        // Get the predicted class
        String classify = classifier.getClass(positivereview);
        System.out.println("Predicted class: " + classify);
    }
}
