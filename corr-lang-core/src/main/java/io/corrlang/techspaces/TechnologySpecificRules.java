package io.corrlang.techspaces;

import no.hvl.past.names.Name;
import no.hvl.past.names.PrintingStrategy;

import java.util.ArrayList;
import java.util.List;

public class TechnologySpecificRules {

    private final List<TechnologySpecificRule> rules = new ArrayList<>();

    public abstract static class TechnologySpecificRule {

        public abstract String message(String techSpace, String... endpoints);
    }


    public static class IdentifyAllWithName extends TechnologySpecificRule {

        private final Name typeName;


        public IdentifyAllWithName(Name typeName) {
            this.typeName = typeName;
        }

        public Name getTypeName() {
            return typeName;
        }

        @Override
        public String message(String techSpace, String... endpoints) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(techSpace);
            stringBuilder.append(" requires to identify all types with name '");
            stringBuilder.append(typeName.print(PrintingStrategy.IGNORE_PREFIX));
            stringBuilder.append("'. This applies to endpoints: ");
            for (String endpoint : endpoints) {
                stringBuilder.append(endpoint);
            }
            return stringBuilder.toString();
        }
    }

    public TechnologySpecificRules identifyTypesWithName(Name name) {
        this.rules.add(new IdentifyAllWithName(name));
        return this;
    }

    public List<TechnologySpecificRule> getRules() {
        return rules;
    }
}
