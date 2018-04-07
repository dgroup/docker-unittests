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

import java.io.UncheckedIOException;
import java.util.Set;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.CmdArgNotFoundException;
import org.dgroup.dockertest.concurrent.ConcurrentTests;
import org.dgroup.dockertest.exception.RootCauseOf;
import org.dgroup.dockertest.termination.AbnormalTermination;
import org.dgroup.dockertest.termination.Termination;
import org.dgroup.dockertest.test.NoScenariosFoundException;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.test.output.std.StdOutputOf;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

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
        final Args args = new Args(std, arguments);
        final Termination termination = new AbnormalTermination(std, args);
        final Set<Output> outputs = args.selectedByUserOutput();
        try (ConcurrentTests concurrently = new ConcurrentTests(args)) {
            concurrently.execute(args.tests())
                .reportTheResults(outputs);
        } catch (final NoScenariosFoundException ex) {
            termination.dueTo(ex);
        } catch (final TestingFailedException ex) {
            termination.dueTo(ex);
        } catch (final CmdArgNotFoundException ex) {
            termination.dueTo(ex);
        } catch (final IllegalYmlFormatException ex) {
            termination.dueTo(ex);
        } catch (final UncheckedIOException ex) {
            final Throwable cause = new RootCauseOf(ex).exception();
            if (cause instanceof IllegalYmlFormatException) {
                termination.dueTo((IllegalYmlFormatException) cause);
            } else {
                termination.dueTo(ex);
            }
        }
    }

}
