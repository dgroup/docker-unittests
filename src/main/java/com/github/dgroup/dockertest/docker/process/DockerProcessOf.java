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
package com.github.dgroup.dockertest.docker.process;

// @checkstyle ImportCohesionCheck (2 lines)
// @checkstyle ImportOrderCheck (10 lines)

import com.github.dgroup.dockertest.docker.DockerProcessExecutionException;
import com.github.dgroup.dockertest.docker.output.TextCmdOutput;
import java.io.IOException;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.WaitContainerResultCallback;

/**
 * Docker container process based on testcontainers.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class DockerProcessOf extends DockerProcessEnvelope {

    /**
     * Ctor.
     * @param img Docker image for testing
     * @param cmd Command to be executed within container
     */
    public DockerProcessOf(final String img, final String... cmd) {
        super(() -> {
            try (final DockerClient client = DockerClientBuilder.getInstance()
                .build()
            ) {
                final CreateContainerResponse container = client
                    .createContainerCmd(img)
                    .withCmd(cmd)
                    .exec();
                final int timestamp = (int) (System.currentTimeMillis() / 1000);
                client.startContainerCmd(container.getId())
                    .exec();
                client.waitContainerCmd(container.getId())
                    .exec(new WaitContainerResultCallback())
                    .awaitCompletion();
                final LogCallback logs = new LogCallback();
                client.logContainerCmd(container.getId())
                    .withStdErr(true)
                    .withStdOut(true)
                    .withSince(timestamp)
                    .exec(logs);
                logs.awaitCompletion();
                return new TextCmdOutput(logs.toString());
            } catch (final IOException | InterruptedException exp) {
                throw new DockerProcessExecutionException(exp);
            }
        });
    }
}
