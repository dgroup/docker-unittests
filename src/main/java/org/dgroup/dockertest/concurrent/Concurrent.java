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
package org.dgroup.dockertest.concurrent;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.cmd.Arg;
import org.dgroup.dockertest.concurrent.func.TimingOut;
import org.dgroup.dockertest.scalar.If;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.outcome.TestingOutcome;
import org.dgroup.dockertest.test.outcome.TestingOutcomeOf;
import org.slf4j.LoggerFactory;

/**
 * Allows to execute tests and print results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (150 lines)
 */
public final class Concurrent implements AutoCloseable {

    /**
     * Instance of executor service for concurrent execution.
     */
    private final Scalar<ExecutorService> exec;
    /**
     * Timeout per single thread dedicated to task(test) execution.
     */
    private final Scalar<Timeout> thread;
    /**
     * Timeout for graceful shutdown of executor service.
     */
    private final Scalar<Timeout> graceful;

    /**
     * Ctor.
     * @param trd Timeout per each thread.
     * @param cct Quantity of concurrent threads to be used for testing.
     * @todo #/DEV Move timeout configuration to yml file in order to define
     *  unique timeout for each test.
     * @checkstyle MagicNumberCheck (15 lines)
     */
    public Concurrent(final Arg<Timeout> trd, final Arg<Integer> cct) {
        this(
            new StickyScalar<>(
                () -> Executors.newFixedThreadPool(
                    new If<>(cct::specifiedByUser, cct::value, () -> 8).value(),
                    new Demons("Test-%s")
                )
            ),
            new If<>(
                trd::specifiedByUser, trd::value,
                () -> new TimeoutOf(10, TimeUnit.MINUTES)
            ),
            () -> new TimeoutOf(5, TimeUnit.SECONDS)
        );
    }

    /**
     * Ctor.
     * @param exc Instance of executor service for concurrent execution.
     * @param thrd Timeout per single thread dedicated to task(test) execution.
     * @param swn Timeout for shutdown of executor service.
     *  Current class implements {@link java.lang.AutoCloseable} interface and
     *  should be used within try-with-resource block. It means that timeout
     *  will be used once all tasks are already completed, therefore, it doesn't
     *  require long timings.
     */
    public Concurrent(final Scalar<ExecutorService> exc,
        final Scalar<Timeout> thrd, final Scalar<Timeout> swn) {
        this.exec = exc;
        this.thread = thrd;
        this.graceful = swn;
    }

    /**
     * Execute the testing.
     * @param tasks To be executed concurrently.
     * @return The testing results.
     */
    public TestingOutcome execute(final Test... tasks) {
        return this.execute(new ListOf<>(tasks));
    }

    /**
     * Execute the testing.
     * @param tasks To be executed concurrently.
     * @return The testing results.
     */
    public TestingOutcome execute(final Collection<Test> tasks) {
        return new TestingOutcomeOf(
            new Mapped<>(
                new TimingOut<>(this.thread),
                new Mapped<>(
                    tsk -> this.exec.value().submit(tsk::execute),
                    tasks
                )
            )
        );
    }

    @Override
    public void close() {
        final ExecutorService exc = new UncheckedScalar<>(this.exec).value();
        try {
            exc.shutdown();
            final Timeout gfl = new UncheckedScalar<>(this.graceful).value();
            if (!exc.awaitTermination(gfl.timeout(), gfl.measure())) {
                exc.shutdownNow();
            }
        } catch (final InterruptedException exp) {
            LoggerFactory.getLogger(Concurrent.class).warn(
                "Can't shutdown the executor gracefully.", exp
            );
            exc.shutdownNow();
        }
    }

}
