package no.hvl.past.corrlang.domainmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Commonality extends CorrLangElement {

    private List<ElementRef> relates;
    private Key key;
    private String consistencyRuleName;
    private Collection<Commonality> subCommonalities;


    public Commonality(String name) {
        super(name);
        this.relates = new ArrayList<>();
        this.subCommonalities = new ArrayList<>();
    }

    public List<ElementRef> getRelates() {
        return relates;
    }

    public Key getKey() {
        return key;
    }

    public String getConsistencyRuleName() {
        return consistencyRuleName;
    }

    public Collection<Commonality> getSubCommonalities() {
        return subCommonalities;
    }

    public void addRelatedElement(ElementRef ref) {
        this.relates.add(ref);
    }

    public void addSubCommonality(Commonality currentCommonality) {
        this.subCommonalities.add(currentCommonality);
    }

    public void setKey(Key keyAlternative) {
        this.key = keyAlternative;
    }


}
