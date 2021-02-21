package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.graph.*;
import no.hvl.past.names.Name;

import java.util.*;

public class CorrSpec extends CorrLangElement {

    private String typedOverName;
    private CorrSpec typedOver;
    private List<String> endpointsList;
    private Collection<Commonality> commonalities;
    private Map<String, Endpoint> endpointRefs;

    private Star formalRepresentation;
    private Map<String, GraphMorphism> schemaTypeEmbeddings;
    private Sketch comprehensiveSchema;


    public CorrSpec(String name) {
        super(name);
        this.commonalities = new ArrayList<>();
        this.endpointsList = new ArrayList<>();
        this.endpointRefs = new LinkedHashMap<>();
        this.schemaTypeEmbeddings = new LinkedHashMap<>();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.handle(this);
    }

    public void setTypedOverName(String typedOverName) {
        this.typedOverName = typedOverName;
    }

    public String getTypedOverName() {
        return typedOverName;
    }

    public CorrSpec getTypedOver() {
        return typedOver;
    }

    public void addEndpoint(String name) {
        this.endpointsList.add(name);
    }

    public List<String> getEndpointsList() {
        return endpointsList;
    }

    public Collection<Commonality> getCommonalities() {
        return commonalities;
    }

    public void addCommonality(Commonality commonality) {
        this.commonalities.add(commonality);
    }

    public void addEndpointRef(String name, Endpoint endpoint) {
        this.endpointRefs.put(name, endpoint);
    }

    public Map<String, Endpoint> getEndpointRefs() {
        return endpointRefs;
    }

    public Star formalRepresentation() {
        return formalRepresentation;
    }

    public void setFormalRepresentation(Star formalRepresentation) {
        this.formalRepresentation = formalRepresentation;
    }

    public void addSchemEmbeeding(String endpoint, GraphMorphism embedding) {
        this.schemaTypeEmbeddings.put(endpoint, embedding);
    }

    public void setComprehensveSchema(Sketch comprehensiveSchema) {
        this.comprehensiveSchema = comprehensiveSchema;
    }

    public Star getFormalRepresentation() {
        return formalRepresentation;
    }

    public Map<String, GraphMorphism> getSchemaTypeEmbeddings() {
        return schemaTypeEmbeddings;
    }

    public Sketch getComprehensiveSchema() {
        return comprehensiveSchema;
    }
}
