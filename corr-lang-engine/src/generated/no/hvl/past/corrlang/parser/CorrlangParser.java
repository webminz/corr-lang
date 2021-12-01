// Generated from Corrlang.g4 by ANTLR 4.8
package no.hvl.past.corrlang.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CorrlangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, WHITESPACE=57, INTEGER=58, FLOAT=59, 
		IDENTIFIER=60, QUALIFIED_ID=61, TECH_IDENTIFIER=62, URL_STRING=63, EXTERNAL_CODE_DELIMITTER=64, 
		EXTERNAL_CODE=65, BOOL=66;
	public static final int
		RULE_corrfile = 0, RULE_fileImport = 1, RULE_importURL = 2, RULE_endpointDefinition = 3, 
		RULE_endpointName = 4, RULE_endpointType = 5, RULE_endpointPlaftorm = 6, 
		RULE_endpointURL = 7, RULE_schemaURL = 8, RULE_corrspec = 9, RULE_corrSpecEndpointRef = 10, 
		RULE_corrSpecName = 11, RULE_corrSpecType = 12, RULE_commonality = 13, 
		RULE_elmentRefDef = 14, RULE_elmentRefAlias = 15, RULE_constraints = 16, 
		RULE_constraint = 17, RULE_constraintName = 18, RULE_commonalityType = 19, 
		RULE_commonalityName = 20, RULE_consistencyRuleRef = 21, RULE_subCummonalities = 22, 
		RULE_consistencyRule = 23, RULE_ruleName = 24, RULE_anonymousConssistencyRule = 25, 
		RULE_consistencyRuleBody = 26, RULE_ruleLanguage = 27, RULE_ruleBodyContent = 28, 
		RULE_keyExpression = 29, RULE_keyAlternative = 30, RULE_keyLiteral = 31, 
		RULE_keyIdentity = 32, RULE_keyIdArgument = 33, RULE_keyIdArgumentContent = 34, 
		RULE_keyRelation = 35, RULE_keyRelDirection = 36, RULE_goal = 37, RULE_goalCorrespondence = 38, 
		RULE_goalQuery = 39, RULE_goalName = 40, RULE_goalTechSpace = 41, RULE_goalActionType = 42, 
		RULE_goalTargetType = 43, RULE_batchTarget = 44, RULE_fileTarget = 45, 
		RULE_fileCreationOverwrite = 46, RULE_fileCreationTarget = 47, RULE_codegenTarget = 48, 
		RULE_codegenVersion = 49, RULE_codegenGroup = 50, RULE_codegenArtefact = 51, 
		RULE_codegenTargetLocation = 52, RULE_serverTarget = 53, RULE_serverTargetPort = 54, 
		RULE_serverTargetContextPath = 55, RULE_constant = 56, RULE_stringConstant = 57, 
		RULE_intConstant = 58, RULE_floatConstant = 59, RULE_boolConstant = 60, 
		RULE_elmentRef = 61;
	private static String[] makeRuleNames() {
		return new String[] {
			"corrfile", "fileImport", "importURL", "endpointDefinition", "endpointName", 
			"endpointType", "endpointPlaftorm", "endpointURL", "schemaURL", "corrspec", 
			"corrSpecEndpointRef", "corrSpecName", "corrSpecType", "commonality", 
			"elmentRefDef", "elmentRefAlias", "constraints", "constraint", "constraintName", 
			"commonalityType", "commonalityName", "consistencyRuleRef", "subCummonalities", 
			"consistencyRule", "ruleName", "anonymousConssistencyRule", "consistencyRuleBody", 
			"ruleLanguage", "ruleBodyContent", "keyExpression", "keyAlternative", 
			"keyLiteral", "keyIdentity", "keyIdArgument", "keyIdArgumentContent", 
			"keyRelation", "keyRelDirection", "goal", "goalCorrespondence", "goalQuery", 
			"goalName", "goalTechSpace", "goalActionType", "goalTargetType", "batchTarget", 
			"fileTarget", "fileCreationOverwrite", "fileCreationTarget", "codegenTarget", 
			"codegenVersion", "codegenGroup", "codegenArtefact", "codegenTargetLocation", 
			"serverTarget", "serverTargetPort", "serverTargetContextPath", "constant", 
			"stringConstant", "intConstant", "floatConstant", "boolConstant", "elmentRef"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'import'", "';'", "'endpoint'", "'{'", "'type'", "'at'", "'technology'", 
			"'schema'", "'}'", "'FILE'", "'SERVER'", "'correspondence'", "'instantiates'", 
			"'('", "','", "')'", "'as'", "'when'", "'check'", "'['", "']'", "'relate'", 
			"'sync'", "'identify'", "'with'", "'rule'", "'using'", "'||'", "'&&'", 
			"'=='", "'++'", "'<~>'", "'~~>'", "'<~~'", "'goal'", "'action'", "'target'", 
			"'query'", "'SCHEMA'", "'FEDERATION'", "'TRANSFORMATION'", "'MATCH'", 
			"'VERIFY'", "'RESTORE'", "'LIB'", "'overwrite'", "'CODEGEN'", "'outputDir'", 
			"'artefactId'", "'groupId'", "'version'", "'contextPath'", "'port'", 
			"'\"'", "'true'", "'false'", null, null, null, null, null, null, null, 
			"'''''"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "WHITESPACE", "INTEGER", 
			"FLOAT", "IDENTIFIER", "QUALIFIED_ID", "TECH_IDENTIFIER", "URL_STRING", 
			"EXTERNAL_CODE_DELIMITTER", "EXTERNAL_CODE", "BOOL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Corrlang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CorrlangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class CorrfileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CorrlangParser.EOF, 0); }
		public TerminalNode WHITESPACE() { return getToken(CorrlangParser.WHITESPACE, 0); }
		public List<FileImportContext> fileImport() {
			return getRuleContexts(FileImportContext.class);
		}
		public FileImportContext fileImport(int i) {
			return getRuleContext(FileImportContext.class,i);
		}
		public List<EndpointDefinitionContext> endpointDefinition() {
			return getRuleContexts(EndpointDefinitionContext.class);
		}
		public EndpointDefinitionContext endpointDefinition(int i) {
			return getRuleContext(EndpointDefinitionContext.class,i);
		}
		public List<CorrspecContext> corrspec() {
			return getRuleContexts(CorrspecContext.class);
		}
		public CorrspecContext corrspec(int i) {
			return getRuleContext(CorrspecContext.class,i);
		}
		public List<ConsistencyRuleContext> consistencyRule() {
			return getRuleContexts(ConsistencyRuleContext.class);
		}
		public ConsistencyRuleContext consistencyRule(int i) {
			return getRuleContext(ConsistencyRuleContext.class,i);
		}
		public List<GoalContext> goal() {
			return getRuleContexts(GoalContext.class);
		}
		public GoalContext goal(int i) {
			return getRuleContext(GoalContext.class,i);
		}
		public CorrfileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_corrfile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCorrfile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCorrfile(this);
		}
	}

	public final CorrfileContext corrfile() throws RecognitionException {
		CorrfileContext _localctx = new CorrfileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_corrfile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(124);
				match(WHITESPACE);
				}
			}

			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(127);
				fileImport();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(137); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(137);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__2:
					{
					setState(133);
					endpointDefinition();
					}
					break;
				case T__11:
					{
					setState(134);
					corrspec();
					}
					break;
				case T__25:
					{
					setState(135);
					consistencyRule();
					}
					break;
				case T__34:
					{
					setState(136);
					goal();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(139); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__11) | (1L << T__25) | (1L << T__34))) != 0) );
			setState(141);
			match(EOF);
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

	public static class FileImportContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public ImportURLContext importURL() {
			return getRuleContext(ImportURLContext.class,0);
		}
		public FileImportContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileImport; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterFileImport(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitFileImport(this);
		}
	}

	public final FileImportContext fileImport() throws RecognitionException {
		FileImportContext _localctx = new FileImportContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_fileImport);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			match(T__0);
			setState(144);
			match(WHITESPACE);
			setState(145);
			importURL();
			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(146);
				match(WHITESPACE);
				}
			}

			setState(149);
			match(T__1);
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(150);
				match(WHITESPACE);
				}
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

	public static class ImportURLContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public ImportURLContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importURL; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterImportURL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitImportURL(this);
		}
	}

	public final ImportURLContext importURL() throws RecognitionException {
		ImportURLContext _localctx = new ImportURLContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importURL);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(URL_STRING);
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

	public static class EndpointDefinitionContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public EndpointNameContext endpointName() {
			return getRuleContext(EndpointNameContext.class,0);
		}
		public EndpointTypeContext endpointType() {
			return getRuleContext(EndpointTypeContext.class,0);
		}
		public EndpointURLContext endpointURL() {
			return getRuleContext(EndpointURLContext.class,0);
		}
		public EndpointPlaftormContext endpointPlaftorm() {
			return getRuleContext(EndpointPlaftormContext.class,0);
		}
		public SchemaURLContext schemaURL() {
			return getRuleContext(SchemaURLContext.class,0);
		}
		public EndpointDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endpointDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterEndpointDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitEndpointDefinition(this);
		}
	}

	public final EndpointDefinitionContext endpointDefinition() throws RecognitionException {
		EndpointDefinitionContext _localctx = new EndpointDefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_endpointDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(T__2);
			setState(156);
			match(WHITESPACE);
			setState(157);
			endpointName();
			setState(158);
			match(WHITESPACE);
			setState(159);
			match(T__3);
			setState(160);
			match(WHITESPACE);
			setState(161);
			match(T__4);
			setState(162);
			match(WHITESPACE);
			setState(163);
			endpointType();
			setState(164);
			match(WHITESPACE);
			setState(165);
			match(T__5);
			setState(166);
			match(WHITESPACE);
			setState(167);
			endpointURL();
			setState(168);
			match(WHITESPACE);
			setState(169);
			match(T__6);
			setState(170);
			match(WHITESPACE);
			setState(171);
			endpointPlaftorm();
			setState(172);
			match(WHITESPACE);
			setState(178);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(173);
				match(T__7);
				setState(174);
				match(WHITESPACE);
				setState(175);
				schemaURL();
				setState(176);
				match(WHITESPACE);
				}
			}

			setState(180);
			match(T__8);
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(181);
				match(WHITESPACE);
				}
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

	public static class EndpointNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public EndpointNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endpointName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterEndpointName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitEndpointName(this);
		}
	}

	public final EndpointNameContext endpointName() throws RecognitionException {
		EndpointNameContext _localctx = new EndpointNameContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_endpointName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			match(IDENTIFIER);
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

	public static class EndpointTypeContext extends ParserRuleContext {
		public EndpointTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endpointType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterEndpointType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitEndpointType(this);
		}
	}

	public final EndpointTypeContext endpointType() throws RecognitionException {
		EndpointTypeContext _localctx = new EndpointTypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_endpointType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			_la = _input.LA(1);
			if ( !(_la==T__9 || _la==T__10) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class EndpointPlaftormContext extends ParserRuleContext {
		public TerminalNode TECH_IDENTIFIER() { return getToken(CorrlangParser.TECH_IDENTIFIER, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public EndpointPlaftormContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endpointPlaftorm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterEndpointPlaftorm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitEndpointPlaftorm(this);
		}
	}

	public final EndpointPlaftormContext endpointPlaftorm() throws RecognitionException {
		EndpointPlaftormContext _localctx = new EndpointPlaftormContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_endpointPlaftorm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			_la = _input.LA(1);
			if ( !(_la==IDENTIFIER || _la==TECH_IDENTIFIER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class EndpointURLContext extends ParserRuleContext {
		public TerminalNode QUALIFIED_ID() { return getToken(CorrlangParser.QUALIFIED_ID, 0); }
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public EndpointURLContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endpointURL; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterEndpointURL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitEndpointURL(this);
		}
	}

	public final EndpointURLContext endpointURL() throws RecognitionException {
		EndpointURLContext _localctx = new EndpointURLContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_endpointURL);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			_la = _input.LA(1);
			if ( !(_la==QUALIFIED_ID || _la==URL_STRING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class SchemaURLContext extends ParserRuleContext {
		public TerminalNode QUALIFIED_ID() { return getToken(CorrlangParser.QUALIFIED_ID, 0); }
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public SchemaURLContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_schemaURL; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterSchemaURL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitSchemaURL(this);
		}
	}

	public final SchemaURLContext schemaURL() throws RecognitionException {
		SchemaURLContext _localctx = new SchemaURLContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_schemaURL);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			_la = _input.LA(1);
			if ( !(_la==QUALIFIED_ID || _la==URL_STRING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class CorrspecContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public CorrSpecNameContext corrSpecName() {
			return getRuleContext(CorrSpecNameContext.class,0);
		}
		public List<CorrSpecEndpointRefContext> corrSpecEndpointRef() {
			return getRuleContexts(CorrSpecEndpointRefContext.class);
		}
		public CorrSpecEndpointRefContext corrSpecEndpointRef(int i) {
			return getRuleContext(CorrSpecEndpointRefContext.class,i);
		}
		public CorrSpecTypeContext corrSpecType() {
			return getRuleContext(CorrSpecTypeContext.class,0);
		}
		public List<CommonalityContext> commonality() {
			return getRuleContexts(CommonalityContext.class);
		}
		public CommonalityContext commonality(int i) {
			return getRuleContext(CommonalityContext.class,i);
		}
		public CorrspecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_corrspec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCorrspec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCorrspec(this);
		}
	}

	public final CorrspecContext corrspec() throws RecognitionException {
		CorrspecContext _localctx = new CorrspecContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_corrspec);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(T__11);
			setState(195);
			match(WHITESPACE);
			setState(196);
			corrSpecName();
			setState(197);
			match(WHITESPACE);
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(198);
				match(T__12);
				setState(199);
				match(WHITESPACE);
				setState(200);
				corrSpecType();
				setState(201);
				match(WHITESPACE);
				}
			}

			setState(205);
			match(T__13);
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(206);
				match(WHITESPACE);
				}
			}

			setState(209);
			corrSpecEndpointRef();
			setState(211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(210);
				match(WHITESPACE);
				}
			}

			setState(221); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(213);
				match(T__14);
				setState(215);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHITESPACE) {
					{
					setState(214);
					match(WHITESPACE);
					}
				}

				setState(217);
				corrSpecEndpointRef();
				setState(219);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHITESPACE) {
					{
					setState(218);
					match(WHITESPACE);
					}
				}

				}
				}
				setState(223); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__14 );
			setState(225);
			match(T__15);
			setState(226);
			match(WHITESPACE);
			setState(227);
			match(T__3);
			setState(232);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(228);
					match(WHITESPACE);
					setState(229);
					commonality();
					}
					} 
				}
				setState(234);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			setState(236);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(235);
				match(WHITESPACE);
				}
			}

			setState(238);
			match(T__8);
			setState(240);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(239);
				match(WHITESPACE);
				}
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

	public static class CorrSpecEndpointRefContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public CorrSpecEndpointRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_corrSpecEndpointRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCorrSpecEndpointRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCorrSpecEndpointRef(this);
		}
	}

	public final CorrSpecEndpointRefContext corrSpecEndpointRef() throws RecognitionException {
		CorrSpecEndpointRefContext _localctx = new CorrSpecEndpointRefContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_corrSpecEndpointRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			match(IDENTIFIER);
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

	public static class CorrSpecNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public CorrSpecNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_corrSpecName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCorrSpecName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCorrSpecName(this);
		}
	}

	public final CorrSpecNameContext corrSpecName() throws RecognitionException {
		CorrSpecNameContext _localctx = new CorrSpecNameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_corrSpecName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(IDENTIFIER);
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

	public static class CorrSpecTypeContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public CorrSpecTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_corrSpecType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCorrSpecType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCorrSpecType(this);
		}
	}

	public final CorrSpecTypeContext corrSpecType() throws RecognitionException {
		CorrSpecTypeContext _localctx = new CorrSpecTypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_corrSpecType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(IDENTIFIER);
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

	public static class CommonalityContext extends ParserRuleContext {
		public CommonalityTypeContext commonalityType() {
			return getRuleContext(CommonalityTypeContext.class,0);
		}
		public List<ElmentRefDefContext> elmentRefDef() {
			return getRuleContexts(ElmentRefDefContext.class);
		}
		public ElmentRefDefContext elmentRefDef(int i) {
			return getRuleContext(ElmentRefDefContext.class,i);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public CommonalityNameContext commonalityName() {
			return getRuleContext(CommonalityNameContext.class,0);
		}
		public SubCummonalitiesContext subCummonalities() {
			return getRuleContext(SubCummonalitiesContext.class,0);
		}
		public KeyExpressionContext keyExpression() {
			return getRuleContext(KeyExpressionContext.class,0);
		}
		public AnonymousConssistencyRuleContext anonymousConssistencyRule() {
			return getRuleContext(AnonymousConssistencyRuleContext.class,0);
		}
		public ConsistencyRuleRefContext consistencyRuleRef() {
			return getRuleContext(ConsistencyRuleRefContext.class,0);
		}
		public ConstraintsContext constraints() {
			return getRuleContext(ConstraintsContext.class,0);
		}
		public CommonalityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commonality; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCommonality(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCommonality(this);
		}
	}

	public final CommonalityContext commonality() throws RecognitionException {
		CommonalityContext _localctx = new CommonalityContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_commonality);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			commonalityType();
			setState(250);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(249);
				match(WHITESPACE);
				}
			}

			setState(252);
			match(T__13);
			setState(254);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(253);
				match(WHITESPACE);
				}
			}

			setState(256);
			elmentRefDef();
			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(257);
				match(WHITESPACE);
				}
			}

			setState(268); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(260);
				match(T__14);
				setState(262);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHITESPACE) {
					{
					setState(261);
					match(WHITESPACE);
					}
				}

				setState(264);
				elmentRefDef();
				setState(266);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHITESPACE) {
					{
					setState(265);
					match(WHITESPACE);
					}
				}

				}
				}
				setState(270); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__14 );
			setState(272);
			match(T__15);
			setState(280);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(273);
				match(WHITESPACE);
				setState(274);
				match(T__16);
				setState(275);
				match(WHITESPACE);
				setState(276);
				commonalityName();
				setState(278);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(277);
					constraints();
					}
					break;
				}
				}
				break;
			}
			setState(283);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(282);
				subCummonalities();
				}
				break;
			}
			setState(298);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(285);
				match(WHITESPACE);
				setState(286);
				match(T__17);
				setState(287);
				match(WHITESPACE);
				setState(288);
				match(T__13);
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHITESPACE) {
					{
					setState(289);
					match(WHITESPACE);
					}
				}

				setState(292);
				keyExpression();
				setState(294);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHITESPACE) {
					{
					setState(293);
					match(WHITESPACE);
					}
				}

				setState(296);
				match(T__15);
				}
				break;
			}
			setState(308);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(300);
				match(WHITESPACE);
				setState(301);
				match(T__18);
				setState(302);
				match(WHITESPACE);
				setState(306);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__3:
					{
					setState(303);
					anonymousConssistencyRule();
					}
					break;
				case T__1:
					{
					}
					break;
				case IDENTIFIER:
					{
					setState(305);
					consistencyRuleRef();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
			}

			setState(310);
			match(T__1);
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

	public static class ElmentRefDefContext extends ParserRuleContext {
		public ElmentRefContext elmentRef() {
			return getRuleContext(ElmentRefContext.class,0);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public ElmentRefAliasContext elmentRefAlias() {
			return getRuleContext(ElmentRefAliasContext.class,0);
		}
		public ElmentRefDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elmentRefDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterElmentRefDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitElmentRefDef(this);
		}
	}

	public final ElmentRefDefContext elmentRefDef() throws RecognitionException {
		ElmentRefDefContext _localctx = new ElmentRefDefContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_elmentRefDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			elmentRef();
			setState(317);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(313);
				match(WHITESPACE);
				setState(314);
				match(T__16);
				setState(315);
				match(WHITESPACE);
				setState(316);
				elmentRefAlias();
				}
				break;
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

	public static class ElmentRefAliasContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public ElmentRefAliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elmentRefAlias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterElmentRefAlias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitElmentRefAlias(this);
		}
	}

	public final ElmentRefAliasContext elmentRefAlias() throws RecognitionException {
		ElmentRefAliasContext _localctx = new ElmentRefAliasContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_elmentRefAlias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			match(IDENTIFIER);
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

	public static class ConstraintsContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public ConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraints; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterConstraints(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitConstraints(this);
		}
	}

	public final ConstraintsContext constraints() throws RecognitionException {
		ConstraintsContext _localctx = new ConstraintsContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_constraints);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(323); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(321);
					match(WHITESPACE);
					setState(322);
					constraint();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(325); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class ConstraintContext extends ParserRuleContext {
		public ConstraintNameContext constraintName() {
			return getRuleContext(ConstraintNameContext.class,0);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitConstraint(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_constraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(T__19);
			setState(328);
			constraintName();
			setState(329);
			match(T__20);
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

	public static class ConstraintNameContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public ConstraintNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraintName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterConstraintName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitConstraintName(this);
		}
	}

	public final ConstraintNameContext constraintName() throws RecognitionException {
		ConstraintNameContext _localctx = new ConstraintNameContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_constraintName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			match(URL_STRING);
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

	public static class CommonalityTypeContext extends ParserRuleContext {
		public CommonalityTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commonalityType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCommonalityType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCommonalityType(this);
		}
	}

	public final CommonalityTypeContext commonalityType() throws RecognitionException {
		CommonalityTypeContext _localctx = new CommonalityTypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_commonalityType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__22) | (1L << T__23))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class CommonalityNameContext extends ParserRuleContext {
		public TerminalNode QUALIFIED_ID() { return getToken(CorrlangParser.QUALIFIED_ID, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public CommonalityNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commonalityName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCommonalityName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCommonalityName(this);
		}
	}

	public final CommonalityNameContext commonalityName() throws RecognitionException {
		CommonalityNameContext _localctx = new CommonalityNameContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_commonalityName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			_la = _input.LA(1);
			if ( !(_la==IDENTIFIER || _la==QUALIFIED_ID) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class ConsistencyRuleRefContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public ConsistencyRuleRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_consistencyRuleRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterConsistencyRuleRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitConsistencyRuleRef(this);
		}
	}

	public final ConsistencyRuleRefContext consistencyRuleRef() throws RecognitionException {
		ConsistencyRuleRefContext _localctx = new ConsistencyRuleRefContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_consistencyRuleRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(IDENTIFIER);
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

	public static class SubCummonalitiesContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public List<CommonalityContext> commonality() {
			return getRuleContexts(CommonalityContext.class);
		}
		public CommonalityContext commonality(int i) {
			return getRuleContext(CommonalityContext.class,i);
		}
		public SubCummonalitiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subCummonalities; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterSubCummonalities(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitSubCummonalities(this);
		}
	}

	public final SubCummonalitiesContext subCummonalities() throws RecognitionException {
		SubCummonalitiesContext _localctx = new SubCummonalitiesContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_subCummonalities);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			match(WHITESPACE);
			setState(340);
			match(T__24);
			setState(341);
			match(WHITESPACE);
			setState(342);
			match(T__3);
			setState(345); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(343);
					match(WHITESPACE);
					setState(344);
					commonality();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(347); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(349);
				match(WHITESPACE);
				}
			}

			setState(352);
			match(T__8);
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

	public static class ConsistencyRuleContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public RuleNameContext ruleName() {
			return getRuleContext(RuleNameContext.class,0);
		}
		public ConsistencyRuleBodyContext consistencyRuleBody() {
			return getRuleContext(ConsistencyRuleBodyContext.class,0);
		}
		public ConsistencyRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_consistencyRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterConsistencyRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitConsistencyRule(this);
		}
	}

	public final ConsistencyRuleContext consistencyRule() throws RecognitionException {
		ConsistencyRuleContext _localctx = new ConsistencyRuleContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_consistencyRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(354);
			match(T__25);
			setState(355);
			match(WHITESPACE);
			setState(356);
			ruleName();
			setState(357);
			match(WHITESPACE);
			setState(358);
			consistencyRuleBody();
			setState(360);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(359);
				match(WHITESPACE);
				}
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

	public static class RuleNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public RuleNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterRuleName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitRuleName(this);
		}
	}

	public final RuleNameContext ruleName() throws RecognitionException {
		RuleNameContext _localctx = new RuleNameContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_ruleName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			match(IDENTIFIER);
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

	public static class AnonymousConssistencyRuleContext extends ParserRuleContext {
		public ConsistencyRuleBodyContext consistencyRuleBody() {
			return getRuleContext(ConsistencyRuleBodyContext.class,0);
		}
		public AnonymousConssistencyRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anonymousConssistencyRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterAnonymousConssistencyRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitAnonymousConssistencyRule(this);
		}
	}

	public final AnonymousConssistencyRuleContext anonymousConssistencyRule() throws RecognitionException {
		AnonymousConssistencyRuleContext _localctx = new AnonymousConssistencyRuleContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_anonymousConssistencyRule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(364);
			consistencyRuleBody();
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

	public static class ConsistencyRuleBodyContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public RuleLanguageContext ruleLanguage() {
			return getRuleContext(RuleLanguageContext.class,0);
		}
		public RuleBodyContentContext ruleBodyContent() {
			return getRuleContext(RuleBodyContentContext.class,0);
		}
		public ConsistencyRuleBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_consistencyRuleBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterConsistencyRuleBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitConsistencyRuleBody(this);
		}
	}

	public final ConsistencyRuleBodyContext consistencyRuleBody() throws RecognitionException {
		ConsistencyRuleBodyContext _localctx = new ConsistencyRuleBodyContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_consistencyRuleBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			match(T__3);
			setState(367);
			match(WHITESPACE);
			setState(368);
			match(T__26);
			setState(369);
			match(WHITESPACE);
			setState(370);
			ruleLanguage();
			setState(371);
			match(WHITESPACE);
			setState(372);
			ruleBodyContent();
			setState(373);
			match(WHITESPACE);
			setState(374);
			match(T__8);
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

	public static class RuleLanguageContext extends ParserRuleContext {
		public TerminalNode TECH_IDENTIFIER() { return getToken(CorrlangParser.TECH_IDENTIFIER, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public RuleLanguageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleLanguage; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterRuleLanguage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitRuleLanguage(this);
		}
	}

	public final RuleLanguageContext ruleLanguage() throws RecognitionException {
		RuleLanguageContext _localctx = new RuleLanguageContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_ruleLanguage);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376);
			_la = _input.LA(1);
			if ( !(_la==IDENTIFIER || _la==TECH_IDENTIFIER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class RuleBodyContentContext extends ParserRuleContext {
		public TerminalNode EXTERNAL_CODE() { return getToken(CorrlangParser.EXTERNAL_CODE, 0); }
		public RuleBodyContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleBodyContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterRuleBodyContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitRuleBodyContent(this);
		}
	}

	public final RuleBodyContentContext ruleBodyContent() throws RecognitionException {
		RuleBodyContentContext _localctx = new RuleBodyContentContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_ruleBodyContent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			match(EXTERNAL_CODE);
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

	public static class KeyExpressionContext extends ParserRuleContext {
		public List<KeyAlternativeContext> keyAlternative() {
			return getRuleContexts(KeyAlternativeContext.class);
		}
		public KeyAlternativeContext keyAlternative(int i) {
			return getRuleContext(KeyAlternativeContext.class,i);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public KeyExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterKeyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitKeyExpression(this);
		}
	}

	public final KeyExpressionContext keyExpression() throws RecognitionException {
		KeyExpressionContext _localctx = new KeyExpressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_keyExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(380);
			keyAlternative();
			setState(386);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__27) {
				{
				{
				setState(381);
				match(T__27);
				setState(382);
				match(WHITESPACE);
				setState(383);
				keyAlternative();
				}
				}
				setState(388);
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

	public static class KeyAlternativeContext extends ParserRuleContext {
		public List<KeyLiteralContext> keyLiteral() {
			return getRuleContexts(KeyLiteralContext.class);
		}
		public KeyLiteralContext keyLiteral(int i) {
			return getRuleContext(KeyLiteralContext.class,i);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public KeyAlternativeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyAlternative; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterKeyAlternative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitKeyAlternative(this);
		}
	}

	public final KeyAlternativeContext keyAlternative() throws RecognitionException {
		KeyAlternativeContext _localctx = new KeyAlternativeContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_keyAlternative);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(389);
			keyLiteral();
			setState(395);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__28) {
				{
				{
				setState(390);
				match(T__28);
				setState(391);
				match(WHITESPACE);
				setState(392);
				keyLiteral();
				}
				}
				setState(397);
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

	public static class KeyLiteralContext extends ParserRuleContext {
		public KeyIdentityContext keyIdentity() {
			return getRuleContext(KeyIdentityContext.class,0);
		}
		public KeyRelationContext keyRelation() {
			return getRuleContext(KeyRelationContext.class,0);
		}
		public KeyLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterKeyLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitKeyLiteral(this);
		}
	}

	public final KeyLiteralContext keyLiteral() throws RecognitionException {
		KeyLiteralContext _localctx = new KeyLiteralContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_keyLiteral);
		try {
			setState(400);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(398);
				keyIdentity();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(399);
				keyRelation();
				}
				break;
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

	public static class KeyIdentityContext extends ParserRuleContext {
		public List<KeyIdArgumentContext> keyIdArgument() {
			return getRuleContexts(KeyIdArgumentContext.class);
		}
		public KeyIdArgumentContext keyIdArgument(int i) {
			return getRuleContext(KeyIdArgumentContext.class,i);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public KeyIdentityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyIdentity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterKeyIdentity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitKeyIdentity(this);
		}
	}

	public final KeyIdentityContext keyIdentity() throws RecognitionException {
		KeyIdentityContext _localctx = new KeyIdentityContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_keyIdentity);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			keyIdArgument();
			setState(403);
			match(T__29);
			setState(404);
			match(WHITESPACE);
			setState(405);
			keyIdArgument();
			setState(411);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__29) {
				{
				{
				setState(406);
				match(T__29);
				setState(407);
				match(WHITESPACE);
				setState(408);
				keyIdArgument();
				}
				}
				setState(413);
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

	public static class KeyIdArgumentContext extends ParserRuleContext {
		public List<KeyIdArgumentContentContext> keyIdArgumentContent() {
			return getRuleContexts(KeyIdArgumentContentContext.class);
		}
		public KeyIdArgumentContentContext keyIdArgumentContent(int i) {
			return getRuleContext(KeyIdArgumentContentContext.class,i);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public KeyIdArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyIdArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterKeyIdArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitKeyIdArgument(this);
		}
	}

	public final KeyIdArgumentContext keyIdArgument() throws RecognitionException {
		KeyIdArgumentContext _localctx = new KeyIdArgumentContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_keyIdArgument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(414);
			keyIdArgumentContent();
			setState(420);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__30) {
				{
				{
				setState(415);
				match(T__30);
				setState(416);
				match(WHITESPACE);
				setState(417);
				keyIdArgumentContent();
				}
				}
				setState(422);
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

	public static class KeyIdArgumentContentContext extends ParserRuleContext {
		public TerminalNode WHITESPACE() { return getToken(CorrlangParser.WHITESPACE, 0); }
		public ElmentRefContext elmentRef() {
			return getRuleContext(ElmentRefContext.class,0);
		}
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public KeyIdArgumentContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyIdArgumentContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterKeyIdArgumentContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitKeyIdArgumentContent(this);
		}
	}

	public final KeyIdArgumentContentContext keyIdArgumentContent() throws RecognitionException {
		KeyIdArgumentContentContext _localctx = new KeyIdArgumentContentContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_keyIdArgumentContent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(425);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QUALIFIED_ID:
				{
				setState(423);
				elmentRef();
				}
				break;
			case T__53:
			case T__54:
			case T__55:
				{
				setState(424);
				constant();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(427);
			match(WHITESPACE);
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

	public static class KeyRelationContext extends ParserRuleContext {
		public List<ElmentRefContext> elmentRef() {
			return getRuleContexts(ElmentRefContext.class);
		}
		public ElmentRefContext elmentRef(int i) {
			return getRuleContext(ElmentRefContext.class,i);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public KeyRelDirectionContext keyRelDirection() {
			return getRuleContext(KeyRelDirectionContext.class,0);
		}
		public KeyRelationContext keyRelation() {
			return getRuleContext(KeyRelationContext.class,0);
		}
		public KeyRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterKeyRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitKeyRelation(this);
		}
	}

	public final KeyRelationContext keyRelation() throws RecognitionException {
		KeyRelationContext _localctx = new KeyRelationContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_keyRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(429);
			elmentRef();
			setState(430);
			match(WHITESPACE);
			setState(431);
			keyRelDirection();
			setState(432);
			match(WHITESPACE);
			setState(435);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				setState(433);
				elmentRef();
				}
				break;
			case 2:
				{
				setState(434);
				keyRelation();
				}
				break;
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

	public static class KeyRelDirectionContext extends ParserRuleContext {
		public KeyRelDirectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyRelDirection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterKeyRelDirection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitKeyRelDirection(this);
		}
	}

	public final KeyRelDirectionContext keyRelDirection() throws RecognitionException {
		KeyRelDirectionContext _localctx = new KeyRelDirectionContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_keyRelDirection);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(437);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__32) | (1L << T__33))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class GoalContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public GoalNameContext goalName() {
			return getRuleContext(GoalNameContext.class,0);
		}
		public GoalCorrespondenceContext goalCorrespondence() {
			return getRuleContext(GoalCorrespondenceContext.class,0);
		}
		public GoalActionTypeContext goalActionType() {
			return getRuleContext(GoalActionTypeContext.class,0);
		}
		public GoalTargetTypeContext goalTargetType() {
			return getRuleContext(GoalTargetTypeContext.class,0);
		}
		public GoalTechSpaceContext goalTechSpace() {
			return getRuleContext(GoalTechSpaceContext.class,0);
		}
		public GoalQueryContext goalQuery() {
			return getRuleContext(GoalQueryContext.class,0);
		}
		public GoalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterGoal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitGoal(this);
		}
	}

	public final GoalContext goal() throws RecognitionException {
		GoalContext _localctx = new GoalContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_goal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(439);
			match(T__34);
			setState(440);
			match(WHITESPACE);
			setState(441);
			goalName();
			setState(442);
			match(WHITESPACE);
			setState(443);
			match(T__3);
			setState(444);
			match(WHITESPACE);
			setState(445);
			match(T__11);
			setState(446);
			match(WHITESPACE);
			setState(447);
			goalCorrespondence();
			setState(448);
			match(WHITESPACE);
			setState(449);
			match(T__35);
			setState(450);
			match(WHITESPACE);
			setState(451);
			goalActionType();
			setState(452);
			match(WHITESPACE);
			setState(458);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(453);
				match(T__6);
				setState(454);
				match(WHITESPACE);
				setState(455);
				goalTechSpace();
				setState(456);
				match(WHITESPACE);
				}
			}

			setState(460);
			match(T__36);
			setState(461);
			match(WHITESPACE);
			setState(462);
			goalTargetType();
			setState(463);
			match(WHITESPACE);
			setState(469);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__37) {
				{
				setState(464);
				match(T__37);
				setState(465);
				match(WHITESPACE);
				setState(466);
				goalQuery();
				setState(467);
				match(WHITESPACE);
				}
			}

			setState(471);
			match(T__8);
			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(472);
				match(WHITESPACE);
				}
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

	public static class GoalCorrespondenceContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public GoalCorrespondenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goalCorrespondence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterGoalCorrespondence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitGoalCorrespondence(this);
		}
	}

	public final GoalCorrespondenceContext goalCorrespondence() throws RecognitionException {
		GoalCorrespondenceContext _localctx = new GoalCorrespondenceContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_goalCorrespondence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(475);
			match(IDENTIFIER);
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

	public static class GoalQueryContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public GoalQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goalQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterGoalQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitGoalQuery(this);
		}
	}

	public final GoalQueryContext goalQuery() throws RecognitionException {
		GoalQueryContext _localctx = new GoalQueryContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_goalQuery);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(477);
			match(URL_STRING);
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

	public static class GoalNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public GoalNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goalName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterGoalName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitGoalName(this);
		}
	}

	public final GoalNameContext goalName() throws RecognitionException {
		GoalNameContext _localctx = new GoalNameContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_goalName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(479);
			match(IDENTIFIER);
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

	public static class GoalTechSpaceContext extends ParserRuleContext {
		public TerminalNode TECH_IDENTIFIER() { return getToken(CorrlangParser.TECH_IDENTIFIER, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public GoalTechSpaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goalTechSpace; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterGoalTechSpace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitGoalTechSpace(this);
		}
	}

	public final GoalTechSpaceContext goalTechSpace() throws RecognitionException {
		GoalTechSpaceContext _localctx = new GoalTechSpaceContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_goalTechSpace);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			_la = _input.LA(1);
			if ( !(_la==IDENTIFIER || _la==TECH_IDENTIFIER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class GoalActionTypeContext extends ParserRuleContext {
		public GoalActionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goalActionType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterGoalActionType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitGoalActionType(this);
		}
	}

	public final GoalActionTypeContext goalActionType() throws RecognitionException {
		GoalActionTypeContext _localctx = new GoalActionTypeContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_goalActionType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(483);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class GoalTargetTypeContext extends ParserRuleContext {
		public ServerTargetContext serverTarget() {
			return getRuleContext(ServerTargetContext.class,0);
		}
		public CodegenTargetContext codegenTarget() {
			return getRuleContext(CodegenTargetContext.class,0);
		}
		public FileTargetContext fileTarget() {
			return getRuleContext(FileTargetContext.class,0);
		}
		public BatchTargetContext batchTarget() {
			return getRuleContext(BatchTargetContext.class,0);
		}
		public GoalTargetTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goalTargetType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterGoalTargetType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitGoalTargetType(this);
		}
	}

	public final GoalTargetTypeContext goalTargetType() throws RecognitionException {
		GoalTargetTypeContext _localctx = new GoalTargetTypeContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_goalTargetType);
		try {
			setState(489);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(485);
				serverTarget();
				}
				break;
			case T__46:
				enterOuterAlt(_localctx, 2);
				{
				setState(486);
				codegenTarget();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 3);
				{
				setState(487);
				fileTarget();
				}
				break;
			case T__44:
				enterOuterAlt(_localctx, 4);
				{
				setState(488);
				batchTarget();
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

	public static class BatchTargetContext extends ParserRuleContext {
		public BatchTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_batchTarget; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterBatchTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitBatchTarget(this);
		}
	}

	public final BatchTargetContext batchTarget() throws RecognitionException {
		BatchTargetContext _localctx = new BatchTargetContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_batchTarget);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(491);
			match(T__44);
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

	public static class FileTargetContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public FileCreationTargetContext fileCreationTarget() {
			return getRuleContext(FileCreationTargetContext.class,0);
		}
		public FileCreationOverwriteContext fileCreationOverwrite() {
			return getRuleContext(FileCreationOverwriteContext.class,0);
		}
		public FileTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileTarget; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterFileTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitFileTarget(this);
		}
	}

	public final FileTargetContext fileTarget() throws RecognitionException {
		FileTargetContext _localctx = new FileTargetContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_fileTarget);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(493);
			match(T__9);
			setState(494);
			match(WHITESPACE);
			setState(495);
			match(T__3);
			setState(496);
			match(WHITESPACE);
			setState(497);
			match(T__5);
			setState(498);
			match(WHITESPACE);
			setState(499);
			fileCreationTarget();
			setState(500);
			match(WHITESPACE);
			setState(506);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__45) {
				{
				setState(501);
				match(T__45);
				setState(502);
				match(WHITESPACE);
				setState(503);
				fileCreationOverwrite();
				setState(504);
				match(WHITESPACE);
				}
			}

			setState(508);
			match(T__8);
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

	public static class FileCreationOverwriteContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(CorrlangParser.BOOL, 0); }
		public FileCreationOverwriteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileCreationOverwrite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterFileCreationOverwrite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitFileCreationOverwrite(this);
		}
	}

	public final FileCreationOverwriteContext fileCreationOverwrite() throws RecognitionException {
		FileCreationOverwriteContext _localctx = new FileCreationOverwriteContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_fileCreationOverwrite);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(510);
			match(BOOL);
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

	public static class FileCreationTargetContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public TerminalNode QUALIFIED_ID() { return getToken(CorrlangParser.QUALIFIED_ID, 0); }
		public FileCreationTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileCreationTarget; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterFileCreationTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitFileCreationTarget(this);
		}
	}

	public final FileCreationTargetContext fileCreationTarget() throws RecognitionException {
		FileCreationTargetContext _localctx = new FileCreationTargetContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_fileCreationTarget);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(512);
			_la = _input.LA(1);
			if ( !(_la==QUALIFIED_ID || _la==URL_STRING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class CodegenTargetContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public CodegenTargetLocationContext codegenTargetLocation() {
			return getRuleContext(CodegenTargetLocationContext.class,0);
		}
		public CodegenArtefactContext codegenArtefact() {
			return getRuleContext(CodegenArtefactContext.class,0);
		}
		public CodegenGroupContext codegenGroup() {
			return getRuleContext(CodegenGroupContext.class,0);
		}
		public CodegenVersionContext codegenVersion() {
			return getRuleContext(CodegenVersionContext.class,0);
		}
		public CodegenTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codegenTarget; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCodegenTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCodegenTarget(this);
		}
	}

	public final CodegenTargetContext codegenTarget() throws RecognitionException {
		CodegenTargetContext _localctx = new CodegenTargetContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_codegenTarget);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(514);
			match(T__46);
			setState(515);
			match(WHITESPACE);
			setState(516);
			match(T__3);
			setState(517);
			match(WHITESPACE);
			setState(518);
			match(T__47);
			setState(519);
			match(WHITESPACE);
			setState(520);
			codegenTargetLocation();
			setState(521);
			match(WHITESPACE);
			setState(522);
			match(T__48);
			setState(523);
			match(WHITESPACE);
			setState(524);
			codegenArtefact();
			setState(525);
			match(WHITESPACE);
			setState(526);
			match(T__49);
			setState(527);
			match(WHITESPACE);
			setState(528);
			codegenGroup();
			setState(529);
			match(WHITESPACE);
			setState(535);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__50) {
				{
				setState(530);
				match(T__50);
				setState(531);
				match(WHITESPACE);
				setState(532);
				codegenVersion();
				setState(533);
				match(WHITESPACE);
				}
			}

			setState(537);
			match(T__8);
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

	public static class CodegenVersionContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public CodegenVersionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codegenVersion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCodegenVersion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCodegenVersion(this);
		}
	}

	public final CodegenVersionContext codegenVersion() throws RecognitionException {
		CodegenVersionContext _localctx = new CodegenVersionContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_codegenVersion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(539);
			match(URL_STRING);
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

	public static class CodegenGroupContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public CodegenGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codegenGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCodegenGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCodegenGroup(this);
		}
	}

	public final CodegenGroupContext codegenGroup() throws RecognitionException {
		CodegenGroupContext _localctx = new CodegenGroupContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_codegenGroup);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(541);
			match(URL_STRING);
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

	public static class CodegenArtefactContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public CodegenArtefactContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codegenArtefact; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCodegenArtefact(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCodegenArtefact(this);
		}
	}

	public final CodegenArtefactContext codegenArtefact() throws RecognitionException {
		CodegenArtefactContext _localctx = new CodegenArtefactContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_codegenArtefact);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(543);
			match(URL_STRING);
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

	public static class CodegenTargetLocationContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public CodegenTargetLocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codegenTargetLocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterCodegenTargetLocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitCodegenTargetLocation(this);
		}
	}

	public final CodegenTargetLocationContext codegenTargetLocation() throws RecognitionException {
		CodegenTargetLocationContext _localctx = new CodegenTargetLocationContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_codegenTargetLocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			match(URL_STRING);
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

	public static class ServerTargetContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CorrlangParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CorrlangParser.WHITESPACE, i);
		}
		public ServerTargetContextPathContext serverTargetContextPath() {
			return getRuleContext(ServerTargetContextPathContext.class,0);
		}
		public ServerTargetPortContext serverTargetPort() {
			return getRuleContext(ServerTargetPortContext.class,0);
		}
		public ServerTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_serverTarget; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterServerTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitServerTarget(this);
		}
	}

	public final ServerTargetContext serverTarget() throws RecognitionException {
		ServerTargetContext _localctx = new ServerTargetContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_serverTarget);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(547);
			match(T__10);
			setState(548);
			match(WHITESPACE);
			setState(549);
			match(T__3);
			setState(550);
			match(WHITESPACE);
			setState(551);
			match(T__51);
			setState(552);
			match(WHITESPACE);
			setState(553);
			serverTargetContextPath();
			setState(554);
			match(WHITESPACE);
			setState(555);
			match(T__52);
			setState(556);
			match(WHITESPACE);
			setState(557);
			serverTargetPort();
			setState(558);
			match(WHITESPACE);
			setState(559);
			match(T__8);
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

	public static class ServerTargetPortContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(CorrlangParser.INTEGER, 0); }
		public ServerTargetPortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_serverTargetPort; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterServerTargetPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitServerTargetPort(this);
		}
	}

	public final ServerTargetPortContext serverTargetPort() throws RecognitionException {
		ServerTargetPortContext _localctx = new ServerTargetPortContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_serverTargetPort);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(561);
			match(INTEGER);
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

	public static class ServerTargetContextPathContext extends ParserRuleContext {
		public TerminalNode URL_STRING() { return getToken(CorrlangParser.URL_STRING, 0); }
		public ServerTargetContextPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_serverTargetContextPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterServerTargetContextPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitServerTargetContextPath(this);
		}
	}

	public final ServerTargetContextPathContext serverTargetContextPath() throws RecognitionException {
		ServerTargetContextPathContext _localctx = new ServerTargetContextPathContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_serverTargetContextPath);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(563);
			match(URL_STRING);
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

	public static class ConstantContext extends ParserRuleContext {
		public StringConstantContext stringConstant() {
			return getRuleContext(StringConstantContext.class,0);
		}
		public BoolConstantContext boolConstant() {
			return getRuleContext(BoolConstantContext.class,0);
		}
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitConstant(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_constant);
		try {
			setState(567);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__53:
				enterOuterAlt(_localctx, 1);
				{
				setState(565);
				stringConstant();
				}
				break;
			case T__54:
			case T__55:
				enterOuterAlt(_localctx, 2);
				{
				setState(566);
				boolConstant();
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

	public static class StringConstantContext extends ParserRuleContext {
		public TerminalNode WHITESPACE() { return getToken(CorrlangParser.WHITESPACE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CorrlangParser.IDENTIFIER, 0); }
		public StringConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringConstant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterStringConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitStringConstant(this);
		}
	}

	public final StringConstantContext stringConstant() throws RecognitionException {
		StringConstantContext _localctx = new StringConstantContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_stringConstant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(569);
			match(T__53);
			setState(570);
			_la = _input.LA(1);
			if ( !(_la==WHITESPACE || _la==IDENTIFIER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(571);
			match(T__53);
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

	public static class IntConstantContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(CorrlangParser.INTEGER, 0); }
		public IntConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intConstant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterIntConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitIntConstant(this);
		}
	}

	public final IntConstantContext intConstant() throws RecognitionException {
		IntConstantContext _localctx = new IntConstantContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_intConstant);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(573);
			match(INTEGER);
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

	public static class FloatConstantContext extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(CorrlangParser.FLOAT, 0); }
		public FloatConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatConstant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterFloatConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitFloatConstant(this);
		}
	}

	public final FloatConstantContext floatConstant() throws RecognitionException {
		FloatConstantContext _localctx = new FloatConstantContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_floatConstant);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(575);
			match(FLOAT);
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

	public static class BoolConstantContext extends ParserRuleContext {
		public BoolConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolConstant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterBoolConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitBoolConstant(this);
		}
	}

	public final BoolConstantContext boolConstant() throws RecognitionException {
		BoolConstantContext _localctx = new BoolConstantContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_boolConstant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(577);
			_la = _input.LA(1);
			if ( !(_la==T__54 || _la==T__55) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class ElmentRefContext extends ParserRuleContext {
		public TerminalNode QUALIFIED_ID() { return getToken(CorrlangParser.QUALIFIED_ID, 0); }
		public ElmentRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elmentRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).enterElmentRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CorrlangListener ) ((CorrlangListener)listener).exitElmentRef(this);
		}
	}

	public final ElmentRefContext elmentRef() throws RecognitionException {
		ElmentRefContext _localctx = new ElmentRefContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_elmentRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(579);
			match(QUALIFIED_ID);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3D\u0248\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\3\2\5\2\u0080\n\2\3\2\7\2\u0083\n\2\f\2\16\2\u0086\13\2\3"+
		"\2\3\2\3\2\3\2\6\2\u008c\n\2\r\2\16\2\u008d\3\2\3\2\3\3\3\3\3\3\3\3\5"+
		"\3\u0096\n\3\3\3\3\3\5\3\u009a\n\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u00b5"+
		"\n\5\3\5\3\5\5\5\u00b9\n\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00ce\n\13\3\13\3\13\5\13"+
		"\u00d2\n\13\3\13\3\13\5\13\u00d6\n\13\3\13\3\13\5\13\u00da\n\13\3\13\3"+
		"\13\5\13\u00de\n\13\6\13\u00e0\n\13\r\13\16\13\u00e1\3\13\3\13\3\13\3"+
		"\13\3\13\7\13\u00e9\n\13\f\13\16\13\u00ec\13\13\3\13\5\13\u00ef\n\13\3"+
		"\13\3\13\5\13\u00f3\n\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\5\17\u00fd"+
		"\n\17\3\17\3\17\5\17\u0101\n\17\3\17\3\17\5\17\u0105\n\17\3\17\3\17\5"+
		"\17\u0109\n\17\3\17\3\17\5\17\u010d\n\17\6\17\u010f\n\17\r\17\16\17\u0110"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u0119\n\17\5\17\u011b\n\17\3\17\5"+
		"\17\u011e\n\17\3\17\3\17\3\17\3\17\3\17\5\17\u0125\n\17\3\17\3\17\5\17"+
		"\u0129\n\17\3\17\3\17\5\17\u012d\n\17\3\17\3\17\3\17\3\17\3\17\3\17\5"+
		"\17\u0135\n\17\5\17\u0137\n\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\5\20"+
		"\u0140\n\20\3\21\3\21\3\22\3\22\6\22\u0146\n\22\r\22\16\22\u0147\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\6\30\u015c\n\30\r\30\16\30\u015d\3\30\5\30\u0161\n\30"+
		"\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u016b\n\31\3\32\3\32\3\33"+
		"\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3\37\3\37\7\37\u0183\n\37\f\37\16\37\u0186\13\37\3 \3"+
		" \3 \3 \7 \u018c\n \f \16 \u018f\13 \3!\3!\5!\u0193\n!\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\7\"\u019c\n\"\f\"\16\"\u019f\13\"\3#\3#\3#\3#\7#\u01a5\n"+
		"#\f#\16#\u01a8\13#\3$\3$\5$\u01ac\n$\3$\3$\3%\3%\3%\3%\3%\3%\5%\u01b6"+
		"\n%\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\5\'\u01cd\n\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'"+
		"\u01d8\n\'\3\'\3\'\5\'\u01dc\n\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3"+
		"-\3-\5-\u01ec\n-\3.\3.\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\5/\u01fd"+
		"\n/\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62"+
		"\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\5\62"+
		"\u021a\n\62\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67"+
		"\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\38\38\39"+
		"\39\3:\3:\5:\u023a\n:\3;\3;\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3?\2\2@\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNP"+
		"RTVXZ\\^`bdfhjlnprtvxz|\2\13\3\2\f\r\4\2>>@@\4\2??AA\3\2\30\32\3\2>?\3"+
		"\2\"$\3\2).\4\2;;>>\3\29:\2\u0240\2\177\3\2\2\2\4\u0091\3\2\2\2\6\u009b"+
		"\3\2\2\2\b\u009d\3\2\2\2\n\u00ba\3\2\2\2\f\u00bc\3\2\2\2\16\u00be\3\2"+
		"\2\2\20\u00c0\3\2\2\2\22\u00c2\3\2\2\2\24\u00c4\3\2\2\2\26\u00f4\3\2\2"+
		"\2\30\u00f6\3\2\2\2\32\u00f8\3\2\2\2\34\u00fa\3\2\2\2\36\u013a\3\2\2\2"+
		" \u0141\3\2\2\2\"\u0145\3\2\2\2$\u0149\3\2\2\2&\u014d\3\2\2\2(\u014f\3"+
		"\2\2\2*\u0151\3\2\2\2,\u0153\3\2\2\2.\u0155\3\2\2\2\60\u0164\3\2\2\2\62"+
		"\u016c\3\2\2\2\64\u016e\3\2\2\2\66\u0170\3\2\2\28\u017a\3\2\2\2:\u017c"+
		"\3\2\2\2<\u017e\3\2\2\2>\u0187\3\2\2\2@\u0192\3\2\2\2B\u0194\3\2\2\2D"+
		"\u01a0\3\2\2\2F\u01ab\3\2\2\2H\u01af\3\2\2\2J\u01b7\3\2\2\2L\u01b9\3\2"+
		"\2\2N\u01dd\3\2\2\2P\u01df\3\2\2\2R\u01e1\3\2\2\2T\u01e3\3\2\2\2V\u01e5"+
		"\3\2\2\2X\u01eb\3\2\2\2Z\u01ed\3\2\2\2\\\u01ef\3\2\2\2^\u0200\3\2\2\2"+
		"`\u0202\3\2\2\2b\u0204\3\2\2\2d\u021d\3\2\2\2f\u021f\3\2\2\2h\u0221\3"+
		"\2\2\2j\u0223\3\2\2\2l\u0225\3\2\2\2n\u0233\3\2\2\2p\u0235\3\2\2\2r\u0239"+
		"\3\2\2\2t\u023b\3\2\2\2v\u023f\3\2\2\2x\u0241\3\2\2\2z\u0243\3\2\2\2|"+
		"\u0245\3\2\2\2~\u0080\7;\2\2\177~\3\2\2\2\177\u0080\3\2\2\2\u0080\u0084"+
		"\3\2\2\2\u0081\u0083\5\4\3\2\u0082\u0081\3\2\2\2\u0083\u0086\3\2\2\2\u0084"+
		"\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u008b\3\2\2\2\u0086\u0084\3\2"+
		"\2\2\u0087\u008c\5\b\5\2\u0088\u008c\5\24\13\2\u0089\u008c\5\60\31\2\u008a"+
		"\u008c\5L\'\2\u008b\u0087\3\2\2\2\u008b\u0088\3\2\2\2\u008b\u0089\3\2"+
		"\2\2\u008b\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008b\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0090\7\2\2\3\u0090\3\3\2\2\2"+
		"\u0091\u0092\7\3\2\2\u0092\u0093\7;\2\2\u0093\u0095\5\6\4\2\u0094\u0096"+
		"\7;\2\2\u0095\u0094\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0097\3\2\2\2\u0097"+
		"\u0099\7\4\2\2\u0098\u009a\7;\2\2\u0099\u0098\3\2\2\2\u0099\u009a\3\2"+
		"\2\2\u009a\5\3\2\2\2\u009b\u009c\7A\2\2\u009c\7\3\2\2\2\u009d\u009e\7"+
		"\5\2\2\u009e\u009f\7;\2\2\u009f\u00a0\5\n\6\2\u00a0\u00a1\7;\2\2\u00a1"+
		"\u00a2\7\6\2\2\u00a2\u00a3\7;\2\2\u00a3\u00a4\7\7\2\2\u00a4\u00a5\7;\2"+
		"\2\u00a5\u00a6\5\f\7\2\u00a6\u00a7\7;\2\2\u00a7\u00a8\7\b\2\2\u00a8\u00a9"+
		"\7;\2\2\u00a9\u00aa\5\20\t\2\u00aa\u00ab\7;\2\2\u00ab\u00ac\7\t\2\2\u00ac"+
		"\u00ad\7;\2\2\u00ad\u00ae\5\16\b\2\u00ae\u00b4\7;\2\2\u00af\u00b0\7\n"+
		"\2\2\u00b0\u00b1\7;\2\2\u00b1\u00b2\5\22\n\2\u00b2\u00b3\7;\2\2\u00b3"+
		"\u00b5\3\2\2\2\u00b4\u00af\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\3\2"+
		"\2\2\u00b6\u00b8\7\13\2\2\u00b7\u00b9\7;\2\2\u00b8\u00b7\3\2\2\2\u00b8"+
		"\u00b9\3\2\2\2\u00b9\t\3\2\2\2\u00ba\u00bb\7>\2\2\u00bb\13\3\2\2\2\u00bc"+
		"\u00bd\t\2\2\2\u00bd\r\3\2\2\2\u00be\u00bf\t\3\2\2\u00bf\17\3\2\2\2\u00c0"+
		"\u00c1\t\4\2\2\u00c1\21\3\2\2\2\u00c2\u00c3\t\4\2\2\u00c3\23\3\2\2\2\u00c4"+
		"\u00c5\7\16\2\2\u00c5\u00c6\7;\2\2\u00c6\u00c7\5\30\r\2\u00c7\u00cd\7"+
		";\2\2\u00c8\u00c9\7\17\2\2\u00c9\u00ca\7;\2\2\u00ca\u00cb\5\32\16\2\u00cb"+
		"\u00cc\7;\2\2\u00cc\u00ce\3\2\2\2\u00cd\u00c8\3\2\2\2\u00cd\u00ce\3\2"+
		"\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d1\7\20\2\2\u00d0\u00d2\7;\2\2\u00d1"+
		"\u00d0\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d5\5\26"+
		"\f\2\u00d4\u00d6\7;\2\2\u00d5\u00d4\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6"+
		"\u00df\3\2\2\2\u00d7\u00d9\7\21\2\2\u00d8\u00da\7;\2\2\u00d9\u00d8\3\2"+
		"\2\2\u00d9\u00da\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dd\5\26\f\2\u00dc"+
		"\u00de\7;\2\2\u00dd\u00dc\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00e0\3\2"+
		"\2\2\u00df\u00d7\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1"+
		"\u00e2\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\7\22\2\2\u00e4\u00e5\7"+
		";\2\2\u00e5\u00ea\7\6\2\2\u00e6\u00e7\7;\2\2\u00e7\u00e9\5\34\17\2\u00e8"+
		"\u00e6\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2"+
		"\2\2\u00eb\u00ee\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ed\u00ef\7;\2\2\u00ee"+
		"\u00ed\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f2\7\13"+
		"\2\2\u00f1\u00f3\7;\2\2\u00f2\u00f1\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3"+
		"\25\3\2\2\2\u00f4\u00f5\7>\2\2\u00f5\27\3\2\2\2\u00f6\u00f7\7>\2\2\u00f7"+
		"\31\3\2\2\2\u00f8\u00f9\7>\2\2\u00f9\33\3\2\2\2\u00fa\u00fc\5(\25\2\u00fb"+
		"\u00fd\7;\2\2\u00fc\u00fb\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fe\3\2"+
		"\2\2\u00fe\u0100\7\20\2\2\u00ff\u0101\7;\2\2\u0100\u00ff\3\2\2\2\u0100"+
		"\u0101\3\2\2\2\u0101\u0102\3\2\2\2\u0102\u0104\5\36\20\2\u0103\u0105\7"+
		";\2\2\u0104\u0103\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u010e\3\2\2\2\u0106"+
		"\u0108\7\21\2\2\u0107\u0109\7;\2\2\u0108\u0107\3\2\2\2\u0108\u0109\3\2"+
		"\2\2\u0109\u010a\3\2\2\2\u010a\u010c\5\36\20\2\u010b\u010d\7;\2\2\u010c"+
		"\u010b\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010f\3\2\2\2\u010e\u0106\3\2"+
		"\2\2\u010f\u0110\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111"+
		"\u0112\3\2\2\2\u0112\u011a\7\22\2\2\u0113\u0114\7;\2\2\u0114\u0115\7\23"+
		"\2\2\u0115\u0116\7;\2\2\u0116\u0118\5*\26\2\u0117\u0119\5\"\22\2\u0118"+
		"\u0117\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u011b\3\2\2\2\u011a\u0113\3\2"+
		"\2\2\u011a\u011b\3\2\2\2\u011b\u011d\3\2\2\2\u011c\u011e\5.\30\2\u011d"+
		"\u011c\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u012c\3\2\2\2\u011f\u0120\7;"+
		"\2\2\u0120\u0121\7\24\2\2\u0121\u0122\7;\2\2\u0122\u0124\7\20\2\2\u0123"+
		"\u0125\7;\2\2\u0124\u0123\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0126\3\2"+
		"\2\2\u0126\u0128\5<\37\2\u0127\u0129\7;\2\2\u0128\u0127\3\2\2\2\u0128"+
		"\u0129\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\7\22\2\2\u012b\u012d\3"+
		"\2\2\2\u012c\u011f\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u0136\3\2\2\2\u012e"+
		"\u012f\7;\2\2\u012f\u0130\7\25\2\2\u0130\u0134\7;\2\2\u0131\u0135\5\64"+
		"\33\2\u0132\u0135\3\2\2\2\u0133\u0135\5,\27\2\u0134\u0131\3\2\2\2\u0134"+
		"\u0132\3\2\2\2\u0134\u0133\3\2\2\2\u0135\u0137\3\2\2\2\u0136\u012e\3\2"+
		"\2\2\u0136\u0137\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139\7\4\2\2\u0139"+
		"\35\3\2\2\2\u013a\u013f\5|?\2\u013b\u013c\7;\2\2\u013c\u013d\7\23\2\2"+
		"\u013d\u013e\7;\2\2\u013e\u0140\5 \21\2\u013f\u013b\3\2\2\2\u013f\u0140"+
		"\3\2\2\2\u0140\37\3\2\2\2\u0141\u0142\7>\2\2\u0142!\3\2\2\2\u0143\u0144"+
		"\7;\2\2\u0144\u0146\5$\23\2\u0145\u0143\3\2\2\2\u0146\u0147\3\2\2\2\u0147"+
		"\u0145\3\2\2\2\u0147\u0148\3\2\2\2\u0148#\3\2\2\2\u0149\u014a\7\26\2\2"+
		"\u014a\u014b\5&\24\2\u014b\u014c\7\27\2\2\u014c%\3\2\2\2\u014d\u014e\7"+
		"A\2\2\u014e\'\3\2\2\2\u014f\u0150\t\5\2\2\u0150)\3\2\2\2\u0151\u0152\t"+
		"\6\2\2\u0152+\3\2\2\2\u0153\u0154\7>\2\2\u0154-\3\2\2\2\u0155\u0156\7"+
		";\2\2\u0156\u0157\7\33\2\2\u0157\u0158\7;\2\2\u0158\u015b\7\6\2\2\u0159"+
		"\u015a\7;\2\2\u015a\u015c\5\34\17\2\u015b\u0159\3\2\2\2\u015c\u015d\3"+
		"\2\2\2\u015d\u015b\3\2\2\2\u015d\u015e\3\2\2\2\u015e\u0160\3\2\2\2\u015f"+
		"\u0161\7;\2\2\u0160\u015f\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u0162\3\2"+
		"\2\2\u0162\u0163\7\13\2\2\u0163/\3\2\2\2\u0164\u0165\7\34\2\2\u0165\u0166"+
		"\7;\2\2\u0166\u0167\5\62\32\2\u0167\u0168\7;\2\2\u0168\u016a\5\66\34\2"+
		"\u0169\u016b\7;\2\2\u016a\u0169\3\2\2\2\u016a\u016b\3\2\2\2\u016b\61\3"+
		"\2\2\2\u016c\u016d\7>\2\2\u016d\63\3\2\2\2\u016e\u016f\5\66\34\2\u016f"+
		"\65\3\2\2\2\u0170\u0171\7\6\2\2\u0171\u0172\7;\2\2\u0172\u0173\7\35\2"+
		"\2\u0173\u0174\7;\2\2\u0174\u0175\58\35\2\u0175\u0176\7;\2\2\u0176\u0177"+
		"\5:\36\2\u0177\u0178\7;\2\2\u0178\u0179\7\13\2\2\u0179\67\3\2\2\2\u017a"+
		"\u017b\t\3\2\2\u017b9\3\2\2\2\u017c\u017d\7C\2\2\u017d;\3\2\2\2\u017e"+
		"\u0184\5> \2\u017f\u0180\7\36\2\2\u0180\u0181\7;\2\2\u0181\u0183\5> \2"+
		"\u0182\u017f\3\2\2\2\u0183\u0186\3\2\2\2\u0184\u0182\3\2\2\2\u0184\u0185"+
		"\3\2\2\2\u0185=\3\2\2\2\u0186\u0184\3\2\2\2\u0187\u018d\5@!\2\u0188\u0189"+
		"\7\37\2\2\u0189\u018a\7;\2\2\u018a\u018c\5@!\2\u018b\u0188\3\2\2\2\u018c"+
		"\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e?\3\2\2\2"+
		"\u018f\u018d\3\2\2\2\u0190\u0193\5B\"\2\u0191\u0193\5H%\2\u0192\u0190"+
		"\3\2\2\2\u0192\u0191\3\2\2\2\u0193A\3\2\2\2\u0194\u0195\5D#\2\u0195\u0196"+
		"\7 \2\2\u0196\u0197\7;\2\2\u0197\u019d\5D#\2\u0198\u0199\7 \2\2\u0199"+
		"\u019a\7;\2\2\u019a\u019c\5D#\2\u019b\u0198\3\2\2\2\u019c\u019f\3\2\2"+
		"\2\u019d\u019b\3\2\2\2\u019d\u019e\3\2\2\2\u019eC\3\2\2\2\u019f\u019d"+
		"\3\2\2\2\u01a0\u01a6\5F$\2\u01a1\u01a2\7!\2\2\u01a2\u01a3\7;\2\2\u01a3"+
		"\u01a5\5F$\2\u01a4\u01a1\3\2\2\2\u01a5\u01a8\3\2\2\2\u01a6\u01a4\3\2\2"+
		"\2\u01a6\u01a7\3\2\2\2\u01a7E\3\2\2\2\u01a8\u01a6\3\2\2\2\u01a9\u01ac"+
		"\5|?\2\u01aa\u01ac\5r:\2\u01ab\u01a9\3\2\2\2\u01ab\u01aa\3\2\2\2\u01ac"+
		"\u01ad\3\2\2\2\u01ad\u01ae\7;\2\2\u01aeG\3\2\2\2\u01af\u01b0\5|?\2\u01b0"+
		"\u01b1\7;\2\2\u01b1\u01b2\5J&\2\u01b2\u01b5\7;\2\2\u01b3\u01b6\5|?\2\u01b4"+
		"\u01b6\5H%\2\u01b5\u01b3\3\2\2\2\u01b5\u01b4\3\2\2\2\u01b6I\3\2\2\2\u01b7"+
		"\u01b8\t\7\2\2\u01b8K\3\2\2\2\u01b9\u01ba\7%\2\2\u01ba\u01bb\7;\2\2\u01bb"+
		"\u01bc\5R*\2\u01bc\u01bd\7;\2\2\u01bd\u01be\7\6\2\2\u01be\u01bf\7;\2\2"+
		"\u01bf\u01c0\7\16\2\2\u01c0\u01c1\7;\2\2\u01c1\u01c2\5N(\2\u01c2\u01c3"+
		"\7;\2\2\u01c3\u01c4\7&\2\2\u01c4\u01c5\7;\2\2\u01c5\u01c6\5V,\2\u01c6"+
		"\u01cc\7;\2\2\u01c7\u01c8\7\t\2\2\u01c8\u01c9\7;\2\2\u01c9\u01ca\5T+\2"+
		"\u01ca\u01cb\7;\2\2\u01cb\u01cd\3\2\2\2\u01cc\u01c7\3\2\2\2\u01cc\u01cd"+
		"\3\2\2\2\u01cd\u01ce\3\2\2\2\u01ce\u01cf\7\'\2\2\u01cf\u01d0\7;\2\2\u01d0"+
		"\u01d1\5X-\2\u01d1\u01d7\7;\2\2\u01d2\u01d3\7(\2\2\u01d3\u01d4\7;\2\2"+
		"\u01d4\u01d5\5P)\2\u01d5\u01d6\7;\2\2\u01d6\u01d8\3\2\2\2\u01d7\u01d2"+
		"\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9\u01db\7\13\2\2"+
		"\u01da\u01dc\7;\2\2\u01db\u01da\3\2\2\2\u01db\u01dc\3\2\2\2\u01dcM\3\2"+
		"\2\2\u01dd\u01de\7>\2\2\u01deO\3\2\2\2\u01df\u01e0\7A\2\2\u01e0Q\3\2\2"+
		"\2\u01e1\u01e2\7>\2\2\u01e2S\3\2\2\2\u01e3\u01e4\t\3\2\2\u01e4U\3\2\2"+
		"\2\u01e5\u01e6\t\b\2\2\u01e6W\3\2\2\2\u01e7\u01ec\5l\67\2\u01e8\u01ec"+
		"\5b\62\2\u01e9\u01ec\5\\/\2\u01ea\u01ec\5Z.\2\u01eb\u01e7\3\2\2\2\u01eb"+
		"\u01e8\3\2\2\2\u01eb\u01e9\3\2\2\2\u01eb\u01ea\3\2\2\2\u01ecY\3\2\2\2"+
		"\u01ed\u01ee\7/\2\2\u01ee[\3\2\2\2\u01ef\u01f0\7\f\2\2\u01f0\u01f1\7;"+
		"\2\2\u01f1\u01f2\7\6\2\2\u01f2\u01f3\7;\2\2\u01f3\u01f4\7\b\2\2\u01f4"+
		"\u01f5\7;\2\2\u01f5\u01f6\5`\61\2\u01f6\u01fc\7;\2\2\u01f7\u01f8\7\60"+
		"\2\2\u01f8\u01f9\7;\2\2\u01f9\u01fa\5^\60\2\u01fa\u01fb\7;\2\2\u01fb\u01fd"+
		"\3\2\2\2\u01fc\u01f7\3\2\2\2\u01fc\u01fd\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe"+
		"\u01ff\7\13\2\2\u01ff]\3\2\2\2\u0200\u0201\7D\2\2\u0201_\3\2\2\2\u0202"+
		"\u0203\t\4\2\2\u0203a\3\2\2\2\u0204\u0205\7\61\2\2\u0205\u0206\7;\2\2"+
		"\u0206\u0207\7\6\2\2\u0207\u0208\7;\2\2\u0208\u0209\7\62\2\2\u0209\u020a"+
		"\7;\2\2\u020a\u020b\5j\66\2\u020b\u020c\7;\2\2\u020c\u020d\7\63\2\2\u020d"+
		"\u020e\7;\2\2\u020e\u020f\5h\65\2\u020f\u0210\7;\2\2\u0210\u0211\7\64"+
		"\2\2\u0211\u0212\7;\2\2\u0212\u0213\5f\64\2\u0213\u0219\7;\2\2\u0214\u0215"+
		"\7\65\2\2\u0215\u0216\7;\2\2\u0216\u0217\5d\63\2\u0217\u0218\7;\2\2\u0218"+
		"\u021a\3\2\2\2\u0219\u0214\3\2\2\2\u0219\u021a\3\2\2\2\u021a\u021b\3\2"+
		"\2\2\u021b\u021c\7\13\2\2\u021cc\3\2\2\2\u021d\u021e\7A\2\2\u021ee\3\2"+
		"\2\2\u021f\u0220\7A\2\2\u0220g\3\2\2\2\u0221\u0222\7A\2\2\u0222i\3\2\2"+
		"\2\u0223\u0224\7A\2\2\u0224k\3\2\2\2\u0225\u0226\7\r\2\2\u0226\u0227\7"+
		";\2\2\u0227\u0228\7\6\2\2\u0228\u0229\7;\2\2\u0229\u022a\7\66\2\2\u022a"+
		"\u022b\7;\2\2\u022b\u022c\5p9\2\u022c\u022d\7;\2\2\u022d\u022e\7\67\2"+
		"\2\u022e\u022f\7;\2\2\u022f\u0230\5n8\2\u0230\u0231\7;\2\2\u0231\u0232"+
		"\7\13\2\2\u0232m\3\2\2\2\u0233\u0234\7<\2\2\u0234o\3\2\2\2\u0235\u0236"+
		"\7A\2\2\u0236q\3\2\2\2\u0237\u023a\5t;\2\u0238\u023a\5z>\2\u0239\u0237"+
		"\3\2\2\2\u0239\u0238\3\2\2\2\u023as\3\2\2\2\u023b\u023c\78\2\2\u023c\u023d"+
		"\t\t\2\2\u023d\u023e\78\2\2\u023eu\3\2\2\2\u023f\u0240\7<\2\2\u0240w\3"+
		"\2\2\2\u0241\u0242\7=\2\2\u0242y\3\2\2\2\u0243\u0244\t\n\2\2\u0244{\3"+
		"\2\2\2\u0245\u0246\7?\2\2\u0246}\3\2\2\2\64\177\u0084\u008b\u008d\u0095"+
		"\u0099\u00b4\u00b8\u00cd\u00d1\u00d5\u00d9\u00dd\u00e1\u00ea\u00ee\u00f2"+
		"\u00fc\u0100\u0104\u0108\u010c\u0110\u0118\u011a\u011d\u0124\u0128\u012c"+
		"\u0134\u0136\u013f\u0147\u015d\u0160\u016a\u0184\u018d\u0192\u019d\u01a6"+
		"\u01ab\u01b5\u01cc\u01d7\u01db\u01eb\u01fc\u0219\u0239";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}