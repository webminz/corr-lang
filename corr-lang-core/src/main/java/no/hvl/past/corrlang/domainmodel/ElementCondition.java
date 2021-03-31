package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.graph.Graph;
import no.hvl.past.keys.ConcatenatedKey;
import no.hvl.past.keys.ConstantKey;
import no.hvl.past.keys.Key;
import no.hvl.past.names.Name;

import java.util.*;

/**
 * Keys provide a mechanism to identify elements quickly,
 * this is used to relate elements from different models.
 */
public abstract class ElementCondition extends CorrLangElement {

    public abstract boolean isPureKey();

    public abstract Set<Key> asKeys(Name targetType, Graph carrier);

    /**
     * Key Alternatives allow elements to be identified by two or more keys.
     */
    public static class Alternative extends ElementCondition {

        private List<AlternativeOption> arguments;

        public Alternative() {
            this.arguments = new ArrayList<>();
        }

        @Override
        public void accept(SyntaxVisitor visitor) throws Throwable {
            for (AlternativeOption argument : arguments) {
                argument.accept(visitor);
            }
        }

        public List<AlternativeOption> getArguments() {
            return arguments;
        }

        public void add(AlternativeOption argument) {
            this.arguments.add(argument);
        }

        @Override
        public boolean equals(Object obj) {
            if (arguments.size() == 1) {
                return arguments.get(0).equals(obj);
            }
            if (obj instanceof Alternative) {
                Alternative other = (Alternative) obj;
                return new HashSet<>(arguments).equals(new HashSet<>(other.arguments));
            }
            return false;
        }

        @Override
        public int hashCode() {
            if (arguments.size() == 1) {
                return arguments.get(0).hashCode();
            } else {
                return new HashSet<>(arguments).hashCode();
            }
        }

        @Override
        public boolean isPureKey() {
            return this.arguments.stream().allMatch(ElementCondition::isPureKey);
        }

        @Override
        public Set<Key> asKeys(Name targetType,Graph carrier) {
            Set<Key> result = new HashSet<>();
            for (AlternativeOption option : this.arguments) {
                if (option.isPureKey()) {
                    result.addAll(option.asKeys(targetType, carrier));
                }
            }
            return result;
        }
    }

    public static abstract class AlternativeOption extends ElementCondition {}

    /**
     * Composite keys are used to make keys more unique when the sub-keys alone are not sufficient.
     */
    public static class Conjunction extends AlternativeOption {

        private List<ConditionLiteral> literals;

        public Conjunction() {
            this.literals = new ArrayList<>();
        }

        @Override
        public void accept(SyntaxVisitor visitor) throws Throwable {
            for (ConditionLiteral literal : literals) {
                literal.accept(visitor);
            }
        }

        public List<ConditionLiteral> getLiterals() {
            return literals;
        }

        public void add(ConditionLiteral literal) {
            this.literals.add(literal);
        }

        @Override
        public boolean equals(Object obj) {
            if (literals.size() == 1) {
                return literals.get(0).equals(obj);
            }
            if (obj instanceof Alternative) {
                return obj.equals(this);
            }
            if (obj instanceof Conjunction) {
                Conjunction other = (Conjunction) obj;
                return new HashSet<>(literals).equals(new HashSet<>(other.literals));
            }
            return false;
        }

        @Override
        public int hashCode() {
            if (this.literals.size() == 1) {
                return this.literals.get(0).hashCode();
            }
            return new HashSet<>(this.literals).hashCode();
        }

        @Override
        public boolean isPureKey() {
            return this.literals.stream().allMatch(ElementCondition::isPureKey);
        }

        @Override
        public Set<Key> asKeys(Name targetType, Graph carrier) {
            if (this.literals.size() == 1) {
                return this.literals.get(0).asKeys(targetType, carrier);
            }
            // TODO implement correctly
            return new HashSet<>();
        }
    }

