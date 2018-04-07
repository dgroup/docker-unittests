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
package org.dgroup.dockertest.cmd;

import java.util.concurrent.TimeUnit;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

/**
 * Unit tests for class {@link Args}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ArgsTest {

    @Test
    public void ymlFilename() throws CmdArgNotFoundException {
        MatcherAssert.assertThat(
            new Args(
                new StdOutput.Fake(new ListOf<>()),
                "-f", ".gitignore"
            ).ymlFilename(),
            Matchers.equalTo(".gitignore")
        );
    }

    @Test
    public void defaultOutput() {
        MatcherAssert.assertThat(
            new Args(
                new StdOutput.Fake(new ListOf<>())
            ).selectedByUserOutput().iterator().next(),
            IsInstanceOf.instanceOf(StdOutput.Fake.class)
        );
    }

    @Test
    public void timeoutPerEachTest() {
        final Timeout timeout = new Args(
            new StdOutput.Fake(new ListOf<>()),
            "--timeout-per-test", "120"
        ).timeoutPerThread();
        MatcherAssert.assertThat(
            "Timeout value is 120",
            timeout.timeout(),
            Matchers.equalTo(120L)
        );
        MatcherAssert.assertThat(
            "Timeout measuring are in seconds",
            timeout.measure(),
            Matchers.equalTo(TimeUnit.SECONDS)
        );
    }

    @Test
    public void defaultTimeoutPerEachTest() {
        final Timeout timeout = new Args(
            new StdOutput.Fake(new ListOf<>())
        ).timeoutPerThread();
        MatcherAssert.assertThat(
            "Timeout value is 2",
            timeout.timeout(),
            Matchers.equalTo(2L)
        );
        MatcherAssert.assertThat(
            "Timeout measuring are in minutes",
            timeout.measure(),
            Matchers.equalTo(TimeUnit.MINUTES)
        );
    }

    @Test
    public void amountOfThreadsForTheTesting() {
        MatcherAssert.assertThat(
            new Args(
                new StdOutput.Fake(new ListOf<>()),
                "--threads", "12"
            ).concurrentThreads(),
            Matchers.equalTo(12)
        );
    }

    @Test
    public void defaultAmountOfThreadsForTheTesting() {
        MatcherAssert.assertThat(
            new Args(
                new StdOutput.Fake(new ListOf<>())
            ).concurrentThreads(),
            Matchers.equalTo(8)
        );
    }

}
