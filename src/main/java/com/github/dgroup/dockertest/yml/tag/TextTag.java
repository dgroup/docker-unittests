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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import org.cactoos.Scalar;

/**
 * Text yml tag.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @todo #/DEV Extend YmlTagEvnvelope instead of implementation of YmlTag.
 */
public final class TextTag implements YmlTag<String> {

    /**
     * Origin.
     */
    private final Scalar<String> yml;
    /**
     * Name of yml tag.
     */
    private final String tag;

    /**
     * Ctor.
     * @param yml Object tree loaded from *.yml file with tests.
     * @param tag Current YML tag name.
     */
    public TextTag(final Scalar<String> yml, final String tag) {
        this.yml = yml;
        this.tag = tag;
    }

    @Override
    public String name() {
        return this.tag;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public String value() throws IllegalYmlFormatException {
        // @checkstyle IllegalCatchCheck (10 lines)
        try {
            return this.yml.value();
        } catch (final Exception exp) {
            throw new IllegalYmlFormatException(exp);
        }
    }
}
