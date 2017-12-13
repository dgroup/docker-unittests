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

import java.util.List;
import org.cactoos.Input;
import org.cactoos.collection.Filtered;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.cmd.Arg;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.yml.YmlTests;

/**
 * Allows to execute tests and print results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 **/
public final class Tests {

    private final Iterable<Test> tests;
    private final Iterable<Output> outputs;

    public Tests(final Arg image, final Input tests, final Iterable<Output> outputs) {
        this.tests = new Mapped<>(
            CachedTest::new,
            new Mapped<>(
                ymlTagTest -> new DefaultTestBasedOnYml(
                    image,
                    ymlTagTest
                ),
                new YmlTests(tests)
            )
        );
        this.outputs = outputs;
    }

    // @todo #18 Cleaning is required
    public void print() {
        final List<TestingOutcome> outcome = new ListOf<>(
            new Mapped<>(Test::execute, this.tests)
        );
        for (final Output output : outputs) {
            for (TestingOutcome out : outcome) {
                output.print(out.message());
            }
        }
        if (failed(outcome)) {
            outputs.forEach(o -> o.finalDecision("Testing failure."));
            throw new TestingFailedException();
        }
        outputs.forEach(o -> o.finalDecision("Testing successful."));
    }

    private boolean failed(List<TestingOutcome> outcomes) {
        return new Filtered<>(
            f -> f.message().startsWith("Failed scenario"),
            outcomes
        ).size() > 0;
    }
}
