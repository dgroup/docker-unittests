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
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * Represents yml tag {@code /tests/test/output}.
 * Tag can contain list of predicates contains, equals, startWith, endWith.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class YmlTagOutput {

    /**
     * Yml string parsed as map.
     */
    private final List<Map<String, String>> tag;

    /**
     * Ctor.
     *
     * @param yml YML string parsed as map.
     */
    public YmlTagOutput(final List<Map<String, String>> yml) {
        this.tag = yml;
    }

    /**
     * Yml tag {@code /tests/test/output} may have several values.
     *
     * @return All specified values for tag {@code output}
     * @todo #18 Implement masked contains like "Protocols:*http*https*ldap"
     */
    public List<YmlTagOutputPredicate> conditions() {
        return new Mapped<>(
            conditions -> {
                final String condition = conditions.keySet().iterator().next();
                final String expected = conditions.values().iterator().next();
                if ("contains".equalsIgnoreCase(condition)) {
                    return new YmlTagOutputPredicate(
                        "contains", expected,
                        actual -> actual.contains(expected)
                    );
                }
                if ("equal".equalsIgnoreCase(condition)) {
                    return new YmlTagOutputPredicate(
                        "equal", expected,
                        actual -> actual.equals(expected)
                    );
                }
                if ("startWith".equalsIgnoreCase(condition)) {
                    return new YmlTagOutputPredicate(
                        "startWith", expected,
                        actual -> actual.startsWith(expected)
                    );
                }
                if ("endWith".equalsIgnoreCase(condition)) {
                    return new YmlTagOutputPredicate(
                        "endWith", expected,
                        actual -> actual.endsWith(expected)
                    );
                }
                throw new IllegalYmlFormatException(
                    new PlainFormattedText(
                        "Tag `output` has unsupported condition: `%s`.",
                        condition
                    )
                );
            },
            this.tag
        );
    }
}
