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
package com.github.dgroup.dockertest.exception;

import com.github.dgroup.dockertest.scalar.If;
import java.util.ArrayList;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Represents root cause exception for particular exception.
 *
 * @author Bohdan Okun (markpolo525@gmail.com)
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@SuppressWarnings({
    "PMD.OnlyOneConstructorShouldDoInitialization",
    "PMD.ConstructorOnlyInitializesOrCallOtherConstructors" })
public final class RootCauseOf {

    /**
     * Root cause exception.
     */
    private final Scalar<Throwable> cause;

    /**
     * Ctor.
     * @param wrapped Top-level exception which may have hierarchy of children
     *  exceptions.
     */
    public RootCauseOf(final Throwable wrapped) {
        this(
            new StickyScalar<>(
                () -> {
                    Throwable rcause = new If<>(
                        wrapped.getCause() == null, wrapped, wrapped::getCause
                    ).value();
                    final List<Throwable> visited = new ArrayList<>(5);
                    while (rcause.getCause() != null
                        && !visited.contains(rcause.getCause())) {
                        rcause = rcause.getCause();
                        visited.add(rcause);
                    }
                    return rcause;
                }
            )
        );
    }

    /**
     * Ctor.
     * @param fcause Function to evaluate the root cause exception.
     *  This constructor also can be used for the unit-testing purposes.
     */
    public RootCauseOf(final Scalar<Throwable> fcause) {
        this.cause = fcause;
    }

    /**
     * Each exception may contain a lot of nested exceptions.
     * @return Root case exception.
     */
    public Throwable exception() {
        return new UncheckedScalar<>(this.cause).value();
    }

}
