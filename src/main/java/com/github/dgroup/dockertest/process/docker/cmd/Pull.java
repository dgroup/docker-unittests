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
package com.github.dgroup.dockertest.process.docker.cmd;

import com.github.dgroup.dockertest.exception.Stacktrace;
import com.github.dgroup.dockertest.process.DockerProcessExecutionException;
import com.github.dgroup.dockertest.process.docker.DockerProcessEnvelope;
import com.github.dgroup.dockertest.process.output.TextCmdOutput;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.PullImageResultCallback;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Pull the Docker image.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1.1
 */
public final class Pull extends DockerProcessEnvelope {

    /**
     * Ctor.
     * @param img The Docker container image.
     * @param client The java-api client to Docker OS process.
     * @todo #/DEV Avoid timeout in 10 minutes for docker pull process.
     *  Right now the application is waiting 10 minutes for the
     *  <em>docker pull image</em>. Potentially it can be replaced by
     *  {@link ResultCallbackTemplate#awaitCompletion()}, but in this case
     *  the process may hangs forever.
     * @checkstyle MagicNumberCheck (20 lines)
     */
    public Pull(final String img, final DockerClient client) {
        super(() -> {
            try {
                final Collection<String> out = new LinkedList<>();
                client.pullImageCmd(img)
                    .exec(new PullCallback(out))
                    .awaitCompletion(10, TimeUnit.MINUTES);
                return new TextCmdOutput(String.join("", out));
            } catch (final InterruptedException cause) {
                Thread.currentThread().interrupt();
                throw new DockerProcessExecutionException(cause);
            }
        });
    }

    /**
     * Asynchronous callback for the docker command <em>pull</em>.
     */
    private static final class PullCallback extends PullImageResultCallback {

        /**
         * The output for Docker pull process.
         */
        private final Collection<String> out;

        /**
         * Ctor.
         * @param out The output for Docker pull process.
         */
        PullCallback(final Collection<String> out) {
            super();
            this.out = out;
        }

        @Override
        public void onNext(final PullResponseItem item) {
            super.onNext(item);
            this.out.add(item.toString());
        }

        @Override
        public void onError(final Throwable throwable) {
            super.onError(throwable);
            this.out.add(new Stacktrace(throwable).toString());
        }
    }
}
