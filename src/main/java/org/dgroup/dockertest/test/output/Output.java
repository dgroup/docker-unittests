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
package org.dgroup.dockertest.test.output;

import java.util.ArrayList;
import java.util.List;
import org.cactoos.list.Joined;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.test.outcome.TestOutcome;
import org.dgroup.dockertest.test.outcome.TestingOutcome;

/**
 * Represent an output where we can print the testing results
 * (std out, xml, html, etc).
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface Output {

    /**
     * Print testing outcome.
     * @param outcome Of testing.
     */
    void print(final TestingOutcome outcome);

    /**
     * Fake instance for unit testing purposes.
     * @checkstyle JavadocMethodCheck (20 lines)
     * @checkstyle JavadocVariableCheck (20 lines)
     */
    final class Fake implements Output {

        private final List<String> output = new ArrayList<>(10);

        @Override
        public void print(final TestingOutcome outcome) {
            this.output.addAll(
                new Joined<>(
                    new Mapped<>(TestOutcome::message, outcome)
                )
            );
        }

        public List<String> lines() {
            return this.output;
        }
    }

}
