package no.hvl.past.corrlang.domainmodel;

import java.io.File;

public class URLReference {

    private final String url;
    private boolean isRetrieved;
    private boolean isLocal;
    private boolean isWeb;
    private boolean hasTLS;
    private File localFile;

    public URLReference(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    // TODO move this into FileSystemUtils
    public void retrieve(File base) {
        if (url.startsWith(".")) {
            // TODO retrieve relative
        } else if (url.startsWith("file:///") || url.startsWith("file://localhost/") || url.startsWith("file://127.0.0.1/")) {
            isLocal = true;
            // TODO retrieve absolute
        } else if (url.startsWith("file://")) {
            // TODO remote file
        } else if (url.startsWith("http")) {
            // TODO web resource
        } else if (url.startsWith("platform")) {
            // TODO eclipse resource
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof URLReference) {
            URLReference other = (URLReference) obj;
            return this.url.equals(other.url);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.url.hashCode();
    }

    @Override
    public String toString() {
        return "URL: '"+ this.url + "'";
    }
}
