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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.OnlyWithinInstalledDocker;
import org.dgroup.dockertest.YmlResource;
import org.dgroup.dockertest.cmd.ArgOf;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.OutputArgOf;
import org.dgroup.dockertest.cmd.UncheckedArg;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.test.output.std.StdOutputOf;
import org.dgroup.dockertest.text.PlainText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for class {@link TestsOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@RunWith(OnlyWithinInstalledDocker.class)
public class TestsOfTest {

    @Test
    public final void execute() throws Exception {
        final YmlResource tests = new YmlResource(
            "docs%simage-tests.yml", File.separator
        );
        final List<String> args = new ListOf<>(
            "-f", tests.path(),
            "-i", "openjdk:9.0.1-11"
        );
        final StdOutput.Fake output = new StdOutput.Fake(new ArrayList<>(12));
        output.print(
            new PlainText(
                "File: %s.", tests.path()
            ).text()
        );
        new TestsOf(
            new Args(
                new ArgOf("-i", args, "Docker image wasn't specified."),
                new ArgOf("-f", args, "YML file with tests wasn't specified."),
                new OutputArgOf(
                    new UncheckedArg("-o", args),
                    "",
                    new HashMap<>(),
                    output
                ),
                output
            )
        ).execute();
        new StdOutputOf().print(
            output.details()
        );
        MatcherAssert.assertThat(
            output.details(),
            Matchers.hasItem("Testing successfully completed.")
        );
    }

}
