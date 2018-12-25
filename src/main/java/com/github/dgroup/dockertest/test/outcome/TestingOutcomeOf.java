/**
 * MIT License
 *
 * Copyright (c) 2017-2018 Yurii Dubinka
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
package com.github.dgroup.dockertest.test.outcome;

import com.github.dgroup.dockertest.cmd.Arg;
import com.github.dgroup.dockertest.cmd.CmdArgNotFoundException;
import com.github.dgroup.dockertest.test.Output;
import com.github.dgroup.dockertest.test.TestOutcome;
import com.github.dgroup.dockertest.test.TestingFailedException;
import com.github.dgroup.dockertest.test.TestingOutcome;
import java.util.Collection;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.collection.CollectionEnvelope;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.And;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Represents outcome for all tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TestingOutcomeOf extends
    CollectionEnvelope<TestOutcome> implements TestingOutcome {

    /**
     * Testing status.
     */
    private final UncheckedScalar<Boolean> passed;

    /**
     * Ctor.
     * @param tests Collection of single test results.
     */
    public TestingOutcomeOf(final TestOutcome... tests) {
        this(new ListOf<>(tests));
    }

    /**
     * Ctor.
     * @param tests Collection of single test results.
     */
    public TestingOutcomeOf(final Collection<TestOutcome> tests) {
        this(
            () -> tests,
            new UncheckedScalar<>(
                new StickyScalar<>(
                    new And(TestOutcome::successful, tests)
                )
            )
        );
    }

    /**
     * Ctor.
     * @param outcome Testing results
     * @param passed Status of the testing
     */
    public TestingOutcomeOf(
        final Scalar<Collection<TestOutcome>> outcome,
        final UncheckedScalar<Boolean> passed
    ) {
        super(outcome);
        this.passed = passed;
    }

    @Override
    public boolean successful() {
        return this.passed.value();
    }

    @Override
    public void report(final Output... outputs)
        throws TestingFailedException {
        this.report(new ListOf<>(outputs));
    }

    @Override
    public void report(final Iterable<Output> outputs)
        throws TestingFailedException {
        new UncheckedScalar<>(
            new And(
                (Proc<Output>) output -> output.print(this),
                outputs
            )
        ).value();
        if (!this.successful()) {
            throw new TestingFailedException();
        }
    }

    @Override
    public void report(final Arg<Collection<Output>> outs)
        throws TestingFailedException {
        try {
            this.report(outs.value());
        } catch (final CmdArgNotFoundException exp) {
            throw new TestingFailedException(exp);
        }
    }

}
