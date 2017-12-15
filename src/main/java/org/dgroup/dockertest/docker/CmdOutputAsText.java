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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dgroup.dockertest.docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * Represent command output for command from docker container.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class CmdOutputAsText implements CmdOutput {

    /**
     * System process output associated with docker container.
     */
    private final Process outcome;

    /**
     * Ctor.
     * @param outcome System process output associated with docker container.
     */
    public CmdOutputAsText(final Process outcome) {
        this.outcome = outcome;
    }

    @Override
    public String asText() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
            this.outcome.getInputStream()
        ))) {
            return new UncheckedText(
                new TextOf(
                    new InputOf(in)
                )
            ).asString();
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
