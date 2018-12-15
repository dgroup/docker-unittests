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
package com.github.dgroup.dockertest.concurrent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.cactoos.list.Mapped;

/**
 * Envelope for {@link ExecutorService}.
 *
 * There is no thread-safety guarantee.
 *
 * The executor service has a lot of methods, thus checkstyle rule
 *  DesignForExtensionCheck was disabled. The client developer might need to
 *  override some methods later, thus, they can't be the `final`.
 *  Let's say "thank you" to the JDK api designers.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of item.
 * @since 1.0
 * @checkstyle DesignForExtensionCheck (150 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ExcsrvEnvelope<X> implements ExecutorService {

    /**
     * Map {@link Callable} to {@link Future}.
     */
    private final SimplifiedFuture fnc;

    /**
     * Ctor.
     * @param fnc To map {@link Callable} to {@link Future}.
     */
    public ExcsrvEnvelope(final SimplifiedFuture<X> fnc) {
        this.fnc = fnc;
    }

    @Override
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void shutdown() {
    }

    @Override
    public List<Runnable> shutdownNow() {
        return Collections.emptyList();
    }

    @Override
    public boolean isShutdown() {
        throw new UnsupportedOperationException("#isShutdown");
    }

    @Override
    public boolean isTerminated() {
        throw new UnsupportedOperationException("#isTerminated");
    }

    @Override
    public boolean awaitTermination(final long timeout, final TimeUnit unit) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> Future<X> submit(final Callable<X> task) {
        return this.fnc.apply(task);
    }

    @Override
    public <X> Future<X> submit(final Runnable task, final X result) {
        return this.submit(
            () -> {
                task.run();
                return result;
            }
        );
    }

    @Override
    public Future<?> submit(final Runnable task) {
        throw new UnsupportedOperationException("#submit(runnable)");
    }

    @Override
    public <X> List<Future<X>> invokeAll(
        final Collection<? extends Callable<X>> tasks
    ) {
        return new Mapped<>(this::submit, tasks);
    }

    @Override
    public <X> X invokeAny(final Collection<? extends Callable<X>> tsks) {
        throw new UnsupportedOperationException("#invokeAny");
    }

    @Override
    public <X> List<Future<X>> invokeAll(
        final Collection<? extends Callable<X>> tsks,
        final long timeout, final TimeUnit unit) {
        throw new UnsupportedOperationException("#invokeAll");
    }

    @Override
    public <X> X invokeAny(final Collection<? extends Callable<X>> tsks,
        final long timeout, final TimeUnit unit) {
        throw new UnsupportedOperationException("#invokeAny(tsks,timeout,unit");
    }

    @Override
    public void execute(final Runnable command) {
        throw new UnsupportedOperationException("#execute");
    }

}
