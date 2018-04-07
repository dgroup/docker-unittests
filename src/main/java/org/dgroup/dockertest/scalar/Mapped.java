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

import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * Map function for particular {@link Scalar}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 1.0
 */
public final class Mapped<X, Y> implements Scalar<Y> {

    /**
     * Origin function.
     */
    private final Func<X, Y> fnc;
    /**
     * Input scalar.
     */
    private final Scalar<X> arg;

    /**
     * Ctor.
     * @param fnc Origin function.
     * @param arg Input scalar.
     */
    public Mapped(final Func<X, Y> fnc, final Scalar<X> arg) {
        this.fnc = fnc;
        this.arg = arg;
    }

    @Override
    public Y value() throws Exception {
        return this.fnc.apply(this.arg.value());
    }

}
