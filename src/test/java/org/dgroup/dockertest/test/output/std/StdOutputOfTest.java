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
package org.dgroup.dockertest.test.output.std;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.test.outcome.TestOutcomeOf;
import org.dgroup.dockertest.test.outcome.TestingOutcomeOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link StdOutputOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.AddEmptyString" })
public final class StdOutputOfTest {

    @Test
    public void printTestingOutcome() throws UnsupportedEncodingException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final StdOutput out = new StdOutputOf(
            new PrintStream(baos, true, StandardCharsets.UTF_8.toString()),
            "..."
        );
        out.print(
            new TestingOutcomeOf(
                new ListOf<>(
                    new TestOutcomeOf(true, "java version is 1.9"),
                    new TestOutcomeOf(true, "curl version is 7.xxx")
                ),
                Collections.emptySet()
            )
        );
        MatcherAssert.assertThat(
            new String(baos.toByteArray(), StandardCharsets.UTF_8),
            Matchers.equalTo("" +
                "...java version is 1.9\n" +
                "...curl version is 7.xxx\n\n" +
                "...\u001B[92;1mTesting successful.\u001B[m\n\n"
            )
        );
    }

    @Test
    public void printFailedTestingOutcome()
        throws UnsupportedEncodingException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final StdOutput out = new StdOutputOf(
            new PrintStream(baos, true, StandardCharsets.UTF_8.toString()),
            "..."
        );
        out.print(
            new TestingOutcomeOf(
                new ListOf<>(
                    new TestOutcomeOf(false, "java version is 1.9"),
                    new TestOutcomeOf(true, "curl version is 7.xxx")
                ),
                Collections.emptySet()
            )
        );
        MatcherAssert.assertThat(
            new String(baos.toByteArray(), StandardCharsets.UTF_8),
            Matchers.equalTo("" +
                "...java version is 1.9\n" +
                "...curl version is 7.xxx\n\n" +
                "...\u001B[91;1mTesting failed.\u001B[m\n\n"
            )
        );
    }

    @Test
    public void printException()
        throws UnsupportedEncodingException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final StdOutput out = new StdOutputOf(
            new PrintStream(baos, true, StandardCharsets.UTF_8.toString()),
            ".."
        );
        out.print(
            "Something went wrongly...",
            new Exception(
                new RuntimeException(
                    new Exception("Shit happens.")
                )
            )
        );
        MatcherAssert.assertThat(
            new String(baos.toByteArray(), StandardCharsets.UTF_8),
            Matchers.startsWith("" +
                "..Something went wrongly...\n" +
                "..org.dgroup.dockertest.test.output.std.StdOutputOfTest." +
                "printException(StdOutputOfTest.java:"
            )
        );
    }

}
