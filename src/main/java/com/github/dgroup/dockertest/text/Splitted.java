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
package com.github.dgroup.dockertest.text;

import org.cactoos.Scalar;
import org.cactoos.collection.CollectionEnvelope;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.StickyScalar;

/**
 * Splitted text.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Splitted extends CollectionEnvelope<String> {

    /**
     * Ctor.
     * @param text Origin.
     */
    public Splitted(final String text) {
        this(text, " ");
    }

    /**
     * Ctor.
     * @param text Origin.
     * @param delimiter For splitting.
     */
    public Splitted(final String text, final String delimiter) {
        this(() -> text, delimiter);
    }

    /**
     * Ctor.
     * @param text Origin.
     * @param delimiter For splitting.
     */
    public Splitted(final Scalar<String> text, final String delimiter) {
        this(new StickyScalar<>(() -> text.value().split(delimiter)));
    }

    /**
     * Ctor.
     * @param text Origin.
     */
    public Splitted(final Scalar<String[]> text) {
        super(() -> new ListOf<>(text.value()));
    }

    /**
     * Convert splitted values to the string array.
     * @return The splitted text.
     */
    public String[] toStringArray() {
        return this.toArray(new String[this.size()]);
    }
}
