package no.hvl.past.corrlang.domainmodel;

public interface Visitor {

    void handle(FileEndpoint fileEndpoint);

    void handle(ServerEndpoint serverEndpoint);

    void handle(CorrSpec corrSpec);

    void handle(ConsistencyRule consistencyRule);

    void handle(Relation relation);

    void handle(Synchronisation synchronisation);

    void handle(Identification identification);
}
