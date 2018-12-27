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
package com.github.dgroup.dockertest.yml;

import com.github.dgroup.dockertest.text.Text;
import com.github.dgroup.dockertest.text.TextOf;
import java.io.IOException;

/**
 * Notify that *.yml file has a wrong structure.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class YmlFormatException extends IOException {
    /**
     * Ctor.
     */
    public YmlFormatException() {
        this("YML file has invalid format");
    }

    /**
     * Ctor.
     *
     * @param msg Describes what exactly tag is wrong.
     */
    public YmlFormatException(final String msg) {
        super(msg);
    }

    /**
     * Ctor.
     *
     * @param cause Original cause.
     */
    public YmlFormatException(final Throwable cause) {
        super(cause);
    }

    /**
     * Ctor.
     *
     * @param msg Describes what exactly tag is wrong.
     * @param cause Original cause.
     */
    public YmlFormatException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    /**
     * Ctor.
     *
     * @param msg Describes what exactly tag is wrong.
     */
    public YmlFormatException(final Text msg) {
        this(msg.text());
    }

    /**
     * Ctor.
     *
     * @param tag YML tag which has incorrect structure.
     * @param <T> The type of tag.
     */
    public <T> YmlFormatException(final Tag<T> tag) {
        this("The tag `%s` is missing or has incorrect structure", tag.name());
    }

    /**
     * Ctor.
     *
     * @param ptrn Template.
     * @param args Arguments for template above.
     */
    public YmlFormatException(final String ptrn, final Object... args) {
        this(new TextOf(ptrn, args));
    }
}
