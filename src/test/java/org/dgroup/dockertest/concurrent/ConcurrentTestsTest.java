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
import java.util.concurrent.TimeUnit;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.OnlyWithinInstalledDocker;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.Timeout;
import org.dgroup.dockertest.cmd.TimeoutOf;
import org.dgroup.dockertest.test.NoScenariosFoundException;
import org.dgroup.dockertest.test.Test.Sleeping;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for class {@link ConcurrentTests}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocVariableCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RunWith(OnlyWithinInstalledDocker.class)
public final class ConcurrentTestsTest {

    @Test
    public void executeConcurrentlySmoke() throws Exception {
        final String path = new TextOf("docs%simage-tests.yml", File.separator)
            .text();
        final StdOutput.Fake out = new StdOutput.Fake(new ArrayList<>(12));
        final Args args = new Args(
            out,
            "-f", path,
            "-i", "openjdk:9.0.1-11"
        );
        out.print(new TextOf("File: %s.", path));
        try (ConcurrentTests concurrently = new ConcurrentTests(args)) {
            concurrently.execute(args.tests())
                .reportTheResults(out);
        }
        MatcherAssert.assertThat(
            out.details(),
            Matchers.hasItem("Testing successfully completed.")
        );
    }

    @Test(timeout = 20 * 1000)
    public void executeConcurrently() throws Exception {
        final StdOutput.Fake out = new StdOutput.Fake(new ArrayList<>(12));
        final Args args = new Args(out, "--threads", "5");
        MatcherAssert.assertThat(
            args.concurrentThreads(), Matchers.equalTo(5)
        );
        try (ConcurrentTests concurrently = new ConcurrentTests(args)) {
            concurrently.execute(
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS))
            )
                .reportTheResults(out);
        }
        MatcherAssert.assertThat(
            "15 tasks (5 seconds each) within 5 threads should take less then" +
                " 20 seconds",
            out.details(),
            Matchers.hasItem("Testing successfully completed.")
        );
    }

    @Test
    public void executeConsequentially() throws Exception {
        final String path = new TextOf("docs%simage-tests.yml", File.separator)
            .text();
        final StdOutput.Fake out = new StdOutput.Fake(new ArrayList<>(12));
        out.print(new TextOf("File: %s.", path));
        new ConcurrentTests(
            out,
            ExcsrvFake::new,
            new Timeout.No(),
            new Timeout.No()
        ).execute(
            new Args(
                out,
                "-f", path,
                "-i", "openjdk:9.0.1-11"
            ).tests()
        ).reportTheResults(out);
        MatcherAssert.assertThat(
            out.details(),
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
