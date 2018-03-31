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

import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Splitted text.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Splitted implements Iterable<Text> {

    /**
     * Origin text.
     */
    private final Scalar<Text> text;
    /**
     * Delimiter for splitting.
     */
    private final String delimiter;

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
        this(() -> () -> text, delimiter);
    }

    /**
     * Ctor.
     * @param text Origin.
     * @param delimiter For splitting.
     */
    public Splitted(final Scalar<Text> text, final String delimiter) {
        this.text = text;
        this.delimiter = delimiter;
    }

    /**
     * Split text to array by particular delimiter.
     * @return Splitted text.
     */
    public String[] asArray() {
        return new UncheckedScalar<>(this.text)
            .value()
            .text()
            .split(this.delimiter);
    }

    @Override
    public Iterator<Text> iterator() {
        return new Mapped<>(
            (Func<String, Text>) txt -> () -> txt,
            this.asStringIterator()
        ).iterator();
    }

    /**
     * Split text to string iterator.
     * @return Splitted text.
     */
    public Iterator<String> asStringIterator() {
        return this.asStrings().iterator();
    }

    /**
     * Split text to string iterable.
     * @return Splitted text.
     */
    public Iterable<String> asStrings() {
        return new IterableOf<>(this.asArray());
    }
}
