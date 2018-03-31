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
import org.cactoos.iterable.Filtered;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.text.Between;
import org.dgroup.dockertest.text.Splitted;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * Represents yml tag {@code /tests}.
 * Tag can contain list of {@link YmlTagTestOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class YmlTagTests extends YmlTagEnvelope<List<YmlTagTest>> {

    /**
     * Ctor.
     * @param tree Yml object tree loaded from *.yml file with tests.
     * @checkstyle IndentationCheck (40 lines)
     * @checkstyle CascadeIndentationCheck (40 lines)
     */
    public YmlTagTests(final String tree) {
        super(() -> {
                if ("version=1, tests=null".equals(tree)) {
                    throw new IllegalYmlFormatException(
                        "`tests` tag has incorrect structure"
                    );
                }
                if ("version=1, tests=[null]".equals(tree)) {
                    throw new IllegalYmlFormatException(
                        "`tests` tag has no defined children"
                    );
                }
                return new ListOf<>(
                    new Filtered<>(
                        t -> !t.output().isEmpty(),
                        new Mapped<>(
                            test -> new YmlTagTestOf(test::text),
                            new Splitted(
                                new Between(tree, ", tests=[").last("]"),
                                "}}, "
                            )
                        )
                    )
                );
            },
            "tests"
        );
    }

}
