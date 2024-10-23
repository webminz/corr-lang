package io.corrlang.domain;

import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.elements.Tuple;
import no.hvl.past.graph.operations.Invert;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.names.Name;
import no.hvl.past.util.Pair;

import java.util.*;
import java.util.stream.Stream;

/**
 * A sys(tem) is a convenience wrapper on top of a {@link Sketch},
 * which adds some support message for the most common metamodel-query operations
 * that are known from popular Frameworks such as Ecore.
 *
 * Moreover, it explicitly adds the notion of messages (i.e. means to access and manipulate the data stored in a system).
 *
 */
public interface Sys {

    interface SysBuilderParent {
        Triple getEdgeByLabel(Name edgeLabel);
        SysBuilderParent addMessage(MessageType msg);
    }

    class Builder implements SysBuilderParent {
        private final String url;
        private final Sketch sketch;
        private final Map<Name, String> displayNames;
        private Map<Name, MessageType> messages = new LinkedHashMap<>();

        public Builder addMessage(MessageType msg) {
            this.messages.put(msg.typeName(), msg);
            return this;
        }

        public Builder(String url, Sketch sketch) {
            this.url = url;
            this.sketch = sketch;
            this.displayNames = new HashMap<>();
        }

        public Builder displayName(Name formalName, String renderName) {
            this.displayNames.put(formalName, renderName);
            return this;
        }

        public MessageBuilder<Builder> beginMessage(Name msgType, boolean hasSideEffect) throws GraphError {
            // TODO check if node exists
            return new MessageBuilder<>(msgType, this, null, hasSideEffect);
        }

        public MessageContainerBuilder beginMessageContainer(Name container) throws GraphError {
            return new MessageContainerBuilder(container, this);
        }

        public Triple getEdgeByLabel(Name label)  {
            return sketch.carrier().get(label).get();
        }

        public Sys build() {
            return new Impl(url,sketch,displayNames,messages);
        }
    }


    class MessageContainerBuilder implements SysBuilderParent {

        private final Name containerName;
        private final Builder parentBuilder;
        private final MessageContainer messageContainer;

        public MessageContainerBuilder(Name containerName, Builder parentBuilder) {
            this.containerName = containerName;
            this.parentBuilder = parentBuilder;
            this.messageContainer = new MessageContainer(containerName);
        }

        public Triple getEdgeByLabel(Name edgeLabel) {
            return parentBuilder.getEdgeByLabel(edgeLabel);
        }

        public Builder endMessageContainer() {
            return parentBuilder;
        }

        public MessageBuilder<MessageContainerBuilder> beginMessage(Name msgType, boolean hasSideEffect) throws GraphError {
            // TODO check if node exists
            return new MessageBuilder<>(msgType.prefixWith(messageContainer.getTypeName()), this, messageContainer, hasSideEffect);
        }

        @Override
        public SysBuilderParent addMessage(MessageType msg) {
            parentBuilder.addMessage(msg);
            return this;
        }
    }


    class MessageBuilder<P extends SysBuilderParent>  {

        private final List<MessageArgument> inputArguments;
        private final List<MessageArgument> outputArguments;
        private final P parentBuilder;
        private final Name type;
        private MessageType result;

        private MessageBuilder(Name type, P parentBuilder, MessageContainer container, boolean hasSideEffect) {
            this.type = type;
            this.parentBuilder = parentBuilder;
            this.inputArguments = new ArrayList<>();
            this.outputArguments = new ArrayList<>();
            this.result = new MessageType(type, inputArguments, outputArguments, container, hasSideEffect);
        }


        public MessageBuilder<P> input(Name edgeLabel) throws GraphError {
            int indx = this.inputArguments.size();
            Triple t = parentBuilder.getEdgeByLabel(edgeLabel.prefixWith(type));
            this.inputArguments.add(new MessageArgument(result, t, indx, false));
            return this;
        }

        public MessageBuilder<P> output(Name edgeLabel) throws GraphError {
            int indx = this.outputArguments.size();
            Triple t = parentBuilder.getEdgeByLabel(edgeLabel.prefixWith(type));
            this.outputArguments.add(new MessageArgument(result, t, indx, true));
            return this;
        }

        public MessageType build() {
            return result;
        }

        public P endMessage() {
            this.parentBuilder.addMessage(this.result);
            return parentBuilder;
        }
    }


    class Impl implements Sys {

