package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.graph.Graph;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.keys.AttributeBasedKey;
import no.hvl.past.keys.Key;
import no.hvl.past.names.Name;
import no.hvl.past.systems.Sys;

import java.util.Optional;

/**
 * Element refs are used to refer to elements in another schema.
 */
public class ElementRef extends CorrLangElement implements ElementCondition.IdentificationArgument, ElementCondition.RelationRulePart {

    private String endpointName;
    private String name;
    private String ownerName;
    private String targetName;

    private Endpoint endpoint;
    private Triple element;

    public ElementRef() {
    }

    public ElementRef(String endpointName, String name) {
        this.endpointName = endpointName;
        this.name = name;
    }

    public ElementRef(String endpointName, String ownerName, String name) {
        this.endpointName = endpointName;
        this.name = name;
        this.ownerName = ownerName;
    }

    public ElementRef(String endpointName, String name, String ownerName, String targetName) {
        this.endpointName = endpointName;
        this.name = name;
        this.ownerName = ownerName;
        this.targetName = targetName;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Optional<Triple> getElement() {
        return Optional.ofNullable(element);
    }

    public void setElement(Triple element) {
        this.element = element;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ElementRef) {
            ElementRef other = (ElementRef) obj;
            if (this.endpointName.equals(other.endpointName)) {
                if (this.name.equals(other.name)) {
                    if (targetName != null) {
                        return this.targetName.equals(other.targetName) && this.ownerName.equals(other.ownerName);
                    } else {
                        if (this.ownerName != null) {
                            return this.ownerName.equals(other.ownerName);
                        } else {
                            return other.ownerName == null;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = this.endpointName.hashCode();
        result = result ^ this.name.hashCode();
        if (this.targetName != null) {
            result = result ^ this.targetName.hashCode();
        }
        if (this.ownerName != null) {
            result = result ^ this.ownerName.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return endpointName + "." + (ownerName != null ? ownerName + "." : "") + name + (targetName != null ? "." + targetName : "") ;
    }

    @Override
    public Key toKey(Name targetType, Graph carrier) {
        return new AttributeBasedKey(carrier, this.element, targetType);
    }

    public Optional<Triple> lookup(Sys system) {
        if (targetName != null) {
            return system.lookup(this.ownerName, this.name, this.targetName);
        } else if (ownerName != null) {
            return system.lookup(this.ownerName, this.name);
        } else {
            return system.lookup(this.name);
        }
    }
}
