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
package org.dgroup.dockertest.cmd;

import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.test.output.HtmlOutput;
import org.dgroup.dockertest.test.output.XmlOutput;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

/**
 * Unit tests for class {@link OutputOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class OutputOfTest {

    @Test
    public void notSpecifiedOutput() {
        MatcherAssert.assertThat(
            new OutputOf(
                new ListOf<>()
            ).specifiedByUser(),
            Matchers.equalTo(false)
        );
    }

    @Test
    public void specifiedOutput() throws CmdArgNotFoundException {
        MatcherAssert.assertThat(
            new OutputOf(
                new ListOf<>("-o", "xml|html")
            ).value(),
            instanceOfInAnyOrder(
                new XmlOutput(), new HtmlOutput()
            )
        );
    }

    private <T> Matcher<Iterable<? extends T>> instanceOfInAnyOrder(
        final T... items
    ) {
        return new IsIterableContainingInAnyOrder<>(
            new Mapped<>(
                i -> IsInstanceOf.instanceOf(i.getClass()),
                new ListOf<>(items)
            )
        );
    }

}
