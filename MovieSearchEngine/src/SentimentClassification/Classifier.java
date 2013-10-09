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
    ArrayList<Attribute> attributes = new ArrayList();

    //create an instance from a string.
    public Instance makeInstance(String body, Instances data) {
        Instance instance = new DenseInstance(2);
        // Set value for type attribute
        Attribute typeAtt = trainingData.attribute("Review");
        instance.setValue(typeAtt, typeAtt.addStringValue(body));
        // Give instance acces to attribute information from the dataset
        instance.setDataset(trainingData);

        return instance;
    }

    public Classifier() {
        //add first attribute: the reviews
        attributes.add(new Attribute("Review", (ArrayList) null));
        //create list of classes that a review could be classed as
        classValues.add("positive");
        classValues.add("negative");
        attributes.add(new Attribute("Class", classValues));
        //create trainingData structure
        trainingData = new Instances("Instances", attributes, 10);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        //add training data
        try {
            for (int i = 1; i <= 500; i++) {
                String review = new Scanner(new File("sentimentTrainingData/pos/pos (" + i + ").txt")).useDelimiter("\\A").next();
                addInstance("positive", review, trainingData);
            }
            for (int i = 1; i <= 500; i++) {
                String review = new Scanner(new File("sentimentTrainingData/neg/neg (" + i + ").txt")).useDelimiter("\\A").next();
                addInstance("negative", review, trainingData);
            }
        } catch (Exception ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringToWordVector filter = new StringToWordVector();
        filter.setLowerCaseTokens(true);
        classifier = new FilteredClassifier();
        classifier.setFilter(filter);
        classifier.setClassifier(new NaiveBayes());
        //classifier.setClassifier(new J48());
        //classifier.setClassifier(new SMO());
        new FilteredClassifier().setFilter(filter);

        //use training data to train classifier
        try {
            classifier.buildClassifier(trainingData);
        } catch (Exception ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //add an instance to the data
    private void addInstance(String classValue, String text, Instances data) {
        Instance instance = makeInstance(text, data);
        instance.setClassValue(classValue);
        data.add(instance);
    }

    //Classify an instance
    public Instance classify(Instance instance) {
        //System.out.println("classifying in progress...");
        instance.setClassMissing();
        double label;
        try {
            label = classifier.classifyInstance(instance);
            instance.setClassValue(label);
            //System.out.println("classifying worked");
        } catch (Exception ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("classifying error");
        }
        return instance;
    }

    //create an instance from a string and classify it.
    public Instance classify(String text) {
        Instance instance = makeInstance(text, trainingData);
        instance = classify(instance);
        return instance;
    }

    // Return for each class how likely it is that the input text belongs to this class
    public double[] getLikelyhoods(String text) {
        Instance instance = classify(text);
        double[] likelyhoods = new double[2];
        try {
            likelyhoods = classifier.distributionForInstance(instance);
        } catch (Exception ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return likelyhoods;
    }

    // Classify a piece of text, return the class as a string
    public String getClass(String text) {
        double[] likelyhoods = getLikelyhoods(text);
        if (likelyhoods[0] < likelyhoods[1]) {
            return classValues.get(1);
        } else {
            return classValues.get(0);
        }
    }
    
    //Evaluate classifier
    public void evaluate(){
        testData = new Instances("Instances", attributes, 10);
        testData.setClassIndex(testData.numAttributes() - 1);
        Evaluation eval;
        try {
            for (int i = 501; i <= 1000; i++) {
                String review = new Scanner(new File("sentimentTrainingData/pos/pos (" + i + ").txt")).useDelimiter("\\A").next();
                addInstance("positive", review, testData);
            }
            for (int i = 501; i <= 1000; i++) {
                String review = new Scanner(new File("sentimentTrainingData/neg/neg (" + i + ").txt")).useDelimiter("\\A").next();
                addInstance("negative", review, testData);
            }
            eval = new Evaluation(trainingData);
            eval.evaluateModel(classifier, testData);
            System.out.println(eval.toSummaryString("\nEvaluation Results\n\n", false));
            System.out.println(eval.pctCorrect()); //output percent correctly predicted from test data
        } catch (Exception ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Evaluation failed");
        }        
    }
}
