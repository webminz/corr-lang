package io.corrlang.domain.schemas;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.corrlang.domain.ElemRef;
import io.corrlang.domain.diagrams.*;
import no.hvl.past.attributes.DataTypeDescription;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.elements.Tuple;
import no.hvl.past.graph.operations.Invert;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.names.Identifier;
import no.hvl.past.names.Name;
import no.hvl.past.names.PrintingStrategy;
import no.hvl.past.util.Multiplicity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An abstract description of a system interface.
 * This can be a
 * - database schema,
 * - an XML/JSON schema,
 * - an OpenAPI specification,
 * - a metamodel
 * - etc.
 * <p>
 * Technically, it is a wrapper around a _sketch_, i.e. a type graph with annotations with convenient getter functions.
 */
public class Schema {



    // TODO: the cache loader need to reworked

    public static class NotFoundException extends RuntimeException {

        public NotFoundException(Name name) {
            super("Could not find an element with symbolic name '" +
                    name.print(PrintingStrategy.DETAILED) +
                    "' in the interface description!");
        }

        public NotFoundException(ElemRef ref) {
            super("Could not find an element with access path '" +
                    ref.toString() +
                    "' in the interface description");
        }
    }


    private final Sketch formalisation;

    private final Map<Name, String> customPresentations;


    public Schema(Sketch formalisation, Map<Name, String> customPresentations) {
        this.formalisation = formalisation;
        this.customPresentations = customPresentations;

    }

    public Schema(Sketch formalisation) {
        this.formalisation = formalisation;
        this.customPresentations = new HashMap<>();
    }

    public Sketch sketch() {
        return formalisation;
    }

    // Lookup mechanism and caches
    private CacheLoader<ElemRef, Optional<Name>> elementRefLookup = new CacheLoader<>() {
        @Override
        public Optional<Name> load(ElemRef key) throws Exception {
            if (key.length() == 1) {
                Identifier id = Name.identifier(key.head());
                if (formalisation.carrier().contains(Triple.node(id))) {
                    return Optional.of(id);
                } else {
                    return Optional.empty();
                }
            }
            return lookupWithContext(Name.identifier(key.head()), key.tail());
        }
    };

    private Optional<Name> lookupWithContext(Name context, ElemRef remainingRef) {
        if (remainingRef.length() == 0) {
            return Optional.of(context);
        }
        if (formalisation.carrier().mentions(context)) {
            SchemaElementType schemaElementType = this.typeOf(context);
            switch (schemaElementType) {
                case MSG_GROUP:
                    return lookupWithContext(Name.identifier(remainingRef.head()).prefixWith(context), remainingRef.tail());
                case MSG_TYP:
                case NODE:
                    if (remainingRef.length() == 1) {
                        Optional<Triple> result = getTripleWithContext(context, remainingRef.head());
                        if (result.isPresent() && remainingRef.length() == 1) {
                            return result.map(Triple::getLabel);
                        } else if (result.isPresent()) {
                            Name newContext = result.get().getLabel();
                            return lookupWithContext(newContext, remainingRef.tail());
                        } else {
                            return Optional.empty();
                        }
                    }
                default:
                    return formalisation.carrier().get(remainingRef.asPrefixedName().prefixWith(context)).map(Triple::getLabel);
            }
        } else {
            if (remainingRef.length() == 0) {
                return Optional.empty();
            } else {
                return lookupWithContext(Name.identifier(remainingRef.head()).prefixWith(context), remainingRef.tail());
            }
        }
    }

    // TODO available names (per niveau)

    private Optional<Triple> getTripleWithContext(Name context, String remainingRef) {
        return this.formalisation.carrier()
                .get(context)
                .flatMap(t1 -> formalisation.carrier().outgoing(t1.getLabel())
                        .filter(t2 -> t2.isEddge() && t2.getLabel().printRaw().equals(remainingRef))
                        .findFirst());
    }

    private final LoadingCache<ElemRef, Optional<Name>> refNameCache = CacheBuilder
            .newBuilder()
            .build(elementRefLookup);

    private final Map<Name, DataTypeDescription> baseTypes = new HashMap<>();
    private final Set<Name> messages = new HashSet<>();
    private final Set<Name> messageGroups = new HashSet<>();
    private boolean keyTypesCachedIndexed = false;

