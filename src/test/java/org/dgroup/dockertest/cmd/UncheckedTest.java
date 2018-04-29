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
package org.dgroup.dockertest.cmd;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link Unchecked}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class UncheckedTest {

    @Test
    public void name() {
        MatcherAssert.assertThat(
            new Unchecked<>(
                () -> new Arg.Fake<>("-f", "test.yml")
            ).name(),
            Matchers.equalTo("-f")
        );
    }

    @Test
    public void value() {
        MatcherAssert.assertThat(
            new Unchecked<>(
                () -> new Arg.Fake<>("-f", "test.yml")
            ).value(),
            Matchers.equalTo("test.yml")
        );
    }

    @Test(expected = IllegalStateException.class)
    public void noValue() {
        new Unchecked<>(
            () -> new Arg<String>() {
                @Override
                public String name() {
                    return "-f";
                }

                @Override
                public String value() throws CmdArgNotFoundException {
                    throw new CmdArgNotFoundException(this::name);
                }

                @Override
                public boolean specifiedByUser() {
                    return false;
                }
            }
        ).value();
    }

    @Test
    public void specifiedByUser() {
        MatcherAssert.assertThat(
            new Unchecked<>(
                () -> new Arg.Fake<>("-f", "test.yml")
            ).specifiedByUser(),
            Matchers.equalTo(true)
        );
    }

}
