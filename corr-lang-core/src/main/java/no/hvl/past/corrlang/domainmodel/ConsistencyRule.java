package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.graph.Diagram;

import java.util.Optional;

public class ConsistencyRule extends CorrLangElement {

    private String language;
    private String body;
    private Diagram formalRepresentation;

    public ConsistencyRule(String name) {
        super(name);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setFormalRepresentation(Diagram diagram) {
        this.formalRepresentation = diagram;
    }

    public Optional<Diagram> formalRepresentation() {
        return Optional.ofNullable(formalRepresentation);
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }



}
