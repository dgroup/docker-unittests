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
package com.github.dgroup.dockertest.yml;

import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.yml.tag.UncheckedTag;
import java.util.Collection;
import org.cactoos.collection.CollectionOf;
import org.cactoos.collection.Mapped;
import org.cactoos.text.JoinedText;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Represents yml tag {@code /tests/test/output}.
 * Tag can contain list of predicates contains, equals, startsWith, endsWith.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @todo #/DEV TgOutput - `Fake`/`Is` implementation is required for unit tests.
 *  Replace TgOutputOf in unit tests in favour of Fake implementation.
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public interface TgOutput extends Tag<Collection<TgOutputPredicate>> {

    /**
     * Hamcrest matcher for unit testing purposes.
     * @checkstyle ProtectedMethodInFinalClassCheck (100 lines)
     */
    class Is extends TypeSafeDiagnosingMatcher<TgOutput> {

        /**
         * The expected predicates within the {@link TgOutput}.
         */
        private final Collection<TgOutputPredicate> expected;

        /**
         * Ctor.
         * @param expected The expected predicates within the {@link TgOutput}.
         */
        public Is(final TgOutputPredicate... expected) {
            this(new CollectionOf<>(expected));
        }

        /**
         * Ctor.
         * @param expected The expected predicates within the {@link TgOutput}.
         */
        public Is(final Collection<TgOutputPredicate> expected) {
            super();
            this.expected = expected;
        }

        @Override
        public void describeTo(final Description desc) {
            describe(desc, this.expected);
        }

        @Override
        protected boolean matchesSafely(
            final TgOutput tag, final Description desc
        ) {
            final Collection<TgOutputPredicate> actual = new UncheckedTag<>(tag)
                .value();
            describe(desc, actual);
            return actual.containsAll(this.expected);
        }

        /**
         * Print the predicates to the hamcrest description.
         * @param desc The description.
         * @param prdcts The predicates to print.
         */
        private static void describe(
            final Description desc, final Collection<TgOutputPredicate> prdcts
        ) {
            desc.appendText("Predicates are ").appendValue(
                new UncheckedText(
                    new JoinedText(
                        ", ",
                        new Mapped<>(
                            exp -> new TextOf(
                                "%s '%s'", exp.comparingType(), exp.expected()
                            ).text(),
                            prdcts
                        )
                    )
                ).asString()
            );
        }
    }
}
