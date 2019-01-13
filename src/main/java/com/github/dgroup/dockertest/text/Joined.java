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
package com.github.dgroup.dockertest.text;

import com.github.dgroup.dockertest.scalar.If;
import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.UncheckedScalar;

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
     * Text values for joining.
     */
    private final Iterable<String> values;
    /**
     * Delimiter for joining procedure.
     */
    private final Scalar<String> delimiter;

    /**
     * Ctor.
     * @param vls Values for joining.
     */
    public Joined(final String... vls) {
        this(new IterableOf<>(vls));
    }

    /**
     * Ctor.
     * @param vls Values for joining.
     */
    public Joined(final Text... vls) {
        this(new Mapped<>(Text::text, vls));
    }

    /**
     * Ctor.
     * @param vls Values for joining.
     */
    public Joined(final Iterable<String> vls) {
        this(vls, " ");
    }

    /**
     * Ctor.
     * @param vls Values for joining.
     * @param dtr Common delimiter for values above.
     */
    public Joined(final Iterable<String> vls, final String dtr) {
        this(vls, new If<>(dtr == null, "", () -> dtr));
    }

    /**
     * Ctor.
     * @param vls Values for joining.
     * @param dtr Common delimiter for values above.
     */
    public Joined(final Iterator<String> vls, final String dtr) {
        this(new IterableOf<>(vls), dtr);
    }

    /**
     * Ctor.
     * @param vls Values for joining.
     * @param dtr Common delimiter for values above.
     */
    public Joined(final Iterable<String> vls, final Scalar<String> dtr) {
        this.values = vls;
        this.delimiter = dtr;
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
