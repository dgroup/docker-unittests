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
package com.github.dgroup.dockertest;

import com.github.dgroup.dockertest.io.Property;
import com.github.dgroup.dockertest.text.Splitted;
import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.text.highlighted.BlueText;
import com.github.dgroup.dockertest.text.highlighted.GreenText;
import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Application logo.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Logo implements Iterable<String> {
    /**
     * Application version.
     */
    private final UncheckedScalar<String> version;

    /**
     * Ctor.
     */
    public Logo() {
        this(new Property("app.version"));
    }

    /**
     * Ctor.
     *
     * @param version The application version.
     */
    public Logo(final Scalar<String> version) {
        this.version = new UncheckedScalar<>(version);
    }

    /**
     * App version and docker logo as string.
     *
     * @return The logo.
     * @checkstyle AddEmptyString (50 lines)
     * @checkstyle OperatorWrapCheck (50 lines)
     * @checkstyle RegexpSinglelineCheck (50 lines)
     * @checkstyle StringLiteralsConcatenationCheck (50 lines)
     * @checkstyle LocalFinalVariableNameCheck (50 lines)
     */
    @SuppressWarnings("PMD.AddEmptyString")
    public String asString() {
        final String eol = System.lineSeparator();
        return new TextOf(
            eol + "Docker image testing (%s)" + eol + "%s",
            new GreenText("v%s", this.version.value()),
            new BlueText("" +
                "                  ##         .            " + eol +
                "            ## ## ##        ==            " + eol +
                "         ## ## ## ##       ===            " + eol +
                "     /\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"___/ ===        " + eol +
                "~~~ {~~ ~~~~ ~~~ ~~~~ ~~ ~ /===- ~~~   " + eol +
                "     \\______ o          __/            " + eol +
                "      \\    \\        __/             " + eol +
                "       \\____\\______/   " + eol
            )
        ).text();
    }

    @Override
    public Iterator<String> iterator() {
        return new Splitted(this.asString(), "\n").iterator();
    }
}
