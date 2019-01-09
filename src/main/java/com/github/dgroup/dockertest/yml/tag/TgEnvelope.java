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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.yml.Tag;
import com.github.dgroup.dockertest.yml.YmlFormatException;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Tag envelope.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 */
public class TgEnvelope<T> implements Tag<T> {

    /**
     * YML tags tree.
     */
    private final Scalar<T> yml;

    /**
     * YML tag name.
     */
    private final String tag;

    /**
     * Ctor.
     * @param yml Object tree loaded from *.yml file with tests.
     * @param tag Current YML tag name.
     */
    public TgEnvelope(final Scalar<T> yml, final String tag) {
        this.yml = yml;
        this.tag = tag;
    }

    @Override
    public final String name() {
        return this.tag;
    }

    @Override
    public final T value() throws YmlFormatException {
        final T val = new UncheckedScalar<>(this.yml).value();
        if (val == null || val.toString().trim().isEmpty()) {
            throw new YmlFormatException(this);
        }
        return val;
    }
}