        private final String url;
        private final Sketch sketch;
        private final Map<Name, MessageType> messages;
        private final Map<Name, String> displayNames;

        // Caches

        private final Set<Name> abstractTypes;
        private final Set<Name> singletonTypes;

        private final Set<Name> stringTypes;
        private final Set<Name> intTypes;
        private final Set<Name> floatTypes;
        private final Set<Name> boolTypes;
        private final Set<Name> otherDataTypes;

        private final Map<Name, Set<Name>> enumTypes;

        private final Map<Triple, Pair<Integer, Integer>> targetMultiplicities;
        private final Map<Triple, Pair<Integer, Integer>> sourceMultiplicities;
        private final Map<Triple, Triple> inversePartners;
        private final Set<Triple> orderedTriples;
        private final Set<Triple> uniqueTriples;
        private final Set<Triple> acyclicTriples;


        public Impl(String url, Sketch sketch, Map<Name, String> displayNames, Map<Name, MessageType> messages) {
            this.url = url;
            this.sketch = sketch;
            this.displayNames = displayNames;
            this.messages = messages;

            this.abstractTypes = new HashSet<>();
            this.singletonTypes = new HashSet<>();
            this.stringTypes = new HashSet<>();
            this.intTypes = new HashSet<>();
            this.floatTypes = new HashSet<>();
            this.boolTypes = new HashSet<>();
            this.otherDataTypes = new HashSet<>();
            this.enumTypes = new HashMap<>();
            this.targetMultiplicities = new HashMap<>();
            this.sourceMultiplicities = new HashMap<>();
            this.inversePartners = new HashMap<>();
            this.orderedTriples = new HashSet<>();
            this.uniqueTriples = new HashSet<>();
            this.acyclicTriples = new HashSet<>();

            updateCaches();
        }

        // TODO has to go into the new class
        private void updateCaches() {
            this.sketch.diagrams().forEach(diagram -> {
                if (AbstractType.getInstance().diagramIsOfType(diagram)) {
                    diagram.nodeBinding().ifPresent(this.abstractTypes::add);
                } else if (Singleton.getInstance().diagramIsOfType(diagram)) {
                    diagram.nodeBinding().ifPresent(this.singletonTypes::add);
                } if (StringDT.getInstance().diagramIsOfType(diagram)) {
                    diagram.nodeBinding().ifPresent(this.stringTypes::add);
                } else if (IntDT.getInstance().diagramIsOfType(diagram)) {
                    diagram.nodeBinding().ifPresent(this.intTypes::add);
                } else if (FloatDT.getInstance().diagramIsOfType(diagram)) {
                    diagram.nodeBinding().ifPresent(this.floatTypes::add);
                } else if (BoolDT.getInstance().diagramIsOfType(diagram)) {
                    diagram.nodeBinding().ifPresent(this.boolTypes::add);
                } else if (EnumValue.class.isAssignableFrom(diagram.label().getClass())) {
                    EnumValue en = (EnumValue) diagram.label();
                    diagram.nodeBinding().ifPresent(n-> enumTypes.put(n, en.literals()));
                } else if (DataTypePredicate.getInstance().diagramIsOfType(diagram)) {
                    diagram.nodeBinding().ifPresent(this.otherDataTypes::add);
                } else if (TargetMultiplicity.class.isAssignableFrom(diagram.label().getClass())) {
                    TargetMultiplicity multiplicity = (TargetMultiplicity) diagram.label();
                   // diagram.edgeBinding().ifPresent(t -> this.targetMultiplicities.put(t, new Pair<>(multiplicity.getLowerBound(), multiplicity.getUpperBound())));
                } else if (SourceMultiplicity.class.isAssignableFrom(diagram.label().getClass())) {
                    SourceMultiplicity multiplicity = (SourceMultiplicity) diagram.label();
                   // diagram.edgeBinding().ifPresent(t -> this.sourceMultiplicities.put(t, new Pair<>(multiplicity.getLowerBound(), multiplicity.getUpperBound())));
                } else if (Ordered.getInstance().diagramIsOfType(diagram)) {
                    diagram.edgeBinding().ifPresent(this.orderedTriples::add);
                } else if (Unique.getInstance().diagramIsOfType(diagram)) {
                    diagram.edgeBinding().ifPresent(this.uniqueTriples::add);
                } else if (Acyclicity.getInstance().diagramIsOfType(diagram)) {
                    diagram.edgeBinding().ifPresent(this.acyclicTriples::add);
                } else if (Invert.class.isAssignableFrom(diagram.getClass())) {
                    Name fwdSrc = diagram.binding().map(Universe.CYCLE_FWD.getSource()).get();
                    Name fwdLbl = diagram.binding().map(Universe.CYCLE_FWD.getLabel()).get();
                    Name fwdTrg = diagram.binding().map(Universe.CYCLE_FWD.getTarget()).get();
                    Name bwdSrc = diagram.binding().map(Universe.CYCLE_BWD.getSource()).get();
                    Name bwdLbl = diagram.binding().map(Universe.CYCLE_BWD.getLabel()).get();
                    Name bwdTrg = diagram.binding().map(Universe.CYCLE_BWD.getTarget()).get();
                    this.inversePartners.put(Triple.edge(fwdSrc, fwdLbl, fwdTrg), Triple.edge(bwdSrc, bwdLbl, bwdTrg));
                    this.inversePartners.put(Triple.edge(bwdSrc, bwdLbl, bwdTrg), Triple.edge(fwdSrc, fwdLbl, fwdTrg));
                }
            });
        }


