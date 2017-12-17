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
package org.dgroup.dockertest.yml;

import org.cactoos.list.ListOf;
import org.dgroup.dockertest.AssertThrown;
import org.dgroup.dockertest.YmlResource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Unit tests for class {@link YmlTests}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class YmlTestsTest {

    @Test
    public void tagVersionIsMissing() {
        AssertThrown.assertThrown(
            () -> new YmlTests(
                new YmlResource("with-missing-version-tag.yml").file()
            ).iterator(),
            new IllegalYmlFormatException("The `version` tag is missing")
        );
    }

    @Test
    public void iterator() {
        MatcherAssert.assertThat(
            "Tests from file `with-3-simple-tests.yml` loaded as Iterable",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ),
            IsCollectionWithSize.hasSize(3)
        );
    }

    @Test
    public void tagAssume() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/assume` equal to `\"node version is 8.5.1\"`",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ).get(1).assume(),
            IsEqual.equalTo("node version is 8.5.1")
        );
    }

    @Test
    public void tagCmd() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/cmd` is equal to `\"node -v\"`",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ).get(1).cmd(),
            IsEqual.equalTo("node -v")
        );
    }

    @Test
    public void tagOutputHasNecessaryStatements() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 4 statements",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ).get(1).output(),
            IsCollectionWithSize.hasSize(4)
        );
    }

    @Test
    public void tagOutputContains() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 1st statement `contains`",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ).get(1).output().get(0).type(),
            IsEqual.equalTo("contains")
        );
    }

    @Test
    public void tagOutputContainsHasExpectedValue() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 1st statement `contains`" +
                " and expected value is `v8.5.0`",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ).get(1).output().get(0).test("v8.5.0"),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void tagOutputStartsWith() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 2nd statement `startsWith`",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ).get(1).output().get(1).type(),
            IsEqual.equalTo("startsWith")
        );
    }

    @Test
    public void tagOutputStartsWithHasExpectedValue() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 2nd statement `startsWith`" +
                " and expected value is `v8.`",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ).get(1).output().get(1).test("v8."),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void tagOutputEndsWith() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 3rd statement `endsWith`",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml").file()
                )
            ).get(1).output().get(2).type(),
            IsEqual.equalTo("endsWith")
        );
    }

    @Test
    public void tagOutputEndsWithHasExpectedValue() {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 3rd statement `endsWith`" +
                " and expected value is `.5.0`",
            new ListOf<>(
                new YmlTests(
                    new YmlResource("with-3-simple-tests.yml")
                        .file()
                )
            ).get(1).output().get(2).test(".5.0"),
            IsEqual.equalTo(true)
        );
    }
}
