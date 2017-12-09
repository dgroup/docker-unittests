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
package org.dgroup.dockertest.test;

import java.util.List;
import org.cactoos.scalar.Ternary;
import org.dgroup.dockertest.UncheckedCallable;
import org.dgroup.dockertest.UncheckedTernary;
import org.dgroup.dockertest.cmd.Arg;
import org.dgroup.dockertest.docker.DefaultDockerContainer;
import org.dgroup.dockertest.docker.DockerContainerArgs;
import org.dgroup.dockertest.docker.FakeDockerContainer;
import org.dgroup.dockertest.yml.YmlTagOutputPredicate;
import org.dgroup.dockertest.yml.YmlTagTest;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 **/
public final class YmlBasedTest implements Test {
    private final String scenario;
    private final String cmd;
    private final List<YmlTagOutputPredicate> conditions;
    private final UncheckedCallable<String> output;

    public YmlBasedTest(String scenario, List<YmlTagOutputPredicate> conditions, String cmd,
                        UncheckedCallable<String> output) {
        this.scenario = scenario;
        this.conditions = conditions;
        this.cmd = cmd;
        this.output = output;
    }

    public YmlBasedTest(Arg image, YmlTagTest yml) {
        this(
                yml.assume(),
                yml.output(),
                yml.cmd(),
                new UncheckedTernary<>(
                        new Ternary<UncheckedCallable<String>>(
                                image.specified(),
                                () -> new DefaultDockerContainer(
                                        new DockerContainerArgs(
                                                image.value(), yml.cmdAsArray()
                                        ).args()
                                )
                                        .run().text(),
                                /*
                                    @todo #/DEV Assessment is required: Do we need OS dependent fake containers for Unix/Windows systems
                                 */
                                () -> new FakeDockerContainer(yml.cmdAsArray())
                                        .run().text()
                        )
                ).value()
        );
    }

    @Override
    public TestingOutcome execute() {
        return new TestingOutcomeByDefault(
                scenario, cmd, output.call(), conditions
        );
    }
}