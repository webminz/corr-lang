package no.hvl.past.corrlang.execution.traverser;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.domainmodel.ElementRef;
import no.hvl.past.corrlang.domainmodel.Endpoint;
import no.hvl.past.corrlang.domainmodel.Identification;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.names.Name;
import no.hvl.past.names.PrintingStrategy;
import no.hvl.past.systems.ComprSys;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceDirective;

import java.util.*;

public class AddImplicitCommonalities extends AbstractTraverser {

    private Multimap<TechSpace, String> involvedTechSpaces = HashMultimap.create();
    private Map<TechSpace, TechSpaceDirective> directives = new HashMap<>();

    private Identification stringId;
    private Identification intId;
    private Identification floatId;
    private Identification booldId;
    private List<Identification> ohterIds = new ArrayList<>();

    public AddImplicitCommonalities() {
        super("AddImplicitCommonalities");
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Sets.newHashSet(LinkEndpointsTraverser.class, ParseEndpointSchemaTraverser.class);
    }


    @Override
    public void handleEndpoint(Endpoint endpoint) throws Throwable {
        TechSpace techSpace = endpoint.getTechSpace().get();
        involvedTechSpaces.put(techSpace, endpoint.getName());
        if (!directives.containsKey(techSpace)) {
            directives.put(techSpace, endpoint.getAdaptor().get().directives());
        }
    }


    @Override
    public void handle(CorrSpec corrSpec) throws Throwable {
        createBoolTypeImplicit(corrSpec);
        createStringTypeImplicit(corrSpec);
        createIntTypeImplicit(corrSpec);
        createFloatTypeImplicit(corrSpec);

        for (TechSpace ts : involvedTechSpaces.keySet()) {
            TechSpaceDirective directive = directives.get(ts);
            directive.implicitTypeIdentities().forEach(implicit -> {
                Identification toAdd = null;
                for (String ep : involvedTechSpaces.get(ts)) {
                    Endpoint endpoint = corrSpec.getEndpointRefs().get(ep);
                    if (endpoint.getSystem().get().schema().carrier().mentions(implicit)) {
                        if (toAdd == null) {
                            toAdd = new Identification();
                            toAdd.setName(implicit.print(PrintingStrategy.IGNORE_PREFIX));
                        }
                        ElementRef ref = new ElementRef(ep, implicit.print(PrintingStrategy.IGNORE_PREFIX));
                        ref.setElement(endpoint.getSystem().get().schema().carrier().get(implicit).get());
                        toAdd.addRelatedElement(ref);
                        ref.setEndpoint(endpoint);
                    }
                }
                if (toAdd != null) {
                    ohterIds.add(toAdd);
                }
            });
        }

        if (booldId != null) {
            corrSpec.addCommonality(booldId);
        }
        if (stringId != null) {
            corrSpec.addCommonality(stringId);
        }
        if (intId != null) {
            corrSpec.addCommonality(intId);
        }
        if (floatId != null) {
            corrSpec.addCommonality(floatId);
        }
        for (Identification other : ohterIds) {
            corrSpec.addCommonality(other);
        }
    }

    private void createStringTypeImplicit(CorrSpec corrSpec) {
        for (TechSpace ts : involvedTechSpaces.keySet()) {
            TechSpaceDirective directive = directives.get(ts);
            if (directive.stringDataType().isPresent()) {
                Name baseTypeName = directive.stringDataType().get();
                for (String ep : involvedTechSpaces.get(ts)) {
                    Endpoint endpoint = corrSpec.getEndpointRefs().get(ep);
                    if (endpoint.getSystem().get().schema().carrier().mentions(baseTypeName)) {
                        if (stringId == null) {
                            stringId = new Identification();
                            stringId.setName(ComprSys.GLOBAL_STRING_NAME.printRaw());
                        }
                        ElementRef ref = new ElementRef(ep, baseTypeName.print(PrintingStrategy.IGNORE_PREFIX));
                        ref.setElement(endpoint.getSystem().get().schema().carrier().get(baseTypeName).get());
                        ref.setEndpoint(endpoint);
                        stringId.addRelatedElement(ref);
                    }
                }
            }
        }
    }

    private void createIntTypeImplicit(CorrSpec corrSpec) {
        for (TechSpace ts : involvedTechSpaces.keySet()) {
            TechSpaceDirective directive = directives.get(ts);
            if (directive.integerDataType().isPresent()) {
                Name baseTypeName = directive.integerDataType().get();
                for (String ep : involvedTechSpaces.get(ts)) {
                    Endpoint endpoint = corrSpec.getEndpointRefs().get(ep);
                        if (endpoint.getSystem().get().schema().carrier().mentions(baseTypeName)) {
                            if (intId == null) {
                                intId = new Identification();
                                intId.setName(ComprSys.GLOBAL_INT_NAME.printRaw());
                            }
                            ElementRef ref = new ElementRef(ep, baseTypeName.print(PrintingStrategy.IGNORE_PREFIX));
                            ref.setEndpoint(endpoint);
                            ref.setElement(endpoint.getSystem().get().schema().carrier().get(baseTypeName).get());
                            intId.addRelatedElement(ref);                        }
                    }

            }
        }
    }


    private void createFloatTypeImplicit(CorrSpec corrSpec) {
        for (TechSpace ts : involvedTechSpaces.keySet()) {
            TechSpaceDirective directive = directives.get(ts);
            if (directive.floatingPointDataType().isPresent()) {
                Name baseTypeName = directive.floatingPointDataType().get();
                for (String ep : involvedTechSpaces.get(ts)) {
                    Endpoint endpoint = corrSpec.getEndpointRefs().get(ep);
                        if (endpoint.getSystem().get().schema().carrier().mentions(baseTypeName)) {
                            if (floatId == null) {
                                floatId = new Identification();
                                floatId.setName(ComprSys.GLOBAL_FLOAT_NAME.printRaw());
                            }
                            ElementRef ref = new ElementRef(ep, baseTypeName.print(PrintingStrategy.IGNORE_PREFIX));
                            ref.setEndpoint(endpoint);
                            ref.setElement(endpoint.getSystem().get().schema().carrier().get(baseTypeName).get());
                            floatId.addRelatedElement(ref);                        }
                    }

            }
        }
    }


    private void createBoolTypeImplicit(CorrSpec corrSpec) {
        for (TechSpace ts : involvedTechSpaces.keySet()) {
            TechSpaceDirective directive = directives.get(ts);
            if (directive.boolDataType().isPresent()) {
                Name baseTypeName = directive.boolDataType().get();
                for (String ep : involvedTechSpaces.get(ts)) {
                    Endpoint endpoint = corrSpec.getEndpointRefs().get(ep);
                        if (endpoint.getSystem().get().schema().carrier().mentions(baseTypeName)) {
                            if (booldId == null) {
                                booldId = new Identification();
                                booldId.setName(ComprSys.GLOBAL_BOOL_NAME.printRaw());
                            }
                            ElementRef ref = new ElementRef(ep, baseTypeName.print(PrintingStrategy.IGNORE_PREFIX));
                            ref.setEndpoint(endpoint);
                            ref.setElement(endpoint.getSystem().get().schema().carrier().get(baseTypeName).get());
                            booldId.addRelatedElement(ref);                        }
                    }

            }
        }
    }
}
