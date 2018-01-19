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
package org.dgroup.dockertest.docker;

import java.io.IOException;
import java.util.List;

/**
 * OS dependent system process.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class SystemProcess {

    /**
     * OS dependent system process instance.
     */
    private final ProcessBuilder process;

    /**
     * Ctor.
     * @param cmd Arguments for OS dependent system process.
     */
    public SystemProcess(final List<String> cmd) {
        this(new ProcessBuilder(cmd).redirectErrorStream(true));
    }

    /**
     * Ctor.
     * @param process Instance of OS dependent system process.
     */
    public SystemProcess(final ProcessBuilder process) {
        this.process = process;
    }

    /**
     * Execute one command.
     * @return Instance of started OS dependent system process.
     * @throws IOException if an I/O error occurs.
     */
    public Process execute() throws IOException {
        return this.process.start();
    }

}
