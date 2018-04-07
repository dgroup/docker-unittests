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
package org.dgroup.dockertest.cmd;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.cactoos.list.StickyList;
import org.dgroup.dockertest.docker.process.DockerProcessOf;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.TestOf;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.std.StdOutput;
import org.dgroup.dockertest.text.TextFile;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;
import org.dgroup.dockertest.yml.YmlString;

/**
 * App command-line arguments specified by user.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @todo #/DEV Split class in order to prevent increasing of lines.
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class Args {

    /**
     * Docker image. Specified by user from shell.
     */
    private final Arg<String> image;
    /**
     * Path to YML file (with tests). Specified by user from shell.
     */
    private final Arg<String> file;
    /**
     * Supported outputs formats. Specified by user from shell.
     */
    private final Arg<Set<Output>> outputs;
    /**
     * Timeout for each test.
     */
    private final Arg<Timeout> tmt;
    /**
     * Amount of threads for testing procedure.
     */
    private final Arg<Integer> threads;
    /**
     * Standard output for application progress.
     */
    private StdOutput std;

    /**
     * Ctor.
     * @param std Standard output for application progress.
     * @param args Command-line arguments specified by user.
     */
    public Args(final StdOutput std, final String... args) {
        this(std, new ListOf<>(args));
    }

    /**
     * Ctor.
     * @param std Standard output for application progress.
     * @param args Command-line arguments specified by user.
     */
    public Args(final StdOutput std, final List<String> args) {
        this(
            new ArgOf("-i", args, "Docker image wasn't specified."),
            new ArgOf("-f", args, "YML file with tests wasn't specified."),
            new OutputOf(args),
            new TimeoutPerThread(args),
            new ConcurrentTreads(args),
            std
        );
    }

    /**
     * Ctor.
     * @param image Docker image. Specified by user from shell.
     * @param file YML file (with tests). Specified by user from shell.
     * @param outputs Supported outputs formats. Specified by user from shell.
     * @param tmt Timeout for each test.
     * @param threads Amount of concurrent threads for testing procedure.
     * @param std Standard output for application progress.
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public Args(
        final Arg<String> image,
        final Arg<String> file,
        final Arg<Set<Output>> outputs,
        final Arg<Timeout> tmt,
        final Arg<Integer> threads,
        final StdOutput std
    ) {
        this.image = image;
        this.file = file;
        this.outputs = outputs;
        this.tmt = tmt;
        this.threads = threads;
        this.std = std;
    }

    /**
     * Docker image specified by user from shell.
     *
     * @return Image name.
     * @throws CmdArgNotFoundException in case if argument
     *  wasn't specified by user.
     */
    public String dockerImage() throws CmdArgNotFoundException {
        return this.image.value();
    }

    /**
     * Docker image. Specified by user from shell.
     *
     * @return List of tests to be executed for output from docker container.
     * @throws CmdArgNotFoundException in case if argument
     *  wasn't specified by user.
     * @throws IllegalYmlFormatException in case if YML file
     *  has wrong/corrupted format.
     */
    public List<Test> tests() throws CmdArgNotFoundException,
        IllegalYmlFormatException {
        return new StickyList<>(
            new Mapped<>(
                ymlTagTest -> new TestOf(
                    ymlTagTest.assume(),
                    ymlTagTest.cmd(),
                    ymlTagTest.output(),
                    new DockerProcessOf(
                        this.dockerImage(),
                        ymlTagTest.containerCommandAsArray()
                    )
                ),
                new YmlString(
                    new TextFile(this.ymlFilename())
                ).asTests()
            )
        );
    }

    /**
     * Supported outputs formats.
     * @return All selected by user outputs.
     */
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public Set<Output> selectedByUserOutput() {
        final Set<Output> outs = new LinkedHashSet<>();
        outs.add(this.std);
        try {
            outs.addAll(this.outputs.value());
        } catch (final CmdArgNotFoundException exp) {
            // @checkstyle MethodBodyCommentsCheck (2 lines)
            // Nothing was specified by user.
            // Only default std output will be used instead.
        }
        return outs;
    }

    /**
     * Path to YML file (with tests).
     *
     * @return YML filename specified by user.
     * @throws CmdArgNotFoundException in case if filename
     *  wasn't specified by user.
     */
    public String ymlFilename() throws CmdArgNotFoundException {
        return this.file.value();
    }

    /**
     * Amount of threads for testing procedure.
     * @return Threads number.
     * @checkstyle MagicNumberCheck (10 lines)
     */
    public int concurrentThreads() {
        Integer trds;
        try {
            trds = this.threads.value();
        } catch (final CmdArgNotFoundException exp) {
            trds = 8;
        }
        return trds;
    }

    /**
     * Standard application output.
     * @return Stdout.
     */
    public StdOutput standardOutput() {
        return this.std;
    }

    /**
     * Timeout for each test.
     * @return Timeout.
     * @todo #/DEV Move timeout configuration to yml file in order to define
     *  unique timeout for each test.
     */
    public Timeout timeoutPerThread() {
        Timeout timeout;
        try {
            timeout = this.tmt.value();
        } catch (final CmdArgNotFoundException exp) {
            timeout = new TimeoutOf(2, TimeUnit.MINUTES);
        }
        return timeout;
    }

}
