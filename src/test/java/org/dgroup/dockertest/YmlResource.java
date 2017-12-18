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
package org.dgroup.dockertest;

import java.io.File;
import org.dgroup.dockertest.text.FormattedTextWithRepeatableArguments;

/**
 * Represents an yml resource available in `src/test/resources/yml/test` dir.
 * Allows to simplify the access to testing resources during unit testing.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class YmlResource {

    /**
     * Path to yml file.
     */
    private final FormattedTextWithRepeatableArguments location;

    /**
     * Ctor.
     *
     * @param name Yml file with tests.
     */
    public YmlResource(final String name) {
        this(
            new FormattedTextWithRepeatableArguments(
                "src{0}test{0}resources{0}yml{0}tests{0}{1}",
                File.separator, name
            )
        );
    }

    /**
     * Ctor.
     *
     * @param location Yml file with tests.
     */
    private YmlResource(final FormattedTextWithRepeatableArguments location) {
        this.location = location;
    }

    /**
     * File with tests.
     *
     * @return Yml file with tests.
     */
    public File file() {
        return new File(this.path());
    }

    /**
     * Path to *.yml file with tests.
     *
     * @return Path to file.
     */
    public String path() {
        return this.location.asString();
    }
}
