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

import org.cactoos.iterable.Mapped;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.yml.YmlTests;

/**
 * Allows to execute tests and print results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class Tests {

    /**
     * Tests to be executed.
     */
    private final Iterable<Test> origin;
    /**
     * Available outputs for printing results.
     */
    private final Iterable<Output> outputs;

    /**
     * Ctor.
     * @param image Command-line argument with docker image.
     * @param yml String with tests to be executed.
     * @param outputs Available outputs for printing results.
     */
    public Tests(final String image, final String yml,
        final Iterable<Output> outputs) {
        this.origin = new Mapped<>(
            CachedTest::new,
            new Mapped<>(
                ymlTagTest -> new BasedOnYmlTest(
                    image,
                    ymlTagTest
                ),
                new YmlTests(yml)
            )
        );
        this.outputs = outputs;
    }

    /**
     * Print tests results to selected outputs.
     * In case if nothing was selected
     * {@link org.dgroup.dockertest.test.output.StdOutput} will be used.
     */
    public void print() {
        final TestingOutcome outcome = new TestingOutcome(
            new Mapped<>(Test::execute, this.origin)
        );
        for (final Output output : this.outputs) {
            outcome.print(output);
        }
    }

}
