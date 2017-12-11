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
package org.dgroup.dockertest.test;

import org.cactoos.iterable.IterableOf;
import org.dgroup.dockertest.YmlResource;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.NoImage;
import org.dgroup.dockertest.test.output.FakeOutput;
import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public class TestsTest {

    @Ignore // @todo #/DEV OS or Env dependent test. Create native containers or install docker to CI env
    @Test
    public void singleTest() {
        FakeOutput output = new FakeOutput();
        new Tests(
            new NoImage(),
            new Args(
                "-f",
                new YmlResource(
                    "with-single-test.yml"
                ).path()
            ).fileWithTests(),
            new IterableOf<>(output)
        ).print();
        assertThat(
            output.lines(), hasSize(2)
        );
        assertThat(
            output.lines().get(0),
            equalTo("")
        );
        assertThat(
            output.lines().get(1),
            equalTo("Testing successful.")
        );
    }
}
