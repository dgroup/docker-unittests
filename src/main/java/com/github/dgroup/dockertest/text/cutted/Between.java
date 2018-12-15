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
package com.github.dgroup.dockertest.text.cutted;

import com.github.dgroup.dockertest.scalar.If;
import com.github.dgroup.dockertest.text.CuttingException;
import com.github.dgroup.dockertest.text.Text;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;

/**
 * Cut text between left and right borders.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.0
 */
public final class Between {

    /**
     * Origin.
     */
    private final Scalar<String> text;
    /**
     * Left border for splitting.
     */
    private final Scalar<Integer> left;

    /**
     * Ctor.
     * @param text Origin.
     * @param left Border for splitting.
     */
    public Between(final Text text, final String left) {
        this((Scalar<String>) text::text, left);
    }

    /**
     * Ctor.
     * @param text Origin.
     * @param left Border for splitting.
     */
    public Between(final String text, final String left) {
        this((Scalar<String>) () -> text, left);
    }

    /**
     * Ctor.
     * @param text Origin.
     * @param left Border for splitting.
     */
    public Between(final Scalar<String> text, final String left) {
        this(
            text,
            new StickyScalar<>(
                () -> {
                    final int start = text.value().indexOf(left);
                    return new If<>(
                        () -> start == -1,
                        () -> start,
                        () -> start + left.length()
                    ).value();
                }
            )
        );
    }

    /**
     * Ctor.
     * @param text Origin.
     * @param left Border for splitting.
     */
    public Between(final Scalar<String> text, final Scalar<Integer> left) {
        this.text = text;
        this.left = left;
    }

    /**
     * Cut text between left and first occurrence of right border.
     * @param right Border for cutting.
     * @return The text
     * @throws CuttingException in case of errors or wrong input data.
     */
    public String first(final String right) throws CuttingException {
        return new Cutted(
            this.text,
            this.left,
            () -> this.text.value().indexOf(right)
        ).text();
    }

    /**
     * Cut text between left and last occurrence of right border.
     * @param right Border for cutting.
     * @return The text
     * @throws CuttingException in case of errors or wrong input data.
     */
    public String last(final String right) throws CuttingException {
        return new Cutted(
            this.text,
            this.left,
            () -> this.text.value().lastIndexOf(right)
        ).text();
    }

}
