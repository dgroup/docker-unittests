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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dgroup.dockertest.yml.tag.test;

import java.util.List;
import org.dgroup.dockertest.yml.tag.output.YmlTagOutputPredicate;

/**
 * Fake instance of {@link YmlTagTest} for unit testing purposes..
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class FakeYmlTagTest implements YmlTagTest {

    /**
     * Name of testing scenario.
     */
    private final String scenario;
    /**
     * Command for execution in docker container.
     */
    private final String command;
    /**
     * List of expected conditions to be applied to the container output.
     */
    private final List<YmlTagOutputPredicate> predicates;

    /**
     * Ctor.
     * @param scenario Name of testing scenario.
     * @param cmd Command for execution in docker container.
     * @param predicates List of expected conditions to be applied
     *  to the container output.
     */
    public FakeYmlTagTest(final String scenario, final String cmd,
        final List<YmlTagOutputPredicate> predicates) {
        this.scenario = scenario;
        this.command = cmd;
        this.predicates = predicates;
    }

    @Override
    public String assume() {
        return this.scenario;
    }

    @Override
    public String cmd() {
        return this.command;
    }

    @Override
    public String[] containerCommandAsArray() {
        return this.command.split(" ");
    }

    @Override
    public List<YmlTagOutputPredicate> output() {
        return this.predicates;
    }

    @Override
    public String name() {
        return "test";
    }

    @Override
    public Object asObject() {
        return "";
    }

}
