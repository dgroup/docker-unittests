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
package org.dgroup.dockertest;

import java.util.Collection;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.text.Text;
import org.dgroup.dockertest.text.TextOf;

/**
 * Application error.
 *
 * Always has the error code which supposed to be exposed to the external
 *  parent process like bash shell, etc.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class AppException extends Exception {

    /**
     * Application exit code.
     */
    private final Integer exit;
    /**
     * Message.
     */
    private final Collection<String> msg;

    /**
     * Ctor.
     * @param msg Message.
     */
    public AppException(final String msg) {
        this(0, msg);
    }

    /**
     * Ctor.
     * @param pattern For the {@link TextOf}.
     * @param args For the {@link TextOf}.
     */
    public AppException(final String pattern, final Object... args) {
        this(0, new TextOf(pattern, args).text());
    }

    /**
     * Ctor.
     * @param exit Application exit code.
     * @param msg The message.
     */
    public AppException(final Integer exit, final Text msg) {
        this(exit, msg.text());
    }

    /**
     * Ctor.
     * @param exit Application exit code.
     * @param cause Original.
     */
    public AppException(final Integer exit, final Exception cause) {
        this(exit, cause.getMessage());
    }

    /**
     * Ctor.
     * @param exit Application exit code.
     * @param msg The message.
     */
    public AppException(final Integer exit, final String msg) {
        this(exit, new ListOf<>(msg));
    }

    /**
     * Ctor.
     * @param exit Application exit code.
     * @param msg The message.
     */
    public AppException(final Integer exit, final Collection<String> msg) {
        super();
        this.exit = exit;
        this.msg = msg;
    }

    /**
     * Application exit code to be exposed to the parent OS process.
     * @return The code.
     */
    public Integer exitCode() {
        return this.exit;
    }

    /**
     * Application error message.
     * @return The message.
     */
    public Collection<String> message() {
        return this.msg;
    }
}
