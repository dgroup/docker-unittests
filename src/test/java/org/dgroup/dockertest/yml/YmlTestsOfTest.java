/**
 * MIT License
 *
 * Copyright (c) 2017 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.dgroup.dockertest.yml;

import org.cactoos.list.ListOf;
import org.dgroup.dockertest.YmlResource;
import org.junit.Test;
import static org.dgroup.dockertest.AssertThrown.assertThrown;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version 0.1.0
 * @since 0.1.0
 */

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class YmlTestsOfTest {
    @Test
    public void versionTagIsMissing() {
        assertThrown(
                () -> new YmlTestsOf(
                        new YmlResource("with-missing-version-tag.yml").file()
                ).iterator(),
                new IllegalYmlFormatException("The `version` tag is missing")
        );
    }

    @Test
    public void iterator() {
        assertThat(
                "Tests from file `with-3-simple-tests.yml` were loaded as Iterable<YmlTest>",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ),
                hasSize(3)
        );
    }

    @Test
    public void assume() {
        assertThat(
                "Tag `tests/test[2]/assume` is equal to `\"node version is 8.5.1\"`",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ).get(1).assume(),
                equalTo("node version is 8.5.1")
        );
    }

    @Test
    public void cmd() {
        assertThat(
                "Tag `tests/test[2]/cmd` is equal to `\"node -v\"`",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ).get(1).cmd(),
                equalTo("node -v")
        );
    }

    @Test
    public void output() {
        assertThat(
                "Tag `tests/test[2]/output` has 4 statements",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ).get(1).output(),
                hasSize(4)
        );
    }

    @Test
    public void outputContains() {
        assertThat(
                "Tag `tests/test[2]/output` has 1st statement `contains`",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ).get(1).output().get(0).type(),
                equalTo("contains")
        );

        assertThat(
                "Tag `tests/test[2]/output` has 1st statement `contains` and expected value is `v8.5.0`",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ).get(1).output().get(0).test("v8.5.0"),
                equalTo(true)
        );
    }

    @Test
    public void outputStartWith() {
        assertThat(
                "Tag `tests/test[2]/output` has 2nd statement `startWith`",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ).get(1).output().get(1).type(),
                equalTo("startWith")
        );

        assertThat(
                "Tag `tests/test[2]/output` has 2nd statement `startWith` and expected value is `v8.`",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ).get(1).output().get(1).test("v8."),
                equalTo(true)
        );
    }

    @Test
    public void outputEndWith() {
        assertThat(
                "Tag `tests/test[2]/output` has 3rd statement `endWith`",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml").file()
                        )
                ).get(1).output().get(2).type(),
                equalTo("endWith")
        );

        assertThat(
                "Tag `tests/test[2]/output` has 3rd statement `endWith` and expected value is `.5.0`",
                new ListOf<>(
                        new YmlTestsOf(
                                new YmlResource("with-3-simple-tests.yml")
                                        .file()
                        )
                ).get(1).output().get(2).test(".5.0"),
                equalTo(true)
        );
    }
}