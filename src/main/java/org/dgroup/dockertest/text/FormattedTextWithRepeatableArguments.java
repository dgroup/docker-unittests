package org.dgroup.dockertest.text;

import lombok.AllArgsConstructor;
import org.cactoos.list.ListOf;

import java.text.MessageFormat;
import java.util.Collection;

@AllArgsConstructor
public class FormattedTextWithRepeatableArguments {

    private final String pattern;
    private final Collection<Object> args;

    public FormattedTextWithRepeatableArguments(String pattern, Object... args) {
        this(pattern, new ListOf<>(args));
    }

    public String asString() {
        return MessageFormat.format(pattern, args.toArray());
    }
}