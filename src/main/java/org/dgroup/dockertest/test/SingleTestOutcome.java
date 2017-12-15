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
import org.cactoos.collection.Filtered;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.text.StringOf;
import org.dgroup.dockertest.yml.tag.YmlTagOutputPredicate;

/**
 * Default implementation of single test result.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class SingleTestOutcome implements TestOutcome {

    /**
     * Name of testing scenario.
     * By default, exported from `assume` section
     * (for each test defined in *.yml).
     */
    private final String scenario;
    /**
     * Command for execution in docker container.
     * By default, exported from `cmd` section
     * (for each test defined in *.yml).
     */
    private final String cmd;
    /**
     * Output from docker container.
     */
    private final String output;
    /**
     * List of expected conditions, which should be applied to output.
     * By default, exported from `output` section (
     * for each test defined in *.yml).
     */
    private final List<YmlTagOutputPredicate> expected;

    /**
     * Ctor.
     */
    public SingleTestOutcome(final String scenario, final String cmd,
        final String output, final List<YmlTagOutputPredicate> expected) {
        this.scenario = scenario;
        this.cmd = cmd;
        this.output = output;
        this.expected = expected;
    }

    public boolean successful() {
        return new Filtered<>(
            t -> !t.test(this.output), this.expected
        ).isEmpty();
    }

    public String message() {
        return this.successful() ? this.scenarioPassed() : this.scenarioFailed();
    }

    private String scenarioPassed() {
        return new PlainFormattedText(
            "Passed scenario `%s` (cmd=`%s`). Output is `%s`",
            this.scenario, this.cmd, this.output
        ).asString();
    }

    private String scenarioFailed() {
        return new PlainFormattedText(
            "Failed scenario `%s` (cmd=`%s`). Got `%s` instead of `%s`",
            this.scenario,
            this.cmd,
            new StringOf(this.expected, ", "),
            this.output
        ).asString();
    }

}
