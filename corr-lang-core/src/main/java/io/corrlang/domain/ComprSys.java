package io.corrlang.domain;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import io.corrlang.domain.keys.Key;
import no.hvl.past.names.Name;
import no.hvl.past.names.PrintingStrategy;
import no.hvl.past.util.Holder;
import no.hvl.past.util.Pair;
import no.hvl.past.util.PartitionAlgorithm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComprSys implements Sys {

    // TODO move them a different place (e.g. the AddImplicitIdentities) such their names are configurable.
    public static final Name GLOBAL_STRING_NAME = Name.identifier("String");
    public static final Name GLOBAL_INT_NAME = Name.identifier("Integer");
    public static final Name GLOBAL_FLOAT_NAME = Name.identifier("Float");
    public static final Name GLOBAL_BOOL_NAME = Name.identifier("Bool");


    public static class ConstructionException extends RuntimeException {

        private final Name comprSysName;
        private static final String PREFIX_1 = "Could not create comprehensive io.corrlang.systems with name '";
        private static final String PREFIX_2 = "' because ";

        public ConstructionException(String message, Name comprSysName) {
            super(PREFIX_1 + comprSysName.print(PrintingStrategy.DETAILED) + PREFIX_2 + message);
            this.comprSysName = comprSysName;
        }

        public Name getComprSysName() {
            return comprSysName;
        }
    }

    public static class Builder {

        private final Universe universe;
        private final List<Sys> localSystems;
        private final Name name;
        private final Map<Name, Sys> sysMap = new HashMap<>();
        private final Map<Sys, GraphBuilders> projectionBuilders = new LinkedHashMap<>();
        private final GraphBuilders commonalitiesBuilder;
        private final Set<Name> identities = new HashSet<>();
        private final Set<Name> syncs = new HashSet<>();
        private final Set<Key> keys = new HashSet<>();
        private final Map<Sys, GraphMorphism> embeddings = new LinkedHashMap<>();
        private final Set<ConsistencyRule> rules = new HashSet<>();
        private final Map<Name, String> namingMap = new HashMap<>();
        private final Map<Name, MessageType> messages = new LinkedHashMap<>();

        private String url = "http://localhost";
        private Star star;
        private Sketch comprehensiveSchema;


        public Builder(Name name, Universe universe) {
            this.name = name;
            this.localSystems = new ArrayList<>();
            this.universe = universe;
            this.commonalitiesBuilder = new GraphBuilders(universe, false, true);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }


        public Builder addSystem(Sys sys) {
            this.localSystems.add(sys);
            this.sysMap.put(Name.identifier(sys.url()), sys);
            this.projectionBuilders.put(sys, new GraphBuilders(universe, false, false));
            return this;
        }

        public Builder renderedName(Name name, String raw) {
            this.namingMap.put(name, raw);
            return this;
        }

        private Sys sys(QualifiedName qualifiedName) {
            return sysMap.get(qualifiedName.getSystem());
        }


        public Builder nodeCommonality(Name commonalityName, QualifiedName... related) {
            commonalitiesBuilder.node(commonalityName);
            for (QualifiedName rel : related) {
                this.projectionBuilders.get(sys(rel)).map(commonalityName, rel.getElement());
            }
            return this;
        }

        public Builder nodeCommonality(Name commonalityName, List<QualifiedName> related) {
            commonalitiesBuilder.node(commonalityName);
            for (QualifiedName rel : related) {
                this.projectionBuilders.get(sys(rel)).map(commonalityName, rel.getElement());
            }
            return this;
        }


        public Builder edgeCommonality(Name sourceCommonality, Name label, Name targetCommonality, QualifiedName... related) {
            commonalitiesBuilder.edge(sourceCommonality, label, targetCommonality);
            for (QualifiedName rel : related) {
                this.projectionBuilders.get(sys(rel)).map(label, rel.getElement());
            }
            return this;
        }

        public Builder edgeCommonality(Name sourceCommonality, Name label, Name targetCommonality, List<QualifiedName> related) {
            commonalitiesBuilder.edge(sourceCommonality, label, targetCommonality);
            for (QualifiedName rel : related) {
                this.projectionBuilders.get(sys(rel)).map(label, rel.getElement());
            }
            return this;
        }


        public Builder identification(Name commonalityName) {
            this.identities.add(commonalityName);
            return this;
        }

        public Builder synchronisation(Name commonalityName) {
            this.syncs.add(commonalityName);
            return this;
        }


        /**
         * Provides a global view of the complete construction in terms of a single artifact (= a sketch).
         * The latter can be used to defined further inter-model constraints.
         */
        void comprehensiveSystemConstruction() throws GraphError {
            // throwing it all together -> Disjoint union
            List<Graph> base = new ArrayList<>();
            base.add(star.apex().carrier().rename(name));
            star.components().map(s -> s.carrier().rename(s.getName())).forEach(base::add);
            GraphUnion union = new GraphUnion(base, Name.anonymousIdentifier());


            //  linguistic extension for all non-identity commonalities
            Set<Triple> linguisticExtension = new HashSet<>();

            // Projections on nodes become edges
            star.apex().carrier()
                    .nodes()
                    .filter(n -> !identities.contains(n))
                    .forEach(n -> {
                        for (int i = 1; i <= star.size(); i++) {
                            GraphMorphism projection = star.projection(i).get();
                            if (projection.definedAt(n)) {
                                linguisticExtension.add(Triple.edge(
                                        n.prefixWith(name),
                                        projection.getName().appliedTo(n),
                                        projection.map(n).get().prefixWith(star.component(i).get().getName())
                                ));
                            }
                        }
                    });
            // TODO introduce HyperEdges to model 2-edges between edges ???

            Superobject superobject = new Superobject(Name.anonymousIdentifier(), union, Name.anonymousIdentifier()) {
                @Override
                protected Stream<Triple> inserts() {
                    return linguisticExtension.stream();
                }
            };

            // Epimorphism by partitioning along identities (basically colimit)
            PartitionAlgorithm<Name> partition = new PartitionAlgorithm<>(superobject.codomain().elements().map(Triple::getLabel).collect(Collectors.toSet()));
            star.apex().carrier().elements()
                    .filter(t -> identities.contains(t.getLabel()))
                    .forEach(t -> {
                        for (int i = 1; i <= star.size(); i++) {
                            GraphMorphism projection = star.projection(i).get();
                            if (projection.definedAt(t.getLabel())) {
                                partition.relate(
                                        t.getLabel().prefixWith(name),
                                        projection.map(t.getLabel()).get().prefixWith(star.component(i).get().getName()));
                            }
                        }
                    });

            EpicMorphism congruence = EpicMorphism.fromPartition(
                    Name.anonymousIdentifier(),
                    Name.anonymousIdentifier(),
                    superobject.codomain(),
                    partition,
                    ns -> ns.elements().filter(n -> n.hasPrefix(name)).findFirst().orElse(ns.toName())
            );


            GraphBuilders comprehensiveSchemaBuilder = new GraphBuilders(universe, true, false);


            // Adding all elements
            congruence.codomain().elements().forEach(t -> {
                if (t.isNode()) {
                    comprehensiveSchemaBuilder.node(t.getLabel());
                } else {
                    comprehensiveSchemaBuilder.edge(t.getSource(), t.getLabel(), t.getTarget());
                }
            });


            // Preserving inheritances
            for (Sys s : localSystems) {
                GraphMorphism composedEmbedding = union.inclusionOf(s.schema().getName()).get().compose(superobject).compose(congruence);
                this.embeddings.put(s, composedEmbedding);
                if (s.schema().carrier() instanceof InheritanceGraph) {
                    InheritanceGraph igraph = (InheritanceGraph) s.schema().carrier();
                    igraph.directInheritances().forEach(tuple -> {
                        composedEmbedding.map(tuple.getDomain())
                                .ifPresent(d -> composedEmbedding.map(tuple.getCodomain())
                                        .ifPresent(c -> comprehensiveSchemaBuilder.map(d,c)));
                    });
                }
            }

            comprehensiveSchemaBuilder.graph(name.absolute());

            Set<Name> diagramsWithCoordination = new HashSet<>();
            Multimap<Name, Pair<MessageType, GraphMorphism>> mergedMessages = HashMultimap.create();


            // Moving all diagrams (by recreation) up and translating message types
            this.star.apex().diagrams().forEach(diagram -> {
                GraphMorphism compose = union.inclusionOf(this.name).get().compose(superobject).compose(congruence);
                moveDiagram(comprehensiveSchemaBuilder, diagramsWithCoordination, diagram, compose);
            });

            for (Sys s : this.localSystems) {

                s.messages().forEach(msg -> {
                    // Identifications of messages creates global messages
                    Name translatedMsgName = embeddings.get(s).map(msg.typeName()).get();
                    if (identities.contains(translatedMsgName.unprefixTop())) {
                        mergedMessages.put(translatedMsgName, new Pair<>(msg, embeddings.get(s)));
                    } else {
                        // Non merged messages are just renamed
                        messages.put(translatedMsgName, msg.substitute(embeddings.get(s)));
                    }
                });

                // moves diagram along the injection morphism
                s.schema().diagrams().forEach(diagram -> {
                    moveDiagram(comprehensiveSchemaBuilder, diagramsWithCoordination, diagram, this.embeddings.get(s));
                });
            }

            // Building global messages
            for (Name k : mergedMessages.keySet()) {
                Set<Name> alreadySeen = new HashSet<>();
                List<MessageArgument> ins = new ArrayList<>();
                List<MessageArgument> outs = new ArrayList<>();

                Holder<Name> msgContainerName = new Holder<>();
                for (Pair<MessageType, GraphMorphism> p : mergedMessages.get(k)) {
                    if (p.getLeft().getGroup().isPresent()) {
                        Name containerName = p.getRight().map(p.getLeft().getGroup().get().getTypeName()).get();
                        if (!msgContainerName.hasValue()) {
                            msgContainerName.set(containerName);
                        } else if (!msgContainerName.unsafeGet().equals(containerName)) {
                            throw new ConstructionException("Message containers '" +
                                    msgContainerName.unsafeGet().print(PrintingStrategy.DETAILED) +
                                    "' and '" + p.getLeft().getGroup().get().getTypeName().print(PrintingStrategy.DETAILED) +
                                    "' have not been identified!", this.name);
                        }
                    }
                }

                MessageType messageType = new MessageType(k, ins, outs, msgContainerName.safeGet().map(MessageContainer::new).orElse(null), mergedMessages.get(k).stream().anyMatch(p -> p.getLeft().hasSideEffects()));

                for (Pair<MessageType, GraphMorphism> p : mergedMessages.get(k)) {
                    for (MessageArgument in : p.getLeft().inputs()) {
                        argumentTranslation(alreadySeen, ins, messageType, p, in,false);
                    }
                    for (MessageArgument out : p.getLeft().outputs()) {
                        argumentTranslation(alreadySeen, outs, messageType, p, out,true);
                    }
                }
                messages.put(k, messageType);
            }


            this.comprehensiveSchema = comprehensiveSchemaBuilder.sketch(name).getResult(Sketch.class);

            // TODO recreate morphisms using a builder to get inheritance morphisms ?!

            for (Name sync : this.syncs) {
                Set<QualifiedName> collect = comprehensiveSchema.carrier().outgoing(sync.prefixWith(name))
                        .filter(Triple::isEddge)
                        .map(Triple::getTarget)
                        .map(n -> {
                            Name sys = n.firstPart();
                            Name el = n.secondPart();
                            // TODO this should be made nicer!
                            return new QualifiedName(
                                    Name.identifier(localSystems.stream().filter(s -> s.schema().getName().equals(sys)).findFirst().get().url()),
                                    el);
                        })
                        .collect(Collectors.toSet());
                this.rules.add(new SynchronizationRule(sync, collect, new ArrayList<>()));
            }
        }

        private void argumentTranslation(
                Set<Name> alreadySeen,
                List<MessageArgument> ins,
                MessageType messageType,
                Pair<MessageType, GraphMorphism> p,
                MessageArgument in,
                boolean isOutput) {
            Name translatedLabel = p.getRight().map(in.asEdge().getLabel()).get();
            if (identities.contains(translatedLabel)) {
                if (!alreadySeen.contains(translatedLabel)) {
                    alreadySeen.add(translatedLabel);
                    int idx = ins.size();
                    ins.add(new MessageArgument(messageType, p.getRight().apply(in.asEdge()).get(), idx, isOutput));
                }
            } else {
                int idx = ins.size();
                ins.add(new MessageArgument(messageType, p.getRight().apply(in.asEdge()).get(), idx, isOutput));
            }
        }

        private void moveDiagram(
                GraphBuilders comprehensiveSchemaBuilder,
                Set<Name> diagramsWithCoordination,
                Diagram diagram,
                GraphMorphism compose) {
            // TODO dangerous ?! re-evaluate
            if (diagram.label() instanceof GraphTheory) {
                comprehensiveSchemaBuilder.startDiagram((GraphTheory) diagram.label());
                diagram.binding().mappings().forEach(t -> {
                    Optional<Name> target = compose.map(t.getCodomain());
                    if (target.isPresent()) {
                        if (identities.contains(target.get().unprefixAll())) {
                            diagramsWithCoordination.add(diagram.getName().prefixWith(name));
                        }
                        comprehensiveSchemaBuilder.map(t.getDomain(), target.get());
                    }
                });
                comprehensiveSchemaBuilder.endDiagram(diagram.getName().prefixWith(name));
            }
        }


        private void mkStar() throws GraphError {
            Sketch apex = commonalitiesBuilder
                    .graph(name.index(0).absolute())
                    // TODO accommodate for potential inheritance and diagrams
                    .sketch(name.index(0))
                    .getResult(Sketch.class);
            List<GraphMorphism> projections = new ArrayList<>();
            for (Sys s : this.localSystems) {
                GraphBuilders projectionBuilder = this.projectionBuilders.get(s);
                projectionBuilder.domain(apex.carrier());
                projectionBuilder.codomain(s.schema().carrier());
                projectionBuilder.morphism(name.projectionOn(s.schema().getName()));
                projections.add(projectionBuilder.getResult(GraphMorphism.class));
            }

            this.star = new StarImpl(name, apex, localSystems.stream().map(Sys::schema).collect(Collectors.toList()), projections);
        }


        /**
         * Instead of building the relations manually, calling node/edge commonality
         * you could directly provide the complete alignment as a {@link Star}.
         */
        public Builder star(Star star) {
            this.star = star;
            return this;
        }


        public Builder key(Key key) {
            this.keys.add(key);
            return this;
        }


        public ComprSys build() throws GraphError {
            if (this.star == null) {
                mkStar();
            }
            comprehensiveSystemConstruction();


            return new ComprSys(
                    url,
                    comprehensiveSchema,
                    namingMap,
                    messages,
                    embeddings,
                    identities,
                    keys,
                    rules);
        }
    }

    private final String url;
    private final Sketch comprehensiveSchema;
    private final Map<Name, String> namingMap;
    private final Map<Name, MessageType> messages;
    private final Map<Sys, GraphMorphism> systemsWithEmbeddings;
    private final Set<Name> mergedElements;
    private final Set<Key> keys;
    private final Set<ConsistencyRule> rules;


    public ComprSys(
            String url,
            Sketch comprehensiveSchema,
            Map<Name, String> namingMap,
            Map<Name, MessageType> messages,
            Map<Sys, GraphMorphism> systemsWithEmbeddings,
            Set<Name> mergedElements,
            Set<Key> keys,
            Set<ConsistencyRule> rules) {
        this.url = url;
        this.comprehensiveSchema = comprehensiveSchema;
        this.namingMap = namingMap;
        this.messages = messages;
        this.systemsWithEmbeddings = systemsWithEmbeddings;
        this.mergedElements = mergedElements;
        this.keys = keys;
        this.rules = rules;
    }


    public Stream<Key> keys() {
        return keys.stream();
    }

    public Stream<ConsistencyRule> rules() {
        return rules.stream();
    }

    public Optional<Name> projection(Sys origin, Name comm) {
        return comprehensiveSchema.carrier()
                .outgoing(comm.prefixWith(comprehensiveSchema.getName()))
                .filter(t -> t.getTarget().firstPart().equals(origin.schema().getName()))
                .findFirst()
                .map(Triple::getTarget)
                .map(Name::secondPart);
    }

    public Stream<Key> relationKeys() {
        return keys.stream().filter(key -> !mergedElements.contains(key.targetType()));
    }

    public GraphMorphism embeddingOf(Sys sys) {
        return this.systemsWithEmbeddings.get(sys);
    }

    public Stream<Sys> components() {
        return systemsWithEmbeddings.keySet().stream();
    }

    public boolean isMerged(Name elementName) {
        return mergedElements.contains(elementName);
    }

    public Stream<Name> localNames(Sys component, Name elementName) {
        GraphMorphism morphism = this.systemsWithEmbeddings.get(component);
        return comprehensiveSchema.carrier().get(elementName).map(t -> morphism.allInstances(t).map(Triple::getLabel)).orElse(Stream.empty());
    }

    @Override
    public String displayName(Name name) {
        if (this.namingMap.containsKey(name)) {
            return this.namingMap.get(name);
        }
        if (name.hasPrefix(this.comprehensiveSchema.getName())) {
            return name.printRaw();
        }
        for (Sys s : this.systemsWithEmbeddings.keySet()) {
            if (name.hasPrefix(s.schema().getName())) {
                return s.displayName(name.unprefixTop());
            }
        }
        // Should not happen
        return name.printRaw();
    }

    @Override
    public Optional<Triple> lookup(String... path) {
        if (path.length == 0) {
            return Optional.empty();
        }
        Optional<Sys> isSystemPrefix = systemsWithEmbeddings.keySet().stream().filter(sys -> sys.schema().getName().equals(Name.identifier(path[0]))).findFirst();
        if (isSystemPrefix.isPresent()) {
            String[] lookup = new String[path.length - 1];
            for (int i = 1; i < path.length; i++) {
                lookup[i - 1] = path[i];
            }
            return isSystemPrefix.get().lookup(lookup);
        }
        Name prefixedName = Name.identifier(path[path.length - 1]);
        if (path.length > 1) {
            for (int i = path.length - 2; i >= 0; i--) {
                prefixedName = prefixedName.prefixWith(Name.identifier(path[i]));
            }
        }
        return comprehensiveSchema.carrier().get(prefixedName);
    }

    @Override
    public Sketch schema() {
        return comprehensiveSchema;
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
