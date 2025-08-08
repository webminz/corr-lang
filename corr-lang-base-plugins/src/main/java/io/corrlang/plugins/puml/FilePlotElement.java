package io.corrlang.plugins.puml;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilePlotElement extends PlotElement {

    private Map<String, NodePlotElement> nodes = new LinkedHashMap<>();
    private List<LinkPlotElement> edges = new ArrayList<>();
    private List<LinkPlotElement> anonEdges = new ArrayList<>();
    private List<NotePlotElement> notes = new ArrayList<>();
    private Map<String, MultiPlotElement> relations = new LinkedHashMap<>();


    public NodePlotElement addNode(String name) {
        NodePlotElement result = new NodePlotElement(name);
        this.nodes.put(name, result);
        return result;
    }

    public LinkPlotElement addNamedEdge(String srcNode, String trgNode, String label) {
        LinkPlotElement result = new LinkPlotElement(srcNode, trgNode, label);
        this.edges.add(result);
        return result;
    }

    public LinkPlotElement addAnonEdge(String src, String trg) {
        LinkPlotElement result = new LinkPlotElement(src, trg);
        this.anonEdges.add(result);
        return result;
    }

    public void addNote(String reference, String content, NotePlotElement.Location location) {
        this.notes.add(new NotePlotElement(content, reference, location));
    }

    public void addMultiNote(String content, List<String> references) {
        this.notes.add(new NotePlotElement(content, references));
    }


    public MultiPlotElement addTraceLink(String name) {
        MultiPlotElement multiPlotElement = new MultiPlotElement(name);
        relations.put(name,multiPlotElement);
        return multiPlotElement;
    }

    public MultiPlotElement getTraceLink(String name) {
        return this.relations.get(name);
    }



    @Override
    public void writePlantUML(Writer writer) throws IOException {
        writer.append("@startuml\n");
        writer.append("set namespaceSeparator ::\n");

        for (NodePlotElement nodePlotElement : this.nodes.values()) {
            nodePlotElement.writePlantUML(writer);
        }
        for (MultiPlotElement multi : this.relations.values()) {
            multi.writePlantUML(writer);
        }
        for (LinkPlotElement link : this.edges) {
            link.writePlantUML(writer);
        }
        for (LinkPlotElement link : this.anonEdges) {
            link.writePlantUML(writer);
        }
        for (NotePlotElement note : this.notes) {
            note.writePlantUML(writer);
        }
        writer.append("\n");

        writer.append("@enduml\n");
    }


}
