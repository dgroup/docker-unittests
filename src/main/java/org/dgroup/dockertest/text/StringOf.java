package org.dgroup.dockertest.text;

import one.util.streamex.StreamEx;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.yml.YmlTagOutputPredicate;

import java.util.List;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class StringOf {

    private final Iterable<String> values;
    private final CharSequence delimiter;

    public StringOf(List<YmlTagOutputPredicate> conditions, String delimiter) {
        this(
                new Mapped<>(conditions, Object::toString),
                delimiter
        );
    }

    public StringOf(Iterable<String> values, String delimiter) {
        this.values = values;
        this.delimiter = delimiter;
    }

    public StringOf(String[] cmd) {
        this(new IterableOf<>(cmd), " ");
    }


    public String asString() {
        return StreamEx.of(values.iterator())
                .joining(delimiter == null ? "" : delimiter);
    }

    @Override
    public String toString() {
        return asString();
    }
}