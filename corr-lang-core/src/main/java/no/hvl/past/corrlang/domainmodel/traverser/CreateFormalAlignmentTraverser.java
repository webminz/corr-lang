package no.hvl.past.corrlang.domainmodel.traverser;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.graph.*;
import no.hvl.past.names.Name;
import no.hvl.past.plugin.MetaRegistry;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceDirective;
import no.hvl.past.util.Pair;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CreateFormalAlignmentTraverser extends AbstractTraverser {

    private static Logger logger = Logger.getLogger(CreateFormalAlignmentTraverser.class);

    private final MetaRegistry registry;
    private final Universe universe;

    public CreateFormalAlignmentTraverser(
            ReportFacade reportFacade,
            MetaRegistry registry,
            Universe universe) {
        super(reportFacade, Collections.singleton(LinkElementsTraverser.class));
        this.registry = registry;
        this.universe = universe;
    }

    @Override
    public void handle(CorrSpec corrSpec) {
        try {
            List<Sketch> components = new ArrayList<>();
            Map<TechSpace, TechSpaceDirective> directives = new LinkedHashMap<>();

            GraphBuilders apexBuilder = new GraphBuilders(universe, false, true);
            List<GraphBuilders> morphismBuilders = new ArrayList<>();
            for (String endpoint : corrSpec.getEndpointsList()) {
                Endpoint endpointObject = corrSpec.getEndpointRefs().get(endpoint);
                TechSpace techSpace = endpointObject.getTechSpace().get();
                if (!directives.containsKey(techSpace)) {
                    directives.put(techSpace, endpointObject.getAdaptor().get().directives());
                }

                components.add(endpointObject.getFormalSchemaRepresentation().get());

                morphismBuilders.add(new GraphBuilders(universe, false, false));
            }

            Set<Name> ids = new HashSet<>();

            // first the implicit commonalities
            for (Map.Entry<TechSpace, TechSpaceDirective> directive : directives.entrySet()) {
                // TODO data types are universal
                if (directive.getValue().stringDataType().isPresent()) {
                    addImplicitIdentity(directive.getValue().stringDataType().get(), corrSpec, apexBuilder, morphismBuilders, ids, directive);
                }
                if (directive.getValue().integerDataType().isPresent()) {
                    addImplicitIdentity(directive.getValue().integerDataType().get(), corrSpec, apexBuilder, morphismBuilders, ids, directive);
                }
                if (directive.getValue().boolDataType().isPresent()) {
                    addImplicitIdentity(directive.getValue().boolDataType().get(),corrSpec, apexBuilder, morphismBuilders, ids, directive);
                }
                if (directive.getValue().floatingPointDataType().isPresent()) {
                    addImplicitIdentity(directive.getValue().floatingPointDataType().get(),corrSpec, apexBuilder, morphismBuilders, ids, directive);
                }
                directive.getValue().implicitTypeIdentities().forEach(name ->{
                    addImplicitIdentity(name, corrSpec, apexBuilder, morphismBuilders, ids, directive);
                });
            }

            for (Commonality commonality : corrSpec.getCommonalities()) {
                // TODO check what type of commonality it is
                apexBuilder.node(commonality.getName());
                for (ElementRef ref : commonality.getRelates()) {
                    int index = corrSpec.getEndpointsList().indexOf(ref.getEndpointName());
                    if (components.get(index).carrier().mentions(Name.identifier(ref.getName()))) {
                        morphismBuilders.get(index).map(Name.identifier(commonality.getName()), Name.identifier(ref.getName()));
                    }
                }
                if (commonality instanceof Identification) {
                    ids.add(Name.identifier(commonality.getName()));
                }
            }

            // TODO add keys


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
            logger.debug("Formal representation of alignment set");

            Pair<Sketch, List<GraphMorphism>> sketchListPair = result.comprehensiveSystem();
            logger.debug("Comprehensive alignment calculated");

            corrSpec.setComprehensveSchema(sketchListPair.getFirst());
            ListIterator<String> listIterator = corrSpec.getEndpointsList().listIterator();
            while (listIterator.hasNext()) {
                String endpoint = listIterator.next();
                int i = listIterator.nextIndex();
                corrSpec.getSchemaTypeEmbeddings().put(endpoint, sketchListPair.getRight().get(i));
            }
        } catch (GraphError error) {
            failed();
            getReportFacade().reportError(ReportErrorType.SEMANTICS,error,corrSpec);
        }
    }

    private void addImplicitIdentity(Name name, CorrSpec corrSpec, GraphBuilders apexBuilder, List<GraphBuilders> morphismBuilders, Set<Name> ids, Map.Entry<TechSpace, TechSpaceDirective> directive) {
        ids.add(name);
        apexBuilder.node(name);
        for (String endpoint : corrSpec.getEndpointsList()) {
            Endpoint endpointObject = corrSpec.getEndpointRefs().get(endpoint);
            if (endpointObject.getTechSpace().get().equals(directive.getKey())) {
                if (endpointObject.getFormalSchemaRepresentation().get().carrier().mentions(name)) {
                    morphismBuilders.get(corrSpec.getEndpointsList().indexOf(endpoint)).map(name, name);
                }
            }
        }
    }
}
