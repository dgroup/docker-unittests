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
package com.github.dgroup.dockertest.test;

import com.github.dgroup.dockertest.process.DockerProcess;
import com.github.dgroup.dockertest.process.DockerProcessExecutionException;
import com.github.dgroup.dockertest.test.outcome.TestOutcomeOf;
import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import com.github.dgroup.dockertest.yml.TgTest;
import com.github.dgroup.dockertest.yml.tag.UncheckedTag;
import com.github.dgroup.dockertest.yml.tag.test.UncheckedTgTest;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.cactoos.iterable.Filtered;
import org.cactoos.list.StickyList;

/**
 * Represents YML based implementation for single test.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TestOf implements Test, Callable<TestOutcome> {

    /**
     * Docker container where we need to execute the test.
     */
    private final DockerProcess process;

    /**
     * Origin test to be executed within docker container.
     */
    private final UncheckedTgTest test;

    /**
     * Ctor.
     * @param test Origin test to be executed within docker container.
     * @param proc Docker container where test be executed.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public TestOf(final TgTest test, final DockerProcess proc) {
        this.test = new UncheckedTgTest(test);
        this.process = proc;
    }

    @Override
    public TestOutcome execute() throws DockerProcessExecutionException {
        final String output = this.process.execute().text();
        final Collection<TgOutputPredicate> failed = new StickyList<>(
            new Filtered<>(
                t -> !t.test(output),
                new UncheckedTag<>(this.test.output()).value()
            )
        );
        return new TestOutcomeOf(this.test, output, failed);
    }

    @Override
    public TestOutcome call() throws DockerProcessExecutionException {
        return this.execute();
    }
}
