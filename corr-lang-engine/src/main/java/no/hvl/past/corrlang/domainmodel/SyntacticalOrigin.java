package no.hvl.past.corrlang.domainmodel;

public class SyntacticalOrigin {

    private String file = "$IN";
    private int startTokenLine = -1;
    private int startTokenColumn = -1;
    private int stopTokenLine = -1;
    private int stopTokenColumn = -1;

    public SyntacticalOrigin(int startTokenLine, int startTokenColumn) {
        this.startTokenLine = startTokenLine;
        this.startTokenColumn = startTokenColumn;
    }

    public SyntacticalOrigin(int startTokenLine, int startTokenColumn, int stopTokenLine, int stopTokenColumn) {
        this.startTokenLine = startTokenLine;
        this.startTokenColumn = startTokenColumn;
        this.stopTokenLine = stopTokenLine;
        this.stopTokenColumn = stopTokenColumn;
    }

    public SyntacticalOrigin(String file, int startTokenLine, int startTokenColumn, int stopTokenLine, int stopTokenColumn) {
        this.file = file;
        this.startTokenLine = startTokenLine;
        this.startTokenColumn = startTokenColumn;
        this.stopTokenLine = stopTokenLine;
        this.stopTokenColumn = stopTokenColumn;
    }

    public SyntacticalOrigin(String file, int startTokenLine, int startTokenColumn) {
        this.file = file;
        this.startTokenLine = startTokenLine;
        this.startTokenColumn = startTokenColumn;
    }


    public String reportElement() {
        StringBuilder result = new StringBuilder();
        result.append("(");
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
                if (stopTokenColumn >= 0) {
                    result.append(':');
                    result.append(stopTokenColumn);
                }
            }
        }
        result.append(")");
        return result.toString();
    }


}
