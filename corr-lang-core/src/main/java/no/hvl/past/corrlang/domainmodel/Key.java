package no.hvl.past.corrlang.domainmodel;

import java.util.ArrayList;
import java.util.List;

public abstract class Key {

    public static class KeyAlternative extends Key {

        private List<KeyComposite> arguments;

        public KeyAlternative() {
            this.arguments = new ArrayList<>();
        }

        public List<KeyComposite> getArguments() {
            return arguments;
        }

        public void add(KeyComposite argument) {
            this.arguments.add(argument);
        }
    }

    public static class KeyComposite extends Key {

        private List<KeyLiteral> literals;

        public KeyComposite() {
            this.literals = new ArrayList<>();
        }

        public List<KeyLiteral> getLiterals() {
            return literals;
        }

        public void add(KeyLiteral literal) {
            this.literals.add(literal);
        }
    }

    public static class KeyConcatenation implements KeyLiteralArgument {

        private final List<KeyLiteralArgument> parts;

        public KeyConcatenation() {
            this.parts = new ArrayList<>();
        }

        public List<KeyLiteralArgument> getParts() {
            return parts;
        }

        public void add(KeyLiteralArgument part) {
            this.parts.add(part);
        }
    }

    public static class KeyConstant implements KeyLiteralArgument{

        public static enum Type{
            INT,
            STRING,
            FLOAT,
            BOOL,
            ENUM_LITERAL
        }

        private String value;
        private Type type;

        public KeyConstant(String value, Type type) {
            this.value = value;
            this.type = type;
        }
    }

    public static class KeyLiteral extends Key {

        public static enum Type {
            EQ,
            REL
        }

        private Type type;
        private List<KeyLiteralArgument> arguments;

        public KeyLiteral() {
            this.arguments = new ArrayList<>();
        }

        public void setType(Type type) {
            this.type = type;
        }

        public List<KeyLiteralArgument> getArguments() {
            return arguments;
        }

        public void add(KeyLiteralArgument argument) {
            this.arguments.add(argument);

        }
    }

    public static interface KeyLiteralArgument {
    }
}
