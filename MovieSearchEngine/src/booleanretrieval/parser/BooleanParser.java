package moviesearchengine.booleanretrieval.parser;

// Generated from Boolean.g4 by ANTLR 4.0
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BooleanParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__1=1, T__0=2, AND=3, OR=4, NOT=5, TERM=6, WS=7;
	public static final String[] tokenNames = {
		"<INVALID>", "')'", "'('", "'AND'", "'OR'", "'NOT'", "TERM", "WS"
	};
	public static final int
		RULE_init = 0, RULE_andexpr = 1, RULE_notexpr = 2, RULE_atom = 3;
	public static final String[] ruleNames = {
		"init", "andexpr", "notexpr", "atom"
	};

	@Override
	public String getGrammarFileName() { return "Boolean.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public BooleanParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class InitContext extends ParserRuleContext {
		public List<AndexprContext> andexpr() {
			return getRuleContexts(AndexprContext.class);
		}
		public TerminalNode OR(int i) {
			return getToken(BooleanParser.OR, i);
		}
		public AndexprContext andexpr(int i) {
			return getRuleContext(AndexprContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(BooleanParser.OR); }
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanListener ) ((BooleanListener)listener).enterInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanListener ) ((BooleanListener)listener).exitInit(this);
		}
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_init);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8); andexpr();
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(9); match(OR);
				setState(10); andexpr();
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndexprContext extends ParserRuleContext {
		public List<TerminalNode> AND() { return getTokens(BooleanParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(BooleanParser.AND, i);
		}
		public List<NotexprContext> notexpr() {
			return getRuleContexts(NotexprContext.class);
		}
		public NotexprContext notexpr(int i) {
			return getRuleContext(NotexprContext.class,i);
		}
		public AndexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanListener ) ((BooleanListener)listener).enterAndexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanListener ) ((BooleanListener)listener).exitAndexpr(this);
		}
	}

	public final AndexprContext andexpr() throws RecognitionException {
		AndexprContext _localctx = new AndexprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_andexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16); notexpr();
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(17); match(AND);
				setState(18); notexpr();
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotexprContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TerminalNode NOT() { return getToken(BooleanParser.NOT, 0); }
		public NotexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanListener ) ((BooleanListener)listener).enterNotexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanListener ) ((BooleanListener)listener).exitNotexpr(this);
		}
	}

	public final NotexprContext notexpr() throws RecognitionException {
		NotexprContext _localctx = new NotexprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_notexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(24); match(NOT);
				}
			}

			setState(27); atom();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public TerminalNode TERM() { return getToken(BooleanParser.TERM, 0); }
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanListener ) ((BooleanListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanListener ) ((BooleanListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_atom);
		try {
			setState(34);
			switch (_input.LA(1)) {
			case TERM:
				enterOuterAlt(_localctx, 1);
				{
				setState(29); match(TERM);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(30); match(2);
				setState(31); init();
				setState(32); match(1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3\t\'\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\3\2\3\2\3\2\7\2\16\n\2\f\2\16"+
		"\2\21\13\2\3\3\3\3\3\3\7\3\26\n\3\f\3\16\3\31\13\3\3\4\5\4\34\n\4\3\4"+
		"\3\4\3\5\3\5\3\5\3\5\3\5\5\5%\n\5\3\5\2\6\2\4\6\b\2\2&\2\n\3\2\2\2\4\22"+
		"\3\2\2\2\6\33\3\2\2\2\b$\3\2\2\2\n\17\5\4\3\2\13\f\7\6\2\2\f\16\5\4\3"+
		"\2\r\13\3\2\2\2\16\21\3\2\2\2\17\r\3\2\2\2\17\20\3\2\2\2\20\3\3\2\2\2"+
		"\21\17\3\2\2\2\22\27\5\6\4\2\23\24\7\5\2\2\24\26\5\6\4\2\25\23\3\2\2\2"+
		"\26\31\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\5\3\2\2\2\31\27\3\2\2\2"+
		"\32\34\7\7\2\2\33\32\3\2\2\2\33\34\3\2\2\2\34\35\3\2\2\2\35\36\5\b\5\2"+
		"\36\7\3\2\2\2\37%\7\b\2\2 !\7\4\2\2!\"\5\2\2\2\"#\7\3\2\2#%\3\2\2\2$\37"+
		"\3\2\2\2$ \3\2\2\2%\t\3\2\2\2\6\17\27\33$";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}