package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.graph.Diagram;

public class ConsistencyRule extends CorrLangElement {

    public enum ConsistencyRuleLanguage {
        OCL,
        EVL
    }

    private ConsistencyRuleLanguage language;
    private String body;

    public ConsistencyRule(String name) {
        super(name);
    }

    public ConsistencyRuleLanguage getLanguage() {
        return language;
    }

    public void setLanguage(ConsistencyRuleLanguage language) {
        this.language = language;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.handle(this);
    }

    public Diagram formalRepresentation() {
        // TODO
        return null;
    }

}
