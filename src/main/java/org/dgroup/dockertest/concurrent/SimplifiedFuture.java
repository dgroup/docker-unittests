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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Represent the function to map the {@link Callable} to the {@link Future}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of item.
 * @since 1.0
 */
public interface SimplifiedFuture<X> {

    /**
     * Map the {@link Callable} to the {@link Future}.
     * @param task To be executed.
     * @return The future result.
     */
    Future<X> apply(final Callable<X> task);

    /**
     * Fake {@link SimplifiedFuture} for unit testing purposes.
     * @param <X> Type of item.
     * @checkstyle AnonInnerLengthCheck (35 lines)
     */
    class Fake<X> implements SimplifiedFuture<X> {

        @Override
        public Future<X> apply(final Callable<X> task) {
            return new Future<X>() {
                @Override
                public boolean cancel(final boolean interrupted) {
                    throw new UnsupportedOperationException("#cancel");
                }

                @Override
                public boolean isCancelled() {
                    throw new UnsupportedOperationException("#isCancelled");
                }

                @Override
                public boolean isDone() {
                    throw new UnsupportedOperationException("#isDone");
                }

                @Override
                @SuppressWarnings("PMD.AvoidCatchingGenericException")
                public X get() throws ExecutionException {
                    // @checkstyle IllegalCatchCheck (5 lines)
                    try {
                        return task.call();
                    } catch (final Exception exp) {
                        throw new ExecutionException(exp);
                    }
                }

                @Override
                public X get(final long timeout, final TimeUnit unit)
                    throws ExecutionException {
                    return this.get();
                }
            };
        }
    }

}
