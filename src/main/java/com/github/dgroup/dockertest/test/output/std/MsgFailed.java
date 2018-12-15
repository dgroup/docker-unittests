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
package com.github.dgroup.dockertest.test.output.std;

import com.github.dgroup.dockertest.test.outcome.TestOutcome;
import com.github.dgroup.dockertest.text.Text;
import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.text.highlighted.RedText;
import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Func;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;

/**
 * Message to standard output about failed testing.
 *
 * The raw output from docker container longer than 90 symbols will be split to
 * several lines.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class MsgFailed implements Iterable<String> {

    /**
     * The single test result.
     */
    private final TestOutcome ocm;
    /**
     * Pattern to detect the size of line for raw docker output.
     * For example, the raw output may have several lines which should be split
     * by length.
     */
    private final Text ptrn;

    /**
     * Ctor.
     * @param out The single test result.
     * @checkstyle MagicNumberCheck (5 lines)
     */
    public MsgFailed(final TestOutcome out) {
        this(out, 90);
    }

    /**
     * Ctor.
     * @param out The single test result.
     * @param lsz The length of the each line of raw docker output.
     */
    public MsgFailed(final TestOutcome out, final int lsz) {
        this(out, new TextOf("(?<=\\G.{%s})", lsz));
    }

    /**
     * Ctor.
     * @param out The single test result.
     * @param ptrn The regexp pattern to split the raw docker output to several
     *  lines if needed.
     *  The default pattern is {@code "(?<=\\G.{%s})"}.
     *  \G is a zero-width assertion that matches the position where the
     *  previous match ended. If there was no previous match, it matches the
     *  beginning of the input, the same as \A. The enclosing lookbehind matches
     *  the position that's four characters along from the end of the last match
     */
    public MsgFailed(final TestOutcome out, final Text ptrn) {
        this.ocm = out;
        this.ptrn = ptrn;
    }

    @Override
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public Iterator<String> iterator() {
        return new Mapped<>(
            Text::text,
            new Joined<>(
                this.scenarioName(),
                this.commandForDockerContainer(),
                this.rawOutputFromDockerContainer(),
                this.expectedOutputHeader(),
                this.expectedOutputPredicates(),
                this.mismatchHeader(),
                this.failedOutputConditions()
            )
        ).iterator();
    }

    /**
     * Describe the testing scenario name.
     * @return The scenario name as collection of lines.
     */
    private List<Text> scenarioName() {
        return new ListOf<>(
            new TextOf(
                "> %s %s", this.ocm.scenario(), new RedText("FAILED")
            )
        );
    }

    /**
     * Describe the docker command from the testing scenario.
     * @return The scenario command as collection of lines.
     */
    private List<Text> commandForDockerContainer() {
        return new ListOf<>(new TextOf("  command: \"%s\"", this.ocm.cmd()));
    }

    /**
     * Describe the raw output from docker container within testing scenario.
     * @return The scenario raw output as collection of lines.
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private List<Text> rawOutputFromDockerContainer() {
        final List<String> raw = new Joined<>(
            new Mapped<>(
                line -> new ListOf<>(line.split(this.ptrn.text())),
                new ListOf<>(this.ocm.rawOutput().split(System.lineSeparator()))
            )
        );
        final List<Text> lns = new ArrayList<>(raw.size());
        final AtomicInteger idx = new AtomicInteger();
        for (final String line : raw) {
            idx.incrementAndGet();
            if (idx.intValue() == 1) {
                lns.add(new TextOf("  output:  \"%s", line));
            } else if (idx.intValue() == raw.size()) {
                lns.add(new TextOf("            %s\"", line));
            } else {
                lns.add(new TextOf("            %s", line));
            }
        }
        return lns;
    }

    /**
     * Describe the expected output header.
     * @return The output header as collection of lines.
     * @checkstyle NonStaticMethodCheck (5 lines)
     */
    private List<Text> expectedOutputHeader() {
        return new ListOf<>(() -> "  expected output:");
    }

    /**
     * Describe the expected conditions within testing scenario.
     * @return The expected conditions as collection of lines.
     */
    private List<Text> expectedOutputPredicates() {
        return new Mapped<>(new ToText(), this.ocm.expectedConditions());
    }

    /**
     * Describe the mismatch header.
     * @return The mismatch header as collection of lines.
     * @checkstyle NonStaticMethodCheck (5 lines)
     */
    private List<Text> mismatchHeader() {
        return new ListOf<>(() -> "  mismatch:");
    }

    /**
     * Describe the failed conditions within testing scenario.
     * @return The failed conditions as collection of lines.
     */
    private List<Text> failedOutputConditions() {
        return new Mapped<>(new ToText(), this.ocm.failedConditions());
    }

    /**
     * Map the {@link TgOutputPredicate} to {@link Text}.
     */
    private final class ToText implements Func<TgOutputPredicate, Text> {
        @Override
        public Text apply(final TgOutputPredicate prd) {
            return new TextOf("    - %s", prd);
        }
    }
}
