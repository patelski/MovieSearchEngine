package SentimentClassification;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.DenseInstance;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.classifiers.meta.FilteredClassifier;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.Evaluation;

/**
 *
 * @author Stefan
 */
public class Classifier {

    private FilteredClassifier classifier;
    private Instances trainingData;
    private Instances testData;
    ArrayList<String> classValues = new ArrayList();

    //create an instance from two strings
    public Instance createInstance(String text, String class1) {
        double[] instanceValue = new double[2];
        instanceValue[0] = trainingData.attribute(0).addStringValue(text);
        instanceValue[1] = trainingData.attribute(1).addStringValue(class1);
        Instance instance = new DenseInstance(1.0, instanceValue);
        return instance;
    }

    public Classifier() {
        //create list of attributes
        ArrayList<Attribute> attributes = new ArrayList();
        //add first attribute: the reviews
        attributes.add(new Attribute("Review", (ArrayList) null));
        //create list of classes that a review could be classed as
        classValues.add("positive");
        classValues.add("negative");
        attributes.add(new Attribute("Class", classValues));
        //create trainingData structure
        trainingData = new Instances("Instances", attributes, 10);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        //add trraining data
        try {
            for (int i = 1; i <= 500; i++) {
                String review = new Scanner(new File("sentimentTrainingData/pos/pos (" + i + ").txt")).useDelimiter("\\A").next();
                trainingData.add(createInstance(review,"positive"));
            }
            for (int i = 1; i <= 500; i++) {
                String review = new Scanner(new File("sentimentTrainingData/pos/neg (" + i + ").txt")).useDelimiter("\\A").next();
                trainingData.add(createInstance(review,"negative"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringToWordVector filter = new StringToWordVector();
        filter.setLowerCaseTokens(true);
        classifier = new FilteredClassifier();
        classifier.setFilter(filter);
        classifier.setClassifier(new NaiveBayes());
        new FilteredClassifier().setFilter(filter);
        //classifier.setClassifier(new J48());
        //classifier.setClassifier(new SMO());
    }
}