    public static abstract class ConditionLiteral extends AlternativeOption {


        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Alternative) {
                return obj.equals(this);
            }
            if (obj instanceof Conjunction) {
                return obj.equals(this);
            }
            if (obj instanceof ConditionLiteral) {
                return transitiveEquals((ConditionLiteral) obj);
            }
            return false;
        }

        protected abstract boolean transitiveEquals(ConditionLiteral obj);
    }


    /**
     * A Key literal is an atomic key.
     */
    public static class Identification extends ConditionLiteral {

        private List<IdentificationArgument> arguments;

        public Identification(IdentificationArgument... args) {
            this.arguments = new ArrayList<>(Arrays.asList(args));
        }

        public Identification() {
            this.arguments = new ArrayList<>();
        }

        @Override
        public void accept(SyntaxVisitor visitor) throws Throwable {
            visitor.handle(this);
            for (IdentificationArgument arg : this.arguments) {
                arg.accept(visitor);
            }
        }

        public List<IdentificationArgument> getArguments() {
            return arguments;
        }

        public void add(IdentificationArgument argument) {
            this.arguments.add(argument);
        }

        @Override
        public int hashCode() {
            return new HashSet<>(this.arguments).hashCode();
        }

        @Override
        protected boolean transitiveEquals(ConditionLiteral obj) {
            if (obj instanceof Identification) {
                Identification other = (Identification) obj;
                return new HashSet<>(arguments).equals(new HashSet<>(other.arguments));
            }
            return false;
        }

        @Override
        public boolean isPureKey() {
            return true;
        }

        @Override
        public Set<Key> asKeys(Name targetType, Graph carrier) {
            Set<Key> result = new HashSet<>();
            for (IdentificationArgument arg : this.arguments) {
                result.add(arg.toKey(targetType, carrier));
            }
            return result;
        }
    }

    public interface IdentificationArgument {
        Key toKey(Name targetType, Graph carrier);

        void accept(SyntaxVisitor visitor) throws Throwable;
    }

    public static class ArgumentConcatenation implements IdentificationArgument {

        private final List<IdentificationArgument> parts;

        public ArgumentConcatenation(IdentificationArgument... args) {
            this.parts = new ArrayList<>(Arrays.asList(args));
        }

        public ArgumentConcatenation() {
            this.parts = new ArrayList<>();
        }

        public List<IdentificationArgument> getParts() {
            return parts;
        }

        public void add(IdentificationArgument part) {
            this.parts.add(part);
        }

        @Override
        public boolean equals(Object obj) {
            if (parts.size() == 1) {
                return parts.get(0).equals(obj);
            }
            if (obj instanceof ArgumentConcatenation) {
                ArgumentConcatenation concatenation = (ArgumentConcatenation) obj;
                return this.parts.equals(concatenation.parts);
            }
            return false;
        }

        @Override
        public int hashCode() {
            if (parts.size() == 1) {
                return parts.get(0).hashCode();
            }
            return parts.hashCode();
        }

        @Override
        public Key toKey(Name targetType, Graph carrier) {
            List<Key> subs = new ArrayList<>();
            for (IdentificationArgument arg : this.parts) {
                subs.add(arg.toKey(targetType, carrier));
            }
            return new ConcatenatedKey(carrier, targetType, subs);
        }

        @Override
        public void accept(SyntaxVisitor visitor) throws Throwable {
            for (IdentificationArgument arg : this.parts) {
                arg.accept(visitor);
            }
        }
    }

    public static class ConstantArgument implements IdentificationArgument {

        @Override
        public Key toKey(Name targetType, Graph carrier) {
            Name val;
            switch (type) {
                case INT:
                    val = Name.value(Long.parseLong(value));
                    break;
                case BOOL:
                    val = this.value.equals("true") ? Name.trueValue() : Name.falseValue();
                    break;
                case FLOAT:
                    val = Name.value(Double.parseDouble(value));
                    break;
                case ENUM_LITERAL:
                    val = Name.identifier(value);
                    break;
                case STRING:
                default:
                    val = Name.value(this.value);
                    break;
            }
            return new ConstantKey(carrier, val, targetType);
        }

        @Override
        public void accept(SyntaxVisitor visitor) throws Throwable {
        }

        public enum Type{
            INT,
            STRING,
            FLOAT,
            BOOL,
            ENUM_LITERAL
        }

        private String value;
        private Type type;

        public ConstantArgument(String value, Type type) {
            this.value = value;
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConstantArgument that = (ConstantArgument) o;
            return value.equals(that.value) &&
                    type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, type);
        }
    }

    public interface RelationRulePart {
        void accept(SyntaxVisitor visitor) throws Throwable;
    }

    public static class RelationRule extends ConditionLiteral implements RelationRulePart {

        @Override
        public boolean isPureKey() {
            return false;
        }

        @Override
        public Set<Key> asKeys(Name targetType, Graph carrier) {
            return Collections.emptySet(); // rules cannot become keys
        }

        public enum Direction {
            SYMM,
            LR,
            RL
        }

        private ElementRef left;
        private Direction direction;
        private RelationRulePart right;


        public ElementRef getLeft() {
            return left;
        }

        public void setLeft(ElementRef left) {
            this.left = left;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public RelationRulePart getRight() {
            return right;
        }

        public void setRight(RelationRulePart right) {
            this.right = right;
        }

        @Override
        protected boolean transitiveEquals(ConditionLiteral obj) {
            if (obj instanceof RelationRule) {
                RelationRule other = (RelationRule) obj;
                return this.getLeft().equals(other.getLeft()) && getDirection().equals(other.getDirection()) && getRight().equals(other.getRight());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getLeft(), getDirection(), getRight());
        }

        @Override
        public void accept(SyntaxVisitor visitor) throws Throwable {
            visitor.handle(this);
            this.left.accept(visitor);
            this.right.accept(visitor);
        }
    }




}
