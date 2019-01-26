/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
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
package com.github.dgroup.dockertest.test;

import com.github.dgroup.dockertest.process.CmdOutput;
import com.github.dgroup.dockertest.process.DockerProcess;
import com.github.dgroup.dockertest.process.DockerProcessExecutionException;
import com.github.dgroup.dockertest.yml.TgTest;
import com.github.dgroup.dockertest.yml.tag.output.TgOutputOf;
import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Unit tests for class {@link TestOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class TestOfTest {

    @org.junit.Test
    public void passed() throws DockerProcessExecutionException {
        MatcherAssert.assertThat(
            new TestOf(
                new TgTest.Fake(
                    "curl version is 7.xxx",
                    "curl --version",
                    new TgOutputOf(
                        Collections.singleton("curl 7."),
                        Collections.singleton("7.57"),
                        Collections.emptySet(), Collections.emptySet(),
                        Collections.emptySet()
                    )
                ),
                new DockerProcess.Fake(
                    new CmdOutput.Fake(
                        "curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57"
                    )
                )
            ).execute().successful(),
            new IsTrue()
        );
    }

    @org.junit.Test
    public void failed() throws DockerProcessExecutionException {
        MatcherAssert.assertThat(
            new TestOf(
                new TgTest.Fake(
                    "curl version is 7.xxx",
                    "curl --version",
                    new TgOutputOf(
                        Collections.singleton("curl 7."),
                        Collections.emptySet(), Collections.emptySet(),
                        Collections.emptySet(),
                        Collections.singleton("OpenSSL/1.0.2m")
                    )
                ),
                new DockerProcess.Fake(
                    new CmdOutput.Fake(
                        "curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57.0 "
                    )
                )
            ).execute().successful(),
            new IsNot<>(new IsTrue())
        );
    }
}
