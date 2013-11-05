/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.util;

import java.io.*;
import java.util.ArrayList;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.*;
import org.apache.lucene.analysis.en.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.*;

/**
 * http://lucene.apache.org/core/4_5_0/core/org/apache/lucene/analysis/package-summary.html
 *
 * @author flavius
 */
public class TextAnalyzer {
    static public ArrayList<String> tokenizeText(String text) throws Exception {
        if ("".equals(text)) {
            return new ArrayList<String>();
        }
        
        Version matchVersion = Version.LUCENE_45;

        // "This is a demo of the TokenStream API" results in
        // "demo" "tokenstream" "api"
        Analyzer analyzer = new StandardAnalyzer(matchVersion);

        // "This is a demo of the TokenStream API" results in
        // "This" "is" "a" "demo" "of" "the" "TokenStream" "API"
//        Analyzer analyzer = new WhitespaceAnalyzer(matchVersion);

        // "This is a demo of the TokenStream API" results in
        // "this" "is" "a" "demo" "of" "the" "tokenstream" "api" 
//        Analyzer analyzer = new SimpleAnalyzer(matchVersion);

//        Analyzer analyzer = new EnglishAnalyzer(matchVersion);
        
        TokenStream ts = analyzer.tokenStream("field", new StringReader(text));

//        OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
//        
//        try {
//            ts.reset(); // Resets this stream to the beginning. (Required)
//            while (ts.incrementToken()) {
//                System.out.println("token: " + ts.reflectAsString(true));
//                System.out.println("token start offset: "
//                        + offsetAtt.startOffset());
//                System.out.println("  token end offset: "
//                        + offsetAtt.endOffset());
//            }
//            // Perform end-of-stream operations, e.g. set the final offset.
//            ts.end();
//
//        } finally {
//            ts.close(); // Release resources associated with this stream.
//        }

        CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);

        try {
            ts.reset(); 
            ArrayList<String> tokens = new ArrayList<String>();
            
            // print all tokens until stream is exhausted
            while (ts.incrementToken()) {
//                System.out.println(termAtt.toString());
                tokens.add(termAtt.toString());
            }

            ts.end();
            return tokens;
        } finally {
            ts.close();
        }
    }
}
