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
package org.dgroup.dockertest.cmd;

import java.io.File;
import java.util.List;

/**
 * Represents a commandline cmd for the file with tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @todo #50m/DEV Rewrite class considering {@link Arg} entity
 * @since 0.1.0
 */
public final class FileArg implements Arg {

    private final Arg arg;

    public FileArg(List<String> args) {
        this(new DefaultArg("-f", args));
    }

    private FileArg(Arg arg) {
        this.arg = arg;
    }

    public File file() {
        this.arg.assertThatArgumentWasSpecified();
        return new File(this.arg.value());
    }

    @Override
    public String name() {
        return this.arg.name();
    }

    @Override
    public String value() {
        return this.arg.value();
    }

    @Override
    public boolean specified() {
        return this.arg.specified();
    }
}