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
package com.github.dgroup.dockertest.hamcrest;

import java.util.Collection;
import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedBiFunc;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.UncheckedScalar;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Hamcrest envelope for {@link java.util.Collection} matchers.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of item.
 * @since 1.0
 */
public class HamcrestCollectionEnvelope<X> extends
    TypeSafeDiagnosingMatcher<Collection<X>> {

    /**
     * Expected value.
     */
    private final UncheckedScalar<Collection<X>> expected;
    /**
     * The function to verify the testing relation between collections, where
     *  the first collection is expected values and the second is actual values.
     */
    private final UncheckedBiFunc<Collection<X>, Collection<X>, Boolean> fnc;

    /**
     * Ctor.
     * @param fnc The function to be applied within expected/actual values.
     * @param exp The expected values within unit test.
     */
    public HamcrestCollectionEnvelope(
        final BiFunc<Collection<X>, Collection<X>, Boolean> fnc,
        final X... exp
    ) {
        this(fnc, () -> new ListOf<>(exp));
    }

    /**
     * Ctor.
     * @param fnc The function to be applied within expected/actual values.
     * @param exp The expected values within unit test.
     */
    @SuppressWarnings("PMD.CallSuperInConstructor")
    public HamcrestCollectionEnvelope(
        final BiFunc<Collection<X>, Collection<X>, Boolean> fnc,
        final Scalar<Collection<X>> exp
    ) {
        this.fnc = new UncheckedBiFunc<>(fnc);
        this.expected = new UncheckedScalar<>(exp);
    }

    @Override
    public final void describeTo(final Description dsc) {
        new Describe<X>().exec(this.expected.value(), dsc);
    }

    @Override
    protected final boolean matchesSafely(
        final Collection<X> actual, final Description dsc
    ) {
        new Describe<X>().exec(actual, dsc);
        return this.fnc.apply(this.expected.value(), actual);
    }

}
