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

import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.YmlResource;
import org.dgroup.dockertest.cmd.FileArg;
import org.dgroup.dockertest.test.output.FakeOutput;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests for class {@link Tests}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public class TestsTest {

    // @todo #19 Create native OS containers or install docker to CI env.
    @Ignore
    @Test
    public final void singleTest() {
        final FakeOutput output = new FakeOutput();
        new Tests(
            "",
            new FileArg(
                new ListOf<>(
                    "-f",
                    new YmlResource(
                        "with-single-test.yml"
                    ).path()
                )
            ).value(),
            new IterableOf<>(output)
        ).print();
        MatcherAssert.assertThat(
            output.lines(), IsCollectionWithSize.hasSize(2)
        );
        MatcherAssert.assertThat(
            output.lines().get(0), Matchers.equalTo("")
        );
        MatcherAssert.assertThat(
            output.lines().get(1), Matchers.equalTo("Testing successful.")
        );
    }
}
