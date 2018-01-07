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
package org.dgroup.dockertest.test.output;

import java.io.PrintStream;
import java.util.List;
import org.cactoos.Proc;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.And;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.test.TestOutcome;
import org.dgroup.dockertest.test.TestingOutcome;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;

/**
 * Standard output for printing app progress and testing results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class StdOutput implements Output {

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
    public StdOutput() {
        this(System.out, "    ");
    }

    /**
     * Ctor.
     * @param out Instance for print procedure.
     * @param indent Default indent from left side of screen.
     */
    public StdOutput(final PrintStream out, final String indent) {
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

    /**
     * Print text to single line.
     * @param msg Text to print
     * @todo #9 Use jansi for colored std output
     */
    public void print(final String msg) {
        this.out.printf("%s%s%n", this.indent, msg);
    }

    /**
     * Print message.
     * @param pattern Template.
     * @param args Arguments for template above.
     */
    public void print(final String pattern, final Object... args) {
        this.print(String.format(pattern, args));
    }

    /**
     * Print app exception.
     * @param msg App exception message
     * @param exp App exception details
     */
    public void print(final String msg, final Exception exp) {
        this.print(
            new Joined<>(
                new ListOf<>(msg),
                new Mapped<>(
                    StackTraceElement::toString,
                    new IterableOf<>(exp.getStackTrace())
                )
            )
        );
    }

    /**
     * Print format details in case corrupted yml file.
     * @param file Name of corrupted yml file.
     * @param exp Exception received during parsing yml tree.
     */
    public void print(final String file,
        final IllegalYmlFileFormatException exp) {
        this.print(
            new Joined<>(
                new ListOf<>(
                    String.format("YML file `%s` has wrong format:", file)
                ),
                new Mapped<>(
                    l -> String.format("%s%s", this.indent, l),
                    exp.detailsSplittedByLines()
                )
            )
        );
    }

    /**
     * Print all messages separately, each on new line.
     * @param messages For separately printing.
     */
    public void print(final List<String> messages) {
        messages.forEach(this::print);
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
                String.valueOf(Ansi.ansi().fg(GREEN).bold().a("[OK] Testing successful.").reset()),
                String.valueOf(Ansi.ansi().fg(RED).bold().a("[ERROR] Testing failed.").reset())
            ).value()
        );
    }

}
