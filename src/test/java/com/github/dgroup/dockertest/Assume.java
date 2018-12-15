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
package com.github.dgroup.dockertest;

import com.github.dgroup.dockertest.exception.Stacktrace;
import com.github.dgroup.dockertest.hamcrest.True;
import org.cactoos.Scalar;
import org.junit.AssumptionViolatedException;

/**
 * Soft assertions within unit tests.
 *
 * Each "soft" assert may thrown the {@link AssumptionViolatedException}.
 *  As the result the unit test will be ignored and not failed. This is a useful
 *  feature for the tests dependent on environment.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle NonStaticMethodCheck (200 lines)
 */
public final class Assume {

    /**
     * Check that condition is {@code true}.
     * @param cnd The condition to be checked.
     * @throws AssumptionViolatedException in case if the condition is false.
     */
    public void that(final Boolean cnd) {
        this.that(() -> cnd);
    }

    /**
     * Check that condition is {@code true}.
     * @param cnd The condition to be checked.
     * @throws AssumptionViolatedException in case if the condition is false.
     * @checkstyle IllegalCatchCheck (10 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void that(final Scalar<Boolean> cnd) {
        try {
            org.junit.Assume.assumeThat(cnd.value(), new True());
        } catch (final Exception cause) {
            org.junit.Assume.assumeThat(
                new Stacktrace(cause).toString(), false, new True()
            );
        }
    }
}
