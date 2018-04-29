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
package org.dgroup.dockertest.yml.tag;

import org.cactoos.list.ListOf;
import org.dgroup.dockertest.hamcrest.HasItems;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit tests for class {@link YmlTagOutputPredicateOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LineLengthCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class YmlTagOutputPredicateOfTest {

    @Test
    public void asString() {
        MatcherAssert.assertThat(
            new ListOf<>(
                new YmlTagOutputPredicateOf(
                    "startsWith", "curl 7.", String::startsWith
                ).asYmlString(),
                new YmlTagOutputPredicateOf(
                    "equals", "curl 7.57.0", String::equals
                ).asYmlString(),
                new YmlTagOutputPredicateOf(
                    "contains", "7.57", String::contains
                ).asYmlString()
            ),
            new HasItems<>(
                "startsWith: \"curl 7.\"",
                "equals:     \"curl 7.57.0\"",
                "contains:   \"7.57\""
            )
        );
    }

}
