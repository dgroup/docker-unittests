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
package org.dgroup.dockertest.test.outcome;

import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Default implementation of single test result.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TestOutcomeOf implements TestOutcome {

    /**
     * Status of test scenario.
     */
    private final Scalar<Boolean> passed;
    /**
     * The message/details regarding testing scenario.
     */
    private final List<String> msg;

    /**
     * Ctor.
     * @param passed Status of test.
     * @param msg Test details.
     */
    public TestOutcomeOf(final Boolean passed, final String... msg) {
        this(() -> passed, new ListOf<>(msg));
    }

    /**
     * Ctor.
     * @param passed Status of test.
     * @param msg Test details.
     */
    public TestOutcomeOf(final Scalar<Boolean> passed, final List<String> msg) {
        this.passed = passed;
        this.msg = msg;
    }

    /**
     * Status of testing scenario.
     * @return True in case of absent failed scenarios.
     */
    public boolean successful() {
        return new UncheckedScalar<>(this.passed).value();
    }

    /**
     * Testing scenario details.
     * @return Scenario details like passed/failed, docker cmd, output.
     */
    public List<String> message() {
        return this.msg;
    }

}
