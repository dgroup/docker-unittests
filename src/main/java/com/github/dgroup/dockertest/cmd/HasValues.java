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
package com.github.dgroup.dockertest.cmd;

import com.github.dgroup.dockertest.cmd.arg.Unchecked;
import java.util.Collection;
import org.cactoos.list.ListOf;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Hamcrest matcher to verify the collection value of {@link Arg}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class HasValues<T> extends
    TypeSafeDiagnosingMatcher<Arg<Collection<T>>> {

    /**
     * Expected value.
     */
    private final Collection<T> expected;

    /**
     * Ctor.
     * @param expected Value within unit test.
     */
    public HasValues(final T... expected) {
        this(new ListOf<>(expected));
    }

    /**
     * Ctor.
     * @param expected Value within unit test.
     */
    @SuppressWarnings("PMD.CallSuperInConstructor")
    public HasValues(final Collection<T> expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(final Description dsc) {
        dsc.appendValue(this.expected);
    }

    @Override
    protected boolean matchesSafely(final Arg<Collection<T>> item,
        final Description dsc) {
        final Collection<T> actual = new Unchecked<>(item).value();
        dsc.appendValue(actual);
        return this.expected.size() == actual.size()
            && this.expected.containsAll(actual);
    }

}
