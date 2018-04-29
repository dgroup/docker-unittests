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

import java.util.List;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.docker.DockerProcessExecutionException;
import org.dgroup.dockertest.docker.process.SystemUnixDockerProcess;

/**
 * Allows to run particular tests within installed docker only.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class ExecuteWithinInstalledDocker implements Scalar<Boolean> {

    /**
     * The condition that docker is installed to the env.
     */
    private final Scalar<Boolean> cnd;

    /**
     * Ctor.
     * @checkstyle ReturnCountCheck (20 lines)
     */
    public ExecuteWithinInstalledDocker() {
        this(
            new StickyScalar<>(
                () -> {
                    try {
                        final List<String> output = new SystemUnixDockerProcess(
                            "docker", "info"
                        ).execute().byLines();
                        return new Or(
                            (Func<String, Boolean>) line -> line.contains(
                                "Server Version:"
                            ),
                            output
                        ).value();
                    } catch (final DockerProcessExecutionException exp) {
                        return false;
                    }
                }
            )
        );
    }

    /**
     * Ctor.
     * @param cnd The condition that docker is installed to the env.
     */
    public ExecuteWithinInstalledDocker(final Scalar<Boolean> cnd) {
        this.cnd = cnd;
    }

    @Override
    public Boolean value() {
        return new UncheckedScalar<>(this.cnd).value();
    }
}
