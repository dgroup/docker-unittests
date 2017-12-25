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
package org.dgroup.dockertest.test.output;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.cactoos.iterator.LengthOf;
import org.cactoos.list.ListOf;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for class {@link SupportedOutputs}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 * @checkstyle JavadocMethodCheck (200 lines)
 * @checkstyle JavadocVariableCheck (200 lines)
 */
@RunWith(DataProviderRunner.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SupportedOutputsTest {

    private final SupportedOutputs outputs = new SupportedOutputs(
        new MapOf<>(
            new MapEntry<>("xml", new XmlOutput()),
            new MapEntry<>("html", new HtmlOutput()),
            new MapEntry<>("std", new StdOutput())
        )
    );

    @Test
    public void thatXmlOutputTypeWasFound() {
        MatcherAssert.assertThat(
            this.outputs.availableFor(new ListOf<>("xml")).next(),
            Matchers.instanceOf(XmlOutput.class)
        );
    }

    @Test
    public void thatOnlyXmlOutputTypeWasFound() {
        MatcherAssert.assertThat(
            new LengthOf(
                this.outputs.availableFor(new ListOf<>("xml"))
            ).value(),
            Matchers.equalTo(1)
        );
    }

    @Test
    public void thatStdoutWasFoundAsDefaultOutput() {
        MatcherAssert.assertThat(
            this.outputs.availableFor(new ListOf<>("csv")).next(),
            Matchers.instanceOf(StdOutput.class)
        );
    }

    @Test
    public void thatOnlyStdoutWasFoundAsDefaultOutput() {
        MatcherAssert.assertThat(
            new LengthOf(
                this.outputs.availableFor(new ListOf<>("csv"))
            ).value(),
            Matchers.equalTo(1)
        );
    }

}
