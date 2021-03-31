package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Goal extends CorrLangElement {

    public enum Action {
        SCHEMA,
        FEDERATION,
        TRANSFORMATION,
        MATCH,
        VERIFY,
        SYNCHRONIZE
    }

    private String correspondenceName;
    private CorrSpec correspondence;
    private String technology;
    private TechSpace techSpace;
    private TechSpaceAdapter<? extends TechSpace> adapter;
    private String query;
    private Map<String, Object> params = new HashMap<>();
    private GoalTarget target;
    private Action action;

    public Goal(String name) {
        super(name);
    }

    public void setCorrespondenceName(String correspondenceName) {
        this.correspondenceName = correspondenceName;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setTarget(GoalTarget target) {
        this.target = target;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void linkTechSpace(TechSpace techSpace) {
        this.techSpace = techSpace;
    }

    public void linkTechSpaceAdapter(TechSpaceAdapter<? extends TechSpace> adapter) {
        this.adapter = adapter;
    }

    public void linkCorrespondence(CorrSpec correspondence) {
        this.correspondence = correspondence;
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    public String getCorrespondenceName() {
        return correspondenceName;
    }

    public CorrSpec getCorrespondence() {
        return correspondence;
    }

    public String getTechnology() {
        return technology;
    }

    public TechSpace getTechSpace() {
        return techSpace;
    }

    public TechSpaceAdapter<? extends TechSpace> getAdapter() {
        return adapter;
    }

    public Action getAction() {
        return action;
    }

    public Optional<Object> getParam(String key) {
        return Optional.ofNullable(this.params.get(key));
    }

    public Optional<String> getQuery() {
        return Optional.ofNullable(this.query);
    }

    public void forTarget(GoalTarget.Visitor handler) throws Throwable {
        this.target.accept(handler);
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }
}
