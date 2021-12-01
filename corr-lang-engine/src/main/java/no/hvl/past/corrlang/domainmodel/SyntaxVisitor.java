package no.hvl.past.corrlang.domainmodel;

public interface SyntaxVisitor {

    void handle(URLReference imprt) throws Throwable;

    void handle(FileEndpoint fileEndpoint) throws Throwable;

    void handle(ServerEndpoint serverEndpoint) throws Throwable;

    void handle(CorrSpec corrSpec) throws Throwable;

    void handle(ConsistencyRule consistencyRule) throws Throwable;

    void handle(Relation relation) throws Throwable;

    void handle(Synchronisation synchronisation) throws Throwable;

    void handle(Identification identification) throws Throwable;

    void handle(ElementRef ref) throws Throwable;

    void handle(ElementCondition.Identification keyIdentification) throws Throwable;

    void handle(ElementCondition.RelationRule keyRelation) throws Throwable;

    void handle(Goal goal) throws Throwable;
}
