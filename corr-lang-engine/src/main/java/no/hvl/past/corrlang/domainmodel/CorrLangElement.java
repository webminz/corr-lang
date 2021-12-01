package no.hvl.past.corrlang.domainmodel;

/**
 * Super type of all CorrLang language elements.
 * By default every language element should have a name
 * and information about its origin (file,from line number/column, to line number/column).
 */
public abstract class CorrLangElement {

    private String name = "";
    private String file = "unavailable";
    private int startTokenLine = -1;
    private int startTokenColumn = -1;
    private int stopTokenLine = -1;
    private int getStopTokenColumn = -1;

    CorrLangElement() {}

    CorrLangElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void accept(SyntaxVisitor visitor) throws Throwable;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setStartTokenLine(int startTokenLine) {
        this.startTokenLine = startTokenLine;
    }

    public void setStartTokenColumn(int startTokenColumn) {
        this.startTokenColumn = startTokenColumn;
    }

    public void setStopTokenLine(int stopTokenLine) {
        this.stopTokenLine = stopTokenLine;
    }

    public void setStopTokenColumn(int getStopTokenColumn) {
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
        result.append(")");
        return result.toString();
    }
}
