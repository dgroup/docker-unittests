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
package com.github.dgroup.dockertest.scalar;

import org.cactoos.Scalar;

/**
 * Throw exception in case if boolean condition is false.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 */
public final class StrictIf<T> implements Scalar<T> {

    /**
     * Expression to evaluate.
     */
    private final Scalar<Boolean> condition;
    /**
     * Consequence value to return in case if {@code cond = true}.
     */
    private final Scalar<T> cons;
    /**
     * Exception message to be thrown.
     */
    private final Scalar<String> msg;

    /**
     * Ctor.
     * @param cond Expression to evaluate.
     * @param cons Consequence value to return in case if {@code cond = true}.
     */
    public StrictIf(final boolean cond, final T cons) {
        this(() -> cond, () -> cons);
    }

    /**
     * Ctor.
     * @param cond Expression to evaluate.
     * @param cons Consequence value to return in case if {@code cond = true}.
     */
    public StrictIf(final Scalar<Boolean> cond, final Scalar<T> cons) {
        this(cond, cons, () -> "");
    }

    /**
     * Ctor.
     * @param cond Expression to evaluate.
     * @param cons Consequence value to return in case if {@code cond = true}.
     * @param msg Exception message to be thrown.
     */
    public StrictIf(
        final Scalar<Boolean> cond,
        final Scalar<T> cons,
        final Scalar<String> msg
    ) {
        this.condition = cond;
        this.cons = cons;
        this.msg = msg;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public T value() throws ConditionNotSatisfiedException {
        // @checkstyle IllegalCatchCheck (15 lines)
        try {
            if (this.condition.value()) {
                return this.cons.value();
            }
            if (this.msg.value().isEmpty()) {
                throw new ConditionNotSatisfiedException();
            } else {
                throw new ConditionNotSatisfiedException(this.msg.value());
            }
        } catch (final Exception exp) {
            throw new ConditionNotSatisfiedException(exp);
        }
    }

}
