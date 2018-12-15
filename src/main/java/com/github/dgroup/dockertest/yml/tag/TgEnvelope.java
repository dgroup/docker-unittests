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

import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import com.github.dgroup.dockertest.yml.YmlTag;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * YmlTag envelope.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 * @todo #154/DEV The `setup` tag is required.
 */
class TgEnvelope<T> implements YmlTag<T> {

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
    TgEnvelope(final Scalar<T> yml, final String tag) {
        this.yml = yml;
        this.tag = tag;
    }

    @Override
    public String name() {
        return this.tag;
    }

    @Override
    public T value() throws IllegalYmlFormatException {
        final T val = new UncheckedScalar<>(this.yml).value();
        if (val == null || val.toString().trim().isEmpty()) {
            throw new IllegalYmlFormatException(
                new TextOf(
                    "`%s` tag is missing or has incorrect structure", this.tag
                )
            );
        }
        return val;
    }

    /**
     * Give child tag value.
     * @param child Tag.
     * @return Value of child tag.
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     * @checkstyle IllegalCatchCheck (20 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    protected T child(final YmlTag<T> child)
        throws IllegalYmlFormatException {
        try {
            final T cval = child.value();
            if (cval == null || cval.toString().trim().isEmpty()) {
                throw new IllegalYmlFormatException(
                    new TextOf(
                        "Tag `%s` has missing required child tag `%s`",
                        this.name(), child.name()
                    )
                );
            }
            return cval;
        } catch (final Exception exp) {
            throw new IllegalYmlFormatException(exp.getMessage(), exp);
        }
    }

}
