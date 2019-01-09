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
package com.github.dgroup.dockertest.yml.tag.test;

import com.github.dgroup.dockertest.yml.Tag;
import com.github.dgroup.dockertest.yml.TgOutput;
import com.github.dgroup.dockertest.yml.TgTest;
import com.github.dgroup.dockertest.yml.YmlFormatException;

/**
 * Represents yml tag {@code /tests/test}.
 * Tag can contain {@code assume}, {@code cmd} and {@link TgOutput}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TgTestOf implements TgTest {

    /**
     * Scenario name, <em>assume</em> YML tag.
     */
    private final Tag<String> scenario;

    /**
     * Container command, <em>cmd</em> YML tag.
     */
    private final Tag<String> command;

    /**
     * Container output, <em>output</em> YML tag.
     */
    private final TgOutput out;

    /**
     * Ctor.
     * @param assume Yml tag under `test`.
     * @param cmd Yml tag under `test`.
     * @param out Yml tag under `test`.
     * @checkstyle IndentationCheck (20 lines)
     */
    public TgTestOf(
        final Tag<String> assume, final Tag<String> cmd, final TgOutput out
    ) {
        this.scenario = assume;
        this.command = cmd;
        this.out = out;
    }

    @Override
    public String assume() throws YmlFormatException {
        return this.scenario.value();
    }

    @Override
    public String cmd() throws YmlFormatException {
        return this.command.value();
    }

    @Override
    public TgOutput output() {
        return this.out;
    }
}
