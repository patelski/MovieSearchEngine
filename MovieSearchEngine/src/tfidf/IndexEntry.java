/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.tfidf;

import java.util.*;

/**
 *
 * @author flavius
 */
public class IndexEntry {
    private int df;
    private HashMap<String, Integer> docIdToTf;

    public IndexEntry(String docId) {
        this.df = 1;
        this.docIdToTf = new HashMap<String, Integer>();
        this.docIdToTf.put(docId, 1);
    }

    public int getDf() {
        return df;
    }

    public HashMap<String, Integer> getDocIdToTf() {
        return docIdToTf;
    }
    
    public void update(String docId) {
        Integer tf = docIdToTf.get(docId);
        if (tf == null) {
            docIdToTf.put(docId, 1);
            this.df++;
        } else {
            docIdToTf.put(docId, ++tf);
        }
    }
}
