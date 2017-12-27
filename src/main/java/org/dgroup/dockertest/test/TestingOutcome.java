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

import java.util.Iterator;
import org.cactoos.collection.Filtered;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.test.output.Output;

/**
 * Represents outcome for all tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class TestingOutcome implements Iterable<TestOutcome> {

    /**
     * Testing outcome.
     */
    private final Iterable<TestOutcome> outcome;

    /**
     * Testing outcome.
     * @param outcome Collection of single test results.
     */
    public TestingOutcome(final Iterable<TestOutcome> outcome) {
        this.outcome = outcome;
    }

    @Override
    public Iterator<TestOutcome> iterator() {
        return this.outcome.iterator();
    }

    /**
     * Print testing results to the selected output.
     * In case if nothing was selected
     * {@link org.dgroup.dockertest.test.output.StdOutput} will be used.
     *
     * @param output Available output for printing.
     */
    public void print(final Output output) {
        for (final TestOutcome out : this) {
            output.print(out.message());
        }
        new UncheckedTernary<>(
            this.failed(),
            () -> {
                output.print("Testing failure.");
                output.flush();
                throw new TestingFailedException();
            },
            () -> {
                output.print("Testing successful.");
                output.flush();
                return true;
            }
        ).value();
    }

    /**
     * Checking all tests outcome for failed scenario's.
     * @return True in failed scenario's found.
     */
    private boolean failed() {
        return new Filtered<>(
            f -> f.message().startsWith("Failed scenario"),
            this.outcome
        ).size() > 0;
    }

}
