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
import org.dgroup.dockertest.test.NonDefinedTestingScopeException;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.Tests;
import org.dgroup.dockertest.test.output.StdOutput;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * Represents the instance of application.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class App {

    /**
     * App command-line arguments from user.
     */
    private final List<String> args;
    /**
     * Tests to be executed.
     */
    private final Tests tests;
    /**
     * Default output for application progress.
     */
    private final StdOutput std;

    /**
     * Ctor.
     * @param args Command-line arguments.
     */
    public App(final List<String> args) {
        this(
            args,
            new Tests(
                new DockerImageArg(args),
                new YmlFileArg(args),
                new OutputArg(args)
            ),
            new OutputArg(args).std()
        );
    }

    /**
     * Ctor.
     * @param args Command-line arguments.
     * @param tests Tests to be executed.
     * @param std Default output for application progress.
     * @checkstyle LineLengthCheck (5 lines)
     */
    public App(final List<String> args, final Tests tests, final StdOutput std) {
        this.args = args;
        this.tests = tests;
        this.std = std;
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
     * @checkstyle MagicNumberCheck (30 lines)
     */
    public void start() {
        try {
            this.std.print(new Logo("0.1.0"));
            this.tests.execute();
        } catch (final TestingFailedException ex) {
            this.shutdownWith(-1);
        } catch (final IllegalYmlFileFormatException ex) {
            this.std.print(new YmlFileArg(this.args).name(), ex);
            this.shutdownWith(-2);
        } catch (final NonDefinedTestingScopeException ex) {
            this.std.print("0 testing scenarios found.");
        }
    }

    /**
     * Shutdown application with error code.
     * The error code is required when the app is invoked from shell scripts:
     *  - {@code 1} testing failed;
     *  - {@code 2} yml file has unsupported/incorrect format.
     * @param code Exit code.
     * @checkstyle NonStaticMethodCheck (10 lines)
     */
    @SuppressWarnings("PMD.DoNotCallSystemExit")
    private void shutdownWith(final int code) {
        Runtime.getRuntime().exit(code);
    }

}
