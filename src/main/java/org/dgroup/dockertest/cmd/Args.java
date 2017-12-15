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
import org.cactoos.Input;
import org.cactoos.io.InputOf;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.StdOutput;

/**
 * Represents application command-line arguments.
 * See https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html for details.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmai.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class Args {

    /**
     * Command-line arguments specified by user.
     */
    private final List<String> arguments;

    /**
     * Ctor.
     *
     * @param arguments Command-line arguments from user.
     */
    public Args(final String... arguments) {
        this.arguments = new ListOf<>(arguments);
    }

    /**
     * Docker image is passed to application through {@link DockerImageArg}.
     * @return Docker image name for testing.
     * @todo #21 Load docker image before running the tests.
     */
    public Arg dockerImage() {
        return new DockerImageArg(this.arguments);
    }

    /**
     * Yml file with tests is passed to application through {@link FileArg}.
     *
     * @return Yml file with tests
     */
    public Input fileWithTests() {
        return new InputOf(
            new FileArg(this.arguments).file()
        );
    }

    /**
     * Available printers for tests results.
     * By default {@link StdOutput} only.
     *
     * @return Available outputs
     */
    public Iterable<Output> availableOutputs() {
        return new OutputArg(this.arguments).outputs();
    }
}
