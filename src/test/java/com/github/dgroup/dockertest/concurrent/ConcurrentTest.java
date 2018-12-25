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
package com.github.dgroup.dockertest.concurrent;

import com.github.dgroup.dockertest.Assume;
import com.github.dgroup.dockertest.DockerWasInstalled;
import com.github.dgroup.dockertest.cmd.Arg;
import com.github.dgroup.dockertest.cmd.arg.CmdArgNotFoundException;
import com.github.dgroup.dockertest.cmd.arg.ContainerName;
import com.github.dgroup.dockertest.hamcrest.HasItems;
import com.github.dgroup.dockertest.test.TestingFailedException;
import com.github.dgroup.dockertest.test.output.std.Std;
import com.github.dgroup.dockertest.text.TextFile;
import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.yml.tag.TagsOf;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.hamcrest.MatcherAssert;

/**
 * Unit tests for class {@link Concurrent}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class ConcurrentTest {

    @org.junit.Test(timeout = 60 * 1000)
    public void executeConcurrently()
        throws CmdArgNotFoundException, TestingFailedException {
        this.execute(
            3,
            new TimeoutOf(7, TimeUnit.SECONDS),
            Paths.get(
                "src", "test", "resources", "yml", "tests", "concurrent.yml"
            )
        );
    }

    /**
     * Execute the testing procedure.
     * @param threads The quantity of threads for {@link ExecutorService}.
     */
    private void execute(final int threads, final Timeout thrd, final Path src)
        throws CmdArgNotFoundException, TestingFailedException {
        new Assume().that(new DockerWasInstalled());
        final Std.Fake out = new Std.Fake(new LinkedList<>());
        out.print(new TextOf("File: %s.", src.toAbsolutePath()));
        new Concurrent(
            new Arg.Fake<>("-timeout", thrd),
            new Arg.Fake<>("-threads", threads)
        ).execute(
            "openjdk:9.0.1-11",
            new ContainerName(new Arg.Fake<>("-c", src.toFile())).value(),
            new TagsOf(new TextFile(src::toFile))
        ).report(out);
        MatcherAssert.assertThat(
            out.details(),
            new HasItems<>("Testing successfully completed.")
        );
    }

}
