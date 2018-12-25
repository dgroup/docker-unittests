/**
 * MIT License
 *
 * Copyright (c) 2017-2018 Yurii Dubinka
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
package com.github.dgroup.dockertest.process.docker;

import com.github.dgroup.dockertest.process.ProcessOf;
import com.github.dgroup.dockertest.process.output.CmdOutputOf;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Represents an instance of docker process on Unix-related systems.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class UnixProcess extends DockerProcessEnvelope {

    /**
     * Ctor.
     * @param cmd Docker container command.
     */
    public UnixProcess(final String... cmd) {
        this(new ProcessOf(cmd));
    }

    /**
     * Ctor.
     * @param cmd Docker container command.
     */
    public UnixProcess(final Iterable<String> cmd) {
        this(new ProcessOf(cmd));
    }

    /**
     * Ctor.
     * @param process System process associated with docker container.
     */
    public UnixProcess(final Scalar<java.lang.Process> process) {
        super(() -> new CmdOutputOf(
            new UncheckedScalar<>(process).value()
        ));
    }

}
