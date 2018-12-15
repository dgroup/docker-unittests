/**
 * MIT License
 *
 * Copyright (c) 2017-2018 Yurii Dubinka
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
package com.github.dgroup.dockertest.cmd;

import com.github.dgroup.dockertest.hamcrest.False;
import com.github.dgroup.dockertest.hamcrest.True;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit tests for class {@link ArgOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AvoidDuplicateLiterals (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ArgAtTest {

    @Test
    public void specified() {
        MatcherAssert.assertThat(
            new ArgOf(
                "-o",
                new ListOf<>("-o", "std"),
                "Argument `-o` wasn't found"
            ).specifiedByUser(),
            new True()
        );
    }

    @Test
    public void notSpecified() {
        MatcherAssert.assertThat(
            new ArgOf(
                "-o",
                new ListOf<>(
                    "-f", "single-test.yml",
                    "-i", "alpine:jdk9"
                ),
                "Argument `-o` wasn't found"
            ).specifiedByUser(),
            new False()
        );
    }

}
