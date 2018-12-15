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

import com.github.dgroup.dockertest.hamcrest.HasItems;
import com.github.dgroup.dockertest.test.outcome.TestOutcome;
import com.github.dgroup.dockertest.yml.tag.TgOutputPredicateOf;
import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link MsgFailed}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LineLengthCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class MsgFailedTest {

    @Test
    public void failedMsg() {
        MatcherAssert.assertThat(
            new ListOf<>(
                new MsgFailed(
                    new TestOutcome.Fake(
                        false,
                        "curl version is 7.xxx",
                        "curl --version",
                        String.format(
                            "curl 7.19.7 (x86_64-redhat-linux-gnu) lib%n" +
                                "Protocols: tftp ftp telnet dict ldap ldaps%n" +
                                "Features: GSS-Negotiate IDN IPv6 Largefile"
                        ),
                        new ListOf<>(
                            new TgOutputPredicateOf(
                                "startsWith", "curl 7.", String::startsWith
                            ),
                            new TgOutputPredicateOf(
                                "contains", "OpenSSL/1.0.2m", String::contains
                            )
                        ),
                        new ListOf<>(
                            new TgOutputPredicateOf(
                                "contains", "OpenSSL/1.0.2m", String::contains
                            )
                        )
                    )
                )
            ),
            Matchers.<List<String>>allOf(
                Matchers.hasSize(10),
                new HasItems<>(
                    "> curl version is 7.xxx \u001B[91;1mFAILED\u001B[m",
                    "  command: \"curl --version\"",
                    "  output:  \"curl 7.19.7 (x86_64-redhat-linux-gnu) lib",
                    "            Protocols: tftp ftp telnet dict ldap ldaps",
                    "            Features: GSS-Negotiate IDN IPv6 Largefile\"",
                    "  expected output:",
                    "    - startsWith: \"curl 7.\"",
                    "    - contains:   \"OpenSSL/1.0.2m\"",
                    "  mismatch:",
                    "    - contains:   \"OpenSSL/1.0.2m\""
                )
            )
        );
    }

}
