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
package com.github.dgroup.dockertest.process.output;

import com.github.dgroup.dockertest.process.CmdOutput;
import com.github.dgroup.dockertest.text.Splitted;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Text output for docker command.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TextCmdOutput implements CmdOutput, Iterable<String> {

    /**
     * Origin.
     */
    private final Scalar<String> out;

    /**
     * Ctor.
     * @param out Origin.
     */
    public TextCmdOutput(final String out) {
        this(() -> out);
    }

    /**
     * Ctor.
     * @param out Origin.
     */
    public TextCmdOutput(final Scalar<String> out) {
        this.out = out;
    }

    @Override
    public String text() {
        return new UncheckedScalar<>(this.out).value();
    }

    @Override
    public List<String> byLines() {
        final List<String> lines = new LinkedList<>();
        this.iterator().forEachRemaining(lines::add);
        return new ListOf<>(lines);
    }

    @Override
    public Iterator<String> iterator() {
        return new Splitted(this.out, "\n").iterator();
    }
}
