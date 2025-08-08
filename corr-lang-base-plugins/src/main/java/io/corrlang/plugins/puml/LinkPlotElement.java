package io.corrlang.plugins.puml;

import java.io.IOException;
import java.io.Writer;

public class LinkPlotElement extends PlotElement {

    public enum LinkType {
        REFERENCE,
        INHERITANCE,
        COMPOSITION,
        AGGREGATION,
        BIREFERENCE,
        BICOMPOSITION,
        BIAGGREGATION
    }

    private String from;
    private String to;
    private LinkType type = LinkType.REFERENCE;
    private String label = "";
    private String srcLabel = "";
    private String trgLabel = "";
    private String specialStyling;

    public LinkPlotElement(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public LinkPlotElement(String from, String to, String label) {
        this.from = from;
        this.to = to;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSrcLabel() {
        return srcLabel;
    }

    public void setSrcLabel(String srcLabel) {
        this.srcLabel = srcLabel;
    }

    public String getTrgLabel() {
        return trgLabel;
    }

    public void setTrgLabel(String trgLabel) {
        this.trgLabel = trgLabel;
    }

    public void setType(LinkType type) {
        this.type = type;
    }

    public void setSpecialStyling(String specialStyling) {
        this.specialStyling = specialStyling;
    }

    @Override
    public void writePlantUML(Writer writer) throws IOException {
        writer.append(from);
        if (srcLabel != null && !srcLabel.isEmpty()) {
            writer.append(" \"");
            writer.append(srcLabel);
            writer.append('"');
        }
        writer.append(' ');
        switch (this.type) {
            case REFERENCE:
                writer.append("-->");
                break;
            case INHERITANCE:
                writer.append("-up-|>");
                break;
            case COMPOSITION:
                writer.append("*-->");
                break;
            case AGGREGATION:
                writer.append("o-->");
                break;
            case BIREFERENCE:
                writer.append("--");
                break;
            case BIAGGREGATION:
                writer.append("*--");
                break;
            case BICOMPOSITION:
                writer.append("o--");
                break;
        }
        if (trgLabel != null && !trgLabel.isEmpty()) {
            writer.append(" \"");
            writer.append(trgLabel);
            writer.append('"');
        }
        writer.append(' ');
        writer.append(to);
        if (specialStyling != null) {
            writer.append(" ");
            writer.append(specialStyling);
        }
        if (label != null && !label.isEmpty()) {
            writer.append(" : ");
            writer.append(label);
        }
        writer.append("\n");
    }
}
