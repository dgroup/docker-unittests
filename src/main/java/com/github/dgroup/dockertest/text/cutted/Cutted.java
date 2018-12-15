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
import org.cactoos.Scalar;

/**
 * Cutted text.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Cutted {

    /**
     * Origin.
     */
    private final Scalar<String> txt;
    /**
     * Left border for cutting.
     */
    private final Scalar<Integer> lft;
    /**
     * Right border for cutting.
     */
    private final Scalar<Integer> rht;

    /**
     * Ctor.
     * @param txt Origin.
     * @param left Border.
     */
    public Cutted(final Scalar<String> txt, final Integer left) {
        this(txt, () -> left);
    }

    /**
     * Ctor.
     * @param txt Origin.
     * @param left Border.
     */
    public Cutted(final Scalar<String> txt, final Scalar<Integer> left) {
        this(txt, left, () -> -1);
    }

    /**
     * Ctor.
     * @param txt Origin.
     * @param left Border for cutting.
     * @param right Border for cutting.
     */
    public Cutted(final String txt, final Integer left, final Integer right) {
        this(() -> txt, () -> left, () -> right);
    }

    /**
     * Ctor.
     * @param txt Origin.
     * @param left Border for cutting.
     * @param right Border for cutting.
     */
    public Cutted(
        final Scalar<String> txt,
        final Scalar<Integer> left,
        final Scalar<Integer> right
    ) {
        this.txt = txt;
        this.lft = left;
        this.rht = right;
    }

    /**
     * Cutted text.
     * @return The text.
     * @throws CuttingException in case of wrong input data
     * @checkstyle IllegalCatchCheck (30 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public String text() throws CuttingException {
        String cutted = "";
        try {
            final String origin = this.txt.value();
            if (this.inRange(this.lft)) {
                cutted = new If<>(
                    () -> this.inRange(this.rht),
                    () -> origin.substring(this.lft.value(), this.rht.value()),
                    () -> origin.substring(this.lft.value())
                ).value();
            }
            return cutted;
        } catch (final Exception exp) {
            throw new CuttingException(exp);
        }
    }

    /**
     * Check that position belongs to the current string range.
     * @param position Of character.
     * @return The true if position within range.
     * @throws CuttingException in case if position can't be found.
     * @checkstyle IllegalCatchCheck (20 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private boolean inRange(final Scalar<Integer> position)
        throws CuttingException {
        try {
            return position.value() >= 0
                && position.value() < this.txt.value().length();
        } catch (final Exception exp) {
            throw new CuttingException(exp);
        }
    }

}
