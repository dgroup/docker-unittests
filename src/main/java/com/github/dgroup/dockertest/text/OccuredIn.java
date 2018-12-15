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
package com.github.dgroup.dockertest.text;

import org.cactoos.Func;

/**
 * Function to check that string has expected amount of occurrences
 *  of particular string/symbol/etc.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class OccuredIn implements Func<Integer, Boolean> {

    /**
     * Original string.
     */
    private final String origin;
    /**
     * String which we want to find.
     */
    private final String search;

    /**
     * Ctor.
     * @param search Search string.
     * @param origin Base string.
     */
    public OccuredIn(final String search, final String origin) {
        this.search = search;
        this.origin = origin;
    }

    @Override
    public Boolean apply(final Integer occurrences) {
        // @checkstyle IllegalTokenCheck (10 lines)
        int last = 0;
        int count = 0;
        while (last != -1) {
            last = this.origin.indexOf(this.search, last);
            if (last != -1) {
                count++;
                last += this.search.length();
            }
        }
        return count == occurrences;
    }

    /**
     * Compare the number of occurrences with the expected amount and throw the
     * {@link IllegalArgumentException} in case mismatch.
     * @param expected Amount of occurrences.
     * @param msg Exception message to be thrown.
     * @throws IllegalArgumentException in case mismatch.
     */
    public void times(final int expected, final String msg) {
        if (!this.apply(expected)) {
            throw new IllegalArgumentException(msg);
        }
    }

}
