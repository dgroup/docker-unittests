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
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.dgroup.dockertest.yml.tag.YmlTag;
import org.dgroup.dockertest.yml.tag.output.YmlTagOutputOf;
import org.dgroup.dockertest.yml.tag.output.YmlTagOutputPredicate;

/**
 * Represents yml tag {@code /tests/test}.
 * Tag can contain {@code assume}, {@code cmd} and {@link YmlTagOutputOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface YmlTagTest extends YmlTag {

    /**
     * Name of testing scenario.
     * Exported from `assume` section {@code /tests/test/assume} for each test
     * defined in *.yml file.
     *
     * @return Value for tag {@code /tests/test/assume}
     * @throws IllegalYmlFileFormatException in case if tag is null/missing
     *  or has no value.
     */
    String assume() throws IllegalYmlFileFormatException;

    /**
     * Command for execution in docker container.
     * Exported from `cmd` section {@code /tests/test/cmd} for each test
     * defined in *.yml file.
     *
     * @return Value for tag {@code /tests/test/cmd}
     * @throws IllegalYmlFileFormatException in case if tag is null/missing
     *  or has no value.
     */
    String cmd() throws IllegalYmlFileFormatException;

    /**
     * Command for execution in docker container as array.
     * Exported from `cmd` section {@code /tests/test/cmd} for each test
     * defined in *.yml file.
     *
     * @return Slitted docker command by spaces.
     *  For example "java -version" became new String[]{"java", "-version"}.
     * @throws IllegalYmlFileFormatException in case if tag is null/missing
     *  or has no value.
     */
    String[] containerCommandAsArray() throws IllegalYmlFileFormatException;

    /**
     * List of expected conditions, which should be applied to output.
     * Exported from `output` section {@code /tests/test/output} for each test
     * defined in *.yml file. Tag may have several values.
     *
     * @return All specified values for tag {@code output}
     * @throws IllegalYmlFileFormatException in case if tag is null/missing
     *  or has no value.
     */
    List<YmlTagOutputPredicate> output() throws IllegalYmlFileFormatException;
}
