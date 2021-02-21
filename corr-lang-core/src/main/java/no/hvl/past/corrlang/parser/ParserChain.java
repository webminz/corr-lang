package no.hvl.past.corrlang.parser;

import com.google.common.base.Charsets;
import no.hvl.past.corrlang.reporting.ReportFacade;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

public class ParserChain {

    private CharStream stream;
    private String fileName;
    private ReportFacade reportFacade;

    private ParserChain(CharStream stream, String fileName, ReportFacade reportFacade) {
        this.stream = stream;
        this.fileName = fileName;
        this.reportFacade = reportFacade;
    }

    private SyntacticalResult parse(SyntacticalResult result)  {

        DefaultCorrlangListener listener = new DefaultCorrlangListener(result, fileName, reportFacade);

        final CorrlangLexer lexer = new CorrlangLexer(stream);
        final CorrlangParser parser = new CorrlangParser(new CommonTokenStream(lexer));
        new ParseTreeWalker().walk(listener, parser.corrfile());

        return result;
    }

    public static SyntacticalResult parseFromFile(String file, ReportFacade reportFacade, SyntacticalResult result) throws IOException {
        CharStream stream = CharStreams.fromFileName(file, Charsets.UTF_8);
        return new ParserChain(stream, file, reportFacade).parse(result);
    }

    public static SyntacticalResult parseFromStream(InputStream inputStream, ReportFacade reportFacade, SyntacticalResult result) throws IOException {
        CharStream stream = CharStreams.fromStream(inputStream, Charsets.UTF_8);
        return new ParserChain(stream, "unavailable",reportFacade).parse(result);
    }

    public static SyntacticalResult parseFromString(String string, ReportFacade reportFacade, SyntacticalResult result) {
        CodePointCharStream charStream = CharStreams.fromString(string);
        return new ParserChain(charStream, "unavailable", reportFacade).parse(result);
    }

    public static SyntacticalResult parseFromFile(File corrSpecFile, ReportFacade reportFacade, SyntacticalResult result) throws IOException {
        CharStream charStream = CharStreams.fromStream(new FileInputStream(corrSpecFile), Charsets.UTF_8);
        return new ParserChain(charStream,corrSpecFile.getName(),reportFacade).parse(result);

    }
}
