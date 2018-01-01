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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.cactoos.collection.Filtered;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * Represents single yml tag in *.yml file with tests.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @todo #56:20min Create each package for each yml tag.
 */
public final class YmlTag {

    /**
     * YML tree with tags.
     */
    private final Map<String, Object> yml;
    /**
     * Tag name.
     */
    private final String name;

    /**
     * Ctor.
     * @param yml Yml tree loaded from *.yml file with tests.
     * @param tag Yml tag name.
     */
    public YmlTag(final Map<String, Object> yml, final String tag) {
        this.yml = yml;
        this.name = tag;
    }

    /**
     * Represent tag value as string.
     * @return Value.
     */
    public String asString() {
        return new UncheckedTernary<>(
            this.value() == null,
            () -> "",
            () -> this.value().toString()
        ).value();
    }

    /**
     * Represent tag value as list.
     * @return List.
     */
    @SuppressWarnings("unchecked")
    public List<Object> list() {
        final List<Object> values = (List<Object>) this.value();
        return new UncheckedTernary<List<Object>>(
            values == null || new Filtered<>(Objects::nonNull, values)
                .isEmpty(),
            ListOf::new,
            () -> values
        ).value();
    }

    /**
     * Represent tag value as map.
     * @return Map.
     */
    @SuppressWarnings("unchecked")
    public Map<Object, Object> map() {
        final Map<Object, Object> value = (Map<Object, Object>) this.value();
        return new UncheckedTernary<>(
            value == null, HashMap::new, () -> value
        ).value();
    }

    /**
     * YML tag value.
     * @return Tag value.
     */
    private Object value() {
        if (this.yml == null || this.yml.get(this.name) == null) {
            throw new IllegalYmlFileFormatException(
                "`%s` tag is missing or has incorrect structure", this.name
            );
        }
        return this.yml.get(this.name);
    }

}
