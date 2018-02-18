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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dgroup.dockertest.text.highlighted;

import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;

/**
 * Envelope for {@link HighlightedText}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
class HighlightedTextEnvelope implements HighlightedText {

    /**
     * Origin.
     */
    private final Scalar<HighlightedText> msg;

    /**
     * Ctor.
     * @param msg Message which should be highlighted.
     * @param color Color which should be used for highlighting.
     */
    HighlightedTextEnvelope(final Object msg, final Color color) {
        this(msg.toString(), color);
    }

    /**
     * Ctor.
     * @param msg Message which should be highlighted.
     * @param color Color which should be used for highlighting.
     */
    HighlightedTextEnvelope(final String msg, final Color color) {
        this(() -> () -> Ansi.ansi()
            .fgBright(color)
            .bold()
            .a(msg)
            .reset()
            .toString()
        );
    }

    /**
     * Ctor.
     * @param msg Origin.
     */
    HighlightedTextEnvelope(final Scalar<HighlightedText> msg) {
        this.msg = msg;
    }

    @Override
    public String text() {
        return new UncheckedScalar<>(this.msg).value().text();
    }

    @Override
    public String toString() {
        return this.text();
    }

}
