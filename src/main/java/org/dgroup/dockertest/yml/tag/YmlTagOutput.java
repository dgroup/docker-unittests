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
import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.list.Mapped;
import org.cactoos.list.Sorted;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.dgroup.dockertest.text.Before;
import org.dgroup.dockertest.text.Between;
import org.dgroup.dockertest.text.Joined;
import org.dgroup.dockertest.text.Splitted;
import org.dgroup.dockertest.text.TextOf;
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
     * @param yml Output tag.
     */
    public YmlTagOutput(final YmlTag<String> yml) {
        this(yml::value);
    }

    /**
     * Ctor.
     * @param yml Output tag.
     */
    public YmlTagOutput(final Scalar<String> yml) {
        this(
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
     * @param yml Output tag.
     * @param supported Conditions applicable for output from docker container.
     * @checkstyle IndentationCheck (40 lines)
     */
    public YmlTagOutput(
        final Scalar<String> yml,
        final Map<String, BiFunc<String, String, Boolean>> supported
    ) {
        super(
            () -> {
                if ("".equals(yml.value()) || yml.value().trim().isEmpty()) {
                    throw new IllegalYmlFormatException(
                        new TextOf(
                            "Tag `output` has missing required child tag `%s`",
                            new Joined(new Sorted<>(supported.keySet()), "|")
                        )
                    );
                }
                return new Mapped<>(
                    cpr -> {
                        final String type = new Before(cpr, "=").text();
                        final String val = new Between(cpr, "=").last("}");
                        if (!supported.containsKey(type)) {
                            throw new IllegalYmlFormatException(
                                new TextOf(
                                    "Unsupported comparing expression `%s:%s`",
                                    type, val
                                )
                            );
                        }
                        return new YmlTagOutputPredicateOf(
                            type, val, supported.get(type)
                        );
                    },
                    new Splitted(yml.value(), "\\}, \\{")
                );
            },
            "output"
        );
    }

}
