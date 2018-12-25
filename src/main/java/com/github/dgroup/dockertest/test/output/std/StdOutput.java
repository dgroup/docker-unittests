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

import com.github.dgroup.dockertest.scalar.If;
import com.github.dgroup.dockertest.test.Output;
import com.github.dgroup.dockertest.test.TestingOutcome;
import com.github.dgroup.dockertest.text.Text;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard output for application progress.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @todo #/DEV Rename the interface to Std, the implementation to StdOutput
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface StdOutput extends Output {

    /**
     * Print text to single line.
     * @param msg The text to print
     */
    void print(final Text msg);

    /**
     * Print text to single line.
     * @param msg The text to print
     */
    void print(final String msg);

    /**
     * Print text to single line.
     * @param msg The text to print.
     */
    void print(final Iterable<String> msg);

    /**
     * Print app exception.
     * @param msg App exception message
     * @param exp App exception details
     */
    void print(final String msg, final Exception exp);

    /**
     * Fake instance for unit-testing purposes.
     * @checkstyle JavadocMethodCheck (100 lines)
     */
    final class Fake implements StdOutput {

        /**
         * Fake application output.
         */
        private final List<String> lines;

        /**
         * Ctor.
         * @checkstyle ConditionalRegexpMultilineCheck (5 lines)
         */
        public Fake() {
            this(new ArrayList<>());
        }

        public Fake(final List<String> lines) {
            this.lines = lines;
        }

        @Override
        public void print(final Text msg) {
            this.lines.add(msg.text());
        }

        @Override
        public void print(final String msg) {
            this.lines.add(msg);
        }

        @Override
        public void print(final Iterable<String> messages) {
            for (final String msg : messages) {
                this.print(msg);
            }
        }

        @Override
        public void print(final String msg, final Exception exp) {
            this.print(msg);
            this.print(exp.toString());
        }

        @Override
        public void print(final TestingOutcome outcome) {
            this.print(
                new If<>(
                    outcome.successful(),
                    "Testing successfully completed.",
                    "Testing failed."
                ).value()
            );
        }

        public List<String> details() {
            return this.lines;
        }
    }

}
