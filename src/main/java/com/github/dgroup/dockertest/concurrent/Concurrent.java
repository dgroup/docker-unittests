/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
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
package com.github.dgroup.dockertest.concurrent;

import com.github.dgroup.dockertest.cmd.Arg;
import com.github.dgroup.dockertest.concurrent.func.TimingOut;
import com.github.dgroup.dockertest.process.docker.Docker;
import com.github.dgroup.dockertest.process.docker.cmd.ExecTty;
import com.github.dgroup.dockertest.process.docker.cmd.Pull;
import com.github.dgroup.dockertest.process.docker.cmd.Remove;
import com.github.dgroup.dockertest.process.docker.cmd.Start;
import com.github.dgroup.dockertest.scalar.If;
import com.github.dgroup.dockertest.test.TestOf;
import com.github.dgroup.dockertest.test.TestingOutcome;
import com.github.dgroup.dockertest.test.outcome.TestingOutcomeOf;
import com.github.dgroup.dockertest.yml.Tags;
import com.github.dockerjava.api.DockerClient;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

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
    private final UncheckedScalar<ExecutorService> exec;
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
     * @param qtt Quantity of concurrent threads to be used for testing.
     * @todo #/DEV Move timeout configuration to yml file in order to define
     *  unique timeout for each test.
     * @checkstyle MagicNumberCheck (15 lines)
     */
    public Concurrent(final Arg<Timeout> trd, final Arg<Integer> qtt) {
        this(
            new StickyScalar<>(
                () -> Executors.newFixedThreadPool(
                    new If<>(qtt::specifiedByUser, qtt::value, () -> 8).value(),
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
        this.exec = new UncheckedScalar<>(exc);
        this.thread = thrd;
        this.graceful = swn;
    }

    /**
     * Execute the testing.
     *
     * @param image The Docker image name.
     * @param container The Docker container name.
     * @param tags The YML file with tags as object.
     * @return The testing results.
     * @checkstyle IllegalCatchCheck (50 lines)
     */
    @SuppressWarnings({
        "PMD.AvoidCatchingGenericException",
        "PMD.AvoidInstantiatingObjectsInLoops"})
    public TestingOutcome execute(
        final String image, final Text container, final Tags tags
    ) {
        try (final DockerClient client = new Docker().value()) {
            try {
                new Pull(image, client).execute();
                new Start(image, container, client).execute();
                for (final String instruction : tags.setup().value()) {
                    new ExecTty(instruction, container, client, true).execute();
                }
                return new TestingOutcomeOf(
                    new ArrayList<>(
                        new Mapped<>(
                            new TimingOut<>(this.thread),
                            this.exec.value().invokeAll(
                                new Mapped<>(
                                    tgTest -> new TestOf(
                                        tgTest,
                                        new ExecTty(
                                            tgTest.cmd(), container, client
                                        )
                                    ),
                                    tags.tests()
                                )
                            )
                        )
                    )
                );
            } finally {
                new Remove(container, client).execute();
            }
        } catch (final Exception exp) {
            throw new IllegalStateException(exp);
        }
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
            Thread.currentThread().interrupt();
            exc.shutdownNow();
        }
    }

}
