package io.corrlang.domain.keys;

import io.corrlang.domain.Endpoint;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A key that always evaluates to the same value.
 * Attention: Should be used in connection with concatenation,
 * otherwise all elements would be always identified!
 */
public class ConstantKey implements Key {

    private Endpoint originalSystem;
    private Name sourceElement;
    private final Name value;
    private final Name definedOn;

    public ConstantKey(Name value, Name definedOn) {
        this.value = value;
        this.definedOn = definedOn;
    }

    @Override
    public Name targetType() {
        return definedOn;
    }

    @Override
    public Name sourceType() {
        if (sourceElement == null) {
            return definedOn;
        }
        return sourceElement;
    }

    public void setOriginalSystem(Endpoint originalSystem) {
        this.originalSystem = originalSystem;
    }

    public void setSourceElement(Name sourceElement) {
        this.sourceElement = sourceElement;
    }

    @Override
    public Endpoint sourceSystem() {
        return originalSystem;
    }

    @Override
    public List<Triple> requiredProperties() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Name> evaluate(Name element, GraphMorphism typedContainer) {
        return Optional.of(value);
    }

//    @Override
//    public Optional<Name> evaluate(Node element, TypedTree typedTree) {
//        return Optional.of(value);
//    }

    @Override
    public Name evaluate(Object element) {
        return value;
    }

    @Override
    public Name getName() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType(), value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConstantKey) {
            ConstantKey k = (ConstantKey) obj;
            return this.definedOn.equals(k.definedOn) && this.value.equals(k.value);
        }
        return false;
    }
}
