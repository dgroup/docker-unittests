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
package com.github.dgroup.dockertest.test.output;
// @checkstyle ImportOrderCheck (10 lines)

import com.github.dgroup.dockertest.test.outcome.TestOutcomeOf;
import com.github.dgroup.dockertest.test.outcome.TestingOutcome;
import com.github.dgroup.dockertest.test.outcome.TestingOutcomeOf;
import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import com.github.dgroup.dockertest.yml.TgTest;
import java.io.StringWriter;
import java.util.Collections;
import com.jcabi.matchers.XhtmlMatchers;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit tests for class {@link XmlOutput}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0.3
 * @checkstyle LineLengthCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class XmlOutputTest {

    @Test
    public void print() {
        final TestingOutcome outcome = new TestingOutcomeOf(
            new TestOutcomeOf(
                new TgTest.Fake(
                    "java version is 1.9, Debian build",
                    "java -version",
                    new ListOf<>(
                        new TgOutputPredicate.Fake(
                            "contains", "1.9", output -> output.contains("1.9")
                        )
                    )
                ),
                "java version \"1.8.0_161\"\n" +
                    "Java(TM) SE Runtime Environment (build 1.8.0_161-b12)\n" +
                    "Java HotSpot(TM) 64-Bit Server VM (build 25.161-b12)\n",
                new ListOf<>(
                    new TgOutputPredicate.Fake(
                        "contains", "1.9", output -> output.contains("1.9")
                    )
                )
            ),
            new TestOutcomeOf(
                new TgTest.Fake(
                    "curl version is 800",
                    "curl --version",
                    new ListOf<>(
                        new TgOutputPredicate.Fake(
                            "contains", "800", output -> output.contains("800")
                        )
                    )
                ),
                "curl 7.54.0 (x86_64-apple-darwin17.0) libcurl/7.54.0\n" +
                    "Protocols: dict file ftp ftps gopher http https \n" +
                    "Features: AsynchDNS IPv6 Largefile GSS-API Kerberos\n",
                Collections.emptyList()
            )
        );
        final StringWriter swr = new StringWriter();
        new XmlOutput("0.1.0", () -> swr).print(outcome);
        MatcherAssert.assertThat(
            swr.toString(),
            XhtmlMatchers.hasXPaths(
                "/report[@format-version='0.1.0']",
                "/report/image[text() = 'openjdk:9.0.1-11']",
                "/report/tests[@overall-status='failed']",
                "/report/tests/test[position()=1]/scenario[text() = 'java version is 1.9, Debian build']",
                "/report/tests/test[position()=1]/status[text() = 'failed']",
                "/report/tests/test[position()=1]/expectedThatOutput/contains[text() = '1.9']",
                "/report/tests/test[position()=1]/docker/command[text() = 'java -version']",
                "/report/tests/test[position()=1]/docker/failed/contains[text() = '1.9']",
                "/report/tests/test[position()=2]/status[text() = 'passed']"
            )
        );
    }

}
