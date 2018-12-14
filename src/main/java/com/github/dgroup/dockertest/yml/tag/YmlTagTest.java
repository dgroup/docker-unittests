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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import java.util.List;

/**
 * Represents yml tag {@code /tests/test}.
 * Tag can contain {@code assume}, {@code cmd} and {@link YmlTagOutput}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface YmlTagTest extends Tag {

    /**
     * Name of testing scenario.
     * Exported from `assume` section {@code /tests/test/assume} for each test
     * defined in *.yml file.
     *
     * @return The value for tag {@code /tests/test/assume}
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    String assume() throws IllegalYmlFormatException;

    /**
     * Command for execution in docker container.
     * Exported from `cmd` section {@code /tests/test/cmd} for each test
     * defined in *.yml file.
     *
     * @return The value for tag {@code /tests/test/cmd}
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    String cmd() throws IllegalYmlFormatException;

    /**
     * Command for execution in docker container as array.
     * Exported from `cmd` section {@code /tests/test/cmd} for each test
     * defined in *.yml file.
     *
     * @return The splitted docker command by the space.
     *  For example "java -version" became new String[]{"java", "-version"}.
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    String[] containerCommandAsArray() throws IllegalYmlFormatException;

    /**
     * List of expected conditions, which should be applied to output.
     * Exported from `output` section {@code /tests/test/output} for each test
     * defined in *.yml file. Tag may have several values.
     *
     * @return All specified values for tag {@code output}
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    List<YmlTagOutputPredicate> output() throws IllegalYmlFormatException;

    /**
     * Fake instance for unit testing purposes.
     * @checkstyle JavadocMethodCheck (10 lines)
     * @checkstyle JavadocVariableCheck (10 lines)
     */
    final class Fake implements YmlTagTest {

        private final String scenario;
        private final String command;
        private final List<YmlTagOutputPredicate> expected;

        public Fake(
            final String scenario,
            final String cmd,
            final List<YmlTagOutputPredicate> expected
        ) {
            this.scenario = scenario;
            this.command = cmd;
            this.expected = expected;
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
            return this.expected;
        }
    }

}
