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
package org.dgroup.dockertest.yml;

import org.dgroup.dockertest.text.PlainText;

/**
 * Notify that *.yml file has a wrong structure.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class IllegalYmlFormatException extends Exception {

    /**
     * Ctor.
     */
    public IllegalYmlFormatException() {
        this("YML file has invalid format");
    }

    /**
     * Ctor.
     * @param cause Original cause.
     */
    public IllegalYmlFormatException(final Throwable cause) {
        super(cause);
    }

    /**
     * Ctor.
     * @param msg Describes what exactly tag is wrong.
     */
    public IllegalYmlFormatException(final PlainText msg) {
        this(msg.toString());
    }

    /**
     * Ctor.
     * @param msg Describes what exactly tag is wrong.
     */
    public IllegalYmlFormatException(final String msg) {
        super(msg);
    }

    /**
     * Ctor.
     * @param msg Describes what exactly tag is wrong.
     * @param cause Original cause.
     */
    public IllegalYmlFormatException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    /**
     * Ctor.
     * @param tag YML tag which has missing required child.
     * @param child Name of child YML tag.
     */
    public IllegalYmlFormatException(final String tag, final String child) {
        this("Tag `%s` has missing required child tag `%s`", tag, child);
    }

    /**
     * Ctor.
     * @param ptrn Template.
     * @param args Arguments for template above.
     */
    private IllegalYmlFormatException(final String ptrn, final Object... args) {
        this(new PlainText(ptrn, args));
    }

}
