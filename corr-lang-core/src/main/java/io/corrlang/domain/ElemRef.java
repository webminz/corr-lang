package io.corrlang.domain;

import com.google.common.base.Objects;
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
}
