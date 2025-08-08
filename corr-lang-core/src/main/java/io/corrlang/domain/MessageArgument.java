package io.corrlang.domain;


import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;


public class MessageArgument {
    // TODO: can probably be removed

    private final MessageType owner;
    private final Triple type;
    private final int order;
    private final boolean isOutput;

    public MessageArgument(MessageType owner, Triple type, int order, boolean isOutput) {
        this.owner = owner;
        this.type = type;
        this.order = order;
        this.isOutput = isOutput;
    }

    public MessageType message() {
        return owner;
    }

    public Triple asEdge() {
        return type;
    }

    public Name returnType() {
        return type.getTarget();
    }

    public Name label() {
        return type.getLabel();
    }

    public boolean isInput() {
        return !isOutput;
    }

    public boolean isOutput() {
        return isOutput;
    }

    public int argumentOrder() {
        return order;
    }

}
