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
package com.github.dgroup.dockertest.process.docker;

import com.github.dgroup.dockertest.process.DockerProcessExecutionException;
import com.github.dgroup.dockertest.process.FakeProcess;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for class {@link UnixProcess}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocVariableCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class UnixProcessTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void execute() throws DockerProcessExecutionException {
        MatcherAssert.assertThat(
            new UnixProcess("echo", "wikipedia").execute().text(),
            Matchers.containsString("wikipedia")
        );
    }

    @Test
    public void exceptionThrown() throws DockerProcessExecutionException {
        this.exception.expect(UncheckedIOException.class);
        this.exception.expectMessage("Exception happens!");
        new UnixProcess(
            () -> new FakeProcess(
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        throw new IOException("Exception happens!");
                    }
                },
                new OutputStream() {
                    @Override
                    public void write(final int bytes) {
                        throw new UnsupportedOperationException("#write");
                    }
                }
            )
        ).execute().text();
    }

}
