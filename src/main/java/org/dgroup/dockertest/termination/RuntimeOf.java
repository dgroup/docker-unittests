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
package org.dgroup.dockertest.termination;

import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Represent current application process.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class RuntimeOf implements Runtime {

    /**
     * Origin.
     */
    private final Scalar<java.lang.Runtime> runtime;

    /**
     * Ctor.
     */
    public RuntimeOf() {
        this(java.lang.Runtime::getRuntime);
    }

    /**
     * Ctor.
     * @param runtime Represent current application process.
     */
    public RuntimeOf(final Scalar<java.lang.Runtime> runtime) {
        this.runtime = runtime;
    }

    @Override
    public void shutdownWith(final int code) {
        new UncheckedScalar<>(this.runtime).value().exit(code);
    }
}