        @Override
        public boolean isAbstract(Name typeName) {
            return this.abstractTypes.contains(typeName);
        }

        @Override
        public boolean isSingleton(Name typeName) {
            return this.singletonTypes.contains(typeName);
        }

        @Override
        public boolean isStringType(Name nodeType) {
            return this.stringTypes.contains(nodeType);
        }

        @Override
        public boolean isIntType(Name nodeType) {
            return this.intTypes.contains(nodeType);
        }

        @Override
        public boolean isBoolType(Name nodeType) {
            return this.boolTypes.contains(nodeType);
        }

        @Override
        public boolean isFloatType(Name nodeType) {
            return this.floatTypes.contains(nodeType);
        }

        @Override
        public boolean isEnumType(Name nodeType) {
            return this.enumTypes.containsKey(nodeType);
        }


        @Override
        public Optional<Triple> getOppositeIfExists(Triple edge) {
            return Optional.ofNullable(this.inversePartners.get(edge));
        }

        @Override
        public boolean isSimpleTypeNode(Name nodeName) {
            return isBoolType(nodeName) || isStringType(nodeName) || isIntType(nodeName) || isEnumType(nodeName) || isFloatType(nodeName) ||
                    this.otherDataTypes.contains(nodeName);
        }

        @Override
        public Pair<Integer, Integer> getSourceMultiplicity(Triple edge) {
            if (this.sourceMultiplicities.containsKey(edge)) {
                return this.sourceMultiplicities.get(edge);
            }
            return new Pair<>(0, -1);
        }

        @Override
        public Pair<Integer, Integer> getTargetMultiplicity(Triple edge) {
            if (this.targetMultiplicities.containsKey(edge)) {
                return this.targetMultiplicities.get(edge);
            }
            return new Pair<>(0, -1);
        }

        @Override
        public boolean isComposition(Triple edge) {
            return getSourceMultiplicity(edge).getRight() == 1 && this.acyclicTriples.contains(edge);
        }

        @Override
        public boolean isAggregation(Triple triple) {
            return Sys.super.isAggregation(triple);
        }

        @Override
        public boolean isOrdered(Triple edge) {
            return this.orderedTriples.contains(edge);
        }

        @Override
        public boolean isUnique(Triple edge) {
            return this.uniqueTriples.contains(edge);
        }

        @Override
        public String displayName(Name name) {
            if (displayNames.containsKey(name)) {
                return this.displayNames.get(name);
            }
            return name.printRaw();
        }

        @Override
        public Optional<Triple> lookup(String... path) {
            Optional<Triple> source = sketch.carrier().get(Name.identifier(path[0]));
            if (path.length == 1 && source.isPresent()) {
                return source;
            }
            if (path.length == 2 && source.isPresent()) {
                return sketch.carrier().edges()
                        .filter(t -> displayName(t.getLabel()) != null)
                        .filter(t -> displayName(t.getLabel()).equals(path[1]))
                        .filter(t -> sketch.carrier().isInvariant(t.getSource(), source.get().getSource()))
                        .findFirst();
            }
            Name result = Name.identifier(path[path.length - 1]);
            if (path.length > 1) {
                for (int i = path.length - 2; i >= 0; i--) {
                    result = result.prefixWith(Name.identifier(path[i]));
                }
            }
            return sketch.carrier().get(result);
        }

