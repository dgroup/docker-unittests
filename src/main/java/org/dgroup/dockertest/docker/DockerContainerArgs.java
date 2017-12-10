/**
 * MIT License
 *
 * Copyright (c) 2017 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.dgroup.dockertest.docker;

import java.util.List;
import org.cactoos.list.ListOf;

/**
 * Represents arguments for docker container.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class DockerContainerArgs {
    private final String image;
    private final List<String> cmd;

    /**
     * @param image docker image for testing
     * @param cmd   contains command which should be executed inside of container
     *              "java -version" will be split to "java", "-version"
     */
    public DockerContainerArgs(String image, String[] cmd) {
        this.image = image;
        this.cmd = new ListOf<>(cmd);
    }

    /**
     * @return command for execution in array format
     */
    public String[] args() {
        List<String> args = new ListOf<>("docker", "run", "--rm", this.image);
        args.addAll(this.cmd);
        return args.toArray(new String[args.size()]);
    }
}
