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
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * Print testing results to standard output.
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

    // @todo #9 Use jansi for colored std output
    @Override
    public void print(final String msg) {
        this.out.printf("%s%s\n", this.indent, msg);
    }

    /**
     * Print message.
     * @param pattern Template.
     * @param args Arguments for template above.
     */
    public void print(final String pattern, final Object... args) {
        this.print(new PlainFormattedText(pattern, args).asString());
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
     * Print multiple lines.
     * @param messages Print in new line each element.
     */
    public void print(final List<String> messages) {
        messages.forEach(this::print);
        this.out.println();
    }

}
