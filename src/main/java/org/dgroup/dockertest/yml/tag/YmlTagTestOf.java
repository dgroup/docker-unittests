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
import org.cactoos.Scalar;
import org.cactoos.iterable.Filtered;
import org.cactoos.list.StickyList;
import org.dgroup.dockertest.text.PlainText;
import org.dgroup.dockertest.text.SplittedText;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * Represents yml tag {@code /tests/test}.
 * Tag can contain {@code assume}, {@code cmd} and {@link YmlTagOutput}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@SuppressWarnings("PMD")
public final class YmlTagTestOf implements YmlTagTest {

    /**
     * YML tag name.
     */
    private final String tag;
    /**
     * Children YML tags.
     */
    private final Scalar<Map<String, Object>> children;

    /**
     * Ctor.
     * @param yml Yml tag `test` transformed to object.
     */
    public YmlTagTestOf(final Map<String, Object> yml) {
        this(yml, "test");
    }

    /**
     * Ctor.
     * @param yml Yml tag `test` transformed to object.
     * @param tag String representation of YML tag `test`.
     */
    public YmlTagTestOf(final Map<String, Object> yml, final String tag) {
        this(
            tag,
            () -> {
                new StrictYmlTree(tag, yml).verify();
                final Map<String, Object> test =
                    (Map<String, Object>) yml.get(tag);
                new StrictYmlTree("tests", tag, test).verify();
                return test;
            }
        );
    }

    /**
     * Ctor.
     * @param tag String representation of YML tag `test`
     * @param children YML tags.
     */
    public YmlTagTestOf(
        final String tag,
        final Scalar<Map<String, Object>> children
    ) {
        this.tag = tag;
        this.children = children;
    }

    @Override
    public String assume() throws IllegalYmlFormatException {
        return this.child("assume");
    }

    @Override
    public String cmd() throws IllegalYmlFormatException {
        return this.child("cmd");
    }

    @Override
    public String[] containerCommandAsArray() throws IllegalYmlFormatException {
        return new SplittedText(this.cmd()).asArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<YmlTagOutputPredicate> output()
        throws IllegalYmlFormatException {
        // @checkstyle IllegalCatchCheck (20 lines)
        try {
            final String child = "output";
            final List<Map<String, String>> cvalue =
                (List<Map<String, String>>) this.children.value().get(child);
            if (cvalue == null) {
                throw new IllegalYmlFormatException(this.tag, child);
            }
            return new YmlTagOutput(
                new StickyList<>(
                    new Filtered<>(Objects::nonNull, cvalue)
                )
            ).value();
        } catch (final Exception exp) {
            throw new IllegalYmlFormatException(exp);
        }
    }

    /**
     * Print `test` tag details to string.
     * @return Details about `test` tag.
     * @throws IllegalYmlFormatException in case if YML tree is corrupted
     *  or has a wrong/invalid format.
     */
    public String asString() throws IllegalYmlFormatException {
        return new PlainText(
            "tag `%s`, assume `%s`, cmd `%s`, output `%s`",
            this.tag, this.assume(), this.cmd(), this.output().size()
        ).text();
    }

    /**
     * Give child tag value as a string.
     * @param name Of child tag.
     * @return Value of child tag.
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     * @checkstyle IllegalCatchCheck (15 lines)
     */
    private String child(final String name)
        throws IllegalYmlFormatException {
        try {
            final Object child = this.children.value().get(name);
            if (child == null || child.toString().trim().isEmpty()) {
                throw new IllegalYmlFormatException(this.tag, name);
            }
            return child.toString();
        } catch (final Exception exp) {
            throw new IllegalYmlFormatException(exp.getMessage(), exp);
        }
    }
}
