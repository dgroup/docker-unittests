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
package org.dgroup.dockertest;

import java.util.List;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.cmd.DockerImageArg;
import org.dgroup.dockertest.cmd.FileArg;
import org.dgroup.dockertest.cmd.OutputArg;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.Tests;

/**
 * Represents the instance of application.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class App {

    /**
     * Application command-line arguments.
     **/
    private final List<String> args;

    /**
     * Ctor.
     * @param args Command-line arguments
     */
    public App(final List<String> args) {
        this.args = args;
    }

    /**
     * Main method.
     * @param args Command-line arguments.
     */
    public static void main(final String... args) {
        new App(new ListOf<>(args))
            .start();
    }

    /**
     * Execute testing procedure.
     */
    @SuppressWarnings("PMD.DoNotCallSystemExit")
    public void start() {
        try {
            new Tests(
                new DockerImageArg(this.args).value(),
                new FileArg(this.args).value(),
                new OutputArg(this.args)
            ).print();
        } catch (final TestingFailedException ex) {
            System.exit(-1);
        }
    }
}
