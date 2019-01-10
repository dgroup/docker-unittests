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
package com.github.dgroup.dockertest.cmd.arg;

import com.github.dgroup.dockertest.cmd.Arg;
import org.cactoos.Func;

/**
 * Map function to the {@link Arg}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of source item.
 * @param <Y> Type of target item.
 * @since 1.0
 */
public final class Mapped<X, Y> implements Arg<Y> {

    /**
     * Origin.
     */
    private final Arg<X> src;
    /**
     * Map function to the argument.
     */
    private final Func<X, Y> fnc;

    /**
     * Ctor.
     * @param fnc Map function.
     * @param src Source argument.
     */
    public Mapped(final Func<X, Y> fnc, final Arg<X> src) {
        this.fnc = fnc;
        this.src = src;
    }

    @Override
    public String name() {
        return this.src.name();
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Y value() throws CmdArgNotFoundException {
        // @checkstyle IllegalCatchCheck (5 lines)
        try {
            return this.fnc.apply(this.src.value());
        } catch (final Exception exp) {
            throw new CmdArgNotFoundException(exp);
        }
    }

    @Override
    public boolean specifiedByUser() {
        return this.src.specifiedByUser();
    }
}
