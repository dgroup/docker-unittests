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
package org.dgroup.dockertest;

import java.io.UncheckedIOException;
import org.dgroup.dockertest.cmd.CmdArgNotFoundException;
import org.dgroup.dockertest.docker.DockerRuntimeException;
import org.dgroup.dockertest.test.output.std.StdOutput;

/**
 * For cases of comm app abnormal termination.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (200 lines)
 */
public final class AbnormalTermination {

    /**
     * Standard output for application progress.
     */
    private final StdOutput std;

    /**
     * Ctor.
     * @param std Standard output for application progress.
     */
    public AbnormalTermination(final StdOutput std) {
        this.std = std;
    }

    /**
     * Shutdown the application with code {@code -1} due to failed testing.
     */
    public void testingFailed() {
        this.shutdownWith(-1);
    }

    /**
     * Shutdown the application with code {@code -3} due to missing required
     * command-line argument.
     * @param exp Occurred exception.
     */
    public void dueTo(final CmdArgNotFoundException exp) {
        this.std.print(exp.getMessage());
        this.shutdownWith(-3);
    }

    /**
     * Shutdown the application with code {@code -4} due to unexpected docker
     * runtime exception.
     * @param exp Occurred exception.
     */
    public void dueTo(final DockerRuntimeException exp) {
        this.std.print(exp.byLines());
        this.shutdownWith(-4);
    }

    /**
     * Shutdown the application with code {@code -5} due to unexpected runtime
     * exception.
     * @param exp Occurred exception.
     */
    public void dueTo(final UncheckedIOException exp) {
        this.std.print("App failed due to unexpected runtime exception:", exp);
        this.shutdownWith(-5);
    }

    /**
     * Shutdown application with error code.
     * The error code is required when the app is invoked from shell scripts:
     *  - {@code -1} testing failed;
     *  - {@code -2} yml file has unsupported/incorrect format;
     *  - {@code -3} required cmd arguments wasn't specified;
     *  - {@code -4} runtime exception happens on docker side.
     *  - {@code -5} runtime exception happens.
     * @param code Exit code.
     * @checkstyle NonStaticMethodCheck (10 lines)
     */
    @SuppressWarnings("PMD.DoNotCallSystemExit")
    private void shutdownWith(final int code) {
        Runtime.getRuntime().exit(code);
    }

}
