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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.Timeout;
import org.dgroup.dockertest.cmd.TimeoutOf;
import org.dgroup.dockertest.concurrent.func.TimingOut;
import org.dgroup.dockertest.test.NoScenariosFoundException;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.outcome.TestingOutcome;
import org.dgroup.dockertest.test.outcome.TestingOutcomeOf;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.text.TextOf;
import org.dgroup.dockertest.text.highlighted.GreenText;

/**
 * Allows to execute tests and print results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (150 lines)
 */
public final class ConcurrentTests implements AutoCloseable {

    /**
     * Standard output for printing application progress.
     */
    private final StdOutput std;
    /**
     * Instance of executor service for concurrent execution.
     */
    private final Scalar<ExecutorService> exec;
    /**
     * Timeout per single thread dedicated to task(test) execution.
     */
    private final Timeout thread;
    /**
     * Timeout for shutdown of executor service.
     */
    private final Timeout swn;

    /**
     * Ctor.
     * @param args Command-line arguments specified by the user.
     * @checkstyle MagicNumberCheck (15 lines)
     */
    public ConcurrentTests(final Args args) {
        this(
            args.standardOutput(),
            () -> Executors.newFixedThreadPool(
                args.concurrentThreads(),
                new Demons("Test-%s")
            ),
            args.timeoutPerThread(),
            new TimeoutOf(5, TimeUnit.SECONDS)
        );
    }

    /**
     * Ctor.
     * @param std Standard output for printing application progress.
     * @param exc Instance of executor service for concurrent execution.
     * @param thrd Timeout per single thread dedicated to task(test) execution.
     * @param swn Timeout for shutdown of executor service.
     *  Current class implements {@link java.lang.AutoCloseable} interface and
     *  should be used within try-with-resource block. It means that timeout
     *  will be used once all tasks are already completed, therefore, it doesn't
     *  require long timings.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public ConcurrentTests(
        final StdOutput std,
        final Scalar<ExecutorService> exc,
        final Timeout thrd,
        final Timeout swn
    ) {
        this.std = std;
        this.exec = exc;
        this.thread = thrd;
        this.swn = swn;
    }

    /**
     * Execute the testing.
     * @param tasks To be executed concurrently.
     * @return Testing results.
     * @throws TestingFailedException in case when at least one test is failed.
     */
    public TestingOutcome execute(final List<Test> tasks)
        throws TestingFailedException {
        if (tasks.isEmpty()) {
            throw new NoScenariosFoundException();
        }
        this.std.print(
            new TextOf(
                "Found scenarios: %s.%n", new GreenText(tasks.size())
            )
        );
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
            if (!exc.awaitTermination(this.swn.timeout(), this.swn.measure())) {
                exc.shutdownNow();
            }
        } catch (final InterruptedException exp) {
            exc.shutdownNow();
        }
    }

}
