package io.corrlang.domain;

import com.google.common.base.Objects;
import no.hvl.past.names.Name;
import no.hvl.past.util.StringUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ElemRef implements Iterable<String> {

    private final String[] path;

    public String head() {
        return path[0];
    }

    public ElemRef tail() {
        String[] p = Arrays.copyOfRange(path, 1, path.length);
        return new ElemRef(p);
    }

    private ElemRef(String[] path) {
        this.path = path;
    }


    public static ElemRef eref(List<String> path) {
        String[] p = new String[path.size()];
        path.toArray(p);
        return new ElemRef(p);
    }

    public static ElemRef eref(String... path) {
        return new ElemRef(path);
    }

    public Name asPrefixedName() {
        int i = this.path.length - 1;
        Name current = Name.identifier(this.path[1]);
        i--;
        while (i >= 0) {
            current.prefixWith(Name.identifier(this.path[i]));
            i--;
        }
        return current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElemRef elemRef = (ElemRef) o;
        return Objects.equal(path, elemRef.path);
    }

    @Override
    public String toString() {
        return StringUtils.fuseList(Arrays.stream(path), ".");
    }

    @Override
    public int hashCode() {
        return Objects.hashCode((Object[]) path);
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            int idx = 0;
            @Override
            public boolean hasNext() {
                return idx != path.length;
            }

            @Override
            public String next() {
                String result = path[idx];
                idx++;
                return result;
            }
        };
    }

    public int length() {
        return path.length;
    }

    /**
     * Checks if the given element reference is a suffix of this element reference.
     */
    public boolean suffixMatch(ElemRef suffix) {
        if (suffix.length() > this.length()) {
            return false;
        }
        int diff = this.length() - suffix.length();
        for (int i = this.length() - 1; i >= this.length() - suffix.length(); i--) {
            if (!java.util.Objects.equals(suffix.path[i - diff], this.path[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean prefixMatch(ElemRef prefix) {
        if (prefix.length() > this.length()) {
            return false;
        }
        for (int i = 0; i < prefix.length(); i++) {
            if (!java.util.Objects.equals(prefix.path[i], this.path[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean exactMatch(ElemRef other) {
        return Arrays.equals(this.path, other.path);
    }

    public ElemRef addPrefix(String prefix) {
        String[] newPath = new String[this.length() + 1];
        newPath[0] = prefix;
        System.arraycopy(this.path, 0, newPath, 1, this.length());
        return new ElemRef(newPath);
    }
}
