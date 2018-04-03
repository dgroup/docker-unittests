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
package org.dgroup.dockertest.docker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.dgroup.dockertest.docker.output.CmdOutputOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link CmdOutputOf} on Unix OS.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle AbbreviationAsWordInNameCheck (500 lines)
 * @checkstyle MethodBodyCommentsCheck (500 lines)
 */
public final class CmdOutputOfTest {

    @Test(timeout = 1000 * 3)
    public void text() throws IOException {
        MatcherAssert.assertThat(
            "Command `java -version` is `1.8`.",
            new CmdOutputOf(
                new ProcessOf("java", "-version").execute()
            ).asText(),
            Matchers.containsString("1.8")
        );
    }

    @Test(expected = IllegalStateException.class)
    public void throwISE() {
        new CmdOutputOf(
            new FakeProcess(
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        throw new IOException("Shit happens");
                    }
                },
                new OutputStream() {
                    @Override
                    public void write(final int bytes) {
                        // ok
                    }
                }
            )
        ).asText();
    }
}
