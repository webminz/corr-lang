package io.corrlang.domain;

import com.google.common.collect.Streams;

import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;

import java.util.*;
import java.util.stream.Stream;

public class MessageType {

    private final Name type;
    private final List<MessageArgument> inputArgs;
    private final List<MessageArgument> outputArgs;
    private final MessageContainer container;
    private final boolean sideEffect;


    public MessageType(Name type, List<MessageArgument> inputArgs, List<MessageArgument> outputArgs) {
        this.type = type;
        this.inputArgs = inputArgs;
        this.outputArgs = outputArgs;
        this.container = null;
        this.sideEffect = true;
    }

    public MessageType(Name type, List<MessageArgument> inputArgs, List<MessageArgument> outputArgs, MessageContainer group, boolean sideEffect) {
        this.type = type;
        this.inputArgs = inputArgs;
        this.outputArgs = outputArgs;
        this.container = group;
        this.sideEffect = sideEffect;
    }

    public Name typeName() {
        return type;
    }

    public Stream<MessageArgument> arguments() {
        return Streams.concat(inputArgs.stream(), outputArgs.stream());
    }

    public List<MessageArgument> inputs() {
        return inputArgs;
    };

    public Optional<MessageContainer> getGroup() {
        return Optional.ofNullable(container);
    }

    public boolean hasSideEffects() {
        return sideEffect;
    }

    public List<MessageArgument> outputs() {
        return outputArgs;
    }

    public MessageType substitute(GraphMorphism graphMorphism) {
        List<MessageArgument> ins = new ArrayList<>();
        List<MessageArgument> outs = new ArrayList<>();
        MessageContainer newMessageContainer;
        if (container == null) {
            newMessageContainer = null;
        } else {
            newMessageContainer = new MessageContainer(graphMorphism.apply(Triple.node(container.getTypeName())).get().getLabel());
        }
        MessageType result = new MessageType(graphMorphism.map(type).get(), ins, outs, newMessageContainer, sideEffect);
        for (MessageArgument arg : inputArgs) {
            ins.add(new MessageArgument(result, graphMorphism.apply(arg.asEdge()).get(), arg.argumentOrder(), false));
        }
        for (MessageArgument arg : outputArgs) {
            outs.add(new MessageArgument(result, graphMorphism.apply(arg.asEdge()).get(), arg.argumentOrder(), true));
        }
        return result;
    }
}
