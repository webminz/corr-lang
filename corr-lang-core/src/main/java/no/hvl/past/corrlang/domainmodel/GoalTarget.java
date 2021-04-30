package no.hvl.past.corrlang.domainmodel;

public abstract class GoalTarget {

    public interface Visitor {

        void handle(ServerRuntime serverRuntime) throws Exception;

        void handle(CodeGeneration codeGeneration) throws Exception;

        void handle(FileCreation fileCreation) throws Exception;

        void handle(Batch batch) throws Exception;

    }

    public abstract void accept(Visitor visitor) throws Exception;


    public static class ServerRuntime extends GoalTarget {

        private int port;
        private String contextPath;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getContextPath() {
            return contextPath;
        }

        public void setContextPath(String contextPath) {
            this.contextPath = contextPath;
        }

        @Override
        public void accept(Visitor visitor) throws Exception {
            visitor.handle(this);
        }
    }

    public static class CodeGeneration extends GoalTarget {
        private URLReference location;
        private String technology;
        private String artefactId = "app";
        private String groupId = "com.example";
        private String version = "1.0";

        public URLReference getLocation() {
            return location;
        }

        public String getTechnology() {
            return technology;
        }

        public void setTechnology(String technology) {
            this.technology = technology;
        }

        public void setLocation(String location) {
            this.location = new URLReference(location);
        }

        public String getArtefactId() {
            return artefactId;
        }

        public void setArtefactId(String artefactId) {
            this.artefactId = artefactId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public void accept(Visitor visitor) throws Exception {
            visitor.handle(this);
        }
    }

    public static class FileCreation extends GoalTarget {
        private URLReference location;
        private boolean overwrite = true;

        public URLReference getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = new URLReference(location);
        }

        public boolean isOverwrite() {
            return overwrite;
        }

        public void setOverwrite(boolean overwrite) {
            this.overwrite = overwrite;
        }

        @Override
        public void accept(Visitor visitor) throws Exception {
            visitor.handle(this);
        }
    }

    public static class Batch extends GoalTarget {

        static Batch instance = new Batch();

        private Batch() {
        }

        @Override
        public void accept(Visitor visitor) throws Exception {
            visitor.handle(this);

        }
    }

    public static ServerRuntime server() {
        return new ServerRuntime();
    }

    public static CodeGeneration codegen() {
        return new CodeGeneration();
    }

    public static FileCreation file() {
        return new FileCreation();
    }

    public static Batch batch() {
        return Batch.instance;
    }
}
