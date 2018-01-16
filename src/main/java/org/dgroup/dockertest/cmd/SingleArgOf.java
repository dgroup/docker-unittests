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

import java.util.List;
import org.dgroup.dockertest.text.PlainFormattedText;

/**
 * Default implementation for single command-line argument.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class SingleArgOf implements Arg {
    /**
     * Command-line argument name.
     */
    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private final String name;
    /**
     * All command-line arguments specified by user.
     */
    private final List<String> args;
    /**
     * Error message in case when command-line argument is missing
     *  or wasn't specified by user.
     */
    private final String absent;

    /**
     * Ctor.
     * @param name Cmd argument name.
     * @param args All cmd arguments.
     */
    public SingleArgOf(final String name, final List<String> args) {
        this(
            name, args, new PlainFormattedText(
                "Argument `%s` wasn't specified", name
            ).asString()
        );
    }

    /**
     * Ctor.
     * @param name Cmd argument name.
     * @param args All cmd arguments.
     * @param absent Error message in case when command-line argument
     *  is missing or wasn't specified by user.
     */
    public SingleArgOf(final String name, final List<String> args,
        final String absent) {
        this.name = name;
        this.args = args;
        this.absent = absent;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String value() throws CmdArgNotFoundException {
        if (this.args.isEmpty()) {
            throw new CmdArgNotFoundException(
                "User arguments are empty."
            );
        }
        final String value = this.args.get(this.args.indexOf(this.name) + 1);
        if (value == null) {
            throw new CmdArgNotFoundException(this.absent);
        }
        return value;
    }

    @Override
    public boolean specifiedByUser() {
        return this.args.indexOf(this.name()) >= 0
            && this.args.indexOf(this.name()) + 1 < this.args.size();
    }
}
