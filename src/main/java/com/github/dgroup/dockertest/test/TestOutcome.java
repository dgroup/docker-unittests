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

import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import java.util.Collection;
import java.util.Collections;

/**
 * Represents single test result.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle TooManyMethods (200 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface TestOutcome {

    /**
     * The test status.
     * @return Status of single test result.
     */
    boolean successful();

    /**
     * The name of testing scenario.
     * @return The name.
     */
    String scenario();

    /**
     * The docker command to be executed within docker container.
     * @return The command.
     */
    String cmd();

    /**
     * The raw output from docker container.
     * @return Text output.
     */
    String rawOutput();

    /**
     * Expected conditions/result within current testing scenario.
     * @return Expected conditions.
     */
    Collection<TgOutputPredicate> expectedConditions();

    /**
     * Failed conditions/result within current testing scenario.
     * @return Failed conditions.
     */
    Collection<TgOutputPredicate> failedConditions();

    /**
     * Fake implementation for unit testing purposes.
     * @checkstyle JavadocVariableCheck (100 lines)
     */
    class Fake implements TestOutcome {

        private final boolean sful;
        private final String snr;
        private final String cmd;
        private final String out;
        private final Collection<TgOutputPredicate> ecnd;
        private final Collection<TgOutputPredicate> fcnd;

        /**
         * Ctor.
         */
        public Fake() {
            this(false, "");
        }

        /**
         * Ctor.
         * @param sful The test status (successful or not).
         * @param snr The name of testing scenario.
         */
        public Fake(final boolean sful, final String snr) {
            this(sful, snr, "");
        }

        /**
         * Ctor.
         * @param sful The test status (successful or not).
         * @param snr The name of testing scenario.
         * @param cmd The docker command to be executed within docker container.
         * @checkstyle LineLengthCheck (4 lines)
         */
        public Fake(final boolean sful, final String snr, final String cmd) {
            this(sful, snr, cmd, "", Collections.emptySet(), Collections.emptySet());
        }

        /**
         * Ctor.
         * @param sful The test status (successful or not).
         * @param snr The name of testing scenario.
         * @param cmd The docker command to be executed within docker container.
         * @param out The raw output from docker container.
         * @param ecnd Expected conditions/result within current test.
         * @param fcnd Failed conditions/result within current test.
         * @checkstyle ParameterNumberCheck (10 lines)
         */
        public Fake(final boolean sful, final String snr, final String cmd,
            final String out, final Collection<TgOutputPredicate> ecnd,
            final Collection<TgOutputPredicate> fcnd) {
            this.sful = sful;
            this.snr = snr;
            this.cmd = cmd;
            this.out = out;
            this.ecnd = ecnd;
            this.fcnd = fcnd;
        }

        @Override
        public boolean successful() {
            return this.sful;
        }

        @Override
        public String scenario() {
            return this.snr;
        }

        @Override
        public String cmd() {
            return this.cmd;
        }

        @Override
        public String rawOutput() {
            return this.out;
        }

        @Override
        public Collection<TgOutputPredicate> expectedConditions() {
            return this.ecnd;
        }

        @Override
        public Collection<TgOutputPredicate> failedConditions() {
            return this.fcnd;
        }
    }
}
