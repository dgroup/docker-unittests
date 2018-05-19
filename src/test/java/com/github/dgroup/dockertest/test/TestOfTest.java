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
package com.github.dgroup.dockertest.test;

import com.github.dgroup.dockertest.docker.DockerProcessExecutionException;
import com.github.dgroup.dockertest.docker.output.CmdOutput;
import com.github.dgroup.dockertest.docker.process.DockerProcess;
import com.github.dgroup.dockertest.hamcrest.HasItems;
import com.github.dgroup.dockertest.yml.tag.YmlTagOutputPredicateOf;
import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link TestOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class TestOfTest {

    @Test
    public void failedMsg() throws DockerProcessExecutionException {
        MatcherAssert.assertThat(
            new TestOf(
                "curl version is 7.xxx",
                "curl --version",
                new ListOf<>(
                    new YmlTagOutputPredicateOf(
                        "startsWith", "curl 7.", String::startsWith
                    ),
                    new YmlTagOutputPredicateOf(
                        "equals", "OpenSSL/1.0.2m", String::equals
                    )
                ),
                new DockerProcess.Fake(
                    new CmdOutput.Fake(
                        "curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57.0 " +
                            "OpenSSL/1.0.2m zlib/1.2.8"
                    )
                )
            ).execute().message(),
            Matchers.<List<String>>allOf(
                Matchers.hasSize(8),
                new HasItems<>(
                    "> curl version is 7.xxx \u001B[91;1mFAILED\u001B[m",
                    "  command: \"curl --version\"",
                    "  output:  \"curl 7.57.0 (x86_64-pc-linux-gnu) " +
                        "libcurl/7.57.0 OpenSSL/1.0.2m zlib/1.2.8\"",
                    "  expected output:",
                    "    - startsWith: \"curl 7.\"",
                    "    - equals:     \"OpenSSL/1.0.2m\"",
                    "  mismatch:",
                    "    - equals:     \"OpenSSL/1.0.2m\""
                )
            )
        );
    }

    @Test
    public void passedMsg() throws DockerProcessExecutionException {
        MatcherAssert.assertThat(
            new TestOf(
                "curl version is 7.xxx",
                "curl --version",
                new ListOf<>(
                    new YmlTagOutputPredicateOf(
                        "startsWith", "curl 7.", String::startsWith
                    ),
                    new YmlTagOutputPredicateOf(
                        "endsWith", "7.57.0 ", String::endsWith
                    )
                ),
                new DockerProcess.Fake(
                    new CmdOutput.Fake(
                        "curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57.0 "
                    )
                )
            ).execute().message(),
            Matchers.allOf(
                Matchers.hasSize(1),
                new HasItems<>(
                    "> curl version is 7.xxx \u001B[92;1mPASSED\u001B[m"
                )
            )
        );
    }

}