    private void doIndexKeyTypesCache() {
        if (!keyTypesCachedIndexed) {
            formalisation.carrier().nodes().forEach(name -> {
                formalisation
                        .diagramsOn(Triple.node(name))
                        .map(Diagram::label)
                        .forEach(formula -> {
                            if (formula instanceof ActionMarker) {
                                messages.add(name);
                            } else if (formula instanceof ActionGroupMarker) {
                                messageGroups.add(name);
                            } else if (formula instanceof StringDT) {
                                baseTypes.put(name, DataTypeDescription.STRING);
                            } else if (formula instanceof FloatDT) {
                                baseTypes.put(name, DataTypeDescription.FLOATING_POINT_NUMBER);
                            } else if (formula instanceof IntDT) {
                                baseTypes.put(name, DataTypeDescription.INTEGRAL_NUMBER);
                            } else if (formula instanceof BoolDT) {
                                baseTypes.put(name, DataTypeDescription.BOOLEAN);
                            } else if (formula instanceof EnumValue) {
                                baseTypes.put(name, DataTypeDescription.ENUMERATED);
                            } else if (formula instanceof DataTypePredicate) {
                                baseTypes.put(name, DataTypeDescription.CUSTOM);
                            }
                        });

            });

            keyTypesCachedIndexed = true;
        }
    }

    public String displayName(Name n) {
        if (this.customPresentations.containsKey(n)) {
            return this.customPresentations.get(n);
        } else {
            return n.print(PrintingStrategy.IGNORE_PREFIX);
        }
    }

    private final CacheLoader<Name, SchemaElementType> typeLookup = new CacheLoader<Name, SchemaElementType>() {
        @Override
        public SchemaElementType load(Name key) throws Exception {
            Optional<Triple> optionalTriple = formalisation.carrier().get(key);
            if (!optionalTriple.isPresent()) {
                // TODO? Exception?
                return null;
            }
            Triple triple = optionalTriple.get();

            if (!keyTypesCachedIndexed) {
                doIndexKeyTypesCache();
            }

            if (triple.isNode()) {
                if (baseTypes.containsKey(triple.getLabel())) {
                    return SchemaElementType.DATA_TYPE;
                } else if (messageGroups.contains(triple.getLabel())) {
                    return SchemaElementType.MSG_GROUP;
                } else if (messages.contains(triple.getLabel())) {
                    return SchemaElementType.MSG_TYP;
                } else {
                    return SchemaElementType.NODE;
                }
            } else {
                if (messageGroups.contains(triple.getSource())) {
                    return SchemaElementType.MSG_CONTMNT;
                } else if (messages.contains(triple.getSource())) {
                    if (formalisation
                            .diagramsOn(triple)
                            .anyMatch(diagram ->
                                    diagram.label() instanceof ActionOutputMarker)) {
                        return SchemaElementType.MSG_OUT;
                    } else {
                        return SchemaElementType.MSG_IN;
                    }
                } else if (baseTypes.containsKey(triple.getTarget())) {
                    return SchemaElementType.PROP;
                } else {
                    return SchemaElementType.LINK;
                }
            }

        }
    };
    private final LoadingCache<Name, SchemaElementType> schemaElementCache = CacheBuilder
            .newBuilder()
            .build(typeLookup);



    // Main accessor methods

    public boolean isMember(ElemRef ref) {
        return refNameCache.getUnchecked(ref).isPresent();
    }

    public boolean isMember(Name name) {
        return formalisation.carrier().mentions(name);
    }

    public Name lookup(ElemRef ref)  {
        return refNameCache.getUnchecked(ref).orElseThrow(() -> new NotFoundException(ref));
    }

    public SchemaElementType typeOf(ElemRef ref)   {
        return typeOf(lookup(ref));
    }

    public SchemaElementType typeOf(Name element) {
        return schemaElementCache.getUnchecked(element);
    }

    public String print(Name element)   {
        if (this.customPresentations.containsKey(element)) {
            return customPresentations.get(element);
        } else {
            return element.print(PrintingStrategy.IGNORE_PREFIX);
        }
    }

    // convenience accessor methods below this point

    public Multiplicity multiplicity(ElemRef ref)   {
        return multiplicity(lookup(ref));
    }

    public Multiplicity multiplicity(Name element)   {
        Optional<Triple> triple = this.formalisation.carrier().get(element);
        if (triple.isEmpty()) {
            throw new NotFoundException(element);
        }
        Optional<TargetMultiplicity> result = this.formalisation
                .diagramsOn(triple.get())
                .filter(diag -> diag.label() instanceof TargetMultiplicity)
                .map(diag -> (TargetMultiplicity) diag.label())
                .findFirst();
        if (result.isPresent()) {
            return result.get().getMultiplicity();
        } else {
            return Multiplicity.of(0, -1);
        }
    }

