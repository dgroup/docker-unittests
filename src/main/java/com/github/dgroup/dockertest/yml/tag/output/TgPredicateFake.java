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
package com.github.dgroup.dockertest.yml.tag.output;

import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import com.github.dgroup.dockertest.yml.YmlFormatException;
import java.util.Objects;
import org.cactoos.Func;

/**
 * Fake implementation of {@link TgOutputPredicate} for the unit test purposes.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1
 */
public final class TgPredicateFake implements TgOutputPredicate {

    /**
     * Compare type: contains, equals, startsWith, endsWith, matches.
     */
    private final String type;

    /**
     * Expected value from test scenario.
     */
    private final String expctd;

    /**
     * Condition which should satisfy the actual value.
     */
    private final Func<String, Boolean> fnc;

    /**
     * Ctor.
     * @param type Comparing type like contains, equal, startsWiths, endsWith.
     * @param expctd Expected value from test scenario.
     */
    public TgPredicateFake(final String type, final String expctd) {
        this(type, expctd, actual -> true);
    }

    /**
     * Ctor.
     * @param type Comparing type like contains, equal, startsWiths, endsWith.
     * @param expctd Expected value from test scenario.
     * @param fnc Condition, which should satisfy the actual value.
     */
    public TgPredicateFake(
        final String type, final String expctd, final Func<String, Boolean> fnc
    ) {
        this.type = type;
        this.expctd = expctd;
        this.fnc = fnc;
    }

    @Override
    public String comparingType() {
        return this.type;
    }

    @Override
    public String expected() {
        return this.expctd;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public boolean test(final String actual)
        throws YmlFormatException {
        // @checkstyle IllegalCatchCheck (5 lines)
        try {
            return this.fnc.apply(actual);
        } catch (final Exception exp) {
            throw new YmlFormatException(exp);
        }
    }

    @Override
    public String toString() {
        return new TextOf("%s %s", this.comparingType(), this.expected())
            .text();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.comparingType(), this.expected());
    }

    @Override
    @SuppressWarnings("PMD.OnlyOneReturn")
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TgOutputPredicate)) {
            return false;
        }
        final TgOutputPredicate that = (TgOutputPredicate) obj;
        return this.comparingType().equals(that.comparingType())
            && this.expected().equals(that.expected());
    }
}
