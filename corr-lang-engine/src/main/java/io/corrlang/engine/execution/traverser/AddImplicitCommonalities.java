package io.corrlang.engine.execution.traverser;

import com.google.common.collect.*;
import io.corrlang.engine.domainmodel.Endpoint;
import io.corrlang.engine.domainmodel.Identification;
import io.corrlang.engine.execution.AbstractExecutor;
import io.corrlang.engine.execution.SemanticException;
import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.domainmodel.CorrSpec;
import io.corrlang.engine.domainmodel.ElementRef;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;
import io.corrlang.domain.ComprSys;
import io.corrlang.domain.MessageType;
import io.corrlang.domain.Sys;
import io.corrlang.plugins.techspace.TechSpace;
import io.corrlang.plugins.techspace.TechSpaceDirective;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class AddImplicitCommonalities extends AbstractTraverser {


    public static final String COMMON_STRING_TYPE_NAME_ARG = "core.datatypes.string.name";
    public static final String COMMON_INTEGER_TYPE_NAME_ARG = "core.datatypes.integer.name";
    public static final String COMMON_FLOAT_TYPE_NAME_ARG = "core.datatypes.floatingpoint.name";
    public static final String COMMON_BOOL_TYPE_NAME_ARG = "core.datatypes.boolean.name";

    private final Multimap<TechSpace, String> involvedTechSpaces = HashMultimap.create();
    private final Map<TechSpace, TechSpaceDirective> directives = new HashMap<>();

    private Identification stringId;
    private Identification intId;
    private Identification floatId;
    private Identification boolId;

    @Autowired
    private PropertyHolder propertyHolder;

    private final List<Identification> otherIds = new ArrayList<>();

    private final Multimap<CorrSpec, Identification> toAdd = LinkedHashMultimap.create();

    private CorrSpec corrSpec;

    public AddImplicitCommonalities() {
        super("AddImplicitCommonalities");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Sets.newHashSet(LinkEndpointsTraverser.class, ParseEndpointSchemaTraverser.class, LinkElementsTraverser.class);
    }


    @Override
    public void handleEndpoint(Endpoint endpoint) throws Throwable {
        TechSpace techSpace = endpoint.getTechSpace().get();
        involvedTechSpaces.put(techSpace, endpoint.getName());
        if (!directives.containsKey(techSpace)) {
            directives.put(techSpace, endpoint.getAdaptor().get().directives());
        }
    }

    // TODO this should happen in another traverse ...
    @Override
    public void handle(Identification identification) throws Throwable {
        super.handle(identification);
        // TODO check if the identification already has defined identifications on the result types
        ElementRef elementRef = identification.getRelates().get(0);
        Sys s = elementRef.getEndpoint().getSystem().get();
        // identification of messages which have exactly one result type are implicitly added
        if (elementRef.getElement().get().isNode()
                && s.isMessageType(elementRef.getElement().get().getLabel())) {
            MessageType messageType = s.getMessageType(elementRef.getElement().get().getLabel());
            if (messageType.outputs().size() == 1) {
                Identification id = new Identification();
                id.setName(Name.identifier(identification.getName()).asResult().printRaw());
                for (ElementRef rel : identification.getRelates()) {
                    if (!rel.getElement().get().isNode() && !s.isMessageType(rel.getElement().get().getLabel())) {
                        throw new SemanticException("The element '" + rel.getName() + "' in identification '" + identification.getName() + "' does not refer to a message type!");
                    }
                    MessageType msg = rel.getEndpoint().getSystem().get().getMessageType(rel.getElement().get().getLabel());
                    if (msg.outputs().size() != 1) {
                        throw new SemanticException("The element '" + rel.getName() + "' in identification '" + identification.getName() + "' has more than one return parameter, thus you have to specify the relation of the return parameters manually");

                    }
                    List<String> pathExpression = new ArrayList<>(rel.getPathExpression());
                    pathExpression.add("result");
                    ElementRef newElementRef = new ElementRef(pathExpression);
                    Triple triple = msg.outputs().get(0).asEdge();
                    newElementRef.setElement(triple);
                    newElementRef.setEndpoint(rel.getEndpoint());
                    id.getRelates().add(newElementRef);
                }
                toAdd.put(corrSpec, id);
            }
        }
    }

    @Override
    public void handle(CorrSpec corrSpec) throws Throwable {
        this.corrSpec = corrSpec;
        this.otherIds.clear();

        createBoolTypeImplicit(corrSpec).ifPresent(id -> toAdd.put(corrSpec, id));
        createStringTypeImplicit(corrSpec).ifPresent(id -> toAdd.put(corrSpec, id));
        createIntTypeImplicit(corrSpec).ifPresent(id -> toAdd.put(corrSpec, id));
        createFloatTypeImplicit(corrSpec).ifPresent(id -> toAdd.put(corrSpec, id));
        createOtherBaseTypeImplicits(corrSpec);

    }




    static void addElementRef(Identification to, Endpoint ep, String epName, Name element) {
        ElementRef elementRef = new ElementRef(epName, element.printRaw());
        elementRef.setElement(ep.getSystem().get().schema().carrier().get(element).get());
        elementRef.setEndpoint(ep);
        to.addRelatedElement(elementRef);
    }

    private Optional<Identification> createStringTypeImplicit(CorrSpec corrSpec) {
        Name name;
        if (propertyHolder.getProperty(COMMON_STRING_TYPE_NAME_ARG) != null) {
            name = Name.identifier(propertyHolder.getProperty(COMMON_STRING_TYPE_NAME_ARG));
        } else {
            name = ComprSys.GLOBAL_STRING_NAME;
        }
        return createBaseTypeImplicits(corrSpec, name, TechSpaceDirective::stringDataType);
    }

    @NotNull
    private Optional<Identification> createBaseTypeImplicits(CorrSpec corrSpec, Name name,
                                                             Function<TechSpaceDirective, Stream<? extends TechSpaceDirective.BaseTypeDescription>> baseTypeDirectiveChoice) {
        Map<TechSpace, Name> defaultNames = new HashMap<>();
        Multimap<TechSpace, Name> otherNames = MultimapBuilder.hashKeys().linkedHashSetValues().build();

        for (TechSpace techSpace : involvedTechSpaces.keySet()) {
            baseTypeDirectiveChoice.apply(directives.get(techSpace))
                    .forEach(stringT -> {
                        if (stringT.isDefault()) {
                            defaultNames.put(techSpace, stringT.typeName());
                        } else {
                            otherNames.put(techSpace, stringT.typeName());
                        }
                    });
        }

        Identification defaultIdentification = new Identification();
        defaultIdentification.setName(name.printRaw());

        handleBaseTypeIdentificationPerTechSpace(corrSpec, defaultNames, otherNames, defaultIdentification);

        if (defaultIdentification.getRelates().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(defaultIdentification);
        }
    }

    private void handleBaseTypeIdentificationPerTechSpace(CorrSpec corrSpec, Map<TechSpace, Name> defaultNames, Multimap<TechSpace, Name> otherNames, Identification defaultIdentification) {
        for (TechSpace ts : involvedTechSpaces.keySet()) {
            Map<Name, Identification> oIds = new LinkedHashMap<>();
            for (Name oId : otherNames.get(ts)) {
                Identification i = new Identification();
                i.setName(oId.printRaw());
                oIds.put(oId, i);
            }
            for (String endpointName : involvedTechSpaces.get(ts)) {
                Endpoint endpoint = corrSpec.getEndpointRefs().get(endpointName);
                if (defaultNames.containsKey(ts) && endpoint.getSystem().get().schema().carrier().mentions(defaultNames.get(ts))) {
                    addElementRef(defaultIdentification, endpoint, endpointName, defaultNames.get(ts));
                }
                for (Name on : otherNames.get(ts)) {
                    if (endpoint.getSystem().get().schema().carrier().mentions(on)) {
                        addElementRef(oIds.get(on), endpoint, endpointName, on);
                    }
                }
            }
            for (Name oId : oIds.keySet()) {
                if (!oIds.get(oId).getRelates().isEmpty()) {
                    toAdd.put(corrSpec, oIds.get(oId));
                }
            }
        }
    }

    private Optional<Identification> createIntTypeImplicit(CorrSpec corrSpec) {
        Name name;
        if (propertyHolder.getProperty(COMMON_INTEGER_TYPE_NAME_ARG) != null) {
            name = Name.identifier(propertyHolder.getProperty(COMMON_INTEGER_TYPE_NAME_ARG));
        } else {
            name = ComprSys.GLOBAL_INT_NAME;
        }
        return createBaseTypeImplicits(corrSpec, name, TechSpaceDirective::integerDataType);
    }


    private Optional<Identification> createFloatTypeImplicit(CorrSpec corrSpec) {
        Name name;
        if (propertyHolder.getProperty(COMMON_FLOAT_TYPE_NAME_ARG) != null) {
            name = Name.identifier(propertyHolder.getProperty(COMMON_FLOAT_TYPE_NAME_ARG));
        } else {
            name = ComprSys.GLOBAL_FLOAT_NAME;
        }
        return createBaseTypeImplicits(corrSpec, name, TechSpaceDirective::floatingPointDataType);
    }


    private Optional<Identification> createBoolTypeImplicit(CorrSpec corrSpec) {
        Name name;
        if (propertyHolder.getProperty(COMMON_BOOL_TYPE_NAME_ARG) != null) {
            name = Name.identifier(propertyHolder.getProperty(COMMON_BOOL_TYPE_NAME_ARG));
        } else {
            name = ComprSys.GLOBAL_BOOL_NAME;
        }
        return createBaseTypeImplicits(corrSpec, name, TechSpaceDirective::boolDataType);
    }

    private void createOtherBaseTypeImplicits(CorrSpec corrSpec) {
        Map<TechSpace, Name> defaultNames = new HashMap<>();
        Multimap<TechSpace, Name> otherNames = MultimapBuilder.hashKeys().linkedHashSetValues().build();

        for (TechSpace techSpace : involvedTechSpaces.keySet()) {
            directives.get(techSpace).otherDataTypes()
                    .forEach(dt -> {
                        otherNames.put(techSpace, dt.typeName());
                    });
        }

        Identification defaultIdentification = new Identification();
        defaultIdentification.setName("unused");
        handleBaseTypeIdentificationPerTechSpace(corrSpec, defaultNames, otherNames, defaultIdentification);
    }

    @Override
    public void postBlock(SyntacticalResult domainModel, String... args) throws Throwable {
        for (CorrSpec c : toAdd.keySet()) {
            for (Identification i : toAdd.get(c)) {
                getLogger().debug("Adding implicit '" + i.toString());
                c.getCommonalities().add(i);
            }
        }
    }
}
