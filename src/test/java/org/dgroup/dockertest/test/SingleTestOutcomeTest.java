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
package org.dgroup.dockertest.test;

import java.util.List;
import org.cactoos.func.UncheckedBiFunc;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.yml.tag.output.DefaultYmlTagOutputPredicate;
import org.dgroup.dockertest.yml.tag.test.FakeYmlTagTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link SingleTestOutcome}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LineLengthCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SingleTestOutcomeTest {

    @Test
    public void passedMsg() {
        MatcherAssert.assertThat(
            new SingleTestOutcome(
                new FakeYmlTagTest(
                    "curl version is 7.xxx",
                    "curl --version",
                    new ListOf<>(
                        new DefaultYmlTagOutputPredicate(
                            "startsWith", "curl 7.", new UncheckedBiFunc<>(String::startsWith)
                        ),
                        new DefaultYmlTagOutputPredicate(
                            "contains", "OpenSSL/1.0.2m", new UncheckedBiFunc<>(String::contains)
                        )
                    )
                ),
                "curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57.0 OpenSSL/1.0.2m zlib/1.2.8"
            ).message(),
            Matchers.<List<String>>allOf(
                Matchers.hasSize(1),
                Matchers.hasItem("> curl version is 7.xxx PASSED")
            )
        );
    }

    @Test
    public void failedMsg() {
        MatcherAssert.assertThat(
            new SingleTestOutcome(
                new FakeYmlTagTest(
                    "curl version is 7.xxx",
                    "curl --version",
                    new ListOf<>(
                        new DefaultYmlTagOutputPredicate(
                            "startsWith", "curl 7.", new UncheckedBiFunc<>(String::startsWith)
                        ),
                        new DefaultYmlTagOutputPredicate(
                            "equals", "OpenSSL/1.0.2m", new UncheckedBiFunc<>(String::equals)
                        )
                    )
                ),
                "curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57.0 OpenSSL/1.0.2m zlib/1.2.8"
            ).message(),
            Matchers.<List<String>>allOf(
                Matchers.hasSize(8),
                Matchers.hasItems(
                    "> curl version is 7.xxx FAILED",
                    "  command: \"curl --version\"",
                    "  output:  \"curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57.0 OpenSSL/1.0.2m zlib/1.2.8\"",
                    "  expected output:",
                    "    - startsWith: \"curl 7.\"",
                    "    - equals:     \"OpenSSL/1.0.2m\"",
                    "  mismatch:",
                    "    - equals:     \"OpenSSL/1.0.2m\""
                )
            )
        );
    }

}
