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

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.concurrent.scalar.Demonized;
import org.dgroup.dockertest.concurrent.scalar.Named;
import org.dgroup.dockertest.text.TextOf;

/**
 * Implementation of {@link ThreadFactory} which allows to build daemon threads
 *  within particular naming.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Demons implements ThreadFactory {

    /**
     * Naming pattern.
     */
    private final String prn;
    /**
     * Thread factory which provide the threads.
     */
    private final Scalar<ThreadFactory> fct;
    /**
     * Amount of allocated threads.
     */
    private final AtomicLong threads;

    /**
     * Ctor.
     * @param prn The naming pattern which has '%s' symbol, which will be
     *  replaced by the thread number.
     */
    public Demons(final String prn) {
        this(prn, Executors::defaultThreadFactory, new AtomicLong(0));
    }

    /**
     * Ctor.
     * @param prn The naming pattern which has '%s' symbol, which will be
     *  replaced by the thread number.
     * @param fct Implementation of {@link ThreadFactory} for allocating of new
     *  threads.
     * @param trd Amount of allocated threads.
     */
    public Demons(final String prn, final Scalar<ThreadFactory> fct,
        final AtomicLong trd) {
        this.prn = prn;
        this.fct = fct;
        this.threads = trd;
    }

    @Override
    public Thread newThread(final Runnable task) {
        return new UncheckedScalar<>(
            new Named(
                new TextOf(this.prn, this.threads.incrementAndGet()),
                new Demonized(
                    () -> this.fct.value().newThread(task)
                )
            )
        ).value();
    }

}
