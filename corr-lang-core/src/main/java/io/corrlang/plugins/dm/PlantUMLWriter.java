package io.corrlang.plugins.dm;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.IOException;
import java.io.OutputStream;

public class PlantUMLWriter {

    private final OutputStream outputStream;

    public PlantUMLWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @SuppressWarnings("deprecation")
    public void write(String input, String charset) throws IOException {
        SourceStringReader sourceStringReader = new SourceStringReader(input, charset);
        sourceStringReader.generateImage(outputStream, new FileFormatOption(FileFormat.PNG)); // other formats do not seem to work right now
    }
}
