package io.corrlang.engine.domainmodel;

import io.corrlang.domain.Sys;
import io.corrlang.plugins.techspace.TechSpace;
import io.corrlang.plugins.techspace.TechSpaceAdapter;

import java.util.Optional;

public abstract class Endpoint extends CorrLangElement {

    private URLReference locationURL;
    private URLReference schemaURL;
    private String technology;
    private TechSpace techSpace;
    private TechSpaceAdapter<? extends TechSpace> techSpaceAdapter;
    private Sys system;


    Endpoint(String name) {
        super(name);
    }

    public void setLocationURL(URLReference locationURL) {
        this.locationURL = locationURL;
    }

    public void setSchemaURL(URLReference schemaURL) {
        this.schemaURL = schemaURL;
    }

    public URLReference getLocationURL() {
        return locationURL;
    }

    public Optional<URLReference> getSchemaURL() {
        return Optional.ofNullable(schemaURL);
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public void setLocationURL(String locationURL) {
        this.locationURL = new URLReference(locationURL);
    }

    public void setSchemaURL(String schemaURL) {
        this.schemaURL = new URLReference(schemaURL);
    }

    public Optional<TechSpaceAdapter<? extends TechSpace>> getAdaptor() {
        return Optional.ofNullable(this.techSpaceAdapter);
    }

    public void setAdaptor(TechSpaceAdapter<? extends TechSpace> adapter) {
        this.techSpaceAdapter = adapter;
    }


    public void setTechSpace(TechSpace techSpace) {
        this.techSpace = techSpace;
    }

    public Optional<TechSpace> getTechSpace() {
        return Optional.of(techSpace);
    }

    public void setSystem(Sys system) {
        this.system = system;
    }

    public Optional<Sys> getSystem() {
        return Optional.of(system);
    }
}
