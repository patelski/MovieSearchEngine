/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.booleanretrieval;

import java.io.*;
import moviesearchengine.util.*;
import java.util.*;

/**
 *
 * @author flavius
 */
public class BooleanRetrieval {

    static private HashMap<String, ArrayList<String>> docIdToContent;
    static private HashMap<String, Posting> termToPosting = new HashMap<String, Posting>();

    static public void readIndexFromDisk() throws Exception {
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = new FileInputStream("index.ser");
            in = new ObjectInputStream(fis);
            termToPosting = (HashMap<String, Posting>) in.readObject();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    static public void main(String[] args) throws Exception {
        System.out.println("maximum amount of memory: " + Runtime.getRuntime().maxMemory() + " bytes");


        String XMLFilename = "movies.xml";


        System.out.println("Starting to parse the XML file...");
        docIdToContent = SAXDocExtraction.extractDocs(XMLFilename);
        System.out.println("Finished parsing the XML file.");


        System.out.println("Starting to tokenize...");
        for (Map.Entry<String, ArrayList<String>> entry : docIdToContent.entrySet()) {
            System.out.println("Processing movie " + entry.getKey());
            entry.setValue(TextAnalyzer.tokenizeText(entry.getValue().get(0)));
        }
        System.out.println("Finished tokenizing.");


        System.out.println("Starting to build the index...");
        buildIndex();
        System.out.println("Finished building the index.");


        // Write index to disk.
        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream("index.ser");
            out = new ObjectOutputStream(fos);
            out.writeObject(termToPosting);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void buildIndex() {
        // for each document in the movie collection
        for (Map.Entry<String, ArrayList<String>> entry : docIdToContent.entrySet()) {
            // for each term in the current document
            // entry.getKey() is docId
            Integer docId = Integer.parseInt(entry.getKey());
            for (String term : entry.getValue()) {
                Posting posting = termToPosting.get(term);
                // if term is not indexed
                if (posting == null) {
                    // add new index posting
                    posting = new Posting();
                    posting.add(docId);
                    termToPosting.put(term, posting);
                } else {
                    // update index posting
                    if (posting.indexOf(docId) == -1) {
                        posting.add(docId);
                    }
                }
            }
            System.out.println("Processed movie " + docId);
        }

        // sorting postings
        for (Posting posting : termToPosting.values()) {
            Collections.sort(posting);
        }
    }

    /*private static Posting conjunction(String term1, String term2) {
        Posting p1 = retrievePostings(term1);
        Posting p2 = retrievePostings(term2);

        if (p1.isEmpty() || p2.isEmpty()) {
            return new Posting();
        } else {
            return intersect(p1, p2);
        }
    }*/

    public static Posting retrievePostings(String term) {
        term = term.trim();
        if ("".equals(term)) {
            return new Posting();
        }

        Posting posting = termToPosting.get(term);
        // if term is not indexed
        if (posting == null) {
            return new Posting();
        } else {
            return posting;
        }
    }

    // Figure 1.6 from the manadatory book
    private static Posting intersect(Posting p1, Posting p2) {
        Posting answer = new Posting();
        int s1 = p1.size();
        int s2 = p2.size();
        int i = 0;
        int j = 0;
        while (i < s1 && j < s2) {
            int docId1 = p1.get(i);
            int docId2 = p2.get(j);
            if (docId1 == docId2) {
                answer.add(docId1);
                i++;
                j++;
            } else if (docId1 < docId2) {
                i++;
            } else {
                j++;
            }
        }
        return answer;
    }

    public static Posting complement(Posting posting) {
        // Build a TreeSet of all docIds.
        TreeSet<Integer> complement = new TreeSet<Integer>();
        for (Posting p : termToPosting.values()) {
            for (int docId : p) {
                complement.add(docId);
            }
        }
        
        // from Posting to HashSet
        TreeSet<Integer> subset = new TreeSet<Integer>();
        for (int docId : posting) {
            subset.add(docId);
        }
        
        complement.removeAll(subset);
        
        // from TreeSet to Posting
        Posting answer = new Posting();
        for (int docId : complement) {
            answer.add(docId);
        }
        
        return answer;
    }
    
    public static Posting union(ArrayList<Posting> postings) {
        if (postings.isEmpty()) {
            return new Posting();
        }

        // TreeSet disregards duplicate elements and orders the elements.
        TreeSet<Integer> ts = new TreeSet<Integer>();
        for (Posting posting : postings) {
            for (int docId : posting) {
                ts.add(docId);
            }
        }

        // from TreeSet to Posting
        Posting answer = new Posting();
        for (int docId : ts) {
            answer.add(docId);
        }

        return answer;
    }

    public static Posting intersection(ArrayList<Posting> postings) {
        if (postings.isEmpty()) {
            return new Posting();
        }

        Collections.sort(postings);

        Posting answer = postings.get(0);

        int i = 1;
        while (i < postings.size()) {
            answer = intersect(answer, postings.get(i));
            i++;
        }

        return answer;
    }

    // Figure 1.7 from the mandatory book
    /*public static Posting intersect(ArrayList<String> terms) {
        if (terms.isEmpty()) {
            return new Posting();
        }

        terms = sortByIncreasingFrequency(terms);
        Posting result = retrievePostings(terms.remove(0));
        while (!terms.isEmpty() && !result.isEmpty()) {
            result = intersect(result, retrievePostings(terms.remove(0)));
        }
        return result;
    }*/

    // Variation of intersect from Figure 1.7 from mandatory book
    /*public static Posting intersectWithAlreadyComputedResult(Posting result, ArrayList<String> terms) {
        if (result.isEmpty() || terms.isEmpty()) {
            return new Posting();
        }

        terms = sortByIncreasingFrequency(terms);
        while (!terms.isEmpty() && !result.isEmpty()) {
            result = intersect(result, retrievePostings(terms.remove(0)));
        }
        return result;
    }*/

    /*private static ArrayList<String> sortByIncreasingFrequency(ArrayList<String> terms) {
        ArrayList<Pair> pairs = new ArrayList<Pair>();
        for (String term : terms) {
            Posting posting = termToPosting.get(term);
            // if term is not indexed
            if (posting == null) {
                pairs.add(new Pair(term, 0));
            } else {
                pairs.add(new Pair(term, posting.size()));
            }
        }

        Collections.sort(pairs);

        terms = new ArrayList<String>();
        for (Pair pair : pairs) {
            terms.add(pair.getTerm());
        }
        return terms;
    }*/
}
