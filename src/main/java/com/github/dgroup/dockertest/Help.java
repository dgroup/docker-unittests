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
package com.github.dgroup.dockertest;

import com.github.dgroup.dockertest.text.Splitted;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.cactoos.Input;
import org.cactoos.io.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;

/**
 * Give help information by lines.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Help implements Iterable<String> {

    /**
     * Help details.
     */
    private final Input src;
    /**
     * The charset of the file within help details.
     */
    private final Charset cset;

    /**
     * Ctor.
     */
    public Help() {
        this("help.info");
    }

    /**
     * Ctor.
     * @param file The file in the classpath with help details.
     */
    public Help(final String file) {
        this(new ResourceOf(file), StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param src The help text as bytes.
     * @param cset The charset of the input.
     */
    public Help(final Input src, final Charset cset) {
        this.src = src;
        this.cset = cset;
    }

    @Override
    public Iterator<String> iterator() {
        try {
            return new Splitted(
                new TextOf(
                    new BytesOf(this.src), this.cset
                ).asString(),
                "\n"
            ).iterator();
        } catch (final IOException exp) {
            throw new UncheckedIOException(exp);
        }
    }

}
