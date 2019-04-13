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

import com.github.dgroup.dockertest.hamcrest.HasItems;
import com.github.dgroup.dockertest.test.TestingFailedException;
import com.github.dgroup.dockertest.test.output.std.Std;
import com.github.dgroup.dockertest.test.output.std.StdOutput;
import com.github.dgroup.dockertest.text.TextOf;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.cactoos.list.ListOf;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Single unit-testing scenario for docker-unittest application.
 *
 * Most of unit tests related to this project will have the same logic:
 * a) Ensure that docker has installed
 * b) Pull image if absent
 * c) Create container (with instructions)
 * d) Check the content
 * e) Remove the container
 * f) Report the results
 *
 * This object allows to avoid the code-duplication within the unit tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1.1
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class ScenarioOf implements Scenario {

    /**
     * The description of the testing scenario.
     */
    private final String description;

    /**
     * The path to the YAML file with tests.
     */
    private final String path;

    /**
     * The docker image for testing.
     */
    private final String image;

    /**
     * Ctor.
     * @param desc The description of the testing scenario.
     * @param path The path to the YAML file with tests.
     * @param img The docker image for testing.
     */
    public ScenarioOf(final String desc, final String path, final String img) {
        this.description = desc;
        this.path = path;
        this.image = img;
    }

    @Override
    public void execute() throws AppException, TestingFailedException {
        new Assume().that(new DockerWasInstalled());
        final String src = Paths.get(this.path).toFile().getAbsolutePath();
        final Std.Fake std = new Std.Fake(new ArrayList<>(10));
        std.print(new TextOf("File: %s.", src));
        new App(
            new ListOf<>(
                "-f", src,
                "-i", this.image
            ),
            std
        ).start();
        new StdOutput().print(std.details());
        new Assertion<>(
            this.description,
            std::details,
            new HasItems<>("Testing successfully completed.")
        ).affirm();
    }
}
