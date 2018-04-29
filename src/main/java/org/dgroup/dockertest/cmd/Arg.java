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
package org.dgroup.dockertest.cmd;

/**
 * Represents single command-line argument.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of item.
 * @since 1.0
 */
public interface Arg<X> {

    /**
     * Fetch name of command-line argument.
     *
     * For example, for {@code -f tests.yml} the name is {@code -f}.
     * @return The argument name.
     */
    String name();

    /**
     * Fetch value of command-line argument.
     *
     * The value should be specified after the name.
     * For example, for {@code -f tests.yml} the value is {@code tests.yml}.
     *
     * @return The argument value.
     * @throws CmdArgNotFoundException in case if the argument wasn't
     *  specified by the user or arguments itself are empty.
     */
    X value() throws CmdArgNotFoundException;

    /**
     * Verify existence of argument in the arguments specified by user.
     * @return The true, in the case, when the user specified the argument
     *  from the shell.
     */
    boolean specifiedByUser();

    /**
     * Fake implementation for unit testing purposes.
     * @param <X> Type of item.
     * @checkstyle JavadocMethodCheck (20 lines)
     * @checkstyle JavadocVariableCheck (20 lines)
     */
    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    final class Fake<X> implements Arg<X> {

        private final String name;
        private final X value;
        private final Boolean specified;

        public Fake(final String nme, final X val) {
            this(nme, val, true);
        }

        public Fake(final String name, final X value, final Boolean specified) {
            this.name = name;
            this.value = value;
            this.specified = specified;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public X value() {
            return this.value;
        }

        @Override
        public boolean specifiedByUser() {
            return this.specified;
        }
    }

}
