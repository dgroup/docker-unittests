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
package com.github.dgroup.dockertest.iterator;

import com.github.dgroup.dockertest.text.TextOf;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

/**
 * Duplex iterator which iterates over 2 iterators at the same time and verify
 *  the relations between elements.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of item.
 * @since 1.0
 */
public final class Duplex<X> implements Iterator<X> {

    /**
     * The first iterator.
     */
    private final Iterator<X> fst;
    /**
     * The second iterator.
     */
    private final Iterator<X> snd;
    /**
     * The function returns true if case when correlation between 2 elements
     *  exists.
     */
    private final BiFunction<X, X, Boolean> fnc;

    /**
     * Ctor.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Duplex(final Iterable<X> fst, final Iterable<X> snd) {
        this(Object::equals, fst.iterator(), snd.iterator());
    }

    /**
     * Ctor.
     * @param fnc The function to detect the correlation between elements.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Duplex(
        final BiFunction<X, X, Boolean> fnc,
        final Iterable<X> fst, final Iterable<X> snd
    ) {
        this(fnc, fst.iterator(), snd.iterator());
    }

    /**
     * Ctor.
     * @param fnc The function to detect the correlation between elements.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Duplex(
        final BiFunction<X, X, Boolean> fnc,
        final Iterator<X> fst,
        final Iterator<X> snd
    ) {
        this.fnc = fnc;
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public boolean hasNext() {
        return this.fst.hasNext() && this.snd.hasNext();
    }

    @Override
    public X next() {
        final X fvl = this.fst.next();
        final X svl = this.snd.next();
        if (this.fnc.apply(fvl, svl)) {
            return fvl;
        } else {
            throw new NoSuchElementException(
                new TextOf(
                    "The is no correlation between `%s` and `%s`.", fvl, svl
                ).text()
            );
        }
    }
}
