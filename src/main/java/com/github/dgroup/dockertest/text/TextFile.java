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
package com.github.dgroup.dockertest.text;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.cactoos.Scalar;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.TextOf;

/**
 * Read file to string.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TextFile {

    /**
     * File to read.
     */
    private final UncheckedScalar<File> origin;
    /**
     * File charset.
     */
    private final Charset charset;

    /**
     * Ctor.
     * @param path The file for reading.
     */
    public TextFile(final File path) {
        this(() -> path);
    }

    /**
     * Ctor.
     * @param origin File for reading.
     */
    public TextFile(final Scalar<File> origin) {
        this(origin, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param origin File for reading.
     * @param charset File charset for reading.
     */
    public TextFile(final Scalar<File> origin, final Charset charset) {
        this.origin = new UncheckedScalar<>(origin);
        this.charset = charset;
    }

    /**
     * File content.
     * @return File content as string.
     * @throws IOException if an I/O error occurs
     * @checkstyle IllegalCatchCheck (20 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public String text() throws IOException {
        try {
            return new TextOf(
                new BytesOf(
                    new InputOf(this.origin.value())
                ),
                this.charset
            ).asString();
        } catch (final Exception exp) {
            throw new IOException(exp);
        }
    }

    /**
     * Absolute path to the file.
     * @return The path.
     */
    public String path() {
        return this.origin.value().getAbsolutePath();
    }

    /**
     * The name of original file.
     * @return The file name itself.
     */
    public String name() {
        return this.origin.value().getName();
    }
}
