package no.hvl.past.corrlang.domainmodel;

public abstract class CorrLangElement {

    private String name;
    private String file = "unavailable";
    private int startTokenLine = -1;
    private int startTokenColumn = -1;
    private int stopTokenLine = -1;
    private int getStopTokenColumn = -1;

    CorrLangElement(String name) {
        this.name = name;
    }

    public abstract void accept(Visitor visitor);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getStartTokenLine() {
        return startTokenLine;
    }

    public void setStartTokenLine(int startTokenLine) {
        this.startTokenLine = startTokenLine;
    }

    public int getStartTokenColumn() {
        return startTokenColumn;
    }

    public void setStartTokenColumn(int startTokenColumn) {
        this.startTokenColumn = startTokenColumn;
    }

    public int getStopTokenLine() {
        return stopTokenLine;
    }

    public void setStopTokenLine(int stopTokenLine) {
        this.stopTokenLine = stopTokenLine;
    }

    public int getGetStopTokenColumn() {
        return getStopTokenColumn;
    }

    public void setGetStopTokenColumn(int getStopTokenColumn) {
        this.getStopTokenColumn = getStopTokenColumn;
    }

    public String reportElement() {
        StringBuilder result = new StringBuilder();
        result.append('\'');
        result.append(name);
        result.append("' (");
        result.append(file);
        if (startTokenLine >= 0) {
            result.append('|');
            result.append(startTokenLine);
            if (startTokenColumn >= 0) {
                result.append(':');
                result.append(startTokenColumn);
            }
            if (stopTokenLine >= 0) {
                result.append('-');
                result.append(startTokenLine);
                if (getStopTokenColumn >= 0) {
                    result.append(':');
                    result.append(getStopTokenColumn);
                }
            }
        }
        return result.toString();
    }
}
