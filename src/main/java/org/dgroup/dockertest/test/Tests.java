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
package org.dgroup.dockertest.test;

import java.util.List;
import java.util.Set;
import org.cactoos.list.Mapped;
import org.cactoos.list.StickyList;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.CmdArgNotFoundException;
import org.dgroup.dockertest.docker.DockerProcessExecutionException;
import org.dgroup.dockertest.docker.process.Pull;
import org.dgroup.dockertest.docker.process.Timed;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.text.HighlightedText;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.fusesource.jansi.Ansi.Color;

/**
 * Allows to execute tests and print results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class Tests {

    /**
     * Docker image for testing.
     */
    private final String image;
    /**
     * Tests to be executed.
     */
    private final List<Test> scope;
    /**
     * Available outputs for printing results.
     */
    private final Set<Output> outputs;
    /**
     * Standard output for application progress.
     */
    private final StdOutput std;

    /**
     * Ctor.
     * @param args Command-line arguments specified by user.
     * @param std Standard output for application progress.
     * @throws CmdArgNotFoundException in case if cmd argument is missing
     *  or not specified by user.
     * @throws IllegalYmlFileFormatException in case if YML file with tests
     *  has wrong/incorrect format.
     */
    public Tests(final Args args, final StdOutput std)
        throws CmdArgNotFoundException, IllegalYmlFileFormatException {
        this(
            args.dockerImage(),
            args.tests(),
            args.selectedByUserOutput(),
            std
        );
    }

    /**
     * Ctor.
     * @param image Docker image for testing.
     * @param scope Tests to be executed.
     * @param out Available outputs for printing results.
     * @param std Standard output for application progress.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Tests(final String image, final List<Test> scope,
        final Set<Output> out, final StdOutput std) {
        this.image = image;
        this.scope = scope;
        this.outputs = out;
        this.std = std;
    }

    /**
     * Print tests results to selected outputs.
     *
     * @throws DockerProcessExecutionException in case runtime exception on the
     *  docker side.
     * @throws TestingFailedException in case when at least one test is failed.
     * @todo #2:8h All tests should be executed concurrently and support
     *  thread-pool configuration from command line. Also, the tool can use
     *  https://github.com/testcontainers/testcontainers-java as a layer for the
     *  docker integration.
     * @todo #66 Unit test is required for smoke testing.
     */
    public void execute() throws DockerProcessExecutionException,
        TestingFailedException {
        if (this.scope.isEmpty()) {
            this.std.print(
                "%s testing scenarios found.",
                new HighlightedText(0, Color.YELLOW)
            );
            return;
        }
        this.std.print(
            "Found scenarios: %s.%n",
            new HighlightedText(this.scope.size(), Color.GREEN)
        );
        this.std.print("Pull image...");
        this.std.print(
            new Timed(
                new Pull(this.image)
            ).execute()
        );
        new TestingOutcome(
            new StickyList<>(
                new Mapped<>(Test::execute, this.scope)
            ),
            this.outputs
        ).reportTheResults();
    }

}
