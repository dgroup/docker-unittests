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
package org.dgroup.dockertest.concurrent;

import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.hamcrest.True;
import org.dgroup.dockertest.text.Joined;
import org.dgroup.dockertest.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit tests for class {@link Demons}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class DemonsTest {

    @Test
    public void newThread() {
        final Demons demons = new Demons("dt-%s");
        MatcherAssert.assertThat(
            "3 runnable were converted to 3 daemon threads with `dt-%s` name",
            new Joined(
                new Mapped<>(
                    trd -> new TextOf(
                        "%s-%s", trd.getName(), trd.isDaemon()
                    ).text(),
                    new Mapped<>(
                        demons::newThread,
                        new ListOf<>(
                            () -> {
                            },
                            () -> {
                            },
                            () -> {
                            }
                        )
                    )
                ),
                ";"
            ).text().matches(
                "^dt-\\d-true;dt-\\d-true;dt-\\d-true$"
            ),
            new True()
        );
    }

}
