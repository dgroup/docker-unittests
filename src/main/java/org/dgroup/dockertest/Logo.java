/**
 * MIT License
 *
 * Copyright (c) 2017 Yurii Dubinka
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
package org.dgroup.dockertest;

import java.util.Iterator;
import org.cactoos.iterable.IterableOf;
import org.dgroup.dockertest.text.Splitted;
import org.dgroup.dockertest.text.TextOf;
import org.dgroup.dockertest.text.highlighted.BlueText;
import org.dgroup.dockertest.text.highlighted.GreenText;

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
    private final String version;

    /**
     * Ctor.
     * @param version Application version.
     */
    public Logo(final String version) {
        this.version = version;
    }

    /**
     * App version and docker logo as string.
     * @return Logo.
     * @checkstyle AddEmptyString (50 lines)
     * @checkstyle OperatorWrapCheck (50 lines)
     * @checkstyle RegexpSinglelineCheck (50 lines)
     * @checkstyle StringLiteralsConcatenationCheck (50 lines)
     */
    @SuppressWarnings("PMD.AddEmptyString")
    public String asString() {
        final GreenText app = new GreenText(
            new TextOf("v%s", this.appVersion())
        );
        final BlueText whale = new BlueText("" +
            "                  ##         .            \n" +
            "            ## ## ##        ==            \n" +
            "         ## ## ## ##       ===            \n" +
            "     /\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"___/ ===        \n" +
            "~~~ {~~ ~~~~ ~~~ ~~~~ ~~ ~ /===- ~~~   \n" +
            "     \\______ o          __/            \n" +
            "      \\    \\        __/             \n" +
            "       \\____\\______/   \n"
        );
        return new TextOf("\nDocker testing tool (%s)\n%s", app, whale)
            .text();
    }

    /**
     * Give app version.
     * @return Version.
     */
    public String appVersion() {
        return this.version;
    }

    @Override
    public Iterator<String> iterator() {
        return new IterableOf<>(
            new Splitted(this.asString(), "\n").asArray()
        ).iterator();
    }
}
