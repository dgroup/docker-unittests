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

import java.util.List;
import org.cactoos.list.ListOf;

/**
 * Command line argument which represents quantity of concurrent threads for
 *  testing procedure.
 *
 * In case if the argument is missing, the default value is 8.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class ConcurrentTreads extends ArgEnvelope<Integer> {

    /**
     * Ctor.
     * @param args Command-line arguments specified by user.
     */
    public ConcurrentTreads(final String... args) {
        this(new ListOf<>(args));
    }

    /**
     * Ctor.
     * @param args Command-line arguments specified by user.
     * @checkstyle IndentationCheck (10 lines)
     * @checkstyle MagicNumberCheck (10 lines)
     */
    public ConcurrentTreads(final List<String> args) {
        super(
            () -> new Alternative<>(
                new Mapped<>(Integer::valueOf, new ArgOf("--threads", args)),
                () -> 8
            )
        );
    }

}
