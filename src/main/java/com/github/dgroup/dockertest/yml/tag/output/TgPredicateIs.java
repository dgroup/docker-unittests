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
package com.github.dgroup.dockertest.yml.tag.output;

import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Hamcrest matcher to test the code which returns {@link TgOutputPredicate}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1
 * @checkstyle ProtectedMethodInFinalClassCheck (100 lines)
 */
public final class TgPredicateIs extends
    TypeSafeDiagnosingMatcher<TgOutputPredicate> {

    /**
     * Expected YML output predicate (contains, equals, startsWith, etc).
     */
    private final TgOutputPredicate expected;

    /**
     * Ctor.
     * @param type The expected YML output predicate type.
     * @param value The expected YML output predicate value.
     */
    public TgPredicateIs(final String type, final String value) {
        this(new TgPredicateFake(type, value));
    }

    /**
     * Ctor.
     * @param expected YML predicate.
     */
    public TgPredicateIs(final TgOutputPredicate expected) {
        super();
        this.expected = expected;
    }

    @Override
    public void describeTo(final Description dsc) {
        this.describe(dsc, this.expected);
    }

    @Override
    protected boolean matchesSafely(final TgOutputPredicate act,
        final Description dsc) {
        this.describe(dsc, act);
        return act.comparingType().equals(this.expected.comparingType())
            && act.expected().equals(this.expected.expected());
    }

    /**
     * Describe YML tag output predicate in Hamcrest terms.
     * @param desc The hamcrest description for unit test AR/ER values.
     * @param prdct The origin YML output predicate.
     * @checkstyle NonStaticMethodCheck (10 lines)
     */
    private void describe(
        final Description desc, final TgOutputPredicate prdct
    ) {
        desc.appendText(" Comparing type '").appendValue(prdct.comparingType())
            .appendText("', value '").appendValue(prdct.expected())
            .appendText("'");
    }
}
