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
package org.dgroup.dockertest.scalar;

import org.cactoos.Scalar;
import org.cactoos.scalar.Ternary;

/**
 * Ternary operation which didn't throw exception.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 */
public final class UncheckedTernary<T> implements Scalar<T> {

    /**
     * Ternary operation.
     */
    private final Ternary<T> origin;

    /**
     * Ctor.
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     */
    public UncheckedTernary(final boolean cnd, final T cons, final T alter) {
        this(new Ternary<>(() -> cnd, () -> cons, () -> alter));
    }

    /**
     * Ctor.
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     */
    public UncheckedTernary(final Scalar<Boolean> cnd, final Scalar<T> cons,
        final Scalar<T> alter) {
        this(new Ternary<>(cnd, cons, alter));
    }

    /**
     * Ctor.
     * @param origin Ternary operation.
     */
    public UncheckedTernary(final Ternary<T> origin) {
        this.origin = origin;
    }

    // @checkstyle IllegalCatchCheck (10 lines)
    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public T value() {
        try {
            return this.origin.value();
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
