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
package org.dgroup.dockertest.cmd;

import java.io.File;
import java.util.List;
import java.util.Set;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.list.StickyList;
import org.dgroup.dockertest.docker.process.DockerProcessOnUnix;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.TestOf;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.text.FileAsString;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.dgroup.dockertest.yml.YmlString;

/**
 * App command-line arguments specified by user.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class Args {

    /**
     * Docker image. Specified by user from shell.
     */
    private final Arg image;
    /**
     * YML file (with tests). Specified by user from shell.
     */
    private final Arg file;
    /**
     * Supported outputs formats. Specified by user from shell.
     */
    private final OutputArg outputs;

    /**
     * Ctor.
     * @param args Command-line arguments specified by user.
     */
    public Args(final String... args) {
        this(new ListOf<>(args));
    }

    /**
     * Ctor.
     * @param args Command-line arguments specified by user.
     */
    public Args(final List<String> args) {
        this(
            new ArgOf("-i", args, "Docker image wasn't specified."),
            new ArgOf("-f", args, "YML file with tests wasn't specified."),
            new OutputArgOf(args)
        );
    }

    /**
     * Ctor.
     * @param image Docker image. Specified by user from shell.
     * @param file YML file (with tests). Specified by user from shell.
     * @param outputs Supported outputs formats. Specified by user from shell.
     */
    public Args(final Arg image, final Arg file, final OutputArg outputs) {
        this.image = image;
        this.file = file;
        this.outputs = outputs;
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
     * @throws IllegalYmlFileFormatException in case if YML file
     *  has wrong/corrupted format.
     */
    public List<Test> tests() throws CmdArgNotFoundException,
        IllegalYmlFileFormatException {
        return new StickyList<>(
            new Mapped<>(
                ymlTagTest -> new TestOf(
                    ymlTagTest.assume(),
                    ymlTagTest.cmd(),
                    ymlTagTest.output(),
                    new DockerProcessOnUnix(
                        new Joined<>(
                            new ListOf<>(
                                "docker", "run", "--rm", this.dockerImage()
                            ),
                            new ListOf<>(ymlTagTest.containerCommandAsArray())
                        )
                    )
                ),
                new YmlString(
                    new FileAsString(
                        new File(
                            this.ymlFilename()
                        )
                    ).content()
                ).asTests()
            )
        );
    }

    /**
     * Supported outputs formats.
     * @return All selected by user outputs.
     */
    public Set<Output> selectedByUserOutput() {
        return this.outputs.asSet();
    }

    /**
     * Name of YML file (with tests).
     *
     * @return YML filename specified by user.
     * @throws CmdArgNotFoundException in case if filename
     *  wasn't specified by user.
     */
    public String ymlFilename() throws CmdArgNotFoundException {
        return this.file.value();
    }
}
