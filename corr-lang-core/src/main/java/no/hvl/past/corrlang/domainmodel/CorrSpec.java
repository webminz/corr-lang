package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.graph.*;
import no.hvl.past.keys.Key;
import no.hvl.past.systems.ComprSys;

import java.util.*;

public class CorrSpec extends CorrLangElement {

    // Parsed
    private String typedOverName;
    private List<String> endpointsList;
    private Collection<Commonality> commonalities;

    // Linked
    private Map<String, Endpoint> endpointRefs;
    private CorrSpec typedOver;

    // Interpreted
    // TODO all summarizes in a comprehensive system
    private Star formalRepresentation;
    private Map<String, GraphMorphism> schemaTypeEmbeddings;
    private Sketch comprehensiveSchema;
    private Set<Key> formalKeys;
    private ComprSys comprSys;


    public CorrSpec(String name) {
        super(name);
        this.commonalities = new ArrayList<>();
        this.endpointsList = new ArrayList<>();
        this.endpointRefs = new LinkedHashMap<>();
        this.schemaTypeEmbeddings = new LinkedHashMap<>();
        this.formalKeys = new HashSet<>();
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }

    public void setTypedOverName(String typedOverName) {
        this.typedOverName = typedOverName;
    }

    public void addEndpoint(String name) {
        this.endpointsList.add(name);
    }

    public List<String> getEndpointsList() {
        return endpointsList;
    }

    public void addCommonality(Commonality commonality) {
        this.commonalities.add(commonality);
    }

    public Collection<Commonality> getCommonalities() {
        return commonalities;
    }

    public Optional<Commonality> getCommonalityWithName(String name) {
        return commonalities.stream().filter(comm -> comm.getName().equals(name)).findFirst();
    }


    public String getTypedOverName() {
        return typedOverName;
    }

    public CorrSpec getTypedOver() {
        return typedOver;
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

    public Optional<Star> getFormalRepresentation() {
        return Optional.ofNullable(formalRepresentation);
    }

    public Map<String, GraphMorphism> getSchemaTypeEmbeddings() {
        return schemaTypeEmbeddings;
    }

    public Optional<Sketch> getComprehensiveSchema() {
        return Optional.ofNullable(comprehensiveSchema);
    }

    public void addFormalKey(Key key) {
        this.formalKeys.add(key);
    }

    public Set<Key> getFormalKeys() {
        return this.formalKeys;
    }

    public Collection<Commonality> allTransitiveCommonalities() {
        List<Commonality> all = new ArrayList<>();
        for (Commonality commonality : commonalities) {
            addCommsTransitively(commonality,all);
        }
        return all;
    }


    private static void addCommsTransitively(Commonality commonality, List<Commonality> result) {
        result.add(commonality);
        for (Commonality sub : commonality.getSubCommonalities()) {
            addCommsTransitively(sub, result);
        }
    }

    public void setComprSys(ComprSys comprSys) {
        this.comprSys = comprSys;
    }

    public ComprSys getComprSys() {
        return comprSys;
    }
}
