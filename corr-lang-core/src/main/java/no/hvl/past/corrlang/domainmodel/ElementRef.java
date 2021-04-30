package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.graph.Element;
import no.hvl.past.graph.Graph;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.keys.AttributeBasedKey;
import no.hvl.past.keys.Key;
import no.hvl.past.names.Name;
import no.hvl.past.systems.Sys;
import no.hvl.past.util.StringUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Element refs are used to refer to elements in another schema.
 */
public class ElementRef extends CorrLangElement implements ElementCondition.IdentificationArgument, ElementCondition.RelationRulePart {

    private List<String> pathExpression = new ArrayList<>();
    private String alias = null;

    private Endpoint endpoint;
    private Triple element;
    private List<Triple> elementPath = new ArrayList<>();

    public ElementRef() {
    }

    public ElementRef(String... path) {
        this.pathExpression.addAll(Arrays.asList(path));
    }

    public String getEndpointName() {
        return pathExpression.get(0);
    }

    public void setEndpointName(String endpointName) {
        this.pathExpression.set(0, endpointName);
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getName() {
        return pathExpression.get(pathExpression.size()-1);
    }

    public void setName(String name) {
        this.pathExpression.set(pathExpression.size() -1,name);
    }

    public String getAlias() {
        return alias;
    }

    public List<String> getPathExpression() {
        return pathExpression;
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }

    public String getOwnerName() {
        return pathExpression.get(1);
    }

    public void setOwnerName(String ownerName) {
        this.pathExpression.set(1,ownerName);
    }


    public Optional<Triple> getElement() {
        return Optional.ofNullable(element);
    }

    public void setElement(Triple element) {
        this.element = element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementRef that = (ElementRef) o;
        return pathExpression.equals(that.pathExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathExpression);
    }

    @Override
    public String toString() {
        return StringUtils.fuseList(this.pathExpression.stream(), ".");
    }

    @Override
    public Key toKey(Name targetType) {
        if (isPath()) {
            List<Triple> path = new ArrayList<>();
            path.add(element);
            path.addAll(elementPath);
            return new AttributeBasedKey(endpoint.getSystem().get(), targetType, path);
        } else {
            Optional<Triple> lookup = getEndpoint().getSystem().get().lookup(this.pathExpression.get(1));
            if (!lookup.get().getLabel().equals(this.element.getSource())) {
                List<Triple> path = new ArrayList<>();
                path.add(lookup.get());
                path.add(element);
                return new AttributeBasedKey(endpoint.getSystem().get(), targetType, path);
            } else {
                return new AttributeBasedKey(endpoint.getSystem().get(), this.element, targetType);
            }
        }
    }

    public void desugarAlias(Map<String, ElementRef> knownAliases) {
        if (knownAliases.containsKey(pathExpression.get(0))) {
            String alias = pathExpression.get(0);
            pathExpression.remove(0);
            ElementRef elementRef = knownAliases.get(alias);
            this.pathExpression.addAll(0, elementRef.getPathExpression());
        }
    }

    public boolean isPath() {
        return !this.elementPath.isEmpty();
    }

    public Optional<Triple> lookup(Sys system) {
        if (this.pathExpression.size() == 2) {
            return system.lookup(this.pathExpression.get(1));
        }
        if (this.pathExpression.size() == 3) {
            Optional<Triple> lookup = system.lookup(this.pathExpression.get(1), this.pathExpression.get(2));
            if (lookup.isPresent()) {
                return lookup;
                //system.lookup(this.pathExpression.get(1));
            }
        }
        boolean foundStart = false;
        int idx = 1;
        Optional<Triple> lookup = Optional.empty();
        while (!foundStart) {
            String[] lookupPath = new String[idx];
            for (int i = 0; i < idx; i++) {
                lookupPath[i] = pathExpression.get(i + 1);
            }
            lookup = system.lookup(lookupPath);
            if (lookup.isPresent()) {
                foundStart = true;
            }
            idx++;
            if (idx == this.pathExpression.size()) {
                return Optional.empty();
            }
        }
        Name start = lookup.get().getTarget();
        while (idx < this.pathExpression.size()) {
            Optional<Triple> triple = system.lookup(system.displayName(start), pathExpression.get(idx));
            if (triple.isPresent()) {
                start = triple.get().getTarget();
                elementPath.add(triple.get());
                idx++;
            } else {
                idx = this.pathExpression.size();
            }
        }
        return lookup;
    }

    public void setAlias(String text) {
        this.alias = text;
    }
}
