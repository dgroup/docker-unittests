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
package org.dgroup.dockertest.concurrent;

import java.util.concurrent.TimeUnit;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Timeout with units and value.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface Timeout {

    /**
     * Timeout.
     * @return The value.
     */
    Long timeout();

    /**
     * Timeout unit.
     * @return Nanoseconds, milliseconds, seconds, etc.
     */
    TimeUnit measure();

    /**
     * No timeout implementation.
     * Originally, implemented for unit testing purposes.
     */
    class No implements Timeout {

        @Override
        public Long timeout() {
            return 0L;
        }

        @Override
        public TimeUnit measure() {
            return TimeUnit.MILLISECONDS;
        }
    }

    /**
     * Hamcrest matcher for unit testing purposes.
     * @checkstyle ProtectedMethodInFinalClassCheck (100 lines)
     */
    final class HasValue extends TypeSafeDiagnosingMatcher<Timeout> {

        /**
         * Origin.
         */
        private final Timeout tmt;

        /**
         * Ctor.
         * @param tmt Origin.
         */
        @SuppressWarnings("PMD.CallSuperInConstructor")
        public HasValue(final Timeout tmt) {
            this.tmt = tmt;
        }

        @Override
        public void describeTo(final Description dsc) {
            dsc.appendValue(this.tmt.toString());
        }

        @Override
        protected boolean matchesSafely(final Timeout itm,
            final Description dsc) {
            dsc.appendValue(itm.toString());
            return itm.timeout().equals(this.tmt.timeout())
                && itm.measure().equals(this.tmt.measure());
        }
    }

}
