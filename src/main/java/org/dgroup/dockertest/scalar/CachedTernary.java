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
package org.dgroup.dockertest.scalar;

import org.cactoos.Scalar;

/**
 * Ternary operation with cached result.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 0.1.0
 */
public final class CachedTernary<T> implements Scalar<T> {

    /**
     * Ternary operation.
     */
    private final UncheckedTernary<T> ternary;
    /**
     * Cached ternary value.
     */
    private T cached;

    /**
     * Ctor.
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     */
    public CachedTernary(final boolean cnd, final UncheckedCallable<T> cons,
        final UncheckedCallable<T> alter) {
        this(new UncheckedTernary<>(cnd, cons, alter));
    }

    /**
     * Ctor.
     * @param ternary Ternary operation.
     */
    public CachedTernary(final UncheckedTernary<T> ternary) {
        this.ternary = ternary;
    }

    @Override
    public T value() {
        if (this.cached == null) {
            this.cached = this.ternary.value();
        }
        return this.cached;
    }

}
