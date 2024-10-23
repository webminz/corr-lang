package io.corrlang.plugins.dm;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GroupPlotElement extends PlotElement {

    private String name;
    private boolean isInstance;
    private Map<String, GroupPlotElement> subGroups = new LinkedHashMap<>();
    private Map<String, NodePlotElement> nodes = new LinkedHashMap<>();
    private Map<String, LinkPlotElement> edges = new LinkedHashMap<>();
    private List<LinkPlotElement> anonEdges = new ArrayList<>();
    private List<NotePlotElement> notes = new ArrayList<>();


    public GroupPlotElement getGroupByName(String name) {
        return this.subGroups.get(name);
    }

    public GroupPlotElement addGroup(String name) {
        GroupPlotElement group = new GroupPlotElement(name, isInstance);
        this.subGroups.put(name, group);
        return group;
    }


    public GroupPlotElement(String name, boolean isInstance) {
        this.name = name;
        this.isInstance = isInstance;
    }

    public NodePlotElement addNode(String name) {
        NodePlotElement result = new NodePlotElement(name);
        this.nodes.put(name, result);
        return result;
    }

    public LinkPlotElement addNamedEdge(String srcNode, String trgNode, String label) {
        LinkPlotElement result = new LinkPlotElement(srcNode, trgNode, label);
        this.edges.put(label, result);
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


    @Override
    public void writePlantUML(Writer writer) throws IOException {
        writer.append("package ");
        writer.append(name);
        writer.append(" ");
        if (isInstance) {
            writer.append("<<Database>>");
        } else {
            writer.append("<<Folder>>");
        }
        writer.append(" {\n");

        for (GroupPlotElement group : this.subGroups.values()) {
            group.writePlantUML(writer);
        }

        for (NodePlotElement nodePlotElement : this.nodes.values()) {
            nodePlotElement.writePlantUML(writer);
        }
        for (LinkPlotElement link : this.edges.values()) {
            link.writePlantUML(writer);
        }
        for (LinkPlotElement link : this.anonEdges) {
            link.writePlantUML(writer);
        }
        for (NotePlotElement note : this.notes) {
            note.writePlantUML(writer);
        }
        writer.append("}\n");

    }
}
