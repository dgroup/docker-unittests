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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.list.StickyList;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.test.output.HtmlOutput;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.XmlOutput;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.test.output.std.StdOutputOf;

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
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class OutputArg {

    /**
     * Supported output formats.
     */
    private final Map<String, Output> out;
    /**
     * Output formats specified by user from command line.
     */
    private final List<String> specified;
    /**
     * Standard output format.
     */
    private final StdOutput std;

    /**
     * Ctor.
     * @param args Command-line arguments from user.
     */
    public OutputArg(final List<String> args) {
        this(
            new UncheckedArg("-o", args),
            "\\|",
            new MapOf<>(
                new MapEntry<>("xml", new XmlOutput()),
                new MapEntry<>("html", new HtmlOutput())
            ),
            new StdOutputOf()
        );
    }

    /**
     * Ctor.
     * @param output Command line argument specified by user.
     * @param delimiter For splitting value specified by user.
     * @param out Supported output formats.
     * @param std Standard output.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public OutputArg(
        final UncheckedArg output,
        final String delimiter,
        final Map<String, Output> out,
        final StdOutput std
    ) {
        this.out = out;
        this.specified = new UncheckedTernary<List<String>>(
            output::specifiedByUser,
            () -> new StickyList<>(output.value().split(delimiter)),
            ListOf::new
        ).value();
        this.std = std;
    }

    /**
     * Give all outputs specified by user as a set.
     * @return Selected outputs by user or default {@link StdOutputOf}.
     */
    public Set<Output> asSet() {
        return new HashSet<>(
            new UncheckedTernary<List<Output>>(
                () -> !this.specified.isEmpty()
                    && this.out.keySet().containsAll(this.specified),
                () -> new Mapped<>(this.out::get, this.specified),
                () -> new ListOf<>(this.std)
            ).value()
        );
    }

}
