/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
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
package com.github.dgroup.dockertest.test;

import com.github.dgroup.dockertest.cmd.Arg;
import java.util.Collection;

/**
 * Represents outcome for all tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface TestingOutcome extends Iterable<TestOutcome> {

    /**
     * Checking all tests outcome for passed scenario's.
     * @return The false, in the case, when failed scenarios found.
     */
    boolean successful();

    /**
     * Print testing outcome to specified outputs.
     * @param outputs Selected by user output formats.
     * @throws TestingFailedException in case if at least one test is failed.
     */
    void report(final Output... outputs) throws TestingFailedException;

    /**
     * Print testing outcome to specified outputs.
     * @param outputs Selected by user output formats.
     * @throws TestingFailedException in case if at least one test is failed.
     */
    void report(final Iterable<Output> outputs) throws TestingFailedException;

    /**
     * Print testing outcome to specified outputs.
     * @param outputs Selected by user output formats.
     * @throws TestingFailedException in case if at least one test is failed.
     */
    void report(final Arg<Collection<Output>> outputs)
        throws TestingFailedException;

}
