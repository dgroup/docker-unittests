/*
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
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
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


import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.YmlBasedTest;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.StdOutput;
import org.dgroup.dockertest.yml.YmlTestsOf;
import java.util.List;

/**
 * Represents application command-line arguments.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmai.com)
 * @version $Id$
 * @since 0.1.0
 **/
public final class Args {

    private final List<String> arguments;

    public Args(String... arguments) {
        this.arguments = new ListOf<>(arguments);
    }

    public Iterable<Test> tests() {
        return new Mapped<>(
                new YmlTestsOf(
                        new InputOf(
                                new FileArg(arguments).file()
                        )
                ),
                ymlTagTest -> new YmlBasedTest(
                        new Arg("-i", arguments),
                        ymlTagTest
                )
        );
    }

    public Iterable<Output> outputs() {
        return new IterableOf<>(new StdOutput());
    }
}