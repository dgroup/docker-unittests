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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import org.cactoos.Func;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Represents yml tag
 * {@code /tests/test/output/contains|equal|startsWith|endsWith}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface YmlTagOutputPredicate {

    /**
     * Comparing type.
     * @return Available types are contains, equal, startsWith, endsWith.
     */
    String comparingType();

    /**
     * Expected value by test.
     * @return Value expected by test. For example, in case of `matches` tag
     *  the method return regexp statement.
     */
    String expectedValue();

    /**
     * Compare expected value with actual.
     *
     * @param actual Output received from docker container.
     * @return True in case if expected value is equal to actual value.
     * @throws IllegalYmlFormatException in case when condition is
     *  unsupported or har wrong/empty value.
     */
    boolean test(final String actual) throws IllegalYmlFormatException;

    /**
     * Fake implementation for the unit test purposes.
     * @checkstyle JavadocMethodCheck (50 lines)
     * @checkstyle JavadocVariableCheck (50 lines)
     */
    final class Fake implements YmlTagOutputPredicate {

        private final String type;
        private final String expected;
        private final Func<String, Boolean> fnc;

        public Fake(final String type, final String expected) {
            this(type, expected, actual -> true);
        }

        public Fake(final String type, final String expected,
            final Func<String, Boolean> fnc) {
            this.type = type;
            this.expected = expected;
            this.fnc = fnc;
        }

        @Override
        public String comparingType() {
            return this.type;
        }

        @Override
        public String expectedValue() {
            return this.expected;
        }

        @Override
        @SuppressWarnings("PMD.AvoidCatchingGenericException")
        public boolean test(final String actual)
            throws IllegalYmlFormatException {
            // @checkstyle IllegalCatchCheck (5 lines)
            try {
                return this.fnc.apply(actual);
            } catch (final Exception exp) {
                throw new IllegalYmlFormatException(exp);
            }
        }
    }

    /**
     * Hamcrest matcher to test the code which returns
     *  {@link YmlTagOutputPredicate}.
     *
     * @checkstyle ProtectedMethodInFinalClassCheck (100 lines)
     */
    final class Is extends TypeSafeDiagnosingMatcher<YmlTagOutputPredicate> {

        /**
         * Expected YML output predicate (contains, equals, startsWith, etc).
         */
        private final YmlTagOutputPredicate expected;

        /**
         * Ctor.
         * @param type The expected YML output predicate type.
         * @param value The expected YML output predicate value.
         */
        public Is(final String type, final String value) {
            this(new Fake(type, value));
        }

        /**
         * Ctor.
         * @param expected YML predicate.
         */
        @SuppressWarnings("PMD.CallSuperInConstructor")
        public Is(final YmlTagOutputPredicate expected) {
            this.expected = expected;
        }

        @Override
        public void describeTo(final Description dsc) {
            this.describe(dsc, this.expected);
        }

        @Override
        protected boolean matchesSafely(final YmlTagOutputPredicate act,
            final Description dsc) {
            this.describe(dsc, act);
            return act.comparingType().equals(this.expected.comparingType())
                && act.expectedValue().equals(this.expected.expectedValue());
        }

        /**
         * Describe YML tag output predicate in Hamcrest terms.
         * @param dsc The hamcrest description for unit test AR/ER values.
         * @param prd The origin YML output predicate.
         * @checkstyle NonStaticMethodCheck (10 lines)
         */
        private void describe(final Description dsc,
            final YmlTagOutputPredicate prd) {
            dsc.appendText("comparing type=")
                .appendValue(prd.comparingType())
                .appendText(", value=")
                .appendValue(prd.expectedValue());
        }
    }
}
