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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dgroup.dockertest.test;

import java.util.Collections;
import java.util.List;
import org.cactoos.iterable.Filtered;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.list.StickyList;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.yml.tag.output.YmlTagOutputPredicate;
import org.dgroup.dockertest.yml.tag.test.YmlTagTest;

/**
 * Default implementation of single test result.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class SingleTestOutcome implements TestOutcome {

    /**
     * Detail regarding executed test.
     */
    private final YmlTagTest test;
    /**
     * Output from docker container.
     */
    private final String output;
    /**
     * Failed scenarios.
     */
    private final List<YmlTagOutputPredicate> failed;

    /**
     * Ctor.
     * @param test Details regarding test which was executed.
     * @param output Output from docker container.
     */
    public SingleTestOutcome(final YmlTagTest test, final String output) {
        this.test = test;
        this.output = output;
        this.failed = new StickyList<>(
            new Filtered<>(
                t -> !t.test(this.output),
                this.test.output()
            )
        );
    }

    /**
     * Status of testing scenario.
     * @return True in case of absent failed scenarios.
     */
    public boolean successful() {
        return this.failed.isEmpty();
    }

    /**
     * Testing scenario details.
     * @return Scenario details like passed/failed, docker cmd, output.
     */
    public List<String> message() {
        return new UncheckedTernary<>(
            this.successful(), this::messagePassed, this::messageFailed
        ).value();
    }

    /**
     * Return success test report for single test.
     * @return Test report for single test.
     */
    private List<String> messagePassed() {
        return Collections.singletonList(
            new PlainFormattedText(
                "> %s PASSED",
                this.test.assume()
            ).asString()
        );
    }

    /**
     * Return failed test report for single test.
     * @return Test report for single test.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    private List<String> messageFailed() {
        return new Joined<>(
            new ListOf<>(
                new PlainFormattedText(
                    "> %s FAILED", this.test.assume()
                ).asString(),
                new PlainFormattedText(
                    "  command: \"%s\"", this.test.cmd()
                ).asString(),
                new PlainFormattedText(
                    "  output:  \"%s\"", this.output
                ).asString(),
                "  expected output:"
            ),
            new Mapped<>(
                o -> new PlainFormattedText("    - %s", o).asString(),
                this.test.output()
            ),
            new ListOf<>("  mismatch:"),
            new Mapped<>(
                o -> new PlainFormattedText("    - %s", o).asString(),
                this.failed
            )
        );
    }

}
