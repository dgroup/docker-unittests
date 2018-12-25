/**
 * MIT License
 *
 * Copyright (c) 2017-2018 Yurii Dubinka
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

package com.github.dgroup.dockertest.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Envelope for {@link Set}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> The type of elements.
 * @since 1.1
 */
@SuppressWarnings("PMD.TooManyMethods")
public class SetEnvelope<T> implements Set<T> {

    /**
     * Origin.
     */
    private final UncheckedScalar<Set<T>> origin;

    /**
     * Ctor.
     * @param origin The origin.
     */
    public SetEnvelope(final Scalar<Set<T>> origin) {
        this.origin = new UncheckedScalar<>(origin);
    }

    @Override
    public final int size() {
        return this.origin.value().size();
    }

    @Override
    public final boolean isEmpty() {
        return this.origin.value().isEmpty();
    }

    @Override
    public final boolean contains(final Object obj) {
        return this.origin.value().contains(obj);
    }

    @Override
    public final Iterator<T> iterator() {
        return this.origin.value().iterator();
    }

    @Override
    public final Object[] toArray() {
        return this.origin.value().toArray();
    }

    @Override
    public final <T> T[] toArray(final T... obj) {
        return this.origin.value().toArray(obj);
    }

    @Override
    public final boolean add(final T obj) {
        throw new UnsupportedOperationException("#add()");
    }

    @Override
    public final boolean remove(final Object obj) {
        throw new UnsupportedOperationException("#remove()");
    }

    @Override
    public final boolean containsAll(final Collection<?> collection) {
        return this.origin.value().containsAll(collection);
    }

    @Override
    public final boolean addAll(final Collection<? extends T> collection) {
        throw new UnsupportedOperationException("#addAll()");
    }

    @Override
    public final boolean retainAll(final Collection<?> collection) {
        throw new UnsupportedOperationException("#retainAll");
    }

    @Override
    public final boolean removeAll(final Collection<?> collection) {
        throw new UnsupportedOperationException("#removeAll");
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException("#clear");
    }
}
