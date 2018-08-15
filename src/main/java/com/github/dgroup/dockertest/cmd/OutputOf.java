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
package com.github.dgroup.dockertest.cmd;

import com.github.dgroup.dockertest.test.output.HtmlOutput;
import com.github.dgroup.dockertest.test.output.Output;
import com.github.dgroup.dockertest.test.output.XmlOutput;
import com.github.dgroup.dockertest.text.Splitted;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.cactoos.Func;
import org.cactoos.collection.Filtered;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;

/**
 * Application output implementation of {@link Arg}.
 * User may specify a few output formats like std, xml, html, etc joined by "|".
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class OutputOf extends ArgEnvelope<Collection<Output>> {

    /**
     * Ctor.
     * @param std Standard application output.
     * @param args Command-line arguments specified by the user.
     */
    public OutputOf(final Output std, final String... args) {
        this(std, new ListOf<>(args));
    }

    /**
     * Ctor.
     * @param std Standard application output.
     * @param args Command-line arguments specified by the user.
     */
    public OutputOf(final Output std, final List<String> args) {
        this(
            std,
            new ArgOf("-o", args),
            arg -> {
                final Map<String, Output> supported = new MapOf<>(
                    new MapEntry<>("xml", new XmlOutput()),
                    new MapEntry<>("html", new HtmlOutput())
                );
                final Splitted specified = new Splitted(arg, "\\|");
                return new HashSet<>(
                    new Filtered<>(
                        Objects::nonNull,
                        new Mapped<>(supported::get, specified)
                    )
                );
            }
        );
    }

    /**
     * Ctor.
     * @param std Standard application output.
     * @param arg Command-line argument specified by the user.
     * @param fnc Function to map string to the {@code Collection<Output>}.
     * @checkstyle IndentationCheck (15 lines)
     */
    public OutputOf(final Output std,
        final Arg<String> arg, final Func<String, Collection<Output>> fnc) {
        super(
            () -> new Alternative<Collection<Output>>(
                new com.github.dgroup.dockertest.cmd.Mapped<>(fnc, arg),
                () -> new ListOf<>(std)
            )
        );
    }

}