    public DataTypeDescription dataType(ElemRef ref)  {
        return dataType(lookup(ref));
    }

    /**
     * Precondition: SchemaElement Type must be DATA_TYPE!
     */
    public DataTypeDescription dataType(Name element)  {
        return baseTypes.get(element);
    }


    public boolean isOrdered(ElemRef element)   {
        return isOrdered(lookup(element));
    }

    public boolean isOrdered(Name element)   {
        return sketch().carrier().get(element).map(t -> sketch().diagramsOn(t).anyMatch(diagram -> diagram.label() instanceof Ordered)).orElse(false);
    }

    public boolean isUnique(ElemRef element) throws NotFoundException  {
        return isUnique(lookup(element));
    }

    public boolean isUnique(Name element) throws NotFoundException  {
        return sketch().carrier().get(element).map(t -> sketch().diagramsOn(t).anyMatch(diagram -> diagram.label() instanceof Unique)).orElse(false);
    }

    public boolean isContainment(ElemRef element) throws NotFoundException  {
        return isContainment(lookup(element));
    }

    public boolean isContainment(Name element) throws NotFoundException {
        return sketch().carrier().get(element).map(e -> {
            return sketch().diagramsOn(e).anyMatch(d -> {
                if (d.label() instanceof SourceMultiplicity sm) {
                    return sm.multiplicity().getLowerBound() == 1 && sm.multiplicity().getUpperBound() == 1;
                } else {
                    return false;
                }
            }) && sketch().diagramsOn(e).anyMatch(d -> d.label() instanceof Acyclicity);
        }).orElse(false);
    }

    // containment parent


    public List<Name> featuresOf(ElemRef ref) throws NotFoundException {
        return featuresOf(lookup(ref));
    }

    public List<Name> featuresOf(Name element) throws NotFoundException {
        return formalisation.carrier()
                .outgoing(element)
                .filter(Triple::isEddge)
                .map(Triple::getLabel)
                .collect(Collectors.toList());
    }


    public Stream<Name> types() {
        return this.formalisation.carrier().nodes().filter(node -> !isSimpleTypeNode(node)).filter(node -> !isAction(node) && !isActionGroup(node));
    }

    private boolean isAction(Name node) {
        return formalisation.diagramsOn(Triple.node(node)).anyMatch(diag -> diag.label() instanceof ActionMarker);
    }

    private boolean isActionGroup(Name node) {
        return formalisation.diagramsOn(Triple.node(node)).anyMatch(diag -> diag.label() instanceof ActionGroupMarker);
    }

    public Stream<Triple> links() {
        return this.formalisation.carrier().edges().filter(e -> ! isAttributeType(e)).filter(e -> !isActionArgument(e) && !isActionResult(e) && ! isActionContainment(e));
    }

    private boolean isActionArgument(Triple e) {
        return formalisation.diagramsOn(e).anyMatch(diag -> diag.label() instanceof ActionInputMarker);
    }

    private boolean isActionResult(Triple e) {
        return formalisation.diagramsOn(e).anyMatch(diag -> diag.label() instanceof ActionOutputMarker);
    }

    private boolean isActionContainment(Triple e) {
        return formalisation.diagramsOn(e).anyMatch(diag -> diag.label() instanceof ActionGroupChildMarker);
    }


    public Stream<Tuple> directSuperTypes() {
        Graph graph = this.formalisation.carrier();
        if (graph instanceof InheritanceGraph igraph) {
            return igraph.directInheritances();
        } else {
            return Stream.empty();
        }
    }

    public boolean isSubTypeOf(Name child, Name parent) {
        if (child.equals(parent)) {
            return true;
        }
        Graph graph = this.formalisation.carrier();
        if (graph instanceof InheritanceGraph igraph) {
            boolean directMatch = igraph.directInheritances().anyMatch(t -> t.getDomain().equals(child) && t.getCodomain().equals(parent));
            if (!directMatch) {
                return false; // TODO: transitive closure
            } else {
                return directMatch;
            }
        } else {
            return false;
        }
    }

    // getSubptypes
    // getSuperTypes

    public Stream<Triple> attributeFeatures(Name owner) {
        return this.formalisation.carrier().outgoing(owner).filter(Triple::isEddge).filter(this::isAttributeType);
    }

    public Stream<Triple> features(Name nodeName) {
        return this.formalisation.carrier().outgoing(nodeName).filter(Triple::isEddge);
    }

    public boolean isEnumType(Name nodeType) {
        return this.formalisation.diagramsOn(Triple.node(nodeType)).anyMatch(d -> EnumValue.getInstance().diagramIsOfType(d));
    }

