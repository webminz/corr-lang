package io.corrlang.plugins.puml;


import io.corrlang.domain.ComprSys;
import io.corrlang.domain.MessageArgument;
import io.corrlang.domain.MessageType;
import io.corrlang.domain.Endpoint;
import io.corrlang.domain.exceptions.CorrLangException;
import no.hvl.past.graph.GraphTheory;
import no.hvl.past.graph.Universe;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.operations.Invert;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.names.*;

import no.hvl.past.util.Multiplicity;
import no.hvl.past.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class PlantUMLPlotter {

    private final boolean printDiagrams;
    private final boolean drawServices;

    private final String pumlExecutable;
    private final Logger logger;


    public PlantUMLPlotter(
            boolean printDiagrams,
            boolean drawServices,
            String pumlExecutable) {
        this.printDiagrams = printDiagrams;
        this.drawServices = drawServices;
        this.pumlExecutable = pumlExecutable;
        this.logger = LoggerFactory.getLogger(getClass());
    }


    public void serializeEndpoint(Endpoint system, OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writeSystem(system, writer);
        writer.flush();
        writer.close();
    }

    public void invokePuml(File pumlFile, File targetDir, String resultFileName) {
        ProcessBuilder command = new ProcessBuilder().command(pumlExecutable.split(" "));
        try {
            Process p = command.start();
            int exitValue = p.exitValue();
            if (exitValue != 0) {
                String text = new String(p.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                logger.error(text);
            }
        } catch (IOException e) {
            throw CorrLangException.io(e);
        }
    }

    public void plot(Endpoint system, OutputStream outputStream) throws IOException {
        if (!drawServices) {
            logger.info("Drawing Messages has been disabled");
        }

        serializeEndpoint(system, outputStream);
    }

    private void writeSystem(Endpoint system, OutputStreamWriter writer) throws IOException {
        FilePlotElement file = new FilePlotElement();

        system.getSchema().types().forEach(node -> {
//            if (system instanceof ComprSys) {
//                if (node.firstPart().equals(system.schema().getName())) {
//                    ComprSys csys = (ComprSys) system;
//                    if (!csys.isMerged(node.secondPart())) {
//                        plotCommWitn(csys, file, node);
//                    } else {
//                        plotNode(system,file,node);
//                    }
//                } else {
//                    plotNode(system,file,node);
//                }
//            } else {
                plotNode(system, file, node);
//            }
        });
        Set<Triple> covered = new HashSet<>();


        system.getSchema().links().forEach(t -> {
//            if (system instanceof ComprSys) {
//                ComprSys csys = (ComprSys) system;
//                if (t.getLabel() instanceof BinaryCombinator) {
//                    plotProjection(csys, file, file, covered, t);
//                } else if (t.getLabel().firstPart().equals(system.schema().getName())) {
//                    if (csys.isMerged(t.getLabel())) {
//                        plotCommLink(csys, file, covered, t);
//                    } else {
//                        plotLink(system,file,covered,t);
//                    }
//                } else {
//                    plotLink(system, file, covered, t);
//                }
//            } else {
                plotLink(system, file, covered, t);
//            }
        });

        system.getSchema().directSuperTypes().forEach(t -> {
            String from = mkReference(system, t.getDomain());
            String to = mkReference(system, t.getCodomain());
            LinkPlotElement linkPlotElement = file.addAnonEdge(from, to);
            linkPlotElement.setType(LinkPlotElement.LinkType.INHERITANCE);
        });


        if (drawServices) {
            List<Name> containers = system.getSchema().messageContainers().collect(Collectors.toList());
            if (containers.isEmpty()) {
                NodePlotElement globalService = file.addNode(system.getName() + "Service");
                globalService.setType(NodePlotElement.NodeType.SERVICE);
                system.getSchema().actions().forEach(message -> plotMessage(system, globalService, message));
            } else {
                for (Name container : containers) {
                    NodePlotElement service = file.addNode(system.getSchema().displayName(container));
                    service.setType(NodePlotElement.NodeType.SERVICE);
                    system.getSchema().actionsInGroup(container).forEach(message -> plotMessage(system, service, message));
                }
            }
        }

        if (printDiagrams) {
            system.getSchema().sketch().diagrams()
                    .filter(d -> !(d instanceof MessageType) && !(d instanceof MessageArgument) &&
                            !DataTypePredicate.getInstance().diagramIsOfType(d) &&
                            !TargetMultiplicity.class.isAssignableFrom(d.label().getClass()) &&
                            !SourceMultiplicity.class.isAssignableFrom(d.label().getClass()) &&
                            !Acyclicity.class.isAssignableFrom(d.label().getClass()) &&
                            !Ordered.class.isAssignableFrom(d.label().getClass()) &&
                            !Unique.class.isAssignableFrom(d.label().getClass()) &&
                            !Invert.class.isAssignableFrom(d.label().getClass()) &&
                            !AbstractType.class.isAssignableFrom(d.label().getClass()) &&
                            !Singleton.class.isAssignableFrom(d.label().getClass()))
                .forEach(d -> {
                    plotDiagram(system, file, d);
                });
        }


        file.writePlantUML(writer);

    }

    private void plotCommLink(ComprSys csys, FilePlotElement metamodel, Set<Triple> covered, Triple t) {
        LinkPlotElement linkPlotElement = metamodel.addNamedEdge(csys.displayName(t.getSource()), csys.displayName(t.getLabel()), csys.displayName(t.getTarget()));
        linkPlotElement.setSpecialStyling("#blue;line.dashed");

    }



    private void plotCommWitn(ComprSys system, FilePlotElement file, Name node) {
        file.addTraceLink(system.displayName(node));
    }

    private void plotDiagram(Endpoint system, FilePlotElement metamodel, no.hvl.past.graph.Diagram d) {
        String content = "";
        Name name = d.getName();
        if (!(name instanceof AnonymousIdentifier)) {
            content = name.print(PrintingStrategy.DETAILED);
        }
        if (d.label() instanceof GraphTheory) {
            content += "\n(";
            content += ((GraphTheory) d.label()).getName().print(PrintingStrategy.DETAILED);
            content += ")";
        }
        if (d.binding().domain().equals(Universe.ONE_NODE)) {
            metamodel.addNote(mkReference(system, d.nodeBinding().get()),content, NotePlotElement.Location.TYPE);
        } else if (d.binding().domain().equals(Universe.ARROW)) {
            Triple triple = d.edgeBinding().get();
            if (system.getSchema().isAttributeType(triple)) {
                metamodel.addNote(mkReference(system,triple.getSource()) + "::" + system.getSchema().displayName(triple.getLabel()), content, NotePlotElement.Location.ATTRIBUTE);
            } else {
                metamodel.addNote(system.getSchema().displayName(triple.getLabel()),content, NotePlotElement.Location.LINK);
            }
        } else {
            List<String> references = new ArrayList<>();
            d.binding().domain().nodes().forEach(n -> d.binding().map(n).map(system.getSchema()::displayName).ifPresent(references::add));
            metamodel.addMultiNote(content,references);
        }
    }

    private void plotMessage(Endpoint system, NodePlotElement service, Name messageName) {
        // TODO: reactivate messages
        StringBuilder serviceMethod = new StringBuilder();
        serviceMethod.append(system.getSchema().displayName(messageName));
        serviceMethod.append('(');
        serviceMethod.append(StringUtils.fuseList(system.getSchema().messageArguments(messageName), arg -> {
            return system.getSchema().displayName(arg.getLabel()) +
                    " : " +
                    system.getSchema().displayName(arg.getTarget()) + makeMultIfNotOptional(system.getSchema().multiplicity(arg.getLabel()));
        }, ", "));
        serviceMethod.append(") : ");
        String returnType = StringUtils.fuseList(system.getSchema().messageOutputs(messageName), arg -> {
            return system.getSchema().displayName(arg.getTarget()) + makeMultIfNotOptional(system.getSchema().multiplicity(arg.getLabel()));
        }, ", ");
        if (returnType.isEmpty()) {
            returnType = "void";
        }
        serviceMethod.append(returnType);
        service.addCompartment(serviceMethod.toString());
    }

    private String mkReference(Endpoint endpoint, Name name) {
        String result = "";
        Name current = name;
//        if (endpoint instanceof ComprSys && name.hasPrefix(endpoint.schema().getName())) {
//            result += endpoint.getSchema().getFormalRepresentation().getName().printRaw() + "::overlaps::";
//            current = name.secondPart();
//        }
        while (current instanceof Prefix) {
            result += current.firstPart().printRaw();
            result += "::";
            current = current.secondPart();
        }
        result += endpoint.getSchema().displayName(name);
        return result;
    }

    private void plotLink(Endpoint system, FilePlotElement metamodel, Set<Triple> covered, Triple t) {
        if (!covered.contains(t)) {
            Optional<Triple> opp = system.getSchema().getOppositeIfExists(t);
            String s = mkReference(system,t.getSource());
            String l = system.getSchema().displayName(t.getLabel());
            String tr = mkReference(system, t.getTarget());
            LinkPlotElement linkPlotElement = metamodel.addNamedEdge(s, tr, l);
            boolean isComp = system.getSchema().isComposition(t);
            if (opp.isPresent()) {
                covered.add(opp.get());
                String ol = system.getSchema().displayName(opp.get().getLabel());
                if (isComp) {
                    linkPlotElement.setType(LinkPlotElement.LinkType.BICOMPOSITION);
                } else {
                    linkPlotElement.setType(LinkPlotElement.LinkType.BIREFERENCE);
                }
                String assocName = system.getSchema().sketch().diagramsOn(t).filter(d -> Invert.class.isAssignableFrom(d.label().getClass())).findFirst().map(d -> d.getName().printRaw()).orElse(l + ":" + ol);
                linkPlotElement.setLabel(assocName);
                String tM = makeMult(system.getSchema().multiplicity(t.getLabel()));
                String sM = makeMult(system.getSchema().multiplicity(opp.get().getLabel()));
                linkPlotElement.setTrgLabel(l + "  " + tM);
                linkPlotElement.setSrcLabel(ol + "  " + sM);
            } else {
                Multiplicity targetMultiplicity = system.getSchema().multiplicity(t.getLabel());
                linkPlotElement.setTrgLabel(makeMult(targetMultiplicity));
//                Pair<Integer, Integer> sourceMultiplicity = system.getSourceMultiplicity(t);
//                if (sourceMultiplicity.getLeft() != 0 || sourceMultiplicity.getRight() != 1) {
//                    linkPlotElement.setSrcLabel(makeMult(sourceMultiplicity));
//                }
                if (isComp) {
                    linkPlotElement.setType(LinkPlotElement.LinkType.COMPOSITION);
                } else if (system.getSchema().isAggregation(t)) {
                    linkPlotElement.setType(LinkPlotElement.LinkType.AGGREGATION);
                }
            }
            covered.add(t);
        }
    }

    private void plotNode(Endpoint system, FilePlotElement file, Name node) {
        String name = mkReference(system,node);


        if (system.getSchema().isEnumType(node)) {
            NodePlotElement enumPlot = file.addNode(name);
            enumPlot.setType(NodePlotElement.NodeType.ENUM);
            for (Name lit : system.getSchema().enumLiterals(node)) {
                enumPlot.addCompartment(lit.printRaw());
            }
        } else if (!system.getSchema().isSimpleTypeNode(node)) {
            NodePlotElement nodePlot = file.addNode(name);
            if (system.getSchema().isAbstract(node)) {
                nodePlot.setType(NodePlotElement.NodeType.ABSTRACT_TYPE);
            } else if (system.getSchema().isSingleton(node)) {
                nodePlot.setType(NodePlotElement.NodeType.SINGLETON_TYPE);
            }
            system.getSchema().attributeFeatures(node).forEach(attEdge -> {
                String attName = system.getSchema().displayName(attEdge.getLabel());
                String typName = system.getSchema().displayName(attEdge.getTarget());
                NodePlotElement.Compartment attCompartment = nodePlot.addCompartment(attName, typName);
                Multiplicity targetMultiplicity = system.getSchema().multiplicity(attEdge.getLabel());
                attCompartment.setPredicates(makeMultIfNotOptional(targetMultiplicity));
            });
        }
    }

    private GroupPlotElement getMetamodelSubpackage(GroupPlotElement groupPlotElement, Name secondPart) {
        if (secondPart instanceof Prefix) {
            Name p = secondPart.firstPart();
            String s = p.printRaw();
            if (groupPlotElement.getGroupByName(s) == null) {
                return getMetamodelSubpackage(groupPlotElement.addGroup(s), secondPart.secondPart());
            } else {
                return getMetamodelSubpackage(groupPlotElement.getGroupByName(s), secondPart.secondPart());
            }
        } else {
            return groupPlotElement;
        }
    }


    private String makeMultIfNotOptional(Multiplicity targetMultiplicity) {
        if (targetMultiplicity.getLowerBound() != 0 || targetMultiplicity.getUpperBound() != 1) {
            return "[" + makeMult(targetMultiplicity) + "]";
        }
        return "";
    }

    private String makeMult(Multiplicity targetMultiplicity) {
        return "" + (targetMultiplicity.getLowerBound() < 0 ? "*" : targetMultiplicity.getLowerBound()) + ".." + (targetMultiplicity.getUpperBound() < 0 ? "*" : targetMultiplicity.getLowerBound()) + "";
    }

}
