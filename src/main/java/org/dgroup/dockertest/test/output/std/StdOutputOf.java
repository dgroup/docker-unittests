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
package org.dgroup.dockertest.test.output.std;

import java.io.PrintStream;
import org.cactoos.Proc;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.And;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.scalar.If;
import org.dgroup.dockertest.test.outcome.TestOutcome;
import org.dgroup.dockertest.test.outcome.TestingOutcome;
import org.dgroup.dockertest.text.TextOf;
import org.dgroup.dockertest.text.highlighted.GreenText;
import org.dgroup.dockertest.text.highlighted.HighlightedText;
import org.dgroup.dockertest.text.highlighted.RedText;

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
    public void print(final String msg) {
        this.print(new ListOf<>(msg));
    }

    @Override
    public void print(final String header, final String... messages) {
        this.print(header);
        this.print(
            new Mapped<>(
                msg -> new TextOf("%s%s", this.indent, msg).text(),
                new ListOf<>(messages)
            )
        );
    }

    @Override
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

    @Override
    public void print(final Iterable<String> messages) {
        for (final String msg : messages) {
            this.out.printf("%s%s%n", this.indent, msg);
        }
    }

    /**
     * Print testing results based on status.
     * @param status Of testing.
     */
    private void printTestingStatus(final boolean status) {
        this.out.println();
        this.print(
            new If<HighlightedText>(
                status,
                new GreenText("Testing successful."),
                new RedText("Testing failed.")
            ).value().text()
        );
        this.out.println();
    }

}
