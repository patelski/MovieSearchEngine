/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.booleanretrieval.parser;

import moviesearchengine.booleanretrieval.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 *
 * @author flavius
 */
public class Test {

    static public void main(String[] args) throws Exception {
        // read index from file
        BooleanRetrieval.readIndexFromDisk();
        
        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream("(NOT david OR (bretanos AND phoenix)) AND radcliffe");

        // create a lexer that feeds off of input CharStream
        BooleanLexer lexer = new BooleanLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        BooleanParser parser = new BooleanParser(tokens);
        ParseTree tree = parser.init(); // begin parsing at init rule

//        System.out.println(tree.toStringTree(parser)); // print LISP-style tree

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        
        BooleanBaseListener bbl = new BooleanBaseListener();
        
        // Walk the tree created during the parse, trigger callbacks 
        walker.walk(bbl, tree);
        
        // Don't forget to remove this.
        bbl.printQueryResult();
    }
}
