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

import com.github.dgroup.dockertest.scalar.If;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import org.cactoos.collection.CollectionOf;
import org.cactoos.collection.Filtered;

/**
 * Null-safe set.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> The type of item.
 * @since 1.1
 */
public class SafeSet<T> extends SetEnvelope<T> {

    /**
     * Ctor.
     * @param src The source collection.
     */
    public SafeSet(final T... src) {
        this(new CollectionOf<T>(src));
    }

    /**
     * Ctor.
     * @param src The source collection.
     */
    public SafeSet(final Collection<T> src) {
        super(
            new If<>(
                () -> src == null || src.isEmpty()
                    || new Filtered<>(Objects::nonNull, src).isEmpty(),
                Collections::emptySet,
                () -> new LinkedHashSet<>(src)
            )
        );
    }
}
