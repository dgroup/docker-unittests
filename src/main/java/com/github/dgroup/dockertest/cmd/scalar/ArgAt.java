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
package com.github.dgroup.dockertest.cmd.scalar;

import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.iterable.ItemAt;

/**
 * Find particular argument in command-line arguments specified by the user.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class ArgAt implements Scalar<String> {

    /**
     * Name of particular cmd argument.
     */
    private final String arg;
    /**
     * All command-line arguments specified by the user.
     */
    private final List<String> args;

    /**
     * Ctor.
     * @param arg Name of particular cmd argument.
     * @param args All command-line arguments specified by the user.
     */
    public ArgAt(final String arg, final List<String> args) {
        this.arg = arg;
        this.args = args;
    }

    @Override
    public String value() throws Exception {
        return new ItemAt<>(
            this.args.indexOf(this.arg) + 1,
            this.args
        ).value();
    }
}
