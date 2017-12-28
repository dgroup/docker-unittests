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
package org.dgroup.dockertest.yml.tag;

import java.util.List;
import java.util.Map;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * Represents single yml tag in *.yml file with tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class YmlTag {

    /**
     * Single yml tag as object.
     */
    private final Object tag;
    /**
     * Tag name.
     */
    private final String name;

    /**
     * Ctor.
     * @param tree Yml tree loaded from *.yml file with tests.
     * @param tag Yml tag name.
     */
    public YmlTag(final Map<String, Object> tree, final String tag) {
        this(
            tree.get(tag),
            tag
        );
    }

    /**
     * Ctor.
     * @param yml Yml tree loaded from *.yml file with tests.
     * @param name Yml tag name.
     */
    public YmlTag(final Object yml, final String name) {
        this.tag = yml;
        this.name = name;
    }

    /**
     * Check that tag is exists in *.yml file with tests.
     * @throws IllegalYmlFileFormatException in case if *.yml file
     *  has missing tag.
     */
    public void verifyExistence() {
        if (this.tag == null) {
            throw new IllegalYmlFileFormatException(
                new PlainFormattedText(
                    "`%s` tag is missing or has incorrect structure", this.name
                )
            );
        }
    }

    /**
     * Represent tag value as string.
     * @return Value.
     */
    public String asString() {
        return new UncheckedTernary<>(
            this.tag == null,
            () -> "",
            () -> this.tag.toString()
        ).value();
    }

    /**
     * Represent tag value as list.
     * @return List.
     */
    public List<Object> list() {
        return (List<Object>) this.tag;
    }

    /**
     * Represent tag value as map.
     * @return Map.
     */
    public Map<Object, Object> map() {
        return (Map<Object, Object>) this.tag;
    }

}
