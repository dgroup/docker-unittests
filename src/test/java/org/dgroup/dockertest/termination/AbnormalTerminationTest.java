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
package org.dgroup.dockertest.termination;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.test.output.std.StdOutputOf;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link AbnormalTermination}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle AddEmptyString (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.AddEmptyString"})
public final class AbnormalTerminationTest {

    @Test
    public void dueToIllegalYmlFormatException()
        throws UnsupportedEncodingException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final StdOutput out = new StdOutputOf(
            new PrintStream(baos, true, StandardCharsets.UTF_8.toString()),
            ".."
        );
        final Runtime.Fake runtime = new Runtime.Fake();
        final Termination termination = new AbnormalTermination(
            runtime,
            out,
            new Args(out, "-f", "docs/image-tests.yml")
        );
        termination.dueTo(
            new IllegalYmlFormatException(new Exception("Shit happens."))
        );
        MatcherAssert.assertThat(
            new String(baos.toByteArray(), StandardCharsets.UTF_8),
            Matchers.equalTo("" +
                "..YML file `docs/image-tests.yml` has the wrong format:\n" +
                "....java.lang.Exception: Shit happens.\n"
            )
        );
        MatcherAssert.assertThat(
            "Exit code is -2",
            runtime.exitCode(),
            Matchers.equalTo(-2)
        );
    }
}
