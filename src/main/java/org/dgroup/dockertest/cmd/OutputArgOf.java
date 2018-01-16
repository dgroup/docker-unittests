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
 * Single-arg implementation of {@link OutputArg}.

 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class OutputArgOf implements OutputArg {

    /**
     * Origin.
     */
    private final UncheckedArg origin;
    /**
     * Supported output formats.
     */
    private final Map<String, Output> supported;
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
     * @param args Outputs selected by user.
     */
    public OutputArgOf(final List<String> args) {
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
     * @param origin Command line argument specified by user.
     * @param delimiter For splitting value specified by user.
     * @param supported Supported output formats.
     * @param std Standard output.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public OutputArgOf(
        final UncheckedArg origin,
        final String delimiter,
        final Map<String, Output> supported,
        final StdOutput std
    ) {
        this(
            origin,
            new UncheckedTernary<List<String>>(
                origin::specifiedByUser,
                () -> new StickyList<>(origin.value().split(delimiter)),
                ListOf::new
            ).value(),
            supported,
            std
        );
    }

    /**
     * Ctor.
     * @param origin Command line argument specified by user.
     * @param specified Output formats by user user.
     * @param supported Supported output formats.
     * @param std Standard output.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public OutputArgOf(
        final UncheckedArg origin,
        final List<String> specified,
        final Map<String, Output> supported,
        final StdOutput std
    ) {
        this.origin = origin;
        this.specified = specified;
        this.supported = supported;
        this.std = std;
    }

    @Override
    public Set<Output> asSet() {
        return new HashSet<>(
            new UncheckedTernary<List<Output>>(
                () -> !this.specified.isEmpty()
                    && this.supported.keySet().containsAll(this.specified),
                () -> new Mapped<>(this.supported::get, this.specified),
                () -> new ListOf<>(this.std)
            ).value()
        );
    }

    @Override
    public String name() {
        return this.origin.name();
    }

    @Override
    public String value() {
        return this.origin.value();
    }

    @Override
    public boolean specifiedByUser() {
        return this.origin.specifiedByUser();
    }

}
