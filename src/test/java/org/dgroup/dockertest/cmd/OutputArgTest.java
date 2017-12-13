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
package org.dgroup.dockertest.cmd;

import java.util.List;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.test.output.HtmlOutput;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.StdOutput;
import org.dgroup.dockertest.test.output.XmlOutput;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Unit tests for class {@link OutputArg}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AvoidStaticImportCheck (500 lines)
 */
public class OutputArgTest {

    @Test
    public void notSpecifiedOutput() {
        assertThat(
            new OutputArg(
                new ListOf<>()
            ).outputs().iterator().next(),
            instanceOf(StdOutput.class)
        );
    }

    @Test
    public void specifiedOutput() {
        List<Output> outputs = new OutputArg(
            new ListOf<>("-o", "xml|html")
        ).outputs();

        assertThat(outputs, hasSize(2));
        assertThat(outputs.get(0), instanceOf(XmlOutput.class));
        assertThat(outputs.get(1), instanceOf(HtmlOutput.class));
    }

}
