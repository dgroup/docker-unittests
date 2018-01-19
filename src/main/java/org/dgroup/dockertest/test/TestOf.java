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

import java.util.List;
import org.cactoos.iterable.Filtered;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.list.StickyList;
import org.dgroup.dockertest.docker.DockerProcessExecutionException;
import org.dgroup.dockertest.docker.process.DockerProcess;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.text.HighlightedText;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.yml.tag.output.YmlTagOutputPredicate;
import org.fusesource.jansi.Ansi.Color;

/**
 * Represents YML based implementation for single test.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class TestOf implements Test {

    /**
     * Docker container where we need to execute the test.
     */
    private final DockerProcess process;
    /**
     * Name of testing scenario.
     */
    private final String assume;
    /**
     * Command to be executed inside of docker container.
     */
    private final String cmd;
    /**
     * Expected conditions which should be applied to output
     *  from docker container.
     */
    private final List<YmlTagOutputPredicate> expected;

    /**
     * Ctor.
     * @param assume Name of testing scenario.
     * @param cmd Command to be executed within docker container.
     * @param expect Expected conditions which should be applied
     *  to output from docker container.
     * @param proc Docker container where test be executed.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public TestOf(final String assume, final String cmd,
        final List<YmlTagOutputPredicate> expect, final DockerProcess proc) {
        this.assume = assume;
        this.cmd = cmd;
        this.expected = expect;
        this.process = proc;
    }

    @Override
    public TestOutcome execute() throws DockerProcessExecutionException {
        final String output = this.process.execute().asText();
        final List<YmlTagOutputPredicate> failed = new StickyList<>(
            new Filtered<>(t -> !t.test(output), this.expected)
        );
        return new TestOutcomeOf(
            failed::isEmpty,
            new UncheckedTernary<>(
                failed::isEmpty,
                this::messagePassed,
                () -> this.messageFailed(output, failed)
            ).value()
        );
    }

    /**
     * Return success test report for single test.
     * @return Test report for single test.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public List<String> messagePassed() {
        return new ListOf<>(
            new PlainFormattedText(
                "> %s %s",
                this.assume, new HighlightedText("PASSED", Color.GREEN)
            ).asString()
        );
    }

    /**
     * Return failed test report for single test.
     *
     * @param output From docker container.
     * @param failed Failed conditions which was applied to the output
     *  from docker container.
     * @return Test report for single test.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public List<String> messageFailed(final String output,
        final List<YmlTagOutputPredicate> failed) {
        return new Joined<>(
            new ListOf<>(
                new PlainFormattedText(
                    "> %s %s",
                    this.assume,
                    new HighlightedText("FAILED", Color.RED)
                ).asString(),
                new PlainFormattedText(
                    "  command: \"%s\"", this.cmd
                ).asString(),
                new PlainFormattedText(
                    "  output:  \"%s\"", output
                ).asString(),
                "  expected output:"
            ),
            new Mapped<>(
                o -> new PlainFormattedText("    - %s", o).asString(),
                this.expected
            ),
            new ListOf<>("  mismatch:"),
            new Mapped<>(
                o -> new PlainFormattedText("    - %s", o).asString(),
                failed
            )
        );
    }

}
