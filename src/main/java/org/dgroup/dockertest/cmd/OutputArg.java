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
package org.dgroup.dockertest.cmd;

import java.util.Iterator;
import java.util.List;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.scalar.CachedTernary;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.SupportedOutputs;

/**
 * Represents a command line argument {@code -o} for output format.
 * Available formats are standard out, html and xml.
 *
 * For example {@code -o std|xml|html} will print testing results
 * to standard output, xml and html file.
 *
 * If case if argument is absent then standard output will be used.

 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class OutputArg implements Iterable<Output> {

    /**
     * Supported output formats.
     */
    private final SupportedOutputs outputs;
    /**
     * Output formats specified by user from command line.
     */
    private final CachedTernary<List<String>> specified;

    /**
     * Ctor.
     * @param args Command-line arguments from user.
     */
    public OutputArg(final List<String> args) {
        this(new DefaultArg("-o", args), "\\|", new SupportedOutputs());
    }

    /**
     * Ctor.
     * @param output Command line argument specified by user.
     * @param delimiter For splitting value specified by user.
     * @param outputs Supported output formats.
     */
    public OutputArg(final Arg output, final String delimiter,
        final SupportedOutputs outputs) {
        this.outputs = outputs;
        this.specified = new CachedTernary<>(
            new UncheckedTernary<List<String>>(
                output.specifiedByUser(),
                () -> new ListOf<>(output.value().split(delimiter)),
                ListOf::new
            )
        );
    }

    @Override
    public Iterator<Output> iterator() {
        return this.outputs.availableFor(this.specified.value());
    }

}
