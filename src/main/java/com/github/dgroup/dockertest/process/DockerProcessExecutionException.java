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
package com.github.dgroup.dockertest.process;

import java.text.MessageFormat;

/**
 * Raise when exception happens during docker command execution.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class DockerProcessExecutionException extends Exception {

    /**
     * Ctor.
     * @param cause Root cause details.
     */
    public DockerProcessExecutionException(final Throwable cause) {
        super(cause);
    }

    /**
     * Ctor.
     * @param msg Exception details.
     */
    public DockerProcessExecutionException(final String msg) {
        super(msg);
    }

    /**
     * Ctor.
     * @param pattern The error message pattern.
     * @param args The arguments for error message.
     * @see java.text.MessageFormat#format(String, Object...).
     */
    public DockerProcessExecutionException(
        final String pattern, final Object ...args
    ) {
        super(MessageFormat.format(pattern, args));
    }
}
