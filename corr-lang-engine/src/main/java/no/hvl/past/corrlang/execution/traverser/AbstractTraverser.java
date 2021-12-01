package no.hvl.past.corrlang.execution.traverser;


import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.parser.SyntacticalResult;


public abstract class AbstractTraverser extends AbstractExecutor implements SyntaxVisitor  {

    public AbstractTraverser(String key) {
        super(key);
    }

    public void executeTransitive(SyntacticalResult domainModel) throws Throwable {
            this.preBlock(domainModel);
            for (URLReference imprt : domainModel.getImports()) {
                this.handle(imprt);
            }
            for (Endpoint endpoint : domainModel.allEndpoints()) {
                handleEndpoint(endpoint);
                endpoint.accept(this);
            }
            for (CorrSpec corrSpec : domainModel.allSpecs()) {
                corrSpec.accept(this);
                for (Commonality comm : corrSpec.getCommonalities()) {
                    processCommonality(comm);
                }
            }
            for (ConsistencyRule rule : domainModel.allRules()) {
                rule.accept(this);
            }
        for (Goal g : domainModel.allGoals()) {
            g.accept(this);
        }
        postBlock(domainModel);
    }

    private void processCommonality(Commonality comm) throws Throwable {
        handleCommonality(comm);
        for (ElementRef ref : comm.getRelates()) {
            handle(ref);
        }
        if (comm.getKey().isPresent()) {
            comm.getKey().get().accept(this);
        }
        if (comm.getConsistencyRule().isPresent()) {
            comm.getConsistencyRule().get().accept(this);
        }
        for (Commonality subComm : comm.getSubCommonalities()) {
            processCommonality(subComm);
        }
        comm.accept(this);
    }

    // The following methods have to be implemented by the respective subclasses.

    public void preBlock(SyntacticalResult domainModel, String... args) throws Throwable {
    }

    public void postBlock(SyntacticalResult domainModel, String... args) throws Throwable {
    }

    @Override
    public void handle(URLReference imprt) throws Throwable {
    }

    public void handleEndpoint(Endpoint endpoint) throws Throwable {
    }

    @Override
    public void handle(FileEndpoint fileEndpoint) throws Throwable {
    }

    @Override
    public void handle(ServerEndpoint serverEndpoint) throws Throwable {
    }

    @Override
    public void handle(CorrSpec corrSpec) throws Throwable {
    }

    @Override
    public void handle(ConsistencyRule consistencyRule) throws Throwable {
    }

    public void handleCommonality(Commonality commonality) throws Throwable {
    }

    @Override
    public void handle(Relation relation) throws Throwable {
    }

    @Override
    public void handle(Synchronisation synchronisation) throws Throwable {
    }

    @Override
    public void handle(Identification identification) throws Throwable {
    }

    @Override
    public void handle(ElementRef ref) throws Throwable {
    }

    @Override
    public void handle(ElementCondition.Identification keyIdentification) throws Throwable {
    }

    @Override
    public void handle(ElementCondition.RelationRule keyRelation) throws Throwable {
    }

    @Override
    public void handle(Goal goal) throws Throwable {
    }


}
