package org.dgroup.dockertest.text;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cactoos.list.ListOf;
import org.cactoos.text.UncheckedText;

import java.util.Collection;

@Slf4j
@AllArgsConstructor
public final class PlainFormattedText {

    private final String pattern;
    private final Collection<Object> args;

    public PlainFormattedText(String pattern, Object... args) {
        this(pattern, new ListOf<>(args));
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
