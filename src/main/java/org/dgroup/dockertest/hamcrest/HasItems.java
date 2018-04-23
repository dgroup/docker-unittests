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
package org.dgroup.dockertest.hamcrest;

import java.util.Collection;
import org.cactoos.list.ListOf;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Hamcrest matcher to verify the structure of the collection.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class HasItems<T> extends
    TypeSafeDiagnosingMatcher<Collection<T>> {

    /**
     * Expected value.
     */
    private final Collection<T> expected;

    /**
     * Ctor.
     * @param expected Value within unit test.
     */
    public HasItems(final T... expected) {
        this(new ListOf<>(expected));
    }

    /**
     * Ctor.
     * @param expected Value within unit test.
     */
    @SuppressWarnings("PMD.CallSuperInConstructor")
    public HasItems(final Collection<T> expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(final Description dsc) {
        new Describe<T>().exec(this.expected, dsc);
    }

    @Override
    protected boolean matchesSafely(
        final Collection<T> actual,
        final Description dsc
    ) {
        new Describe<T>().exec(actual, dsc);
        return actual.containsAll(this.expected);
    }

}
