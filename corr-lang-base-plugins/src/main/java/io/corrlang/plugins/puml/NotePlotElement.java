package io.corrlang.plugins.puml;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class NotePlotElement extends PlotElement {

    private static long noteCounter = 0;

    @Override
    public void writePlantUML(Writer writer) throws IOException {
        writer.append("note");
        String noteId = "";
        switch (location) {
            case TYPE:
                writer.append(" top of ");
                writer.append(references.get(0));
                break;
            case ATTRIBUTE:
                writer.append(" right of ");
                writer.append(references.get(0));
                break;
            case LINK:
                writer.append(" on ");
                writer.append(references.get(0));
                break;
            case MULTI_NODE:
                noteId = "N" + (noteCounter++);
                writer.append("as ");
                writer.append(noteId);
                break;
        }
        writer.append('\n');
        writer.append(content);
        writer.append('\n');
        writer.append("end note\n");
        if (location.equals(Location.MULTI_NODE)) {
            for (String ref : references) {
                writer.append(noteId);
                writer.append("-[dotted]-");
                writer.append(ref);
                writer.append('\n');
            }
        }

    }

    public enum Location {
        TYPE,
        ATTRIBUTE,
        LINK,
        MULTI_NODE
    }

    private String content;
    private List<String> references;
    private Location location;

    public NotePlotElement(String content, String reference, Location location) {
        this.content = content;
        this.references = new ArrayList<>();
        this.references.add(reference);
        this.location = location;
    }

    public NotePlotElement(String content, List<String> references) {
        this.content = content;
        this.references = references;
        this.location = Location.MULTI_NODE;
    }
}
