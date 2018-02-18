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
package org.dgroup.dockertest.yml.tag.output;

import java.util.List;
import java.util.Map;
import org.cactoos.func.UncheckedBiFunc;
import org.cactoos.list.Mapped;
import org.cactoos.list.Sorted;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.dgroup.dockertest.text.Joined;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.dgroup.dockertest.yml.tag.YmlTag;

/**
 * Represents yml tag {@code /tests/test/output}.
 * Tag can contain list of predicates contains, equals, startsWith, endsWith.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class YmlTagOutputOf implements YmlTag {

    /**
     * Yml string parsed as map.
     */
    private final List<Map<String, String>> tag;
    /**
     * Supported conditions.
     */
    private final Map
        <String, UncheckedBiFunc<String, String, Boolean>> supported;

    /**
     * Ctor.
     * @param yml YML string parsed as map.
     */
    public YmlTagOutputOf(final List<Map<String, String>> yml) {
        this(
            yml,
            new MapOf<>(
                new MapEntry<>(
                    "contains", new UncheckedBiFunc<>(String::contains)
                ),
                new MapEntry<>(
                    "equal", new UncheckedBiFunc<>(String::equalsIgnoreCase)
                ),
                new MapEntry<>(
                    "startsWith", new UncheckedBiFunc<>(String::startsWith)
                ),
                new MapEntry<>(
                    "endsWith", new UncheckedBiFunc<>(String::endsWith)
                ),
                new MapEntry<>(
                    "matches", new UncheckedBiFunc<>(String::matches)
                )
            )
        );
    }

    /**
     * Ctor.
     * @param yml YML string parsed as map.
     * @param supported Conditions applicable for output from docker container.
     */
    public YmlTagOutputOf(final List<Map<String, String>> yml,
        final Map<String, UncheckedBiFunc<String, String, Boolean>> supported) {
        this.tag = yml;
        this.supported = supported;
    }

    /**
     * Yml tag {@code /tests/test/output} may have several values.
     *
     * @return All specified values for tag {@code output}
     * @throws IllegalYmlFileFormatException in case if tag is null/missing
     *  or has no value.
     */
    public List<YmlTagOutputPredicate> conditions()
        throws IllegalYmlFileFormatException {
        if (this.tag.isEmpty()) {
            throw new IllegalYmlFileFormatException(
                this,
                new Joined(
                    new Sorted<>(this.supported.keySet()),
                    "|"
                ).asString()
            );
        }
        return new Mapped<>(
            conditions -> {
                final String condition = conditions.keySet().iterator().next();
                return new YmlTagOutputPredicateOf(
                    condition,
                    conditions.values().iterator().next(),
                    this.supported.get(condition)
                );
            },
            this.tag
        );
    }

    @Override
    public String name() {
        return "output";
    }

    @Override
    public Object asObject() {
        return this.tag;
    }
}
