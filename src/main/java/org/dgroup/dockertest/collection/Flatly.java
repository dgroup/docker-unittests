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
package org.dgroup.dockertest.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.cactoos.collection.CollectionEnvelope;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.StickyScalar;

/**
 * Flat collection.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of item.
 * @since 1.0
 */
public final class Flatly<X> extends CollectionEnvelope<X> {

    /**
     * Ctor.
     * @param src Origin.
     */
    @SafeVarargs
    public Flatly(final Collection<X>... src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src Origin.
     */
    public Flatly(final Collection<Collection<X>> src) {
        super(
            new StickyScalar<>(
                () -> {
                    final List<X> dst = new ArrayList<>(10);
                    for (final Collection<X> item : src) {
                        dst.addAll(item);
                    }
                    return Collections.unmodifiableList(dst);
                }
            )
        );
    }
}
