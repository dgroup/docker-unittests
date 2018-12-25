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
package com.github.dgroup.dockertest.text.highlighted;

import com.github.dgroup.dockertest.hamcrest.HasItems;
import com.github.dgroup.dockertest.test.output.std.Std;
import com.github.dgroup.dockertest.test.output.std.StdOutput;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit tests for class {@link HighlightedTextEnvelope}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class HighlightedTextTest {

    @Test
    public void text() {
        MatcherAssert.assertThat(
            new ListOf<>(
                new RedText("FAILED").text(),
                new GreenText("PASSED").text()
            ),
            new HasItems<>(
                "\u001B[91;1mFAILED\u001B[m",
                "\u001B[92;1mPASSED\u001B[m"
            )
        );
    }

    @Test
    public void visualColor() {
        final Std std = new StdOutput();
        std.print(new RedText("FAILED"));
        std.print(new GreenText("PASSED"));
        std.print(new YellowText("WARNING"));
        std.print(new BlueText("WHALE"));
    }

}
