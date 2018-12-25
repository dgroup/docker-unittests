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
package com.github.dgroup.dockertest;

import com.github.dgroup.dockertest.cmd.Arg;
import com.github.dgroup.dockertest.cmd.arg.CmdArgNotFoundException;
import com.github.dgroup.dockertest.cmd.arg.ConcurrentTreads;
import com.github.dgroup.dockertest.cmd.arg.ContainerName;
import com.github.dgroup.dockertest.cmd.arg.ImageOf;
import com.github.dgroup.dockertest.cmd.arg.OutputOf;
import com.github.dgroup.dockertest.cmd.arg.TimeoutPerThread;
import com.github.dgroup.dockertest.cmd.arg.YmlFileOf;
import com.github.dgroup.dockertest.cmd.arg.YmlTags;
import com.github.dgroup.dockertest.concurrent.Concurrent;
import com.github.dgroup.dockertest.concurrent.Timeout;
import com.github.dgroup.dockertest.exception.RootCauseOf;
import com.github.dgroup.dockertest.exception.Stacktrace;
import com.github.dgroup.dockertest.termination.RuntimeOf;
import com.github.dgroup.dockertest.test.Output;
import com.github.dgroup.dockertest.test.TestingFailedException;
import com.github.dgroup.dockertest.test.output.std.Std;
import com.github.dgroup.dockertest.test.output.std.StdOutput;
import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.text.highlighted.GreenText;
import com.github.dgroup.dockertest.text.highlighted.YellowText;
import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import com.github.dgroup.dockertest.yml.Tags;
import java.io.File;
import java.util.Collection;
import java.util.List;
import org.cactoos.Text;
import org.cactoos.list.ListOf;

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
    private final Std std;

    /**
     * Ctor.
     * @param args Command-line arguments specified by the user from the shell.
     * @param std Standard application output.
     */
    public App(final List<String> args, final Std std) {
        this.args = args;
        this.std = std;
    }

    /**
     * Start point.
     * @param cargs The cmd arguments specified by the user from the shell.
     * @see YmlFileOf
     * @see ImageOf
     * @see OutputOf
     * @see ConcurrentTreads
     */
    public static void main(final String... cargs) {
        final Std std = new StdOutput(System.out, "    ");
        final List<String> args = new ListOf<>(cargs);
        if (args.isEmpty()) {
            std.print(new Help());
            return;
        }
        try {
            new App(args, std).start();
        } catch (final AppException cause) {
            std.print(cause.message());
            new RuntimeOf().shutdownWith(
                cause.exitCode()
            );
        }
    }

    /**
     * Start the testing procedure:
     * 1. Parse command-line arguments specified by the user;
     * 2. Build tests from the YML file;
     * 3. Detect the expected output formats like std, xml or html;
     * 4. Execute tests concurrently;
     * 5. Report the results.
     * @throws AppException in case of testing or other errors.
     * @checkstyle MagicNumberCheck (100 lines)
     * @checkstyle IllegalCatchCheck (100 lines)
     * @checkstyle ExecutableStatementCountCheck (100 lines)
     */
    @SuppressWarnings({
        "PMD.PreserveStackTrace",
        "PMD.AvoidCatchingGenericException"})
    public void start() throws AppException {
        final Arg<File> file = new YmlFileOf(this.args);
        final Arg<String> image = new ImageOf(this.args);
        final Arg<Timeout> ttrd = new TimeoutPerThread(this.args);
        final Arg<Integer> threads = new ConcurrentTreads(this.args);
        final Arg<Collection<Output>> out = new OutputOf(this.std, this.args);
        final Arg<Text> container = new ContainerName(file);
        final Arg<Tags> tags = new YmlTags(file);
        try (final Concurrent ctly = new Concurrent(ttrd, threads)) {
            if (!file.specifiedByUser()) {
                throw new AppException("YML file with tests wasn't specified.");
            }
            if (tags.value().tests().isEmpty()) {
                throw new AppException(
                    new TextOf(
                        "%s testing scenarios found.", new YellowText(0)
                    )
                );
            }
            this.std.print(new Logo());
            this.std.print(
                new TextOf(
                    "Found scenarios: %s.",
                    new GreenText(tags.value().tests().size())
                )
            );
            ctly.execute(
                image.value(), container.value(), tags.value()
            ).report(out);
        } catch (final TestingFailedException ex) {
            throw new AppException(-1, ex);
        } catch (final IllegalYmlFormatException ex) {
            throw new AppException(-2, ex);
        } catch (final CmdArgNotFoundException exp) {
            throw new AppException(-3, exp);
        } catch (final RuntimeException ex) {
            final Throwable cause = new RootCauseOf(ex).exception();
            if (cause instanceof IllegalYmlFormatException) {
                throw new AppException(-2, cause);
            }
            if (cause instanceof CmdArgNotFoundException) {
                throw new AppException(-3, cause);
            }
            throw new AppException(
                -4, new TextOf("App failed due to %s", new Stacktrace(cause))
            );
        }
    }

}
