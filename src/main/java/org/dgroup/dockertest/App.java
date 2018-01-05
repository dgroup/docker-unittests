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

import java.io.UncheckedIOException;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.CmdArgNotFoundException;
import org.dgroup.dockertest.docker.DockerRuntimeException;
import org.dgroup.dockertest.exception.RootCause;
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
    private final Args args;
    /**
     * Default output for application progress.
     */
    private final StdOutput std;

    /**
     * Ctor.
     * @param args Command-line arguments.
     */
    public App(final Args args) {
        this(args, args.std());
    }

    /**
     * Ctor.
     * @param args Command-line arguments.
     * @param std Default output for application progress.
     * @checkstyle LineLengthCheck (5 lines)
     */
    public App(final Args args, final StdOutput std) {
        this.args = args;
        this.std = std;
    }

    /**
     * Main method.
     * @param args Command-line arguments.
     */
    public static void main(final String... args) {
        new App(new Args(args))
            .start();
    }

    /**
     * Execute testing procedure.
     * @todo #62 Exception handling refactoring is required.
     * @checkstyle MagicNumberCheck (100 lines)
     * @checkstyle ExecutableStatementCountCheck (100 lines)
     */
    @SuppressWarnings({ "PMD.PreserveStackTrace",
        "PMD.ExceptionAsFlowControl" })
    public void start() {
        this.std.print(new Logo("1.0.0").byLines());
        try {
            final Tests tests = new Tests(this.args);
            try {
                tests.execute();
            } catch (final NonDefinedTestingScopeException ex) {
                this.std.print(ex.getMessage());
            } catch (final UncheckedIOException ex) {
                final Throwable cause = new RootCause(ex).exception();
                if (cause instanceof IllegalYmlFileFormatException) {
                    throw new IllegalYmlFileFormatException(cause);
                } else {
                    this.std.print(
                        "App failed due to unexpected runtime exception:", ex
                    );
                }
            }
        } catch (final CmdArgNotFoundException ex) {
            this.std.print(ex.getMessage());
            this.shutdownWith(-3);
        } catch (final IllegalYmlFileFormatException error) {
            try {
                this.std.print(this.args.ymlFilename(), error);
            } catch (final CmdArgNotFoundException ex) {
                this.std.print(ex.getMessage());
                this.shutdownWith(-3);
            }
            this.shutdownWith(-2);
        } catch (final TestingFailedException ex) {
            this.shutdownWith(-1);
        } catch (final DockerRuntimeException ex) {
            this.std.print(ex.byLines());
            this.shutdownWith(-4);
        }
    }

    /**
     * Shutdown application with error code.
     * The error code is required when the app is invoked from shell scripts:
     *  - {@code -1} testing failed;
     *  - {@code -2} yml file has unsupported/incorrect format;
     *  - {@code -3} required cmd arguments wasn't specified;
     *  - {@code -4} runtime exception happens on docker side.
     * @param code Exit code.
     * @checkstyle NonStaticMethodCheck (10 lines)
     */
    @SuppressWarnings("PMD.DoNotCallSystemExit")
    private void shutdownWith(final int code) {
        Runtime.getRuntime().exit(code);
    }

}
