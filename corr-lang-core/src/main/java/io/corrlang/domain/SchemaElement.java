package io.corrlang.domain;

public enum SchemaElement {
    MSG_TYP {
        @Override
        public boolean belongsToDynamicPart() {
            return true;
        }
    },
    MSG_IN {
        @Override
        public boolean belongsToDynamicPart() {
            return true;
        }
    },
    MSG_OUT {
        @Override
        public boolean belongsToDynamicPart() {
            return true;
        }
    },
    MSG_GROUP {
        @Override
        public boolean belongsToDynamicPart() {
            return true;
        }
    },
    MSG_CONTMNT {
        @Override
        public boolean belongsToDynamicPart() {
            return true;
        }
    },
    NODE {
        @Override
        public boolean belongsToDynamicPart() {
            return false;
        }
    },
    DATA_TYPE {
        @Override
        public boolean belongsToDynamicPart() {
            return false;
        }
    },
    LINK {
        @Override
        public boolean belongsToDynamicPart() {
            return false;
        }
    },
    PROP {
        @Override
        public boolean belongsToDynamicPart() {
            return false;
        }
    },
    HYPLINK {
        @Override
        public boolean belongsToDynamicPart() {
            return false;
        }
    };


    public abstract boolean belongsToDynamicPart();

    public boolean belongsToStaticPart() {
        return !belongsToDynamicPart();
    }
}
