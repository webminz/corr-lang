package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

public class URLReference {

    private final String url;
    private String fullyResolvedUrl;
    private boolean isRetrieved;
    private boolean isLocal;
    private boolean isWeb;
    private boolean hasTLS;
    private File localFile;

    public URLReference(String url) {
        this.url = url;
    }

    public String getUrl() {
        return fullyResolvedUrl;
    }

    public void retrieve(FileSystemUtils base) throws IOException {
        if (!url.contains(":")) {
            if (url.startsWith("/")) {
                this.fullyResolvedUrl = "file://" + url;
            } else {
                this.fullyResolvedUrl = "file://" + base.file(url).getAbsolutePath();
            }
        } else {

            if (url.startsWith("classpath:")) {
               this.fullyResolvedUrl = "file://" +  base.file(url).getAbsolutePath();
            } else {
                this.fullyResolvedUrl = url;
            }

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
