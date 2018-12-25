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
package com.github.dgroup.dockertest.test;

import com.github.dgroup.dockertest.concurrent.Timeout;
import com.github.dgroup.dockertest.process.DockerProcessExecutionException;
import com.github.dgroup.dockertest.text.TextOf;

/**
 * Represents single unit test for the docker image.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface Test {

    /**
     * Execute single test within docker.
     * @return Testing results.
     * @throws DockerProcessExecutionException in case of runtime exceptions
     *  on docker side.
     */
    TestOutcome execute() throws DockerProcessExecutionException;

    /**
     * Fake implementation of {@link Test} for unit testing purposes.
     */
    final class Sleeping implements Test {

        /**
         * Timeout.
         */
        private final Timeout tmt;

        /**
         * Ctor.
         * @param tmt Timeout for sleeping period.
         */
        public Sleeping(final Timeout tmt) {
            this.tmt = tmt;
        }

        @Override
        public TestOutcome execute() throws DockerProcessExecutionException {
            try {
                this.tmt.measure().sleep(this.tmt.timeout());
                return new TestOutcome.Fake(
                    true,
                    new TextOf(
                        "[%s:%s] Slept %s %s.",
                        Thread.currentThread().getName(),
                        Integer.toHexString(this.hashCode()),
                        this.tmt.timeout(),
                        this.tmt.measure()
                    ).text()
                );
            } catch (final InterruptedException exp) {
                Thread.currentThread().interrupt();
                throw new DockerProcessExecutionException(exp);
            }
        }
    }

}
