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
import org.dgroup.dockertest.cmd.OutputArg;
import org.dgroup.dockertest.cmd.YmlFileArg;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.Tests;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.StdOutput;

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
     */
    private final List<String> args;
    /**
     * Default output for application progress.
     */
    private final Output std;

    /**
     * Ctor.
     * @param args Command-line arguments.
     */
    public App(final String... args) {
        this(new ListOf<>(args), new StdOutput());
    }

    /**
     * Ctor.
     * @param args Command-line arguments.
     * @param std Default output for application progress.
     */
    public App(final List<String> args, final Output std) {
        this.args = args;
        this.std = std;
    }

    /**
     * Main method.
     * @param args Command-line arguments.
     */
    public static void main(final String... args) {
        new App(args).start();
    }

    /**
     * Execute testing procedure.
     */
    public void start() {
        try {
            this.std.print(
                new Logo("0.1.0").asString()
            );
            new Tests(
                new DockerImageArg(this.args),
                new YmlFileArg(this.args),
                new OutputArg(this.args)
            ).print();
        } catch (final TestingFailedException ex) {
            this.shutdownWith(-1);
        }
    }

    /**
     * Shutdown application with error code.
     * The error code is required when the app is invoked from shell scripts.
     * @param code Exit code.
     * @checkstyle NonStaticMethodCheck (10 lines)
     */
    @SuppressWarnings("PMD.DoNotCallSystemExit")
    private void shutdownWith(final int code) {
        Runtime.getRuntime().exit(code);
    }

}
