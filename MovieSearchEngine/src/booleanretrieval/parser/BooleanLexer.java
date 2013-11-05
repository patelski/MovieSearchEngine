package moviesearchengine.booleanretrieval.parser;

// Generated from Boolean.g4 by ANTLR 4.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BooleanLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__1=1, T__0=2, AND=3, OR=4, NOT=5, TERM=6, WS=7;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"')'", "'('", "'AND'", "'OR'", "'NOT'", "TERM", "WS"
	};
	public static final String[] ruleNames = {
		"T__1", "T__0", "AND", "OR", "NOT", "TERM", "WS"
	};


	public BooleanLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Boolean.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 6: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\t.\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3"+
		"\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\7\7"+
		"#\n\7\f\7\16\7&\13\7\3\b\6\b)\n\b\r\b\16\b*\3\b\3\b\2\t\3\3\1\5\4\1\7"+
		"\5\1\t\6\1\13\7\1\r\b\1\17\t\2\3\2\5\5\62;C\\c|\6//\62;C\\c|\5\13\f\17"+
		"\17\"\"/\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\3\21\3\2\2\2\5\23\3\2\2\2\7\25\3\2\2\2\t\31"+
		"\3\2\2\2\13\34\3\2\2\2\r \3\2\2\2\17(\3\2\2\2\21\22\7+\2\2\22\4\3\2\2"+
		"\2\23\24\7*\2\2\24\6\3\2\2\2\25\26\7C\2\2\26\27\7P\2\2\27\30\7F\2\2\30"+
		"\b\3\2\2\2\31\32\7Q\2\2\32\33\7T\2\2\33\n\3\2\2\2\34\35\7P\2\2\35\36\7"+
		"Q\2\2\36\37\7V\2\2\37\f\3\2\2\2 $\t\2\2\2!#\t\3\2\2\"!\3\2\2\2#&\3\2\2"+
		"\2$\"\3\2\2\2$%\3\2\2\2%\16\3\2\2\2&$\3\2\2\2\')\t\4\2\2(\'\3\2\2\2)*"+
		"\3\2\2\2*(\3\2\2\2*+\3\2\2\2+,\3\2\2\2,-\b\b\2\2-\20\3\2\2\2\5\2$*";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}