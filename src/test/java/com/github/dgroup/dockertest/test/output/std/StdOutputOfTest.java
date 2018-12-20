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
package com.github.dgroup.dockertest.test.output.std;

import com.github.dgroup.dockertest.test.outcome.TestOutcome;
import com.github.dgroup.dockertest.test.outcome.TestingOutcomeOf;
import com.github.dgroup.dockertest.yml.tag.output.TgPredicateFake;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.HasLines;

/**
 * Unit tests for class {@link StdOutputOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle LineLengthCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.AddEmptyString"})
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
                new TestOutcome.Fake(true, "java version is 1.9"),
                new TestOutcome.Fake(true, "curl version is 7.xxx")
            )
        );
        MatcherAssert.assertThat(
            new String(baos.toByteArray(), StandardCharsets.UTF_8),
            new HasLines(
                "...> java version is 1.9 \u001B[92;1mPASSED\u001B[m",
                "...> curl version is 7.xxx \u001B[92;1mPASSED\u001B[m",
                "...\u001B[92;1mTesting successful.\u001B[m"
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
                new TestOutcome.Fake(
                    false,
                    "java version is 1.9", "java -version",
                    String.format("" +
                        "java version \"1.8.0_161\"%n" +
                        "Java(TM) SE Runtime Environment (build 1.8.0_161)%n" +
                        "Java HotSpot(TM) 64-Bit Server VM (build 25.161)"
                    ),
                    new ListOf<>(
                        new TgPredicateFake(
                            "contains", "1.9", raw -> raw.contains("1.9")
                        )
                    ),
                    new ListOf<>(
                        new TgPredicateFake(
                            "contains", "1.9", raw -> raw.contains("1.9")
                        )
                    )
                ),
                new TestOutcome.Fake(
                    true, "curl version is 7.xxx", "curl --version"
                )
            )
        );
        MatcherAssert.assertThat(
            new String(baos.toByteArray(), StandardCharsets.UTF_8),
            new HasLines(
                "...> java version is 1.9 \u001B[91;1mFAILED\u001B[m",
                "...  command: \"java -version\"",
                "...  output:  \"java version \"1.8.0_161\"",
                "...            Java(TM) SE Runtime Environment (build 1.8.0_161)",
                "...            Java HotSpot(TM) 64-Bit Server VM (build 25.161)\"",
                "...  expected output:",
                "...    - contains 1.9",
                "...  mismatch:",
                "...    - contains 1.9",
                "...> curl version is 7.xxx \u001B[92;1mPASSED\u001B[m",
                "",
                "...\u001B[91;1mTesting failed.\u001B[m"
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
                "..Something went wrongly..." + System.lineSeparator() +
                "..com.github.dgroup.dockertest.test.output.std" +
                ".StdOutputOfTest." +
                "printException(StdOutputOfTest.java:"
            )
        );
    }

}
