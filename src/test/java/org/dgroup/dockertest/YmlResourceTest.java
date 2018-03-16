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
package org.dgroup.dockertest;

import java.io.File;
import java.io.IOException;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link YmlResource}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings({ "PMD.AddEmptyString", "PMD.AvoidDuplicateLiterals" })
public final class YmlResourceTest {

    @Test
    public void path() {
        MatcherAssert.assertThat(
            new YmlResource("with-single-test.yml").path(),
            Matchers.endsWith("" +
                "src" + File.separator +
                "test" + File.separator +
                "resources" + File.separator +
                "yml" + File.separator +
                "tests" + File.separator +
                "with-single-test.yml"
            )
        );
    }

    @Test
    public void asString() throws IOException {
        MatcherAssert.assertThat(
            new ListOf<>(
                new YmlResource("with-single-test.yml").asString().split("\n")
            ),
            Matchers.hasItems(
                "version: 1",
                "",
                "tests:",
                "  - test:",
                "      assume:  \"curl version is 7.xxx\"",
                "      cmd:     \"curl --version\"",
                "      output:",
                "        - startsWith:  \"curl 7.\"",
                "        - contains:    \"Protocols: \"",
                "        - contains:    \"Features: \""
            )
        );
    }

}
