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
package org.dgroup.dockertest.concurrent;

import java.io.File;
import java.util.ArrayList;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.OnlyWithinInstalledDocker;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.Timeout;
import org.dgroup.dockertest.test.NoScenariosFoundException;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.test.output.std.StdOutputOf;
import org.dgroup.dockertest.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for class {@link ConcurrentTests}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocVariableCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RunWith(OnlyWithinInstalledDocker.class)
public final class ConcurrentTestsTest {

    private final String path =
        new TextOf("docs%simage-tests.yml", File.separator).text();
    private final StdOutput.Fake out =
        new StdOutput.Fake(new ArrayList<>(12));

    @Before
    public void printPathToFileWithTestingScenarios() {
        this.out.details().clear();
        this.out.print(new TextOf("File: %s.", this.path));
    }

    @After
    public void flushToStd() {
        new StdOutputOf().print(this.out.details());
    }

    @Test
    public void executeConcurrentlySmoke() throws Exception {
        final Args args = new Args(
            this.out,
            "-f", this.path,
            "-i", "openjdk:9.0.1-11"
        );
        try (ConcurrentTests concurrently = new ConcurrentTests(args)) {
            concurrently.execute(args.tests())
                .reportTheResults(this.out);
        }
        MatcherAssert.assertThat(
            this.out.details(),
            Matchers.hasItem("Testing successfully completed.")
        );
    }

    @Test
    public void executeConsequentially() throws Exception {
        new ConcurrentTests(
            this.out,
            ExcsrvFake::new,
            new Timeout.No(),
            new Timeout.No()
        ).execute(
            new Args(
                this.out,
                "-f", this.path,
                "-i", "openjdk:9.0.1-11"
            ).tests()
        ).reportTheResults(this.out);
        MatcherAssert.assertThat(
            this.out.details(),
            Matchers.hasItem("Testing successfully completed.")
        );
    }

    @Test(expected = NoScenariosFoundException.class)
    public void noScenariosFound() throws TestingFailedException {
        new ConcurrentTests(
            new StdOutput.Fake(),
            ExcsrvFake::new,
            new Timeout.No(),
            new Timeout.No()
        ).execute(
            new ListOf<>()
        );
    }

}
