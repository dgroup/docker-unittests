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
package com.github.dgroup.dockertest.yml;

/**
 * Represents yml tag
 * {@code /tests/test/output/contains|equal|startsWith|endsWith}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface TgOutputPredicate {

    /**
     * Comparing type.
     * @return Available types are contains, equal, startsWith, endsWith.
     */
    String comparingType();

    /**
     * Expected value by test.
     * @return Value expected by test. For example, in case of `matches` tag
     *  the method return regexp statement.
     */
    String expected();

    /**
     * Compare expected value with actual.
     *
     * @param actual Output received from docker container.
     * @return True in case if expected value is equal to actual value.
     * @throws YmlFormatException in case when condition is
     *  unsupported or har wrong/empty value.
     */
    boolean test(final String actual) throws YmlFormatException;
}
