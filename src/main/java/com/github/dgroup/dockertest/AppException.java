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
package com.github.dgroup.dockertest;

import com.github.dgroup.dockertest.text.Splitted;
import com.github.dgroup.dockertest.text.Text;
import java.util.Collection;
import org.cactoos.list.ListOf;

/**
 * Application error.
 *
 * Always has the error code which supposed to be exposed to the external
 *  parent process like CLI, bash shell, etc.
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
     * @param msg The message to be print to the user.
     */
    public AppException(final String msg) {
        this(0, msg);
    }

    /**
     * Ctor.
     * @param msg The message to be print to the user.
     */
    public AppException(final Text msg) {
        this(0, msg.text());
    }

    /**
     * Ctor.
     * @param exit Application exit code to be print to the user.
     * @param msg The message to be print to the user.
     */
    public AppException(final Integer exit, final Text msg) {
        this(exit, msg.text());
    }

    /**
     * Ctor.
     * @param exit Application exit code to be print to the user.
     * @param cause The original root cause to be print to the user.
     */
    public AppException(final Integer exit, final Throwable cause) {
        this(exit, new Splitted(cause.getMessage(), "\n"));
    }

    /**
     * Ctor.
     * @param exit Application exit code to be print to the user.
     * @param msg The message to be print to the user.
     */
    public AppException(final Integer exit, final String msg) {
        this(exit, new ListOf<>(msg));
    }

    /**
     * Ctor.
     * @param exit Application exit code to be print to the user.
     * @param msg The message to be print to the user.
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
