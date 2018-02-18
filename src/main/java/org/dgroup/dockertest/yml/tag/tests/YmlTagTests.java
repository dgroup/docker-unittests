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
package org.dgroup.dockertest.yml.tag.tests;

import java.util.List;
import java.util.Map;
import org.cactoos.iterable.Filtered;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;
import org.dgroup.dockertest.yml.tag.YmlTag;
import org.dgroup.dockertest.yml.tag.YmlTagOf;
import org.dgroup.dockertest.yml.tag.test.YmlTagTest;
import org.dgroup.dockertest.yml.tag.test.YmlTagTestOf;

/**
 * Represents yml tag {@code /tests}.
 * Tag can contain list of {@link YmlTagTestOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class YmlTagTests {

    /**
     * Single yml tag as object.
     */
    private final YmlTag tag;

    /**
     * Ctor.
     * @param tree Yml object tree loaded from *.yml file with tests.
     */
    public YmlTagTests(final Map<String, Object> tree) {
        this(new YmlTagOf(tree, "tests"));
    }

    /**
     * Ctor.
     * @param tag Yml object tree loaded from *.yml file with tests.
     */
    public YmlTagTests(final YmlTag tag) {
        this.tag = tag;
    }

    /**
     * Give all yml `test` tags defined in YML file.
     * @return List of tests to be executed.
     * @throws IllegalYmlFileFormatException in case if yml file has
     *  illegal/wrong format.
     */
    @SuppressWarnings("unchecked")
    public List<YmlTagTest> asList() throws IllegalYmlFileFormatException {
        return new ListOf<>(
            new Filtered<>(
                t -> !t.output().isEmpty(),
                new Mapped<>(
                    YmlTagTestOf::new,
                    new Mapped<>(
                        test -> (Map<String, Object>) test,
                        this.tag.asList()
                    )
                )
            )
        );
    }

}
