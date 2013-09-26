/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
import au.com.bytecode.opencsv.CSVReader;

/**
 *
 * @author Stefan
 */
public class TextClassifier{
    private FilteredClassifier classifier;
    private Instances trainingData;
    private Instances testData;
    ArrayList<String> classValues = new ArrayList();
    
    public TextClassifier(){
        //create list of attributes
        ArrayList<Attribute> attributes = new ArrayList();
        //add first attribute: the text gathered from the course page
        attributes.add(new Attribute("Text", (ArrayList) null));
        //create list of classes that a piece of text could belong to       
        classValues.add("Code");
        classValues.add("Name");
        classValues.add("Department");
        classValues.add("Teacher");
        classValues.add("ECTS");
        classValues.add("Prerequisites");
        classValues.add("Webpage");
        classValues.add("Description");
        classValues.add("Type of examination");
        attributes.add(new Attribute("Class", classValues));
        //create trainingData structure
        trainingData = new Instances("Instances", attributes, 10);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        //System.out.println("test");
        
        //add training data
        try {
            CSVReader reader = new CSVReader(new FileReader("data/trainingDataSorted.csv"),';');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null){
                addInstance(nextLine[0],nextLine[1], trainingData);
            }
        } catch (Exception ex) {
            Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        StringToWordVector filter = new StringToWordVector();
        filter.setLowerCaseTokens(true);
        classifier = new FilteredClassifier();
        classifier.setFilter(filter);
        classifier.setClassifier(new NaiveBayes());
        new FilteredClassifier().setFilter(filter);
        //classifier.setClassifier(new J48());
        //classifier.setClassifier(new SMO());
        
        //use training data to train classifier
        try {
            classifier.buildClassifier(trainingData);
        } catch (Exception ex) {
            Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Evaluate classifier
        testData = new Instances("Instances", attributes, 10);
        testData.setClassIndex(trainingData.numAttributes() - 1);
        Evaluation eval;
        try {
            CSVReader reader = new CSVReader(new FileReader("data/testDataSorted.csv"),';');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null){
                addInstance(nextLine[0],nextLine[1],testData);
                //System.out.println(getClass(nextLine[1]) + " : " + nextLine[1]); output detailed results for test evaluation
            }
            eval = new Evaluation(trainingData);
            eval.evaluateModel(classifier, testData);
            //System.out.println(eval.toSummaryString("\nResults\n\n", false));
            //System.out.println(eval.pctCorrect()); //output percent correctly predicted from test data
        } catch (Exception ex) {
            Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Evaluation failed");
        }        
    }
    
    //create an instance from a string.
    public Instance makeInstance(String body, Instances data) {
        Instance instance = new DenseInstance(2);
        // Set value for type attribute
        Attribute typeAtt = trainingData.attribute("Text");
        instance.setValue(typeAtt, typeAtt.addStringValue(body));
        // Give instance acces to attribute information from the dataset
        instance.setDataset(trainingData);

        return instance;
    }
    
    //add an instance to the data.
    private void addInstance(String classValue, String text, Instances data){
        Instance instance = makeInstance(text, data);
        instance.setClassValue(classValue);
        data.add(instance);
    }
    
    
    //Classify an instance
    public Instance classify(Instance instance){
        instance.setClassMissing();
        double label;
        try {
            label = classifier.classifyInstance(instance);
            instance.setClassValue(label);
        } catch (Exception ex) {
            Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instance;
    }
    
    //create an instance from a string and classify it.
    public Instance createInstance(String text){
        System.out.println("classifying in progress...");
        Instance instance = makeInstance(text, trainingData);
        double label;
        try {
            label = classifier.classifyInstance(instance);
            instance.setClassValue(label);
            System.out.println("classifying worked");
        } catch (Exception ex) {
            Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("classifying error");
        }
        return instance;
    }
    
    //Get the class that a specific string is most likely to belong to.
    public String getClass(String text){
        //System.out.println("classifying in progress...");
        Instance instance = makeInstance(text, trainingData);
        String classV = "Undefined";
        double label;
        try {
            label = classifier.classifyInstance(instance);
            instance.setClassValue(label);
            //classV = Double.toString(instance.value(1));
            classV = instance.classAttribute().value((int) instance.classValue());
            //System.out.println("classifying worked");
        } catch (Exception ex) {
            Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("classifying error");
        }
        
        return classV;
    }
    
    //Get the index of the maximum from an array of doubles. If there is more than one maximum, get the last one.
    private int getLastMaxIndex(double[] array){
        int index = 0;
        for (int i = 1; i < array.length; i++){
            if (array[i] >= array[index]){
                index = i;
            }
        }
        return index;
    }
    
    //Given a set of strings, retrieve for each class a string that is the best match for that class.
    public String[] getClasses(ArrayList<String> stuff){
        double[][] distros = new double[stuff.size()][classValues.size()];
        double[][] transposed = new double[distros[0].length][distros.length];
        String[] bestElements = new String[classValues.size()];
        
        //For each string, create an array that contains the likelyhoods for each class that the string belongs to the class.
        for (int i = 0; i < stuff.size(); i++){
            Instance instance = makeInstance(stuff.get(i), trainingData);
            try {
                distros[i] = classifier.distributionForInstance(instance);
            } catch (Exception ex) {
                Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Transpose the distros "matrix".
        for (int i = 0; i < distros[0].length; i++){
            //System.out.println(classValues.get(i));
            for (int j = 0; j < distros.length; j++){
                transposed[i][j] = distros[j][i];
                //System.out.println(transposed[i][j]);
            }
        }
        
        //For each class, find the best match from the input strings.
        for (int i = 0; i < transposed.length; i++){
            int lastmaxindex = getLastMaxIndex(transposed[i]);
            if (transposed[i][lastmaxindex] > 0.05){
                bestElements[i] = stuff.get(lastmaxindex);
            } else{
                //If there are no strings that have a likelyhood to belong to the class above 0.5, the class is undefined.
                bestElements[i] = "undefined";
            }
        }
        return bestElements;
    }
}
