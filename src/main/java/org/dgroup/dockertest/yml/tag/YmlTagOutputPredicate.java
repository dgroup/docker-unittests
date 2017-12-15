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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dgroup.dockertest.yml.tag;

import java.util.function.Predicate;
import org.dgroup.dockertest.text.PlainFormattedText;

/**
 * Represents yml tag
 * {@code /tests/test/output/contains|equal|startWith|endWith}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class YmlTagOutputPredicate {

    /**
     * Compare type: contains, equals, startWith, endWith.
     */
    private final String type;
    /**
     * Expected value from test scenario.
     */
    private final String expected;
    /**
     * Condition which should satisfy the actual value.
     */
    private final Predicate<String> predicate;

    /**
     * Ctor.
     * @param type Comparing type like contains, equal, startWith, endWith.
     * @param expected Expected value from test scenario.
     * @param predicate Condition, which should satisfy the actual value.
     */
    public YmlTagOutputPredicate(final String type, final String expected,
        final Predicate<String> predicate) {
        this.type = type;
        this.expected = expected;
        this.predicate = predicate;
    }

    /**
     * Comparing type.
     * @return Available types like contains, equal, startWith, endWith.
     */
    public String type() {
        return this.type;
    }

    /**
     * Compare expected value with actual.
     * @param actual Output received from docker container.
     * @return True in case if expected value is equal to actual value.
     */
    public boolean test(final String actual) {
        return this.predicate.test(actual);
    }

    @Override
    public String toString() {
        return new PlainFormattedText("%s=`%s`", this.type, this.expected)
            .asString();
    }
}
