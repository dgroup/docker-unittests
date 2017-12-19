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

import org.cactoos.collection.Filtered;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.text.StringOf;
import org.dgroup.dockertest.yml.tag.YmlTagTest;

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
     * Ctor.
     * @param test Details regarding test which was executed.
     * @param output Output from docker container.
     */
    public SingleTestOutcome(final YmlTagTest test, final String output) {
        this.test = test;
        this.output = output;
    }

    /**
     * Status of testing scenario.
     * @return True in case of absent failed scenarios.
     */
    public boolean successful() {
        return new Filtered<>(
            t -> !t.test(this.output), this.test.output()
        ).isEmpty();
    }

    /**
     * Testing scenario details.
     * @return Scenario details like passed/failed, docker cmd, output.
     */
    public String message() {
        return new UncheckedTernary<>(
            this.successful(),
            () -> new PlainFormattedText(
                "Passed scenario `%s` (cmd=`%s`). Output is `%s`",
                this.test.assume(),
                this.test.cmd(),
                this.output
            ).asString(),
            () -> new PlainFormattedText(
                "Failed scenario `%s` (cmd=`%s`). `%s` didn't match to `%s`",
                this.test.assume(),
                this.test.cmd(),
                this.output,
                new StringOf(this.test.output(), ", ")
            ).asString()
        ).value();
    }

}
