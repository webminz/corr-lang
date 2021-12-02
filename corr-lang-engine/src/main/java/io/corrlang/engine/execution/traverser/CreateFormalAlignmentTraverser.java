package io.corrlang.engine.execution.traverser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.corrlang.engine.domainmodel.Commonality;
import io.corrlang.engine.domainmodel.CorrSpec;
import io.corrlang.engine.execution.AbstractExecutor;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import io.corrlang.domain.keys.Key;
import no.hvl.past.names.Name;
import no.hvl.past.MetaRegistry;
import io.corrlang.domain.ComprSys;
import io.corrlang.domain.QualifiedName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

// TODO use instance variables to leverage usage of method params
public class CreateFormalAlignmentTraverser extends AbstractTraverser {


    @Autowired
    private  MetaRegistry registry;
    @Autowired
    private Universe universe;
    private Multimap<Triple, Name> participations;

    private ComprSys.Builder builder;


    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public CreateFormalAlignmentTraverser() {
        super("CreateFormalAlignment");
        this.participations = ArrayListMultimap.create();
    }


    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.singleton(ApplyFinalTechnologySpecificRulesTraverser.class);
    }

    @Override
    public void handle(CorrSpec corrSpec) throws GraphError {
        // Init result vars
        buildKeys(corrSpec);

        this.builder = new ComprSys.Builder(Name.identifier(corrSpec.getName()), universe);

        for (String ep : corrSpec.getEndpointsList()) {
            this.builder.addSystem(corrSpec.getEndpointRefs().get(ep).getSystem().get());
        }

        for (Commonality commonality : corrSpec.allTransitiveCommonalities()) {
            addCommonality(commonality);
        }

        for (Key k : corrSpec.getFormalKeys()) {
            this.builder.key(k);
        }

        corrSpec.setComprSys(this.builder.build());
    }

    private void addCommonality(Commonality commonality) {
        if (commonality.isNodeCorrespondence()) {
            handleNode(commonality);
        } else if (commonality.isEdgeCorrespondence()) {
            handleEdge(commonality);
        }
    }

    private void handleEdge(Commonality commonality) {
        Name srcName = Name.identifier(commonality.getParent().get().getName());
        Name lblName = Name.identifier(commonality.getName()).prefixWith(srcName);
        Name trgName = Name.identifier(commonality.getTarget().get().getName());
        this.builder.edgeCommonality(srcName, lblName, trgName, commonality.getRelates().stream().map(
                com -> QualifiedName.qname(com.getEndpoint().getSystem().get(),com.getElement().get().getLabel())
        ).collect(Collectors.toList()));
        if (commonality.isIdentity()) {
            this.builder.identification(lblName);
        }
    }

    public void handleNode(Commonality commonality) {
        Name commWitnsName = Name.identifier(commonality.getName());
        this.builder.nodeCommonality(commWitnsName, commonality.getRelates().stream()
                .map(com -> QualifiedName.qname(com.getEndpoint().getSystem().get(), com.getElement().get().getLabel()))
                .collect(Collectors.toList()));
        if (commonality.isIdentity()) {
            this.builder.identification(commWitnsName);
        }
        if (commonality.isSynchronizeElements()) {
            this.builder.synchronisation(commWitnsName);
        }
    }

    // TODO maybe move to a different traverser
    private void buildKeys(CorrSpec corrSpec) {
        for (Commonality commonality : corrSpec.getCommonalities()) {
            if (commonality.isNodeCorrespondence() && commonality.getKey().isPresent()) {
                for (Key k : commonality.getKey().get().asKeys(Name.identifier(commonality.getName()))) {
                    corrSpec.addFormalKey(k);
                }
            }
        }

    }









}
