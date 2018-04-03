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
package org.dgroup.dockertest.cmd;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.dgroup.dockertest.scalar.If;
import org.dgroup.dockertest.test.output.HtmlOutput;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.XmlOutput;
import org.dgroup.dockertest.text.Splitted;

/**
 * Application output implementation of {@link Arg}.
 * User may specify a few output formats like std, xml, html, etc joined by "|".
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class OutputOf extends ArgEnvelope<Set<Output>> {

    /**
     * Ctor.
     * @param args Outputs selected by user.
     */
    public OutputOf(final List<String> args) {
        this(
            "-o", args, "\\|",
            new MapOf<>(
                new MapEntry<>("xml", new XmlOutput()),
                new MapEntry<>("html", new HtmlOutput())
            )
        );
    }

    /**
     * Ctor.
     * @param label Associated with command-line output argument.
     * @param args All command-line arguments specified by user.
     * @param delimiter For splitting value specified by user.
     * @param supported Supported output formats.
     * @checkstyle IndentationCheck (30 lines)
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public OutputOf(
        final String label,
        final List<String> args,
        final String delimiter,
        final Map<String, Output> supported
    ) {
        super(
            label, args,
            arg -> {
                final Splitted specified = new Splitted(arg, delimiter);
                return new HashSet<>(
                    new If<List<Output>>(
                        () -> supported.keySet().containsAll(specified),
                        () -> new Mapped<>(supported::get, specified),
                        ListOf::new
                    ).value()
                );
            }
        );
    }

}
