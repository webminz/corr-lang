package io.corrlang.domain;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.corrlang.domain.diagrams.ActionGroupMarker;
import io.corrlang.domain.diagrams.ActionMarker;
import io.corrlang.domain.diagrams.ActionOutputMarker;
import no.hvl.past.attributes.DataTypeDescription;
import no.hvl.past.graph.Diagram;
import no.hvl.past.graph.Sketch;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.names.Identifier;
import no.hvl.past.names.Name;
import no.hvl.past.names.PrintingStrategy;
import no.hvl.past.util.Multiplicity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An abstract description of a system interface.
 * This can be a database schema, an XML schema, an OpenAPI specification, a metamodel
 * and many more...
 *
 */
public class IntfcDesc {

    public static class NotFoundException extends Exception {

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


    public IntfcDesc(Sketch formalisation, Map<Name, String> customPresentations) {
        this.formalisation = formalisation;
        this.customPresentations = customPresentations;
    }

    public IntfcDesc(Sketch formalisation) {
        this.formalisation = formalisation;
        this.customPresentations = new HashMap<>();
    }

    public Sketch getFormalRepresentation() {
        return formalisation;
    }

    // Lookup mechanism and caches
    private CacheLoader<ElemRef, Optional<Name>> elementRefLookup = new CacheLoader<ElemRef, Optional<Name>>() {
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
        if (formalisation.carrier().mentions(context)) {
            if (remainingRef.length() == 0) {
               return formalisation.carrier().get(context).map(Triple::getLabel);
            } else {
                return lookupWithContext(Name.identifier(remainingRef.head()).prefixWith(context), remainingRef.tail());
            }
        } else {
            if (remainingRef.length() == 0) {
                return Optional.empty();
            } else {
                return lookupWithContext(Name.identifier(remainingRef.head()).prefixWith(context), remainingRef.tail());
            }
        }
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

    private final CacheLoader<Name, SchemaElement> typeLookup = new CacheLoader<Name, SchemaElement>() {
        @Override
        public SchemaElement load(Name key) throws Exception {
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
                    return SchemaElement.DATA_TYPE;
                } else if (messageGroups.contains(triple.getLabel())) {
                    return SchemaElement.MSG_GROUP;
                } else if (messages.contains(triple.getLabel())) {
                    return SchemaElement.MSG_TYP;
                } else {
                    return SchemaElement.NODE;
                }
            } else {
                if (messageGroups.contains(triple.getSource())) {
                    return SchemaElement.MSG_CONTMNT;
                } else if (messages.contains(triple.getSource())) {
                    if (formalisation
                            .diagramsOn(triple)
                            .anyMatch(diagram ->
                                    diagram.label() instanceof ActionOutputMarker)) {
                        return SchemaElement.MSG_OUT;
                    } else {
                        return SchemaElement.MSG_IN;
                    }
                } else if (baseTypes.containsKey(triple.getTarget())) {
                    return SchemaElement.PROP;
                } else {
                    return SchemaElement.LINK;
                }
            }

        }
    };
    private final LoadingCache<Name, SchemaElement> schemaElementCache = CacheBuilder
            .newBuilder()
            .build(typeLookup);

    // TODO more caches




    //



    // Main accessor methods

    public boolean isMember(ElemRef ref) {
        return refNameCache.getUnchecked(ref).isPresent();
    }

    public boolean isMember(Name name) {
        return formalisation.carrier().mentions(name);
    }

    public Name lookup(ElemRef ref) throws NotFoundException {
        return refNameCache.getUnchecked(ref).orElseThrow(() -> new NotFoundException(ref));
    }

    public SchemaElement typeOf(ElemRef ref) throws NotFoundException  {
        return typeOf(lookup(ref));
    }

    public SchemaElement typeOf(Name element) {
        return schemaElementCache.getUnchecked(element);
    }

    public String print(Name element) throws NotFoundException  {
        return null; // TODO
    }

    // convenience accessor methods below this point

    public Multiplicity multiplicity(ElemRef ref) throws NotFoundException  {
        return multiplicity(lookup(ref));
    }

    public Multiplicity multiplicity(Name element) throws NotFoundException  {
        return null; // TODO
    }

    public DataTypeDescription dataType(ElemRef ref) throws NotFoundException {
        return dataType(lookup(ref));
    }

    /**
     * Precondition: SchemaElement Type must be DATA_TYPE!
     */
    public DataTypeDescription dataType(Name element)  {
        return baseTypes.get(element);
    }


    public boolean isOrdered(ElemRef element) throws NotFoundException  {
        return isOrdered(lookup(element));
    }

    public boolean isOrdered(Name element) throws NotFoundException  {
        return false; // TODO
    }

    public boolean isUnique(ElemRef element) throws NotFoundException  {
        return isUnique(lookup(element));
    }

    public boolean isUnique(Name element) throws NotFoundException  {
        return false; // TODO
    }

    public boolean isContainment(ElemRef element) throws NotFoundException  {
        return isContainment(lookup(element));
    }

    public boolean isContainment(Name element) throws NotFoundException {
        return false; // TODO
    }

    // containment parent


    public List<Name> featuresOf(ElemRef ref) throws NotFoundException {
        return featuresOf(lookup(ref));
    }

    public List<Name> featuresOf(Name element) throws NotFoundException {
        return formalisation.carrier()
                .outgoing(element)
                .map(Triple::getLabel)
                .collect(Collectors.toList());
    }

    // TODO
    // available names (per niveau)

    // isSubTypeOf
    // getSubptypes
    // getSuperTypes

    // getActionGroupChildren
    // getActionGroupParent
    // getActionInputParameters
    // getActionOutputParameter


}
