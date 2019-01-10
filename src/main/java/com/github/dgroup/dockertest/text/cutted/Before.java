/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
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
package com.github.dgroup.dockertest.text.cutted;

import com.github.dgroup.dockertest.text.CuttingException;
import com.github.dgroup.dockertest.text.Text;

/**
 * Cut text before particular string.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Before {

    /**
     * Origin.
     */
    private final String origin;
    /**
     * Right border for cutting.
     */
    private final String right;

    /**
     * Ctor.
     * @param origin Text.
     * @param right Border for cutting.
     */
    public Before(final Text origin, final String right) {
        this(origin.text(), right);
    }

    /**
     * Ctor.
     * @param origin Text.
     * @param right Border for cutting.
     */
    public Before(final String origin, final String right) {
        this.origin = origin;
        this.right = right;
    }

    /**
     * Cut text before right border.
     * @return Text.
     * @throws CuttingException in case wrong input data.
     */
    public String text() throws CuttingException {
        if (this.origin.contains(this.right)) {
            return this.origin.substring(
                0, this.origin.indexOf(this.right)
            );
        } else {
            throw new CuttingException(
                "Can't cut `%s` from `%s`.", this.right, this.origin
            );
        }
    }
}
