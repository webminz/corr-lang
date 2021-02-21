package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.graph.elements.Triple;

public class ElementRef implements Key.KeyLiteralArgument {

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

    public Triple getElement() {
        return element;
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
}
