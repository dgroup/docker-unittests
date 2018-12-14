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
package com.github.dgroup.dockertest.yml;

import com.github.dgroup.dockertest.text.TextFile;
import com.github.dgroup.dockertest.yml.tag.YmlTagSetup;
import com.github.dgroup.dockertest.yml.tag.YmlTagTest;
import com.github.dgroup.dockertest.yml.tag.YmlTagTests;
import com.github.dgroup.dockertest.yml.tag.YmlTagVersion;
import java.util.List;
import java.util.Map;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.yaml.snakeyaml.Yaml;

/**
 * Transform *.yml file with tests to collection of {@link YmlTagTest}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @todo #/DEV YmlString export to interface with several methods:
 *  - version
 *  - setup
 *  - tests
 *  Each method should return the corresponding YML tag.
 * @todo #/DEV Rename the class to YmlTags.
 */
public final class YmlString {

    /**
     * Yml tags as tree.
     */
    private final Scalar<String> tree;

    /**
     * Ctor.
     * @param yml Tags defined in file with tests as string.
     * @checkstyle IndentationCheck (20 lines)
     * @checkstyle IllegalCatchCheck (25 lines)
     */
    @SuppressWarnings({
        "PMD.PreserveStackTrace", "PMD.AvoidCatchingGenericException"
    })
    public YmlString(final TextFile yml) {
        this(
            new StickyScalar<>(
                () -> {
                    try {
                        final String text = new Yaml()
                            .loadAs(yml.text(), Map.class)
                            .toString();
                        return text.substring(1, text.length() - 1);
                    } catch (final Exception ex) {
                        throw new IllegalYmlFormatException(
                            "YML file `%s` has the wrong format:%n %s",
                            yml.path(), ex.getMessage()
                        );
                    }
                }
            )
        );
    }

    /**
     * Ctor.
     * @param tree YML tags defined in file with tests as string.
     */
    public YmlString(final Scalar<String> tree) {
        this.tree = tree;
    }

    /**
     * Parsed yml tests as list.
     * @return The yml tests.
     * @throws IllegalYmlFormatException in case if YML file has
     *  wrong/corrupted/unsupported format.
     */
    public List<YmlTagTest> asTests() throws IllegalYmlFormatException {
        new YmlTagVersion(
            this.ymlTree()
        ).verify();
        return new YmlTagTests(
            this.ymlTree()
        ).value();
    }

    /**
     * Load string with tests as yml tree.
     * @return The YML tree.
     * @throws IllegalYmlFormatException in case if YML file has
     *  wrong/corrupted/unsupported format.
     * @checkstyle IllegalCatchCheck (10 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public String ymlTree() throws IllegalYmlFormatException {
        try {
            return this.tree.value();
        } catch (final Exception exp) {
            throw new IllegalYmlFormatException(exp);
        }
    }

    /**
     * Load string with setup information.
     *
     * @return The tag.
     * @throws IllegalYmlFormatException in case if YML file has
     *  wrong/corrupted/unsupported format.
     */
    public YmlTagSetup setupTag() throws IllegalYmlFormatException {
        return new YmlTagSetup(this.ymlTree());
    }
}
