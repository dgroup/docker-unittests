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

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * Read file to string.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class FileAsString {

    /**
     * File to read.
     */
    private final File origin;
    /**
     * File charset.
     */
    private final Charset charset;

    /**
     * Ctor.
     * @param origin For reading.
     * @todo #4 Add cmd flag which allows user to select encoding
     *  for *.yml file with tests.
     */
    public FileAsString(final File origin) {
        this(origin, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param origin File for reading.
     * @param charset File charset for reading.
     */
    public FileAsString(final File origin, final Charset charset) {
        this.origin = origin;
        this.charset = charset;
    }

    /**
     * File content.
     * @return File content as string.
     */
    public String content() {
        return new UncheckedText(
            new TextOf(
                new BytesOf(
                    new InputOf(this.origin)
                ),
                this.charset
            )
        ).asString();
    }

}
