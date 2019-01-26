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
import com.github.dgroup.dockertest.scalar.If;
import org.cactoos.Scalar;

/**
 * Argument with alternative value in case if original is missing.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of command-line argument.
 * @since 1.0
 */
public final class Alternative<X> implements Arg<X> {

    /**
     * Original argument.
     */
    private final Arg<X> origin;
    /**
     * The alternative value.
     */
    private final Scalar<X> alt;

    /**
     * Ctor.
     * @param src The original argument value.
     * @param alt The alternative argument value.
     */
    public Alternative(final Arg<X> src, final X alt) {
        this(src, () -> alt);
    }

    /**
     * Ctor.
     * @param src The original argument value.
     * @param alt The alternative argument value.
     */
    public Alternative(final Arg<X> src, final Scalar<X> alt) {
        this.origin = src;
        this.alt = alt;
    }

    @Override
    public String name() {
        return this.origin.name();
    }

    @Override
    public X value() {
        return new If<>(
            this.origin::specifiedByUser, this.origin::value, this.alt
        ).value();
    }

    @Override
    public boolean specifiedByUser() {
        return true;
    }
}
