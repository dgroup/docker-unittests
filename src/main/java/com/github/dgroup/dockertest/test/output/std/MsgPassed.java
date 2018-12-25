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

import com.github.dgroup.dockertest.test.TestOutcome;
import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.text.highlighted.GreenText;
import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Message to standard output about passed testing.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0.3
 */
public final class MsgPassed implements Iterable<String> {

    /**
     * The testing scenario name.
     */
    private final UncheckedScalar<String> scenario;

    /**
     * Ctor.
     * @param outcome The single test result.
     */
    public MsgPassed(final TestOutcome outcome) {
        // @checkstyle RequireThisCheck (1 line)
        this(outcome::scenario);
    }

    /**
     * Ctor.
     * @param scenario The testing scenario name.
     */
    public MsgPassed(final String scenario) {
        this(() -> scenario);
    }

    /**
     * Ctor.
     * @param scenario The testing scenario name.
     */
    public MsgPassed(final Scalar<String> scenario) {
        this.scenario = new UncheckedScalar<>(scenario);
    }

    @Override
    public Iterator<String> iterator() {
        return new ListOf<>(
            new TextOf(
                "> %s %s", this.scenario.value(), new GreenText("PASSED")
            ).text()
        ).iterator();
    }

}
