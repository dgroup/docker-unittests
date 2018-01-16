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
package org.dgroup.dockertest.exception;

import java.util.ArrayList;
import java.util.List;
import org.dgroup.dockertest.scalar.UncheckedTernary;

/**
 * Represents root cause exception for particular exception.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class RootCause {
    /**
     * Exception.
     */
    private final Exception origin;

    /**
     * Ctor.
     *
     * @param exp Exception.
     */
    public RootCause(final Exception exp) {
        this.origin = exp;
    }

    /**
     * Each exception may contain a lot of nested exceptions.
     *
     * @return Root case exception.
     */
    public Throwable exception() {
        Throwable cause = new UncheckedTernary<>(
            this.origin.getCause() == null,
            this.origin,
            this.origin.getCause()
        ).value();
        final List<Throwable> visited = new ArrayList<>(5);
        while (cause.getCause() != null
            && !visited.contains(cause.getCause())) {
            cause = cause.getCause();
            visited.add(cause);
        }
        return cause;
    }
}
