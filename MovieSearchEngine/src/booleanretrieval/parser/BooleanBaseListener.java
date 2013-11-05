package moviesearchengine.booleanretrieval.parser;

// Generated from Boolean.g4 by ANTLR 4.0
import java.util.*;
import moviesearchengine.booleanretrieval.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ErrorNode;

public class BooleanBaseListener implements BooleanListener {

    private Stack<ArrayList<Posting>> postingsStack = new Stack<ArrayList<Posting>>();
    private Stack<Boolean> notStack = new Stack<Boolean>();
    private boolean firstTime = true;

    public void printQueryResult() {
        // Print whatever you have in the stack.
        while (!postingsStack.empty()) {
            ArrayList<Posting> postings = postingsStack.pop();
            for (Posting p : postings) {
                System.out.println("Query Results");
                System.out.println("--------------------");
                for (int i : p) {
                    System.out.print(i + " ");
                }
                System.out.println();
                System.out.println("--------------------");
            }
        }
    }

    @Override
    public void enterAtom(BooleanParser.AtomContext ctx) {
    }

    @Override
    public void exitAtom(BooleanParser.AtomContext ctx) {
    }

    @Override
    public void enterAndexpr(BooleanParser.AndexprContext ctx) {
        ArrayList<Posting> postings = new ArrayList<Posting>();
        postingsStack.push(postings);
    }

    @Override
    public void exitAndexpr(BooleanParser.AndexprContext ctx) {
        ArrayList<Posting> postings = postingsStack.pop();
        if (!postings.isEmpty()) {
            Posting posting = BooleanRetrieval.intersection(postings);
            // Add posting to stack even if it's empty.
            postingsStack.peek().add(posting);
        }
    }

    @Override
    public void enterInit(BooleanParser.InitContext ctx) {
        if (firstTime) {
            // This stack entry should hold the final result.
            ArrayList<Posting> postings = new ArrayList<Posting>();
            postingsStack.push(postings);
            firstTime = false;
        }

        ArrayList<Posting> postings = new ArrayList<Posting>();
        postingsStack.push(postings);
    }

    @Override
    public void exitInit(BooleanParser.InitContext ctx) {
        ArrayList<Posting> postings = postingsStack.pop();
        if (!postings.isEmpty()) {
            Posting posting = BooleanRetrieval.union(postings);
            postingsStack.peek().add(posting);
        }
    }

    @Override
    public void enterNotexpr(BooleanParser.NotexprContext ctx) {
        ArrayList<Posting> postings = new ArrayList<Posting>();
        postingsStack.push(postings);
        notStack.push(false);
    }

    @Override
    public void exitNotexpr(BooleanParser.NotexprContext ctx) {
        ArrayList<Posting> postings = postingsStack.pop();
        if (!postings.isEmpty()) {
            if (notStack.pop()) {
                // Evaluate NOT.
                if (postings.size() == 1) {
                    Posting posting = BooleanRetrieval.complement(postings.get(0));
                    postingsStack.peek().add(posting);
                } else {
                    // Don't forget to remove this.
                    throw new RuntimeException("NOT is a unary operator.");
                }
            } else {
                // Don't evaluate NOT.
                if (postings.size() == 1) {
                    postingsStack.peek().add(postings.get(0));
                } else {
                    // Don't forget to remove this.
                    throw new RuntimeException("NOT is a unary operator.");
                }
            }
        }
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        String term = node.getText();
        // Ignore terminals (, ), AND, OR, and NOT. 
        if (!"(".equals(term) && !")".equals(term) && !"AND".equals(term) && !"OR".equals(term) && !"NOT".equals(term)) {
            postingsStack.peek().add(BooleanRetrieval.retrievePostings(term));
        } else if ("NOT".equals(term)) {
            notStack.pop();
            notStack.push(true);
        }
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
    }
}