        @Override
        public Sketch schema() {
            return sketch;
        }

        @Override
        public String url() {
            return url;
        }

        @Override
        public Stream<MessageType> messages() {
            return messages.values().stream();
        }

        @Override
        public MessageType getMessageType(Name type) {
            return messages.get(type);
        }

        @Override
        public boolean isMessageType(Name typeName) {
            return messages.containsKey(typeName);
        }
    }

    String displayName(Name name);

    Optional<Triple> lookup(String... path);

    Sketch schema();

    String url();

    Stream<MessageType> messages();

    default boolean isMessageContainer(Name name) {
        return messages().anyMatch(msg -> msg.getGroup().map(MessageContainer::getTypeName).map(name::equals).orElse(false));
    }

    default boolean isMessageRelated(Name type) {
        return messages().anyMatch(msg -> {
            if (msg.typeName().equals(type)) {
                return true;
            }
            if (msg.getGroup().map(cont -> cont.getTypeName().equals(type)).orElse(false)) {
                return true;
            }
            return msg.arguments().anyMatch(arg -> arg.asEdge().getLabel().equals(type));
        });
    }

    default MessageType getMessageType(Name type) {
        return messages().filter(m -> m.typeName().equals(type)).findFirst().orElse(null);
    }

//    default TreeBuildStrategy treeBuildStrategy() {
//        return new TreeBuildStrategy.TypedStrategy() {
//
//            @Override
//            public Graph getSchemaGraph() {
//                return schema().carrier();
//            }
//
//            @Override
//            public Optional<Name> rootType(String label) {
//                return lookup(label).map(Triple::getLabel);
//            }
//
//            @Override
//            public Optional<Triple> lookupType(Name parentType, String field) {
//                return features(parentType).filter(t -> t.getLabel().printRaw().equals(field)).findFirst();
//            }
//
//            @Override
//            public boolean isStringType(Name typeName) {
//                return Sys.this.isStringType(typeName);
//            }
//
//            @Override
//            public boolean isBoolType(Name typeName) {
//                return Sys.this.isBoolType(typeName);
//            }
//
//            @Override
//            public boolean isFloatType(Name typeName) {
//                return Sys.this.isFloatType(typeName);
//            }
//
//            @Override
//            public boolean isIntType(Name typeName) {
//                return Sys.this.isIntType(typeName);
//            }
//
//            @Override
//            public boolean isEnumType(Name typeName) {
//                return Sys.this.isEnumType(typeName);
//            }
//        };
//    }

    default Stream<Name> types() {
        return this.schema().carrier().nodes().filter(n -> !this.isMessageType(n));
    }

    default Stream<Triple> links() {
        return schema().carrier().edges().filter(t -> !this.isAttributeType(t) && !isMessageType(t.getSource()));
    }


    default boolean isSubtypeOf(Triple first, Triple second) {
        return first.equals(second);
    }

    default Stream<Tuple> directSuperTypes() {
        Graph graph = schema().carrier();
        if (graph instanceof InheritanceGraph) {
            InheritanceGraph igraph = (InheritanceGraph) graph;
            return igraph.directInheritances();
        } else {
            return Stream.empty();
        }
    }

    default Stream<Triple> attributeFeatures(Name owner) {
        return schema().carrier().outgoing(owner).filter(Triple::isEddge).filter(this::isAttributeType);
    }


    default Stream<Triple> features(Name nodeName) {
        return schema().carrier().outgoing(nodeName).filter(Triple::isEddge);
    }

    default boolean isEnumType(Name nodeType) {
        return this.schema().diagramsOn(Triple.node(nodeType)).anyMatch(d -> EnumValue.getInstance().diagramIsOfType(d));
    }

    default boolean isBoolType(Name nodeType) {
        return this.schema().diagramsOn(Triple.node(nodeType)).anyMatch(d -> BoolDT.getInstance().diagramIsOfType(d));
    }

    default boolean isFloatType(Name nodeType) {
        return this.schema().diagramsOn(Triple.node(nodeType)).anyMatch(d -> FloatDT.getInstance().diagramIsOfType(d));
    }

    default boolean isIntType(Name nodeType) {
        return this.schema().diagramsOn(Triple.node(nodeType)).anyMatch(d -> IntDT.getInstance().diagramIsOfType(d));
    }

    default boolean isStringType(Name nodeType) {
        return this.schema().diagramsOn(Triple.node(nodeType)).anyMatch(d -> StringDT.getInstance().diagramIsOfType(d));
    }

