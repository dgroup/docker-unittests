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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.cactoos.collection.Filtered;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.UncheckedTernary;
import org.dgroup.dockertest.test.output.HtmlOutput;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.StdOutput;
import org.dgroup.dockertest.test.output.XmlOutput;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class OutputArg {

    private final Arg origin;
    private final Map<String, Output> outputs;
    private final String delimiter;

    public OutputArg(final List<String> arguments) {
        this(new DefaultArg("-o", arguments), "\\|");
    }

    public OutputArg(final Arg origin, final String delimiter) {
        this.origin = origin;
        this.outputs = new HashMap<>();
        this.outputs.put("xml", new XmlOutput());
        this.outputs.put("html", new HtmlOutput());
        this.outputs.put("std", new StdOutput());
        this.delimiter = delimiter;
    }


    public List<Output> outputs() {
        return new UncheckedTernary<List<Output>>(
            this.origin.specified(),
            () -> new ListOf<>(
                new Filtered<>(
                    Objects::nonNull,
                    new Mapped<>(
                        this.outputs::get,
                        new ListOf<>(
                            this.origin.value().split(this.delimiter)
                        )
                    )
                )
            ),
            () -> new ListOf<>(
                new StdOutput()
            )
        ).value();
    }
}
