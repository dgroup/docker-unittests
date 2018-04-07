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
package org.dgroup.dockertest.test.output.std;

import java.util.ArrayList;
import java.util.List;
import org.cactoos.collection.Mapped;
import org.dgroup.dockertest.scalar.If;
import org.dgroup.dockertest.test.outcome.TestingOutcome;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.text.Text;

/**
 * Standard output for application progress.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface StdOutput extends Output {

    /**
     * Print text to single line.
     * @param msg Text to print
     */
    void print(final Text msg);

    /**
     * Print text to single line.
     * @param msg Text to print
     */
    void print(final String msg);

    /**
     * Print text to single line.
     * @param header Text to print as header.
     * @param lines Text to print as separate lines.
     */
    void print(final String header, final Object... lines);

    /**
     * Print text to single line.
     * @param msg Text to print
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
     * @checkstyle HiddenFieldCheck (50 lines)
     * @checkstyle JavadocMethodCheck (100 lines)
     * @checkstyle JavadocVariableCheck (10 lines)
     */
    final class Fake implements StdOutput {

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
        public void print(final String header, final Object... lines) {
            this.print(header);
            this.print(new Mapped<>(Object::toString, lines));
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
