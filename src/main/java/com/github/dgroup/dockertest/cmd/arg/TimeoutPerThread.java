/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
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
package com.github.dgroup.dockertest.cmd.arg;

import com.github.dgroup.dockertest.concurrent.Timeout;
import com.github.dgroup.dockertest.concurrent.TimeoutOf;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.cactoos.list.ListOf;

/**
 * Each test will be executed in separate thread.
 * User may specify the timeout(in seconds) for each test in command line args:
 *  {@code --timeout-per-test 120}.
 *
 * In case if the argument is missing, the default timeout is 5 minutes.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TimeoutPerThread extends ArgEnvelope<Timeout> {

    /**
     * Ctor.
     * @param args Command-line arguments specified by user.
     */
    public TimeoutPerThread(final String... args) {
        this(new ListOf<>(args));
    }

    /**
     * Ctor.
     * @param args Command-line arguments specified by user.
     * @checkstyle MagicNumberCheck (10 lines)
     */
    public TimeoutPerThread(final List<String> args) {
        super(() -> new Alternative<Timeout>(
            new Mapped<>(
                arg -> new TimeoutOf(arg, TimeUnit.SECONDS),
                new ArgOf("--timeout-per-test", args)
            ),
            () -> new TimeoutOf(5, TimeUnit.MINUTES)
        ));
    }

}
