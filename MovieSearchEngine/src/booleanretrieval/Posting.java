/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.booleanretrieval;

import java.util.*;

/**
 *
 * @author flavius
 */
public class Posting extends ArrayList<Integer> implements Comparable<Posting> {

    @Override
    public int compareTo(Posting p) {
        return ((Integer) this.size()).compareTo((Integer) p.size());
    }
}
