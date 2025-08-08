package io.corrlang.plugins.puml;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class NodePlotElement extends PlotElement{

    public static class Compartment extends PlotElement {
        private String name;
        private String typeName;
        private String predicates = "";

        public Compartment(String name) {
            this.name = name;
        }

        public Compartment(String name, String typeName) {
            this.name = name;
            this.typeName = typeName;
        }

        public Compartment(String name, String typeName, String predicates) {
            this.name = name;
            this.typeName = typeName;
            this.predicates = predicates;
        }

        public String getPredicates() {
            return predicates;
        }

        public void setPredicates(String predicates) {
            this.predicates = predicates;
        }

        @Override
        public void writePlantUML(Writer writer) throws IOException {
            writer.append("   ");
            writer.append(name);
            if (typeName != null) {
                writer.append(" : ");
                writer.append(typeName);
                if (predicates != null && !predicates.isEmpty()) {
                    writer.append(' ');
                    writer.append(predicates);
                }
            }
            writer.append("\n");
        }
    }

    public enum NodeType {
        TYPE,
        ABSTRACT_TYPE,
        SINGLETON_TYPE,
        OBJECT,
        ENUM,
        SERVICE
    }

    private String name;
    private NodeType type = NodeType.TYPE;
    private List<Compartment> compartments = new ArrayList<>();


    public void addCompartment(String name) {
        this.compartments.add(new Compartment(name));
    }

    public Compartment addCompartment(String name, String type) {
        Compartment result = new Compartment(name, type);
        this.compartments.add(result);
        return result;
    }

    public NodePlotElement(String name) {
        this.name = name;
    }

    public void setType(NodeType type) {
        this.type = type;
    }



    @Override
    public void writePlantUML(Writer writer) throws IOException {
        switch (this.type) {
            case TYPE:
            case SERVICE:
            case SINGLETON_TYPE:
                writer.append("class ");
                break;
            case ABSTRACT_TYPE:
                writer.append("abstract class ");
                break;
            case ENUM:
                writer.append("enum ");
                break;
            case OBJECT:
                writer.append("object ");
                break;
        }
        writer.append(name);
        if (this.type.equals(NodeType.SERVICE)) {
            writer.append(" << (S,orchid) Service >>");
        } else if (this.type.equals(NodeType.SINGLETON_TYPE)) {
            writer.append(" << (C,green) Singleton >>");
        }
        writer.append(" {\n");

        for (Compartment compartment : this.compartments) {
            compartment.writePlantUML(writer);
        }

        writer.append("}\n\n");

    }
}
