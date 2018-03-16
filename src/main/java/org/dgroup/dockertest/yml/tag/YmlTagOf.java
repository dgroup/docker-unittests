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
package org.dgroup.dockertest.yml.tag;

import java.util.Map;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * Represents single yml tag in *.yml file.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class YmlTagOf implements YmlTag {

    /**
     * YML tags tree.
     */
    private final Map<String, Object> yml;
    /**
     * YML tag name.
     */
    private final String tag;

    /**
     * Ctor.
     * @param yml Yml tags tree loaded from *.yml file with tests.
     * @param tag Yml tag name.
     */
    public YmlTagOf(final Map<String, Object> yml, final String tag) {
        this.yml = yml;
        this.tag = tag;
    }

    @Override
    public String name() {
        return this.tag;
    }

    @Override
    public Object asObject() throws IllegalYmlFileFormatException {
        if (this.yml == null || this.yml.get(this.tag) == null) {
            throw new IllegalYmlFileFormatException(
                "`%s` tag is missing or has incorrect structure", this.tag
            );
        }
        return this.yml.get(this.tag);
    }

}
