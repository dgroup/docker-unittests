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
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.CmdArgNotFoundException;
import org.dgroup.dockertest.docker.DockerProcessExecutionException;
import org.dgroup.dockertest.exception.RootCause;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.Tests;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.test.output.std.StdOutputOf;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * App start point with main method only.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle HideUtilityClassConstructorCheck (10 lines)
 */
@SuppressWarnings("PMD.UseUtilityClass")
public final class App {

    /**
     * Start point.
     * @param arguments YML file with tests and docker image name.
     */
    public static void main(final String... arguments) {
        final StdOutput std = new StdOutputOf(System.out, "    ");
        final AbnormalTermination termination = new AbnormalTermination(std);
        final Args args = new Args(arguments);
        try {
            std.print(new Logo("1.0").byLines());
            new Tests(args, std)
                .execute();
        } catch (final TestingFailedException ex) {
            termination.testingFailed();
        } catch (final CmdArgNotFoundException ex) {
            termination.dueTo(ex);
        } catch (final IllegalYmlFileFormatException ex) {
            std.print(filename(args), ex);
        } catch (final DockerProcessExecutionException ex) {
            termination.dueTo(ex);
        } catch (final UncheckedIOException ex) {
            final Throwable cause = new RootCause(ex).exception();
            if (cause instanceof IllegalYmlFileFormatException) {
                std.print(
                    filename(args), (IllegalYmlFileFormatException) cause
                );
            } else {
                termination.dueTo(ex);
            }
        }
    }

    /**
     * This method will be invoked when {@link IllegalYmlFileFormatException}
     * occurs. It means that YML file (specified by user) has wrong format.
     * As the result, we are able to extract file name at this time.
     *
     * @param args Command-line arguments from user.
     * @return YML file name
     */
    private static String filename(final Args args) {
        String file;
        try {
            file = args.ymlFilename();
        } catch (final CmdArgNotFoundException ex) {
            file = "";
        }
        return file;
    }

}
