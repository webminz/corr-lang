package no.hvl.past.corrlang.parser;

import com.google.common.base.Charsets;
import no.hvl.past.corrlang.reporting.ReportFacade;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserChain {

    private CharStream stream;
    private String fileName;
    private ReportFacade reportFacade;

    private ParserChain(CharStream stream, String fileName, ReportFacade reportFacade) {
        this.stream = stream;
        this.fileName = fileName;
        this.reportFacade = reportFacade;
    }

    private SyntacticalResult parse(SyntacticalResult result) throws ParseException {
        DefaultCorrlangListener listener = new DefaultCorrlangListener(result, fileName, reportFacade);
        List<String> recognitionExceptions = new ArrayList<>();

        Logger logger = LogManager.getLogger(getClass());

        final CorrlangLexer lexer = new CorrlangLexer(stream);
        final CorrlangParser parser = new CorrlangParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                String message = "Location: line: " + line + " column: " + charPositionInLine + " details: " + msg; //+ " (" + ((CommonToken)offendingSymbol).getType() + " vs. " + e.getExpectedTokens().toString() +")";
                logger.error(message);
                recognitionExceptions.add(message);
            }
        });
        new ParseTreeWalker().walk(listener, parser.corrfile());

        if (recognitionExceptions.isEmpty()) {
            return result;
        } else {
            throw new ParseException(fileName, recognitionExceptions);
        }
    }

    public static SyntacticalResult parseFromFile(String file, ReportFacade reportFacade, SyntacticalResult result) throws IOException, ParseException {
        CharStream stream = CharStreams.fromFileName(file, Charsets.UTF_8);
        return new ParserChain(stream, file, reportFacade).parse(result);
    }

    public static SyntacticalResult parseFromStream(InputStream inputStream, ReportFacade reportFacade, SyntacticalResult result) throws IOException, ParseException {
        CharStream stream = CharStreams.fromStream(inputStream, Charsets.UTF_8);
        return new ParserChain(stream, "unavailable",reportFacade).parse(result);
    }

    public static SyntacticalResult parseFromString(String string, ReportFacade reportFacade, SyntacticalResult result) throws ParseException {
        CodePointCharStream charStream = CharStreams.fromString(string);
        return new ParserChain(charStream, "unavailable", reportFacade).parse(result);
    }

    public static SyntacticalResult parseFromFile(File corrSpecFile, ReportFacade reportFacade, SyntacticalResult result) throws IOException, ParseException {
        CharStream charStream = CharStreams.fromStream(new FileInputStream(corrSpecFile), Charsets.UTF_8);
        return new ParserChain(charStream,corrSpecFile.getName(),reportFacade).parse(result);

    }
}
