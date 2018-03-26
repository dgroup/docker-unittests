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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.cactoos.BiFunc;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.Sorted;
import org.cactoos.list.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.dgroup.dockertest.text.Joined;
import org.dgroup.dockertest.text.PlainText;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * Represents yml tag {@code /tests/test/output}.
 * Tag can contain list of predicates contains, equals, startsWith, endsWith.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class YmlTagOutput extends
    YmlTagEnvelope<List<YmlTagOutputPredicate>> {

    /**
     * Ctor.
     * @param yml YML string parsed as map.
     */
    public YmlTagOutput(final List<Map<String, String>> yml) {
        this(
            "output",
            yml,
            new MapOf<>(
                new MapEntry<>("contains", String::contains),
                new MapEntry<>("equal", String::equalsIgnoreCase),
                new MapEntry<>("startsWith", String::startsWith),
                new MapEntry<>("endsWith", String::endsWith),
                new MapEntry<>("matches", String::matches)
            )
        );
    }

    /**
     * Ctor.
     * @param tag Name of YML tag.
     * @param yml YML string parsed as map.
     * @param supported Conditions applicable for output from docker container.
     * @checkstyle IndentationCheck (40 lines)
     */
    public YmlTagOutput(
        final String tag,
        final List<Map<String, String>> yml,
        final Map<String, BiFunc<String, String, Boolean>> supported
    ) {
        super(
            () -> {
                if (yml == null || yml.isEmpty()) {
                    throw new IllegalYmlFormatException(
                        tag,
                        new Joined(new Sorted<>(supported.keySet()), "|").text()
                    );
                }
                return new Mapped<>(
                    cnds -> {
                        final String cnd = cnds.keySet().iterator().next();
                        if (!supported.containsKey(cnd)) {
                            throw new IllegalYmlFormatException(
                                new PlainText(
                                    "Unsupported comparing expression `%s:%s`",
                                    cnd, cnds.get(cnd)
                                )
                            );
                        }
                        return new YmlTagOutputPredicateOf(
                            cnd,
                            cnds.values().iterator().next(),
                            supported.get(cnd)
                        );
                    },
                    new Filtered<>(Objects::nonNull, yml)
                );
            },
            tag
        );
    }

}
