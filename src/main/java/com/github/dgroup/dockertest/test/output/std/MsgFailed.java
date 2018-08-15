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
package com.github.dgroup.dockertest.test.output.std;

import com.github.dgroup.dockertest.test.outcome.TestOutcome;
import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.text.highlighted.RedText;
import java.util.Iterator;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;

/**
 * Message to standard output about failed testing.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class MsgFailed implements Iterable<String> {

    /**
     * The single test result.
     */
    private final TestOutcome outcome;

    /**
     * Ctor.
     * @param outcome The single test result.
     */
    public MsgFailed(final TestOutcome outcome) {
        this.outcome = outcome;
    }

    @Override
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public Iterator<String> iterator() {
        // @checkstyle LineLengthCheck (10 lines)
        return new Joined<>(
            new ListOf<>(
                new TextOf(
                    "> %s %s", this.outcome.scenario(), new RedText("FAILED")
                ).text(),
                new TextOf("  command: \"%s\"", this.outcome.cmd()).text(),
                new TextOf("  output:  \"%s\"", this.outcome.rawOutput()).text(),
                "  expected output:"
            ),
            new Mapped<>(
                ec -> new TextOf("    - %s", ec).text(),
                this.outcome.expectedConditions()
            ),
            new ListOf<>("  mismatch:"),
            new Mapped<>(
                fc -> new TextOf("    - %s", fc).text(),
                this.outcome.failedConditions()
            )
        ).iterator();
    }

}
