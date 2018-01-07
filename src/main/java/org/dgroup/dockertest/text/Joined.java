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
package org.dgroup.dockertest.text;

import one.util.streamex.StreamEx;
import org.cactoos.iterable.IterableOf;
import org.dgroup.dockertest.scalar.UncheckedTernary;

/**
 * Represents different objects like arrays, iterables, etc as string
 *  joined by particular delimiter.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Joined {

    /**
     * All string values for joining.
     */
    private final Iterable<String> values;
    /**
     * Delimiter for joining procedure.
     */
    private final UncheckedTernary<String> delimiter;

    /**
     * Ctor.
     * @param values For joining procedure.
     */
    public Joined(final String... values) {
        this(new IterableOf<>(values), " ");
    }

    /**
     * Ctor.
     * @param values Is values.
     * @param delimiter Is delimiter
     */
    public Joined(final Iterable<String> values, final String delimiter) {
        this.values = values;
        this.delimiter = new UncheckedTernary<>(
            () -> delimiter == null,
            () -> "",
            () -> delimiter
        );
    }

    /**
     * Transform array/collection/etc to string.
     * @return New joined string in accordance with {@code this.delimiter }
     * @todo #61 Remove streamex dependency from the project,
     *  use cactoos mapped iterable + func instead.
     */
    public String asString() {
        return StreamEx.of(this.values.iterator())
            .joining(this.delimiter.value());
    }

    @Override
    public String toString() {
        return this.asString();
    }

}
