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
package org.dgroup.dockertest.docker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * OS dependent system process.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@SuppressWarnings("PMD.CallSuperInConstructor")
public final class ProcessOf extends Process {

    /**
     * OS dependent system process instance.
     */
    private final Scalar<Process> origin;

    /**
     * Ctor.
     * @param cmd Arguments for OS dependent system process.
     */
    public ProcessOf(final List<String> cmd) {
        this(
            new StickyScalar<>(
                () -> new ProcessBuilder(cmd).redirectErrorStream(true).start()
            )
        );
    }

    /**
     * Ctor.
     * @param origin OS dependent system process.
     */
    public ProcessOf(final Scalar<Process> origin) {
        this.origin = origin;
    }

    /**
     * Execute one command.
     * @return Instance of started OS dependent system process.
     * @throws IOException if an I/O error occurs.
     * @checkstyle IllegalCatchCheck (6 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Process execute() throws IOException {
        try {
            return this.origin.value();
        } catch (final Exception exp) {
            throw new IOException(exp);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return new UncheckedScalar<>(this.origin).value().getOutputStream();
    }

    @Override
    public InputStream getInputStream() {
        return new UncheckedScalar<>(this.origin).value().getInputStream();
    }

    @Override
    public InputStream getErrorStream() {
        return new UncheckedScalar<>(this.origin).value().getErrorStream();
    }

    @Override
    public int waitFor() throws InterruptedException {
        return new UncheckedScalar<>(this.origin).value().waitFor();
    }

    @Override
    public int exitValue() {
        return new UncheckedScalar<>(this.origin).value().exitValue();
    }

    @Override
    public void destroy() {
        new UncheckedScalar<>(this.origin).value().destroy();
    }

}
