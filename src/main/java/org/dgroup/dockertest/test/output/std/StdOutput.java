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

import java.util.List;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.docker.output.CmdOutput;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.text.StrictFormattedText;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * Standard output for application progress.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public interface StdOutput extends Output {

    /**
     * Print text to single line.
     * @param msg Text to print
     */
    void print(final String msg);

    /**
     * Print message.
     * @param pattern Template.
     * @param args Arguments for template above.
     */
    default void print(final String pattern, final Object... args) {
        this.print(
            new StrictFormattedText(pattern, args).asString()
        );
    }

    /**
     * Print output from docker container to standard output.
     * @param output From docker container.
     */
    void print(final CmdOutput output);

    /**
     * Print app exception.
     * @param msg App exception message
     * @param exp App exception details
     */
    default void print(final String msg, final Exception exp) {
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
    void print(final String file, final IllegalYmlFileFormatException exp);

    /**
     * Print all messages separately, each on new line.
     * @param messages For separately printing.
     */
    default void print(final List<String> messages) {
        messages.forEach(this::print);
    }

}
