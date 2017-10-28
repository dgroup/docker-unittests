package org.dgroup.dockertest.text;

import org.cactoos.list.ListOf;

import java.text.MessageFormat;
import java.util.Collection;


public class FormattedTextWithRepeatableArguments {

    private final String pattern;
    private final Collection<Object> args;

    public FormattedTextWithRepeatableArguments(String pattern, Object... args) {
        this(pattern, new ListOf<>(args));
    }

    public FormattedTextWithRepeatableArguments(String pattern, Collection<Object> args) {
        this.pattern = pattern;
        this.args = args;
    }

    public String asString() {
        return MessageFormat.format(pattern, args.toArray());
    }
}