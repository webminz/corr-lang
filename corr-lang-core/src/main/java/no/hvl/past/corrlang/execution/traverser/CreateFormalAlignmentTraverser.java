package no.hvl.past.corrlang.execution.traverser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.keys.Key;
import no.hvl.past.names.Name;
import no.hvl.past.plugin.MetaRegistry;
import no.hvl.past.systems.ComprSys;
import no.hvl.past.systems.Sys;
import no.hvl.past.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

// TODO use instance variables to leverage usage of method params
public class CreateFormalAlignmentTraverser extends AbstractTraverser {


    private static final Name GLOBAL_STRING_NAME = Name.identifier("String");
    private static final Name GLOBAL_INT_NAME = Name.identifier("Integer");
    private static final Name GLOBAL_FLOAT_NAME = Name.identifier("Float");
    private static final Name GLOBAL_BOOL_NAME = Name.identifier("Bool");

    @Autowired
    private  MetaRegistry registry;
    @Autowired
    private Universe universe;
    private Multimap<Triple, Name> participations;

    public CreateFormalAlignmentTraverser() {
        super("CreateFormalAlignment");
        this.participations = ArrayListMultimap.create();
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.singleton(LinkCommonalities.class);
    }

    @Override
    public void handle(CorrSpec corrSpec) throws GraphError {
        // Init result vars
        List<Sketch> components = new ArrayList<>();
        List<GraphBuilders> morphismBuilders = new ArrayList<>();
        Set<Name> ids = new HashSet<>();

        GraphBuilders apexBuilder = makeBuilders(corrSpec, components, morphismBuilders);

        for (Commonality commonality : corrSpec.allTransitiveCommonalities()) {
            addCommonality(corrSpec, commonality, apexBuilder, components, morphismBuilders, ids);
        }

        buildFormalConstructs(corrSpec, components, morphismBuilders, ids, apexBuilder);


    }

    private void addCommonality(
            CorrSpec corrSpec,
            Commonality commonality,
            GraphBuilders apexBuilder,
            List<Sketch> components,
            List<GraphBuilders> morphismBuilders,
            Set<Name> ids) {
        if (commonality.isNodeCorrespondence()) {
            handleNode(apexBuilder, commonality,corrSpec,components,morphismBuilders,ids);
        } else if (commonality.isEdgeCorrespondence()) {
            handleEdge(apexBuilder, commonality, corrSpec, components, morphismBuilders, ids);
        }
    }

    private void handleEdge(
            GraphBuilders apexBuilder,
            Commonality commonality,
            CorrSpec corrSpec,
            List<Sketch> components,
            List<GraphBuilders> morphismBuilders,
            Set<Name> ids) {
        Name srcName = Name.identifier(commonality.getParent().get().getName());
        Name lblName = Name.identifier(commonality.getName());
        Name trgName = Name.identifier(commonality.getTarget().get().getName());
        apexBuilder.edge(srcName, lblName, trgName);
        for (ElementRef ref : commonality.getRelates()) {
            int index = corrSpec.getEndpointsList().indexOf(ref.getEndpointName());
            morphismBuilders.get(index).map(
                   lblName, ref.getElement().get().getLabel());
        }
        if (commonality.isIdentity()) {
            ids.add(lblName);
        }
    }

    public void handleNode(
            GraphBuilders apexBuilder,
            Commonality commonality,
            CorrSpec corrSpec,
            List<Sketch> components,
            List<GraphBuilders> morphismBuilders,
            Set<Name> ids) {
        Name commWitnsName = Name.identifier(commonality.getName());
        apexBuilder.node(commWitnsName);
        for (ElementRef ref : commonality.getRelates()) {
            int index = corrSpec.getEndpointsList().indexOf(ref.getEndpointName());
            morphismBuilders.get(index).map(commWitnsName, ref.getElement().get().getLabel());
        }
        if (commonality.isIdentity()) {
            ids.add(commWitnsName);
        }
    }

    // TODO maybe move to a different traverser
    private void buildKeys(CorrSpec corrSpec) {
        for (Commonality commonality : corrSpec.getCommonalities()) {
            if (commonality.isNodeCorrespondence() && commonality.getKey().isPresent()) {
                for (Key k : commonality.getKey().get().asKeys(Name.identifier(commonality.getName()), corrSpec.getComprehensiveSchema().get().carrier())) {
                    corrSpec.addFormalKey(k);
                }
            }
        }

    }

    // TODO may be moved to a different traverser
    private void buildFormalConstructs(CorrSpec corrSpec, List<Sketch> components, List<GraphBuilders> morphismBuilders, Set<Name> ids, GraphBuilders apexBuilder) throws GraphError {
        Sketch apex = apexBuilder.graph(Name.identifier(corrSpec.getName() + "_0").absolute()).sketch(Name.identifier(corrSpec.getName() + "_0")).getResult(Sketch.class);
        List<GraphMorphism> projections = new ArrayList<>();
        for (GraphBuilders builders : morphismBuilders) {
            builders.domain(apex.carrier());
            builders.codomain(components.get(morphismBuilders.indexOf(builders)).carrier());
            builders.morphism("Projection_" + morphismBuilders.indexOf(builders));
            projections.add(builders.getResult(GraphMorphism.class));
        }

        Star result = new StarImpl(Name.identifier(corrSpec.getName()), apex, components, projections, ids);
        corrSpec.setFormalRepresentation(result);
        getLogger().debug("Formal representation of alignment for " + corrSpec.getName() + " is set");

        Pair<Sketch, List<GraphMorphism>> sketchListPair = result.comprehensiveSystem();
        getLogger().debug("Comprehensive system is calculated");

        corrSpec.setComprehensveSchema(sketchListPair.getFirst());

        Map<Sys, GraphMorphism> embeddings = new LinkedHashMap<>();

        ListIterator<String> listIterator = corrSpec.getEndpointsList().listIterator();
        while (listIterator.hasNext()) {
            String endpoint = listIterator.next();
            int i = listIterator.nextIndex();
            GraphMorphism embMorph = sketchListPair.getRight().get(i);
            corrSpec.getSchemaTypeEmbeddings().put(endpoint, embMorph);
            embeddings.put(corrSpec.getEndpointRefs().get(endpoint).getSystem().get(), embMorph);
        }


        buildKeys(corrSpec);


        ComprSys cs = new ComprSys.Impl("http://127.0.0.1",
                corrSpec.getComprehensiveSchema().get(),
                new HashMap<>(),
                embeddings,
                ids,
                corrSpec.getFormalKeys());
        corrSpec.setComprSys(cs);
    }


    @NotNull
    private GraphBuilders makeBuilders(CorrSpec corrSpec, List<Sketch> components, List<GraphBuilders> morphismBuilders) {
        GraphBuilders apexBuilder = new GraphBuilders(universe, false, true);
        for (String endpoint : corrSpec.getEndpointsList()) {
            Endpoint endpointObject = corrSpec.getEndpointRefs().get(endpoint);
            components.add(endpointObject.getSystem().get().schema());
            morphismBuilders.add(new GraphBuilders(universe, false, false));
        }
        return apexBuilder;
    }




}
