package org.dgroup.dockertest.yml;

import org.dgroup.dockertest.text.PlainFormattedText;

import java.util.function.Predicate;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class YmlTagOutputPredicate {

    private final String type;
    private final String expected;
    private final Predicate<String> predicate;

    public YmlTagOutputPredicate(String type, String expected, Predicate<String> predicate) {
        this.type = type;
        this.expected = expected;
        this.predicate = predicate;
    }

    public String type() {
        return type;
    }

    public boolean test(String actualValue) {
        return predicate.test(actualValue);
    }

    @Override
    public String toString() {
        return new PlainFormattedText("%s=`%s`", type, expected)
                .asString();
    }
}