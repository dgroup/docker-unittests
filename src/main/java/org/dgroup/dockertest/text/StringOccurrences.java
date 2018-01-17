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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dgroup.dockertest.text;

/**
 * Represents occurrences in string.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class StringOccurrences {

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
     * @param origin Base string.
     * @param search Search string.
     */
    public StringOccurrences(final String origin, final String search) {
        this.origin = origin;
        this.search = search;
    }

    /**
     * Compare the occurrences with.
     * @param occurrences Amount {@code this.search} in {@code this.base}.
     * @return True in case amount mismatch.
     */
    public boolean nonEqualTo(final int occurrences) {
        return !this.equalTo(occurrences);
    }

    /**
     * Compare the occurrences with.
     * @param occurrences Amount {@code this.search} in {@code this.base}.
     * @return True in case equal amount.
     * @checkstyle IllegalTokenCheck (10 lines)
     */
    public boolean equalTo(final int occurrences) {
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
}
