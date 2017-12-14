/**
 *  MIT License
 *
 *  Copyright (c) 2017 Yurii Dubinka
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package org.dgroup.dockertest.docker;

/**
 * Represents an instance of docker container.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class DefaultDockerContainer implements DockerContainer {

    /**
     * System process associated with docker container.
     */
    private final SystemProcess process;

    /**
     * Ctor.
     * @param cmd Docker container command.
     */
    public DefaultDockerContainer(final DockerContainerCommand cmd) {
        this(new SystemProcess(cmd.args()));
    }

    /**
     * Ctor.
     * @param process System process associated with docker container.
     */
    public DefaultDockerContainer(final SystemProcess process) {
        this.process = process;
    }

    @Override
    public CmdOutput run() {
        return new CmdOutputAsText(process.execute());
    }

}
