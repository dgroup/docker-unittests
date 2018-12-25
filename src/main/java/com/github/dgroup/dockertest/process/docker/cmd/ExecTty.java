/**
 * MIT License
 *
 * Copyright (c) 2017-2018 Yurii Dubinka
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
package com.github.dgroup.dockertest.process.docker.cmd;

import com.github.dgroup.dockertest.process.DockerProcessExecutionException;
import com.github.dgroup.dockertest.process.docker.DockerProcessEnvelope;
import com.github.dgroup.dockertest.process.output.TextCmdOutput;
import com.github.dgroup.dockertest.text.Splitted;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.cactoos.Text;
import org.cactoos.text.UncheckedText;

/**
 * Execute a command within the Docker container.
 *
 * TTY basically means “a console”. When you are in Windows or Linux and open a
 *  terminal to start interacting with the OS via typing some text and getting
 *  some text responses, that’s TTY. It’s pseudo-TTY in docker because it’s
 *  been a pseudo-TTY since being able to connect to an arbitrary computer
 *  remotely created the need for a common “terminal” experience independent of
 *  what shell the user is running and what the abilities of the physical
 *  device I’m connecting to are.
 * Read more here https://www.quora.com/What-does-the-t-or-tty-do-in-Docker.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1
 */
public final class ExecTty extends DockerProcessEnvelope {

    /**
     * Ctor.
     * @param cmd The command to be executed within the Docker container.
     * @param name The Docker container name.
     * @param client The java-api client to Docker OS process.
     */
    public ExecTty(
        final String cmd, final Text name, final DockerClient client
    ) {
        this(cmd, name, client, false);
    }

    /**
     * Ctor.
     * @param cmd The command to be executed within the Docker container.
     * @param name The Docker container name.
     * @param client The java-api client to Docker OS process.
     * @param tty The "console" interaction mode.
     * @checkstyle IndentationCheck (50 lines)
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public ExecTty(
        final String cmd, final Text name, final DockerClient client,
        final Boolean tty
    ) {
        super(
            () -> {
                try {
                    final OutputStream logs = new ByteArrayOutputStream();
                    client.execStartCmd(
                        client.execCreateCmd(new UncheckedText(name).asString())
                            .withAttachStdout(true)
                            .withAttachStderr(true)
                            .withCmd(
                                new Splitted(cmd, " ").toStringArray()
                            )
                            .exec()
                            .getId()
                    )
                        .withDetach(false)
                        .withTty(tty)
                        .exec(new ExecStartResultCallback(logs, logs))
                        .awaitCompletion();
                    return new TextCmdOutput(logs.toString());
                } catch (final InterruptedException exp) {
                    Thread.currentThread().interrupt();
                    throw new DockerProcessExecutionException(exp);
                }
            }
        );
    }
}
