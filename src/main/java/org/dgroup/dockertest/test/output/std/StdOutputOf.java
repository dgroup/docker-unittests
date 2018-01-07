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
package org.dgroup.dockertest.test.output.std;

import java.io.PrintStream;
import org.cactoos.Proc;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.And;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.docker.output.CmdOutput;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.test.TestOutcome;
import org.dgroup.dockertest.test.TestingOutcome;
import org.dgroup.dockertest.text.HighlightedText;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.fusesource.jansi.Ansi.Color;

/**
 * Standard output for printing app progress and testing results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class StdOutputOf implements StdOutput {

    /**
     * Standard indent from left side of screen.
     */
    private final String indent;
    /**
     * Standard output.
     */
    private final PrintStream out;

    /**
     * Ctor.
     */
    public StdOutputOf() {
        this(System.out, "    ");
    }

    /**
     * Ctor.
     * @param out Instance for print procedure.
     * @param indent Default indent from left side of screen.
     */
    public StdOutputOf(final PrintStream out, final String indent) {
        this.out = out;
        this.indent = indent;
    }

    @Override
    public void print(final TestingOutcome outcome) {
        this.printTestingStatus(
            new UncheckedScalar<>(
                new And(
                    (Proc<TestOutcome>) test -> this.print(test.message()),
                    outcome
                )
            ).value() && outcome.successful()
        );
    }

    @Override
    public void print(final CmdOutput output) {
        this.print(output.byLines());
        this.out.println();
    }

    @Override
    public void print(final String msg) {
        this.out.printf("%s%s%n", this.indent, msg);
    }

    @Override
    public void print(final String file,
        final IllegalYmlFileFormatException exp) {
        this.print(
            new Joined<>(
                new ListOf<>(
                    new PlainFormattedText(
                        "YML file `%s` has wrong format:", file
                    ).asString()
                ),
                new Mapped<>(
                    l -> new PlainFormattedText(
                        "%s%s", this.indent, l
                    ).asString(),
                    exp.detailsSplittedByLines()
                )
            )
        );
    }

    /**
     * Print testing status based on status.
     * @param status Of testing.
     */
    private void printTestingStatus(final boolean status) {
        this.out.println();
        this.print(
            new UncheckedTernary<>(
                status,
                new HighlightedText("Testing successful.", Color.GREEN),
                new HighlightedText("Testing failed.", Color.RED)
            ).value().toString()
        );
    }

}