    public boolean isBoolType(Name nodeType) {
        return this.formalisation.diagramsOn(Triple.node(nodeType)).anyMatch(d -> BoolDT.getInstance().diagramIsOfType(d));
    }

    public boolean isFloatType(Name nodeType) {
        return this.formalisation.diagramsOn(Triple.node(nodeType)).anyMatch(d -> FloatDT.getInstance().diagramIsOfType(d));
    }

    public boolean isIntType(Name nodeType) {
        return this.formalisation.diagramsOn(Triple.node(nodeType)).anyMatch(d -> IntDT.getInstance().diagramIsOfType(d));
    }

    public  boolean isStringType(Name nodeType) {
        return this.formalisation.diagramsOn(Triple.node(nodeType)).anyMatch(d -> StringDT.getInstance().diagramIsOfType(d));
    }

    public boolean isSimpleTypeNode(Name nodeName) {
        return this.formalisation.diagramsOn(Triple.node(nodeName)).anyMatch(d -> DataTypePredicate.getInstance().diagramIsOfType(d));
    }

    public boolean isAttributeType(Triple edge) {
        return this.isSimpleTypeNode(edge.getTarget());
    }

    public Optional<Triple> getOppositeIfExists(Triple edge) {
        return this.formalisation.diagramsOn(edge).filter(d -> Invert.class.isAssignableFrom(d.label().getClass())).findFirst()
                .flatMap(d -> {
                    if (d.binding().map(Universe.CYCLE_FWD.getLabel()).map(n -> n.equals(edge.getLabel())).orElse(false)) {
                        return d.edgeBinding(Universe.CYCLE_BWD);
                    } else {
                        return d.edgeBinding(Universe.CYCLE_FWD);
                    }
                });
    }



    public boolean isCollectionValued(Triple edge) {
        return multiplicity(edge.getLabel()).isCollection();
    }


    public boolean isOrdered(Triple edge) {
        return this.formalisation.diagramsOn(edge).anyMatch(d -> Ordered.getInstance().diagramIsOfType(d));
    }

    public boolean isUnique(Triple edge) {
        return this.formalisation.diagramsOn(edge).anyMatch(d -> Unique.getInstance().diagramIsOfType(d));
    }

    public boolean isAbstract(Name typeName) {
        return this.formalisation.diagramsOn(Triple.node(typeName)).anyMatch(d -> AbstractType.getInstance().diagramIsOfType(d));
    }

    public boolean isSingleton(Name typeName) {
        return this.formalisation.diagramsOn(Triple.node(typeName)).anyMatch(d -> Singleton.getInstance().diagramIsOfType(d));
    }


    public Set<Name> enumLiterals(Name enumTypeName) {
        return this.formalisation.diagramsOn(Triple.node(enumTypeName))
                .filter(d -> EnumValue.class.isAssignableFrom(d.label().getClass()))
                .findFirst()
                .map(d -> {
                    EnumValue e = (EnumValue) d.label();
                    return e.literals();
                }).orElse(new HashSet<>());
    }

    public boolean isAggregation(Triple triple) {
        return !this.isComposition(triple) && this.formalisation.diagramsOn(triple).anyMatch(d -> Acyclicity.class.isAssignableFrom(d.label().getClass()));
    }

    public boolean isComposition(Triple edge) {
        return this.formalisation.diagramsOn(edge).anyMatch(d -> Acyclicity.class.isAssignableFrom(d.label().getClass())) &&
                this.formalisation.diagramsOn(edge).anyMatch(d -> d.label() instanceof SourceMultiplicity); // FIXME: should be at least one
    }


    public Stream<Name> messageContainers() {
        return sketch().carrier().nodes().filter(this::isActionGroup);
    }


    public Optional<Name> actionGroupParent(Name actionChild) {
        return sketch().carrier().incoming(actionChild).map(Triple::getSource).findFirst();
    }

    public Stream<Name> actionsInGroup(Name actionContainer) {
        return sketch().carrier().outgoing(actionContainer).filter(Triple::isEddge).map(Triple::getTarget).filter(this::isAction);
    }

    public Stream<Triple> messageArguments(Name messageName) {
        return sketch().carrier().outgoing(messageName).filter(Triple::isEddge).filter(this::isActionArgument).sorted();
    }


    public Stream<Triple> messageOutputs(Name messageName) {
        return sketch().carrier().outgoing(messageName).filter(Triple::isEddge).filter(this::isActionResult).sorted();
    }

    public Stream<Name> actions() {
        return sketch().carrier().nodes().filter(this::isAction);
    }

}
