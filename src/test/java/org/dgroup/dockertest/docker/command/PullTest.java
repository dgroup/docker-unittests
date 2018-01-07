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
package org.dgroup.dockertest.docker.command;

import org.dgroup.dockertest.docker.DockerImageNotFoundException;
import org.dgroup.dockertest.docker.DockerRuntimeException;
import org.dgroup.dockertest.docker.output.FakeCmdOutput;
import org.dgroup.dockertest.docker.process.FakeDockerProcess;
import org.dgroup.dockertest.docker.process.Pull;
import org.junit.Test;

/**
 * Unit tests for class {@link Pull}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LineLengthCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class PullTest {

    @Test(expected = DockerImageNotFoundException.class)
    public void imageNotFound() throws DockerRuntimeException {
        new Pull(
            "openjdk:9999999",
            new FakeDockerProcess(
                new FakeCmdOutput(
                    "Error response from daemon: pull access denied for openjdk:9999999, " +
                        "repository does not exist or may require 'docker login'\n"
                )
            )
        ).execute();
    }

}
