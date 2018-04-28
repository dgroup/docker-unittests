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
package org.dgroup.dockertest;

import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.cmd.Arg;
import org.dgroup.dockertest.cmd.CmdArgNotFoundException;
import org.dgroup.dockertest.cmd.ConcurrentTreads;
import org.dgroup.dockertest.cmd.ImageOf;
import org.dgroup.dockertest.cmd.OutputOf;
import org.dgroup.dockertest.cmd.TimeoutPerThread;
import org.dgroup.dockertest.cmd.YmlFileOf;
import org.dgroup.dockertest.concurrent.Concurrent;
import org.dgroup.dockertest.concurrent.Timeout;
import org.dgroup.dockertest.exception.RootCauseOf;
import org.dgroup.dockertest.termination.Runtime;
import org.dgroup.dockertest.termination.RuntimeOf;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.TestsOf;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.test.output.std.StdOutputOf;
import org.dgroup.dockertest.text.TextOf;
import org.dgroup.dockertest.text.highlighted.GreenText;
import org.dgroup.dockertest.text.highlighted.YellowText;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * App start point with main method only.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class App {

    /**
     * Command-line arguments specified by the user from the shell.
     */
    private final List<String> args;
    /**
     * Standard application output.
     */
    private final StdOutput std;

    /**
     * Ctor.
     * @param args Command-line arguments specified by the user from the shell.
     * @param std Standard application output.
     */
    public App(final List<String> args, final StdOutput std) {
        this.args = args;
        this.std = std;
    }

    /**
     * Start point.
     * @param args YML file with tests and docker image name.
     */
    public static void main(final String... args) {
        final StdOutput std = new StdOutputOf(System.out, "    ");
        final Runtime rtm = new RuntimeOf();
        try {
            new App(
                new ListOf<>(args), std
            ).start();
        } catch (final AppException exp) {
            std.print(exp.message());
            rtm.shutdownWith(exp.exitCode());
        }
    }

    /**
     * Start the testing procedure:
     * 1. Parse command-line arguments specified by the user;
     * 2. Build tests from the YML file;
     * 3. Detect the expected output formats like std, xml, html, etc.
     * 4. Execute tests concurrently;
     * 5. Report the results.
     * @throws AppException in case of testing or other errors.
     * @checkstyle MagicNumberCheck (100 lines)
     * @checkstyle ExecutableStatementCountCheck (100 lines)
     */
    @SuppressWarnings("PMD.PreserveStackTrace")
    public void start() throws AppException {
        if (this.args.isEmpty()) {
            this.std.print(new Help());
            return;
        }
        final Arg<String> file = new YmlFileOf(this.args);
        final Arg<String> image = new ImageOf(this.args);
        final Arg<Timeout> ttrd = new TimeoutPerThread(this.args);
        final Arg<Integer> threads = new ConcurrentTreads(this.args);
        final Arg<Collection<Output>> out = new OutputOf(this.std, this.args);
        final Collection<Test> tests = new TestsOf(image, file);
        try (final Concurrent ctly = new Concurrent(ttrd, threads)) {
            if (!file.specifiedByUser()) {
                throw new AppException("YML file with tests wasn't specified.");
            }
            if (tests.isEmpty()) {
                throw new AppException(
                    new TextOf(
                        "%s testing scenarios found.", new YellowText(0)
                    )
                );
            }
            this.std.print(new Logo("1.0"));
            this.std.print(
                new TextOf("Found scenarios: %s.", new GreenText(tests.size()))
            );
            ctly.execute(tests).report(out);
        } catch (final TestingFailedException ex) {
            throw new AppException(-1, ex);
        } catch (final UncheckedIOException ex) {
            final Throwable cause = new RootCauseOf(ex).exception();
            if (cause instanceof IllegalYmlFormatException) {
                throw new AppException(-2, cause);
            }
            if (cause instanceof CmdArgNotFoundException) {
                throw new AppException(-3, cause);
            }
            throw new AppException(
                -4, new TextOf("App failed due to unexpected error: %s", cause)
            );
        }
    }

}
