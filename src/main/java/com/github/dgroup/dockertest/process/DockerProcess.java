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
package com.github.dgroup.dockertest.process;

/**
 * Represents single manipulation with Docker container.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface DockerProcess {

    /**
     * Execute command inside of docker container.
     * @return The output of the command within the Docker container.
     * @throws DockerProcessExecutionException in case runtime exception on the
     *  docker side.
     */
    CmdOutput execute() throws DockerProcessExecutionException;

    /**
     * Fake implementation for unit testing purposes.
     * @checkstyle JavadocMethodCheck (10 lines)
     * @checkstyle JavadocVariableCheck (10 lines)
     */
    final class Fake implements DockerProcess {

        private final CmdOutput output;

        public Fake(final CmdOutput output) {
            this.output = output;
        }

        @Override
        public CmdOutput execute() {
            return this.output;
        }

    }
}
