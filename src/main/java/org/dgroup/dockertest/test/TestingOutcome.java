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
import java.util.Set;
import org.cactoos.Proc;
import org.cactoos.scalar.And;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.test.output.Output;

/**
 * Represents outcome for all tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TestingOutcome implements Iterable<TestOutcome> {

    /**
     * Multiple tests results.
     */
    private final Iterable<TestOutcome> outcome;
    /**
     * Testing status.
     */
    private final UncheckedScalar<Boolean> passed;
    /**
     * Outputs for printing results.
     */
    private final Set<Output> outputs;

    /**
     * Ctor.
     * @param outcome Collection of single test results.
     * @param outputs Available outputs for printing tests results.
     */
    public TestingOutcome(final Iterable<TestOutcome> outcome,
        final Set<Output> outputs) {
        this(
            outcome,
            new UncheckedScalar<>(
                new StickyScalar<>(
                    new And(TestOutcome::successful, outcome)
                )
            ),
            outputs
        );
    }

    /**
     * Ctor.
     * @param outcome Collection of single test results.
     * @param passed Status of the testing.
     * @param outputs Available outputs for printing tests results.
     */
    public TestingOutcome(final Iterable<TestOutcome> outcome,
        final UncheckedScalar<Boolean> passed, final Set<Output> outputs) {
        this.outcome = outcome;
        this.passed = passed;
        this.outputs = outputs;
    }

    @Override
    public Iterator<TestOutcome> iterator() {
        return this.outcome.iterator();
    }

    /**
     * Checking all tests outcome for passed scenario's.
     * @return True in passed scenario's found.
     */
    public boolean successful() {
        return this.passed.value();
    }

    /**
     * Print testing outcome to specified outputs.
     * @throws TestingFailedException in case if at least one test is failed.
     */
    public void reportTheResults() throws TestingFailedException {
        new UncheckedScalar<>(
            new And(
                (Proc<Output>) output -> output.print(this),
                this.outputs
            )
        ).value();
        if (!this.successful()) {
            throw new TestingFailedException();
        }
    }

}
