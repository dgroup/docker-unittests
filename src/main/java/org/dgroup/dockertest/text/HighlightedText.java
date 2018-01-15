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
package org.dgroup.dockertest.text;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;

/**
 * Allows to highlight text via <b>jansi</b> library.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class HighlightedText {

    /**
     * Message which should be highlighted.
     */
    private final String msg;
    /**
     * Color which should be used for highlighting.
     */
    private final Color color;

    /**
     * Ctor.
     * @param msg For highlighting.
     * @param color For highlighting.
     */
    public HighlightedText(final Object msg, final Color color) {
        this(msg.toString(), color);
    }

    /**
     * Ctor.
     * @param msg For highlighting.
     * @param color For highlighting.
     */
    public HighlightedText(final String msg, final Color color) {
        this.msg = msg;
        this.color = color;
    }

    @Override
    public String toString() {
        return Ansi.ansi()
            .fgBright(this.color)
            .bold()
            .a(this.msg)
            .reset()
            .toString();
    }

}
