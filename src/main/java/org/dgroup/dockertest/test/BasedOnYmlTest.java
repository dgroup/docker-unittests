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
package org.dgroup.dockertest.test;

import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.docker.DockerRuntimeException;
import org.dgroup.dockertest.docker.process.DockerProcess;
import org.dgroup.dockertest.docker.process.DockerProcessOnUnix;
import org.dgroup.dockertest.yml.tag.test.YmlTagTest;

/**
 * Represents YML based implementation for single test.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class BasedOnYmlTest implements Test {

    /**
     * YML tag with testing details.
     */
    private final YmlTagTest test;
    /**
     * Docker container where we need to execute the test.
     */
    private final DockerProcess process;

    /**
     * Ctor.
     * @param image Docker image which can be used for container creation.
     * @param test Single test to be executed in docker container.
     */
    public BasedOnYmlTest(final String image, final YmlTagTest test) {
        this(
            test,
            new DockerProcessOnUnix(
                new Joined<>(
                    new ListOf<>("docker", "run", "--rm", image),
                    new ListOf<>(test.dockerCmdAsArray())
                )
            )
        );
    }

    /**
     * Ctor.
     * @param test Yml test to be executed.
     * @param process Docker container where test be executed.
     */
    public BasedOnYmlTest(final YmlTagTest test, final DockerProcess process) {
        this.test = test;
        this.process = process;
    }

    @Override
    public TestOutcome execute() throws DockerRuntimeException {
        return new SingleTestOutcome(
            this.test,
            this.process.execute().asText()
        );
    }
}
