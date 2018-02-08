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
package org.dgroup.dockertest.docker.process;

import org.cactoos.list.ListOf;
import org.dgroup.dockertest.docker.DockerImageNotFoundException;
import org.dgroup.dockertest.docker.DockerProcessExecutionException;
import org.dgroup.dockertest.docker.output.CmdOutput;
import org.dgroup.dockertest.docker.output.TextCmdOutput;

/**
 * Represents the {@code docker pull} operation which allows to download image
 * from remote docker repository.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Pull implements DockerProcess {

    /**
     * Docker image which we are going to pull.
     */
    private final String image;
    /**
     * Dedicated docker process for execution of pull operation.
     */
    private final DockerProcess origin;

    /**
     * Ctor.
     * @param image Docker image for pull operation.
     */
    public Pull(final String image) {
        this(
            image,
            new DockerProcessOnUnix(
                new ListOf<>("docker", "pull", image)
            )
        );
    }

    /**
     * Ctor.
     * @param image Docker image for pull operation.
     * @param origin Dedicated docker process for execution of pull operation.
     */
    public Pull(final String image, final DockerProcess origin) {
        this.image = image;
        this.origin = origin;
    }

    @Override
    public CmdOutput execute() throws DockerProcessExecutionException {
        final String output = this.origin.execute().asText();
        if (output.contains("pull access denied")) {
            throw new DockerImageNotFoundException(this.image);
        }
        return new TextCmdOutput(output);
    }

}
