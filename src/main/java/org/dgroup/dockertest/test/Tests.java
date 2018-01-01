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
import org.dgroup.dockertest.cmd.Arg;
import org.dgroup.dockertest.cmd.OutputArg;
import org.dgroup.dockertest.docker.DockerProcessOnUnix;
import org.dgroup.dockertest.docker.command.Pull;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.StdOutput;
import org.dgroup.dockertest.yml.tag.test.YmlTagTest;

/**
 * Allows to execute tests and print results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
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
     * @param image Docker image for testing.
     * @param tests Yml tags with tests to be executed.
     * @param out Available outputs for printing results.
     */
    public Tests(final Arg image, final Iterable<YmlTagTest> tests,
        final OutputArg out) {
        this(
            image.value(),
            new Mapped<>(
                CachedTest::new,
                new Mapped<>(
                    ymlTagTest -> new BasedOnYmlTest(image, ymlTagTest),
                    tests
                )
            ),
            out.asSet(),
            out.std()
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
     * @todo #2:8h All tests should be executed concurrently
     *  and support thread-pool configuration from command line.
     * @todo #51 Print timing for `docker pull` command.
     */
    public void execute() {
        if (this.scope.isEmpty()) {
            throw new NonDefinedTestingScopeException();
        }
        this.std.print("Found scenarios: %s.%n", this.scope.size());
        this.std.print("Verify image...");
        this.std.print(
            new DockerProcessOnUnix(
                new Pull(this.image)
            ).run()
        );
        new TestingOutcome(
            new StickyList<>(
                new Mapped<>(Test::execute, this.scope)
            ),
            this.outputs
        ).print();
    }

}
