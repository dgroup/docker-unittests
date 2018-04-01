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
package org.dgroup.dockertest.termination;

import java.io.UncheckedIOException;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.cmd.CmdArgNotFoundException;
import org.dgroup.dockertest.docker.DockerProcessExecutionException;
import org.dgroup.dockertest.test.NoScenariosFoundException;
import org.dgroup.dockertest.test.TestingFailedException;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.text.Splitted;
import org.dgroup.dockertest.text.TextOf;
import org.dgroup.dockertest.text.highlighted.YellowText;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * For common application abnormal termination cases.
 *
 * The error code is required when the app is invoked from shell scripts:
 *  - {@code -1} testing failed;
 *  - {@code -2} yml file has unsupported/incorrect format;
 *  - {@code -3} required cmd arguments wasn't specified;
 *  - {@code -4} runtime exception happens on docker side;
 *  - {@code -5} runtime exception happens.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (200 lines)
 */
public final class AbnormalTermination implements Termination {

    /**
     * Represent current application process.
     */
    private final Runtime runtime;
    /**
     * Standard output for application progress.
     */
    private final StdOutput std;
    /**
     * Application arguments specified by user.
     */
    private final Args args;

    /**
     * Ctor.
     * @param std Standard output for application progress.
     * @param args Application arguments specified by user.
     */
    public AbnormalTermination(final StdOutput std, final Args args) {
        this(new RuntimeOf(), std, args);
    }

    /**
     * Ctor.
     * @param runtime Represent current application process.
     * @param std Standard output for application progress.
     * @param args Application arguments specified by user.
     */
    public AbnormalTermination(
        final Runtime runtime,
        final StdOutput std,
        final Args args
    ) {
        this.runtime = runtime;
        this.std = std;
        this.args = args;
    }

    @Override
    public void dueTo(final TestingFailedException exp) {
        this.runtime.shutdownWith(-1);
    }

    @Override
    public void dueTo(final NoScenariosFoundException exp) {
        this.std.print(
            new TextOf(
                "%s testing scenarios found.", new YellowText(0)
            ).text()
        );
    }

    @Override
    public void dueTo(final CmdArgNotFoundException exp) {
        this.std.print(exp.getMessage());
        this.runtime.shutdownWith(-3);
    }

    @Override
    public void dueTo(final DockerProcessExecutionException exp) {
        this.std.print(new Splitted(exp.getMessage(), "\n"));
        this.runtime.shutdownWith(-4);
    }

    @Override
    public void dueTo(final UncheckedIOException exp) {
        this.std.print("App failed due to unexpected runtime exception:", exp);
        this.runtime.shutdownWith(-5);
    }

    @Override
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public void dueTo(final IllegalYmlFormatException exp) {
        try {
            this.std.print(
                new TextOf(
                    "YML file `%s` has the wrong format:",
                    this.args.ymlFilename()
                ).text(),
                new Splitted(exp.getMessage(), "\n").toArray()
            );
            this.runtime.shutdownWith(-2);
        } catch (final CmdArgNotFoundException exception) {
            // @checkstyle MethodBodyCommentsCheck (3 lines)
            // Ignored, as this method should be invoked when IYFE occurs.
            // It means that YML file (specified by user) has wrong format.
            // As the result, we are able to extract file name at this time.
        }
    }

}
