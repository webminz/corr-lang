package io.corrlang.domain.keys;

import com.fasterxml.jackson.databind.JsonNode;
import io.corrlang.domain.Sys;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.trees.Node;
import no.hvl.past.graph.trees.TypedNode;
import no.hvl.past.graph.trees.TypedTree;
import no.hvl.past.names.Name;
import no.hvl.past.names.PrintingStrategy;
import org.w3c.dom.Element;


import java.util.*;

// TODO change evaluate to optional
public class AttributeBasedKey implements Key {

    private final Sys originalSystem;
    private final List<Triple> attributeEdges;
    private final Name targetType;

    public AttributeBasedKey(Sys originalSystem, Triple attributeEdge, Name targetType) {
        this.attributeEdges = Collections.singletonList(attributeEdge);
        this.targetType = targetType;
        this.originalSystem = originalSystem;
    }

    public AttributeBasedKey(Sys originalSystem, Name targetType, List<Triple> propertyPath) {
        this.attributeEdges = propertyPath;
        this.targetType = targetType;
        this.originalSystem = originalSystem;
    }

    public AttributeBasedKey(Sys originalSystem, Name targetType, Triple... propertyPath) {
        this.attributeEdges = Arrays.asList(propertyPath);
        this.targetType = targetType;
        this.originalSystem = originalSystem;
    }

    @Override
    public Name targetType() {
        return targetType;
    }

    @Override
    public Name sourceType() {
        return attributeEdges.get(0).getSource();
    }

    @Override
    public Sys sourceSystem() {
        return originalSystem;
    }

    @Override
    public List<Triple> requiredProperties() {
        return attributeEdges;
    }

    private Optional<Name> evaluateImpl(
            Name element,
            GraphMorphism container,
            int idx) {
        Optional<Triple> result = container.domain()
                .outgoing(element)
                .filter(t -> container.apply(t).map(e -> attributeEdges.get(idx).equals(e)).orElse(false))
                .findFirst();
        int currentIdx = idx + 1;
        if (result.isPresent()) {
            if (currentIdx == attributeEdges.size()) {
                return result.map(Triple::getTarget);
            } else {
                return evaluateImpl(result.get().getTarget(), container, currentIdx);
            }
        }
        return Optional.empty();
    }

    private Optional<Name> evaluateImpl(
            Node element,
            TypedTree container,
            int idx) {

        if (attributeEdges.get(idx).isNode()) {
            if (element instanceof TypedNode) {
                if (!((TypedNode) element).nodeType().equals(attributeEdges.get(idx).getLabel())) {
                    return Optional.empty();
                }
            }
            return evaluateImpl(element, container, idx + 1);
        }

        Optional<Node> result;
        if (element instanceof TypedNode) {
            result = ((TypedNode) element).feature(attributeEdges.get(idx)).findFirst().map(x -> x);
        } else {
            String label = attributeEdges.get(idx).getLabel().printRaw();
            result = element.childNodesByKey(label).findFirst();
        }
        int currentIdx = idx + 1;
        if (result.isPresent()) {
            if (currentIdx == attributeEdges.size()) {
                return result.map(Node::elementName);
            } else {
                if (element.isLeaf()) {
                    return evaluateImpl(container.findNodeById(result.get().elementName()).get(),container,currentIdx);
                } else {
                    return evaluateImpl(result.get(), container, currentIdx);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Name> evaluate(Name element, GraphMorphism typedContainer) {
        return evaluateImpl(element, typedContainer, 0);
    }

    @Override
    public Optional<Name> evaluate(Node element, TypedTree typedTree) {
        return evaluateImpl(element, typedTree, 0);
    }

    @Override
    public Name evaluate(Object element) throws KeyNotEvaluated {
        // TODO implement properly
        if (element instanceof Map) {
            return turnToKey(((Map) element).get(attributeEdges.get(0).getLabel().print(PrintingStrategy.IGNORE_PREFIX)));
        }

        if (element instanceof Node) {

            Optional<Name> attribute = ((Node) element).childrenByKey(attributeEdges.get(0).getLabel().printRaw()).findFirst().map(c -> c.child().elementName());
            if (attribute.isPresent()) {
                return attribute.get();
            }
            // TODO find child
        }

        if (element instanceof JsonNode) {
            JsonNode node = (JsonNode) element;
            if (node.isObject() && node.has(attributeEdges.get(0).getLabel().print(PrintingStrategy.IGNORE_PREFIX))) {
                return turnToKey(node.get(attributeEdges.get(0).getLabel().print(PrintingStrategy.IGNORE_PREFIX)));
            }
        }

        if (element instanceof Element) {
            Element xml = (Element) element;
            if (xml.hasAttribute(attributeEdges.get(0).getLabel().print(PrintingStrategy.IGNORE_PREFIX))) {
                return turnToKey(xml.getAttribute(attributeEdges.get(0).getLabel().print(PrintingStrategy.IGNORE_PREFIX)));
            }
            // TODO find child
        }

        return reflectionCall(element);
    }

    private Name reflectionCall(Object element) throws KeyNotEvaluated {
        // TODO use reflection to call a method or getter with the right name
        throw new KeyNotEvaluated();
    }

    private Name turnToKey(Object o) throws KeyNotEvaluated {
        if (o instanceof Name) {
            return (Name) o;
        }

        if (o instanceof String) {
            return Name.value((String)o);
        }
        if (o instanceof JsonNode) {
            JsonNode json = (JsonNode) o;
            if (json.isTextual()) {
                return Name.value(json.textValue());
            } else if (json.isIntegralNumber()) {
                return Name.value(json.longValue());
            } else if (json.isFloatingPointNumber()) {
                return Name.value(json.doubleValue());
            } else if (json.isBoolean()) {
                return json.booleanValue() ? Name.trueValue() : Name.falseValue();
            } else {
                throw new KeyNotEvaluated();
            }
        }

        return Name.identifier(o.toString());
    }

    @Override
    public Name getName() {
        return attributeEdges.get(0).getLabel().prefixWith(attributeEdges.get(0).getSource());
    }


    @Override
    public int hashCode() {
        return Objects.hash(targetType, attributeEdges);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttributeBasedKey) {
            AttributeBasedKey k = (AttributeBasedKey) obj;
            return this.targetType.equals(k.targetType) && this.attributeEdges.equals(k.attributeEdges);
        }
        return false;
    }
}
