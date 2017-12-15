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
package org.dgroup.dockertest.cmd;

import org.cactoos.list.ListOf;
import org.dgroup.dockertest.test.output.HtmlOutput;
import org.dgroup.dockertest.test.output.StdOutput;
import org.dgroup.dockertest.test.output.XmlOutput;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

/**
 * Unit tests for class {@link OutputArg}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class OutputArgTest {

    @Test
    public void notSpecifiedOutput() {
        MatcherAssert.assertThat(
            new OutputArg(
                new ListOf<>()
            ).outputs().iterator().next(),
            IsInstanceOf.instanceOf(StdOutput.class)
        );
    }

    @Test
    public void specifiedOutputSize() {
        MatcherAssert.assertThat(
            new OutputArg(
                new ListOf<>("-o", "xml|html")
            ).outputs(),
            IsCollectionWithSize.hasSize(2)
        );
    }

    @Test
    public void thatFirstOutputTypeIsXml() {
        MatcherAssert.assertThat(
            new OutputArg(
                new ListOf<>("-o", "xml|html")
            ).outputs().get(0),
            IsInstanceOf.instanceOf(XmlOutput.class)
        );
    }

    @Test
    public void thatSecondOutputTypeIsHtml() {
        MatcherAssert.assertThat(
            new OutputArg(
                new ListOf<>("-o", "xml|html")
            ).outputs().get(1),
            IsInstanceOf.instanceOf(HtmlOutput.class)
        );
    }

}
