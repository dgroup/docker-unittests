/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.dgroup.dockertest.text;

import java.util.Collection;
import org.cactoos.list.ListOf;
import org.cactoos.text.UncheckedText;

/**
 * Represents a formatted {@link Text} which didn't throw the
 * exception and verify amount of arguments passed to pattern.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TextOf implements Text {

    /**
     * String pattern for formatting.
     */
    private final String pattern;
    /**
     * Arguments, which should be used for patern.
     */
    private final Collection<Object> args;

    /**
     * Ctor.
     * @param pattern Template.
     * @param args Arguments for template above.
     */
    public TextOf(final String pattern, final Object... args) {
        this(pattern, new ListOf<>(args));
    }

    /**
     * Ctor.
     * @param pattern Template.
     * @param args Arguments for template above.
     */
    public TextOf(final String pattern, final Collection<Object> args) {
        this.pattern = pattern;
        this.args = args;
    }

    @Override
    public String text() {
        new OccuredIn("%s", this.pattern).times(
            this.args.size(),
            String.format(
                "Wrong amount of arguments(%s) for pattern '%s'.",
                this.args.size(), this.pattern
            )
        );
        return new UncheckedText(
            new org.cactoos.text.FormattedText(
                this.pattern, this.args
            )
        ).asString();
    }

    @Override
    public String toString() {
        return this.text();
    }

}
