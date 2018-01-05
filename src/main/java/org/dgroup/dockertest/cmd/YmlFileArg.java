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
package org.dgroup.dockertest.cmd;

import java.io.File;
import java.util.List;
import org.dgroup.dockertest.text.FileAsString;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.dgroup.dockertest.yml.YmlString;
import org.dgroup.dockertest.yml.tag.test.YmlTagTest;

/**
 * Represents a command line argument with the yml file with tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class YmlFileArg implements Arg {

    /**
     * Contain yml file with tests.
     */
    private final Arg origin;

    /**
     * Ctor.
     * @param args Command-line arguments are passed to the app by the user.
     */
    public YmlFileArg(final List<String> args) {
        this(
            new SingleArg(
                "-f", args, "YML file with tests wasn't specified."
            )
        );
    }

    /**
     * Ctor.
     * @param origin Yml file with tests passed to app by user with key "-f".
     */
    private YmlFileArg(final Arg origin) {
        this.origin = origin;
    }

    /**
     * The filename should be specified by the user with key "-f".
     * @return Yml file with tests.
     * @throws CmdArgNotFoundException in case if filename
     *  wasn't specified by user.
     */
    public File file() throws CmdArgNotFoundException {
        return new File(this.filename());
    }

    /**
     * The filename should be specified by the user with key "-f".
     * @return Yml file with tests.
     * @throws CmdArgNotFoundException in case if filename
     *  wasn't specified by user.
     */
    public String filename() throws CmdArgNotFoundException {
        return this.origin.value();
    }

    @Override
    public String name() {
        return this.origin.name();
    }

    @Override
    public String value() throws CmdArgNotFoundException {
        return new FileAsString(this.file())
            .content();
    }

    @Override
    public boolean specifiedByUser() {
        return this.origin.specifiedByUser();
    }

    /**
     * Return all yml `test` tags defined in YML file.
     *
     * @return Defined YML `test` tags.
     * @throws CmdArgNotFoundException in case if YML file
     *  wasn't specified by user.
     * @throws IllegalYmlFileFormatException in case if YML file
     *  has wrong/corrupted format.
     */
    public List<YmlTagTest> testsYmlTags() throws CmdArgNotFoundException,
        IllegalYmlFileFormatException {
        return new YmlString(this.value()).asTests();
    }
}
