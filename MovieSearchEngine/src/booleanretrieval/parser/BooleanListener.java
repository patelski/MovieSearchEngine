package moviesearchengine.booleanretrieval.parser;

// Generated from Boolean.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;

public interface BooleanListener extends ParseTreeListener {
	void enterAtom(BooleanParser.AtomContext ctx);
	void exitAtom(BooleanParser.AtomContext ctx);

	void enterAndexpr(BooleanParser.AndexprContext ctx);
	void exitAndexpr(BooleanParser.AndexprContext ctx);

	void enterInit(BooleanParser.InitContext ctx);
	void exitInit(BooleanParser.InitContext ctx);

	void enterNotexpr(BooleanParser.NotexprContext ctx);
	void exitNotexpr(BooleanParser.NotexprContext ctx);
}