package io.corrlang.domain.builders;


import io.corrlang.domain.IntfcDesc;
import io.corrlang.domain.diagrams.*;
import no.hvl.past.attributes.DataOperation;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.elements.Tuple;
import no.hvl.past.graph.operations.Invert;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.names.Identifier;
import no.hvl.past.names.Name;
import no.hvl.past.util.Multiplicity;
import no.hvl.past.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SystemInterfaceDescriptionBuilder {

    private Name symbolicName;
    private final GraphBuilders graphBuilders;
    private final Map<Name, String> customName = new HashMap<>();
    private final Map<Name, GraphPredicate> valueTypeNodes = new HashMap<>();
    private final Set<Name> abstractTypeNodes = new HashSet<>();
    private final Set<Triple> orderedEdges = new HashSet<>();
    private final Set<Triple> uniqueEdges = new HashSet<>();
    private final Map<Triple, Multiplicity> multiplicities = new HashMap<>();
    private final Map<Name, Tuple> symmetricEdges = new HashMap<>();
    private final Set<Name> acyclicEdges = new HashSet<>();
    private final Map<Triple, Pair<String, DataOperation>> dataConstraints = new HashMap<>();

    private final Set<Triple> containmentEdges = new HashSet<>();
    private final Set<Name> actions = new HashSet<>();
    private final Set<Name> actionGroups = new HashSet<>();
    private final Set<Triple> actionGroupMemberships = new HashSet<>();
    private final Set<Triple> actionInputs = new HashSet<>();
    private final Set<Triple> actionOutputs = new HashSet<>();

    public SystemInterfaceDescriptionBuilder updateSymbolicName(String name) {
        symbolicName = Name.identifier(name);
        return this;
    }


    public class ObjectTypeBuilder {

        public class FieldBuilder {
            private final Name fieldName;
            private final Name targetTypeName;

            public FieldBuilder(Name fieldName, Name targetTypeName) {
                this.fieldName = fieldName;
                this.targetTypeName = targetTypeName;
            }

            public FieldBuilder multiplicity(Multiplicity mult) {
                multiplicities.put(Triple.edge(
                        ObjectTypeBuilder.this.typeName,
                        fieldName,
                        targetTypeName), mult);
                return this;
            }

            public FieldBuilder listSemantics() {
                orderedEdges.add(Triple.edge(
                        ObjectTypeBuilder.this.typeName,
                        fieldName,
                        targetTypeName));
                return this;
            }

            public FieldBuilder setSemantics() {
                uniqueEdges.add(Triple.edge(
                        ObjectTypeBuilder.this.typeName,
                        fieldName,
                        targetTypeName));
                return this;
            }

            public FieldBuilder makeHierarchy() {
                if (targetTypeName.equals(ObjectTypeBuilder.this.typeName)) {
                    acyclicEdges.add(fieldName);
                }
                return this;
            }

            public FieldBuilder orderedSetSemantics() {
                orderedEdges.add(Triple.edge(
                        ObjectTypeBuilder.this.typeName,
                        fieldName,
                        targetTypeName));
                uniqueEdges.add(Triple.edge(
                        ObjectTypeBuilder.this.typeName,
                        fieldName,
                        targetTypeName));
                return this;
            }

            public FieldBuilder makeContainment() {
                containmentEdges.add(Triple.edge(
                        ObjectTypeBuilder.this.typeName,
                        fieldName,
                        targetTypeName));
                if (targetTypeName.equals(ObjectTypeBuilder.this.typeName)) {
                    acyclicEdges.add(fieldName);
                }
                return this;
            }

            public FieldBuilder makeContainmentWithParentRef(String backRefRoleName) {
                containmentEdges.add(Triple.edge(
                        ObjectTypeBuilder.this.typeName,
                        fieldName,
                        targetTypeName));
                Name backRefName = Name.identifier(backRefRoleName).prefixWith(targetTypeName);
                graphBuilders.edge(targetTypeName, backRefName, ObjectTypeBuilder.this.typeName);
                symmetricEdges.put(fieldName, new Tuple(fieldName, backRefName));
                multiplicities.put(
                        Triple.edge(targetTypeName, backRefName, ObjectTypeBuilder.this.typeName),
                        Multiplicity.of(1, 1));
                if (targetTypeName.equals(ObjectTypeBuilder.this.typeName)) {
                    acyclicEdges.add(fieldName);
                }
                return this;
            }

            public FieldBuilder makeSymmetric(String reverseRoleName, String associationName) {
                Name backRefName = Name.identifier(reverseRoleName).prefixWith(targetTypeName);
                graphBuilders.edge(targetTypeName, backRefName, ObjectTypeBuilder.this.typeName);
                symmetricEdges.put(Name.identifier(associationName), new Tuple(fieldName, backRefName));
                return this;
            }

            public FieldBuilder makeSymmetric(String reverseRoleName,
                                              String associationName,
                                              Multiplicity reverseMultiplity,
                                              boolean reverseIsOrdered,
                                              boolean reverseIsUnique) {
                Name backRefName = Name.identifier(reverseRoleName).prefixWith(targetTypeName);
                graphBuilders.edge(targetTypeName, backRefName, ObjectTypeBuilder.this.typeName);
                symmetricEdges.put(Name.identifier(associationName), new Tuple(fieldName, backRefName));
                Triple backRefTriple = Triple.edge(targetTypeName, backRefName, ObjectTypeBuilder.this.typeName);
                multiplicities.put(backRefTriple, reverseMultiplity);
                if (reverseIsOrdered) {
                    orderedEdges.add(backRefTriple);
                }
                if (reverseIsUnique) {
                    uniqueEdges.add(backRefTriple);
                }
                return this;
            }

            public FieldBuilder constraint(DataOperation op, String representation) {
                dataConstraints.put(Triple.edge(
                        ObjectTypeBuilder.this.typeName,
                        fieldName,
                        targetTypeName), new Pair<>(representation, op));
                return this;
            }

            public ObjectTypeBuilder endField() {
                return ObjectTypeBuilder.this;
            }



        }


        private final Name typeName;

        public ObjectTypeBuilder(Name typeName) {
            this.typeName = typeName;
        }

        public ObjectTypeBuilder field(String name, String target) {
            Name lbl = Name.identifier(name).prefixWith(typeName);
            Name trg = Name.identifier(target);
            graphBuilders.edge(typeName, lbl, trg);
            return this;
        }

        public FieldBuilder buildField(String name, String target) {
            Name lbl = Name.identifier(name).prefixWith(typeName);
            Name trg = Name.identifier(target);
            graphBuilders.edge(typeName, lbl, trg);
            return new FieldBuilder(lbl, trg);
        }

        public ObjectTypeBuilder makeAbstract() {
            SystemInterfaceDescriptionBuilder.this.abstractTypeNodes.add(typeName);
            return this;
        }

        public ObjectTypeBuilder inheritsFrom(String supertype) {
            graphBuilders.map(typeName, Name.identifier(supertype));
            return this;
        }



        public SystemInterfaceDescriptionBuilder endObjectType() {
            return SystemInterfaceDescriptionBuilder.this;
        }

    }

    public class ActionBuilder {


        public class ActionArgBuilder {
            private final Name fieldName;
            private final Name targetTypeName;

            public ActionArgBuilder(Name fieldName, Name targetTypeName) {
                this.fieldName = fieldName;
                this.targetTypeName = targetTypeName;
            }

            public ActionArgBuilder multiplicity(Multiplicity mult) {
                multiplicities.put(Triple.edge(ActionBuilder.this.actionName, fieldName, targetTypeName), mult);
                return this;
            }

            public ActionArgBuilder listSemantics() {
                orderedEdges.add(Triple.edge(ActionBuilder.this.actionName, fieldName, targetTypeName));
                return this;
            }

            public ActionArgBuilder setSemantics() {
                uniqueEdges.add(Triple.edge(ActionBuilder.this.actionName, fieldName, targetTypeName));
                return this;
            }

            public ActionBuilder endArgument() {
                return ActionBuilder.this;
            }
        }


        private final Name actionName;
        private final ActionGroupBuilder actionGroup;
        private AtomicInteger inArgCounter = new AtomicInteger();
        private AtomicInteger outArgCounter = new AtomicInteger();

        public ActionBuilder(Name actionName, ActionGroupBuilder actionGroup) {
            this.actionName = actionName;
            this.actionGroup = actionGroup;
        }

        public ActionBuilder inputArg(String name, String targetName) {
            Name lbl = Name.identifier(name).prefixWith(actionName).index(inArgCounter.incrementAndGet());
            graphBuilders.edge(actionName, lbl, Name.identifier(targetName));
            actionInputs.add(Triple.edge(actionName, lbl, Name.identifier(targetName)));
            return this;
        }

        public ActionArgBuilder buildInputArg(String name, String targetName) {
            Name lbl = Name.identifier(name).prefixWith(actionName).index(inArgCounter.incrementAndGet());
            graphBuilders.edge(actionName, lbl, Name.identifier(targetName));
            actionInputs.add(Triple.edge(actionName, lbl, Name.identifier(targetName)));
            return new ActionArgBuilder(lbl, Name.identifier(targetName));
        }

        public ActionBuilder outputArg(String name, String targetName) {
            Name lbl = Name.identifier(name).prefixWith(actionName).index(outArgCounter.incrementAndGet());
            graphBuilders.edge(actionName, lbl, Name.identifier(targetName));
            actionOutputs.add(Triple.edge(actionName, lbl, Name.identifier(targetName)));
            return this;
        }

        public ActionArgBuilder buildOutputArg(String name, String targetName) {
            Name lbl = Name.identifier(name).prefixWith(actionName).index(outArgCounter.incrementAndGet());
            graphBuilders.edge(actionName, lbl, Name.identifier(targetName));
            actionOutputs.add(Triple.edge(actionName, lbl, Name.identifier(targetName)));
            return new ActionArgBuilder(lbl, Name.identifier(targetName));
        }

        public ActionGroupBuilder endActionAndBackToGroup() {
            if (actionGroup != null) {
                return actionGroup;
            }
            throw new RuntimeException("API usage error: This action builder has no parent group!");
        }

        public SystemInterfaceDescriptionBuilder end() {
            return SystemInterfaceDescriptionBuilder.this;
        }
    }

    public class ActionGroupBuilder {
        private final Name actionGroupName;
        private ActionGroupBuilder parentGroup;

        public ActionGroupBuilder(Name actionGroupName) {
            this.actionGroupName = actionGroupName;
        }

        public ActionGroupBuilder(Name actionGroupName, ActionGroupBuilder parentGroup) {
            this.actionGroupName = actionGroupName;
            this.parentGroup = parentGroup;
        }

        public ActionBuilder action(String actionName) {
            Name id = Name.identifier(actionName).prefixWith(actionGroupName);
            actions.add(id);
            return new ActionBuilder(id, this);
        }

        public ActionGroupBuilder startChildGroup(String childName, boolean prefixWithParent) {
            Name child;
            if (prefixWithParent) {
                child = Name.identifier(childName).prefixWith(actionGroupName);
            } else {
                child = Name.identifier(childName);
            }
            graphBuilders.node(child);
            actionGroups.add(child);
            Name label = child.childOf(actionGroupName);
            actionGroupMemberships.add(Triple.edge(actionGroupName, label, child));
            graphBuilders.edge(actionGroupName, label, child);
            return new ActionGroupBuilder(child, this);
        }

        public ActionGroupBuilder endCurrentGroup() {
            if (parentGroup != null) {
                return parentGroup;
            }
            throw new RuntimeException("API usage error: This action group builder has no parent group!");
        }

        public SystemInterfaceDescriptionBuilder end() {
            return SystemInterfaceDescriptionBuilder.this;
        }
    }




    public SystemInterfaceDescriptionBuilder(Name symbolicName, Universe universe) {
        this.graphBuilders = new GraphBuilders(universe, true, false);
        this.symbolicName = symbolicName;
    }

    public SystemInterfaceDescriptionBuilder stringValueType(String name) {
        graphBuilders.node(Name.identifier(name));
        this.valueTypeNodes.put(Name.identifier(name), StringDT.getInstance());
        return this;
    }


    public SystemInterfaceDescriptionBuilder floatValueType(String name) {
        graphBuilders.node(Name.identifier(name));
        this.valueTypeNodes.put(Name.identifier(name), FloatDT.getInstance());
        return this;
    }


    public SystemInterfaceDescriptionBuilder intValueType(String name) {
        graphBuilders.node(Name.identifier(name));
        this.valueTypeNodes.put(Name.identifier(name), IntDT.getInstance());
        return this;
    }


    public SystemInterfaceDescriptionBuilder boolValueType(String name) {
        graphBuilders.node(Name.identifier(name));
        this.valueTypeNodes.put(Name.identifier(name), BoolDT.getInstance());
        return this;
    }

    public SystemInterfaceDescriptionBuilder enumValueType(String name, Class<? extends Enum<?>> enumClass) {
        graphBuilders.node(Name.identifier(name));
        List<Name> values = new ArrayList<>();
        for (Enum<?> e : enumClass.getEnumConstants()) {
            values.add(Name.identifier(e.name()));
        }
        this.valueTypeNodes.put(Name.identifier(name), EnumValue.getInstance(values));
        return this;
    }


    public SystemInterfaceDescriptionBuilder customValueType(String name, DataTypePredicate dataTypePredicate) {
        graphBuilders.node(Name.identifier(name));
        this.valueTypeNodes.put(Name.identifier(name), dataTypePredicate);
        return this;
    }

    public ObjectTypeBuilder objectType(String name) {
        Name identifier = Name.identifier(name);
        graphBuilders.node(identifier);
        return new ObjectTypeBuilder(identifier);
    }

    public ActionBuilder action(String actionName) {
        Identifier id = Name.identifier(actionName);
        actions.add(id);
        return new ActionBuilder(id, null);
    }

    public ActionGroupBuilder actionGroup(String actionGroupName) {
        Identifier id = Name.identifier(actionGroupName);
        actionGroups.add(id);
        return new ActionGroupBuilder(id);
    }

    Sketch buildUnderlyingSketch() {
        graphBuilders.inheritanceGraph(symbolicName.absolute());

        // base types
        for (Name baseTypeName : valueTypeNodes.keySet()) {
            graphBuilders.startDiagram(valueTypeNodes.get(baseTypeName));
            graphBuilders.map(Universe.ONE_NODE_THE_NODE, baseTypeName);
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }
        // abstract types
        for (Name absractTypeName : abstractTypeNodes) {
            graphBuilders.startDiagram(AbstractType.getInstance());
            graphBuilders.map(Universe.ONE_NODE_THE_NODE, absractTypeName);
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // multiplicities
        for (Triple multEdgeName : multiplicities.keySet()) {
            graphBuilders.startDiagram(TargetMultiplicity.getInstance(multiplicities.get(multEdgeName)));
            graphBuilders.map(Universe.ARROW_SRC_NAME, multEdgeName.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, multEdgeName.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, multEdgeName.getTarget());
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // ordered semantics references
        for (Triple ord : orderedEdges) {
            graphBuilders.startDiagram(Ordered.getInstance());
            graphBuilders.map(Universe.ARROW_SRC_NAME, ord.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, ord.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, ord.getTarget());
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // unique semantics references
        for (Triple uniq : uniqueEdges) {
            graphBuilders.startDiagram(Unique.getInstance());
            graphBuilders.map(Universe.ARROW_SRC_NAME, uniq.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, uniq.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, uniq.getTarget());
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // symmetric associations
        for (Name symm : symmetricEdges.keySet()) {
            Tuple t = symmetricEdges.get(symm);
            Name left = t.getDomain().firstPart();
            Name right = t.getCodomain().firstPart();
            Name fwd = t.getDomain();
            Name bwd = t.getCodomain();
            graphBuilders.startDiagram(Invert.getInstance());
            graphBuilders.map(Universe.CYCLE_FWD.getSource(), left);
            graphBuilders.map(Universe.CYCLE_FWD.getTarget(), right);
            graphBuilders.map(Universe.CYCLE_FWD.getLabel(), fwd);
            graphBuilders.map(Universe.CYCLE_BWD.getLabel(), bwd);
            graphBuilders.endDiagram(symm);

        }

        // acyclic references
        for (Name acycl : acyclicEdges) {
            graphBuilders.startDiagram(Acyclicity.getInstance());
            graphBuilders.map(Universe.LOOP_THE_LOOP.getSource(), acycl.firstPart());
            graphBuilders.map(Universe.LOOP_THE_LOOP.getLabel(), acycl);
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // Data constraints
        for (Triple dc : dataConstraints.keySet()) {
            graphBuilders.startDiagram(AttributePredicate.getInstance(dataConstraints.get(dc).getRight()));
            graphBuilders.map(Universe.ARROW_SRC_NAME, dc.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, dc.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, dc.getTarget());
            graphBuilders.endDiagram(Name.identifier(dataConstraints.get(dc).getFirst()));
        }

        // containments
        for (Triple contnmt : containmentEdges) {
            graphBuilders.startDiagram(ContainmentMarker.getInstance());
            graphBuilders.map(Universe.ARROW_SRC_NAME, contnmt.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, contnmt.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, contnmt.getTarget());
            graphBuilders.endDiagram(Name.anonymousIdentifier());

            // TODO when there are multiple possible candidates, an xor has to be created
            graphBuilders.startDiagram(SourceMultiplicity.getInstance(1,1));
            graphBuilders.map(Universe.ARROW_SRC_NAME, contnmt.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, contnmt.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, contnmt.getTarget());
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // Action Types
        for (Name action : actions) {
            graphBuilders.startDiagram(ActionMarker.getInstance());
            graphBuilders.map(Universe.ONE_NODE_THE_NODE, action);
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // Action inputs
        for (Triple actionInput : actionInputs) {
            graphBuilders.startDiagram(ActionInputMarker.getInstance());
            graphBuilders.map(Universe.ARROW_SRC_NAME, actionInput.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, actionInput.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, actionInput.getTarget());
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // Action outputs
        for (Triple actionOutput : actionOutputs) {
            graphBuilders.startDiagram(ActionOutputMarker.getInstance());
            graphBuilders.map(Universe.ARROW_SRC_NAME, actionOutput.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, actionOutput.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, actionOutput.getTarget());
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // Action groups
        for (Name actionGroup : actionGroups) {
            graphBuilders.startDiagram(ActionGroupMarker.getInstance());
            graphBuilders.map(Universe.ONE_NODE_THE_NODE, actionGroup);
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }

        // Action group memberships
        for (Triple agMember : actionGroupMemberships) {
            graphBuilders.startDiagram(ActionGroupChildMarker.getInstance());
            graphBuilders.map(Universe.ARROW_SRC_NAME, agMember.getSource());
            graphBuilders.map(Universe.ARROW_LBL_NAME, agMember.getLabel());
            graphBuilders.map(Universe.ARROW_TRG_NAME, agMember.getTarget());
            graphBuilders.endDiagram(Name.anonymousIdentifier());
        }


        graphBuilders.sketch(symbolicName);
        return graphBuilders.getResult(Sketch.class);
    }


    public IntfcDesc build() {
        return new IntfcDesc(buildUnderlyingSketch(), this.customName);
    }


}
