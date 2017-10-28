package org.dgroup.dockertest.text;

import org.cactoos.list.ListOf;
import org.cactoos.text.UncheckedText;

import java.util.Collection;


public final class PlainFormattedText {

    private final String pattern;
    private final Collection<Object> args;

    public PlainFormattedText(String pattern, Object... args) {
        this(pattern, new ListOf<>(args));
    }

    public PlainFormattedText(String pattern, Collection<Object> args){
        this.pattern = pattern;
        this.args = args;
    }

    public String asString() {
        if (new StringOccurrences(pattern, "%s").nonEqualTo(args.size()))
            throw new IllegalArgumentException(
                    "Wrong amount of arguments("+args.size()+") for pattern '"+pattern+"'."
            );

        return new UncheckedText(new org.cactoos.text.FormattedText(pattern, args))
                .asString();
    }

    @Override
    public String toString() {
        return asString();
    }
}
