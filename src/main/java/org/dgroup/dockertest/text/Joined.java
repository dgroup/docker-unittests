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
package org.dgroup.dockertest.text;

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.scalar.If;

/**
 * Represents different objects like arrays, iterables, etc as string
 *  joined by particular delimiter.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Joined implements Text {

    /**
     * All string values for joining.
     */
    private final Iterable<String> values;
    /**
     * Delimiter for joining procedure.
     */
    private final Scalar<String> delimiter;

    /**
     * Ctor.
     * @param values For joining.
     */
    public Joined(final String... values) {
        this(new IterableOf<>(values));
    }

    /**
     * Ctor.
     * @param values For joining.
     */
    public Joined(final Iterable<String> values) {
        this(values, " ");
    }

    /**
     * Ctor.
     * @param values For joining.
     * @param dlmtr Common delimiter for values above.
     */
    public Joined(final Iterable<String> values, final String dlmtr) {
        this(values, new If<>(dlmtr == null, "", () -> dlmtr));
    }

    /**
     * Ctor.
     * @param values For joining.
     * @param dlmtr Common delimiter for values above.
     */
    public Joined(final Iterable<String> values, final Scalar<String> dlmtr) {
        this.values = values;
        this.delimiter = dlmtr;
    }

    @Override
    public String text() {
        return String.join(
            new UncheckedScalar<>(this.delimiter).value(),
            this.values
        );
    }

    @Override
    public String toString() {
        return this.text();
    }

}