    default boolean isSimpleTypeNode(Name nodeName) {
        return this.schema().diagramsOn(Triple.node(nodeName)).anyMatch(d -> DataTypePredicate.getInstance().diagramIsOfType(d));
    }

    default boolean isAttributeType(Triple edge) {
        return this.isSimpleTypeNode(edge.getTarget());
    }

    default Optional<Triple> getOppositeIfExists(Triple edge) {
        return this.schema().diagramsOn(edge).filter(d -> Invert.class.isAssignableFrom(d.label().getClass())).findFirst()
                .flatMap(d -> {
                    if (d.binding().map(Universe.CYCLE_FWD.getLabel()).map(n -> n.equals(edge.getLabel())).orElse(false)) {
                        return d.edgeBinding(Universe.CYCLE_BWD);
                    } else {
                        return d.edgeBinding(Universe.CYCLE_FWD);
                    }
                });
    }

    // TODO
    default Pair<Integer, Integer> getTargetMultiplicity(Triple edge) {
        return new Pair<>(0, 1);
//        return this.schema().diagramsOn(edge)
//                .filter(d -> TargetMultiplicity.class.isAssignableFrom(d.label().getClass()))
//                .findFirst()
//                .map(d -> (TargetMultiplicity)d.label())
//                .map(mult -> new Pair<>(mult.getLowerBound(), mult.getUpperBound()))
//                .orElse(new Pair<>(0, -1));
    }

    // TODO
    default Pair<Integer, Integer> getSourceMultiplicity(Triple edge) {
        return new Pair<>(0, 1);
//        return this.schema().diagramsOn(edge)
//                .filter(d -> SourceMultiplicity.class.isAssignableFrom(d.label().getClass()))
//                .findFirst()
//                .map(d -> (SourceMultiplicity)d.label())
//                .map(mult -> new Pair<>(mult.getLowerBound(), mult.getUpperBound()))
//                .orElse(new Pair<>(0, -1));
    }

    default boolean isCollectionValued(Triple edge) {
        return !getTargetMultiplicity(edge).getRight().equals(1);
    }

    default boolean hasTargetMultiplicity(Triple edge, int lowerBound, int upperBound) {
        return this.schema().diagramsOn(edge).anyMatch(d -> TargetMultiplicity.getInstance(lowerBound, upperBound).diagramIsOfType(d));
    }

    default boolean hasSourceMultiplicity(Triple edge, int lowerBound, int upperBound) {
        return this.schema().diagramsOn(edge).anyMatch(d -> SourceMultiplicity.getInstance(lowerBound, upperBound).diagramIsOfType(d));
    }

    default boolean isOrdered(Triple edge) {
        return this.schema().diagramsOn(edge).anyMatch(d -> Ordered.getInstance().diagramIsOfType(d));
    }

    default boolean isUnique(Triple edge) {
        return this.schema().diagramsOn(edge).anyMatch(d -> Unique.getInstance().diagramIsOfType(d));
    }

    default boolean isAbstract(Name typeName) {
        return this.schema().diagramsOn(Triple.node(typeName)).anyMatch(d -> AbstractType.getInstance().diagramIsOfType(d));
    }

    default boolean isSingleton(Name typeName) {
        return this.schema().diagramsOn(Triple.node(typeName)).anyMatch(d -> Singleton.getInstance().diagramIsOfType(d));
    }

    default boolean isMessageType(Name typeName) {
        return this.messages().anyMatch(m -> m.typeName().equals(typeName));
    }

    default Set<Name> enumLiterals(Name enumTypeName) {
        return this.schema().diagramsOn(Triple.node(enumTypeName))
                .filter(d -> EnumValue.class.isAssignableFrom(d.label().getClass()))
                .findFirst()
                .map(d -> {
                    EnumValue e = (EnumValue) d.label();
                    return e.literals();
                }).orElse(new HashSet<>());
    }

    default boolean isAggregation(Triple triple) {
        return !this.isComposition(triple) && this.schema().diagramsOn(triple).anyMatch(d -> Acyclicity.class.isAssignableFrom(d.label().getClass()));
    }

    default boolean isComposition(Triple edge) {
        return this.schema().diagramsOn(edge).anyMatch(d -> Acyclicity.class.isAssignableFrom(d.label().getClass())) &&
                this.schema().diagramsOn(edge).anyMatch(d -> d.label() instanceof SourceMultiplicity);
    }



}
