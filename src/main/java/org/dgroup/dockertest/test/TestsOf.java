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
package org.dgroup.dockertest.test;

import org.cactoos.list.Mapped;
import org.cactoos.list.StickyList;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.CmdArgNotFoundException;
import org.dgroup.dockertest.test.outcome.TestingOutcome;
import org.dgroup.dockertest.test.outcome.TestingOutcomeOf;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.text.highlighted.GreenText;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * Allows to execute tests and print results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TestsOf {

    /**
     * Testing results.
     */
    private final TestingOutcome tests;
    /**
     * Standard output for application progress.
     */
    private final UncheckedScalar<StdOutput> std;

    /**
     * Ctor.
     * @param args Command-line arguments specified by user.
     * @throws CmdArgNotFoundException in case if cmd argument is missing
     *  or not specified by user.
     * @throws IllegalYmlFileFormatException in case if YML file with tests
     *  has wrong/incorrect format.
     */
    public TestsOf(final Args args)
        throws CmdArgNotFoundException, IllegalYmlFileFormatException {
        this(
            new TestingOutcomeOf(
                new StickyList<>(
                    new Mapped<>(Test::execute, args.tests())
                ),
                args.selectedByUserOutput()
            ),
            args.stdOutput()
        );
    }

    /**
     * Ctor.
     * @param tests Testing results.
     * @param std Standard output for application progress.
     */
    public TestsOf(
        final TestingOutcome tests,
        final UncheckedScalar<StdOutput> std
    ) {
        this.tests = tests;
        this.std = std;
    }

    /**
     * Print tests results to selected outputs.
     *
     * @throws TestingFailedException in case when at least one test is failed.
     * @todo #2:8h All tests should be executed concurrently and support
     *  thread-pool configuration from command line.
     */
    public void execute() throws TestingFailedException {
        if (this.tests.isEmpty()) {
            throw new NoScenariosFoundException();
        }
        this.std.value().print(
            "Found scenarios: %s.%n",
            new GreenText(this.tests.size())
        );
        this.tests.reportTheResults();
    }

}
