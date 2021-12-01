package io.corrlang.domain.keys;

import io.corrlang.domain.Sys;
import no.hvl.past.attributes.BoolValue;
import no.hvl.past.attributes.FloatValue;
import no.hvl.past.attributes.IntegerValue;
import no.hvl.past.attributes.StringValue;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.trees.Node;
import no.hvl.past.graph.trees.TypedTree;
import no.hvl.past.names.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConcatenatedKey implements Key {

    private final Sys originalSystem;
    private final Name sourceType;
    private final Name targetType;
    private final List<Key> childKeys;

    public ConcatenatedKey(Name targetType, Name sourceType, Sys originalSystem, List<Key> childKeys) {
        this.childKeys = childKeys;
        this.targetType = targetType;
        this.originalSystem = originalSystem;
        this.sourceType = sourceType;
    }


    @Override
    public Name targetType() {
        return targetType;
    }

    @Override
    public Name sourceType() {
        return sourceType;
    }

    @Override
    public Sys sourceSystem() {
        return originalSystem;
    }

    @Override
    public List<Triple> requiredProperties() {
        List<Triple> result = new ArrayList<>();
        for (Key child : childKeys) {
            result.addAll(child.requiredProperties());
        }
        return result;
    }

    @Override
    public Optional<Name> evaluate(Name element, GraphMorphism typedContainer) {
        List<Name> childEvals = new ArrayList<>();
        for (Key child : childKeys) {
            Optional<Name> evaluate = child.evaluate(element, typedContainer);
            if (evaluate.isPresent()) {
                childEvals.add(evaluate.get());
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(concatenation(childEvals));
    }

    // TODO refactor
    @Override
    public Optional<Name> evaluate(Node element, TypedTree typedTree) {
        List<Name> childEvals = new ArrayList<>();
        for (Key child : childKeys) {
            Optional<Name> evaluate = child.evaluate(element, typedTree);
            if (evaluate.isPresent()) {
                childEvals.add(evaluate.get());
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(concatenation(childEvals));
    }

    private Name concatenation(List<Name> evaluatedKeys) {
        StringBuilder builder = new StringBuilder();
        for (Name evaluated : evaluatedKeys) {
            if (evaluated instanceof StringValue) {
                builder.append(((StringValue) evaluated).getStringValue());
            } else if (evaluated instanceof IntegerValue) {
                builder.append(((IntegerValue) evaluated).getIntegerValue().toString());
            } else if (evaluated instanceof FloatValue) {
                builder.append(Double.toString(((FloatValue) evaluated).getFloatValue()));
            } else if (evaluated instanceof BoolValue) {
                builder.append(Boolean.toString(((BoolValue) evaluated).isTrue()));
            } else {
                builder.append(evaluated.printRaw());
            }
        }
        return Name.value(builder.toString());
    }

    @Override
    public Name evaluate(Object element) throws KeyNotEvaluated {
        // TODO add parsing etc.
        List<StringValue> childEvals = new ArrayList<>();
        for (Key child : childKeys) {
            Name evaluate = child.evaluate(element);
            if (!evaluate.isValue() && !(evaluate instanceof StringValue)) {
                throw new KeyNotEvaluated();
            }
            childEvals.add((StringValue) evaluate);
        }
        StringValue result = Name.value("");
        for (StringValue v : childEvals) {
            result = result.concat(v);
        }
        return result;
    }

    @Override
    public Name getName() {
        return Name.concat(childKeys.stream().map(Key::getName).collect(Collectors.toList()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType, this.childKeys);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConcatenatedKey) {
            ConcatenatedKey k = (ConcatenatedKey) obj;
            return this.targetType.equals(k.targetType) && childKeys.equals(k.childKeys);
        }
        return false;
    }
}
