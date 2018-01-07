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
package org.dgroup.dockertest.yml.tag.version;

import java.util.Map;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.dgroup.dockertest.yml.tag.YmlTag;
import org.dgroup.dockertest.yml.tag.YmlTagOf;

/**
 * Represents yml tag {@code /version}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class YmlTagVersion {

    /**
     * Single yml tag as object.
     */
    private final YmlTag tag;

    /**
     * Ctor.
     *
     * @param tree Yml object tree loaded from *.yml file with tests.
     */
    public YmlTagVersion(final Map<String, Object> tree) {
        this.tag = new YmlTagOf(tree, "version");
    }

    /**
     * Allows to verify version of *.yml file.
     * For now only version `1` is supported.
     * @throws IllegalYmlFileFormatException in case if tag is null/missing
     *  or has no value.
     */
    public void verify() throws IllegalYmlFileFormatException {
        if (!"1".equals(this.tag.asString())) {
            throw new IllegalArgumentException(
                new PlainFormattedText(
                    "Unsupported version: %s",
                    this.tag.asString()
                ).asString()
            );
        }
    }

}
