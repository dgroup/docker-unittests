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
package com.github.dgroup.dockertest.docker;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Fake implementation of {@link java.lang.Process}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@SuppressWarnings("PMD.CallSuperInConstructor")
public final class FakeProcess extends java.lang.Process implements Process {

    /**
     * Stream connected to the normal output of the sub-process.
     */
    private final InputStream inp;
    /**
     * Stream connected to the normal input of the sub-process.
     */
    private final OutputStream out;
    /**
     * Stream connected to the error output of the sub-process.
     */
    private final InputStream err;
    /**
     * Causes the current thread to wait, if necessary, until the process
     *  represented by this {@code Process} object has terminated.
     */
    private final int exit;
    /**
     * The exit value for the sub-process.
     */
    private final int wait;

    /**
     * Ctor.
     * @param input Stream connected to the normal output of the sub-process.
     * @param output Stream connected to the normal input of the sub-process.
     */
    public FakeProcess(final InputStream input, final OutputStream output) {
        this(input, output, input, 0, 0);
    }

    /**
     * Ctor.
     * @param input Stream connected to the normal output of the sub-process.
     * @param output Stream connected to the normal input of the sub-process.
     * @param err Input stream connected to the error output of the sub-process.
     * @param wait Causes the current thread to wait, if necessary, until the
     *  process represented by this {@code Process} object has terminated.
     * @param exit The exit value for the sub-process.
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public FakeProcess(
        final InputStream input,
        final OutputStream output,
        final InputStream err,
        final int wait,
        final int exit
    ) {
        this.inp = input;
        this.out = output;
        this.err = err;
        this.wait = wait;
        this.exit = exit;
    }

    @Override
    public OutputStream getOutputStream() {
        return this.out;
    }

    @Override
    public InputStream getInputStream() {
        return this.inp;
    }

    @Override
    public InputStream getErrorStream() {
        return this.err;
    }

    @Override
    public int waitFor() {
        return this.wait;
    }

    @Override
    public int exitValue() {
        return this.exit;
    }

    @Override
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void destroy() {
    }

    @Override
    public java.lang.Process execute() {
        return this;
    }

}
