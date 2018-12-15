/**
 * MIT License
 *
 * Copyright (c) 2017-2018 Yurii Dubinka
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
import com.github.dgroup.dockertest.yml.tag.TgSetup;
import com.github.dgroup.dockertest.yml.tag.TgTests;
import com.github.dgroup.dockertest.yml.tag.TgVersion;
import java.util.Collection;
import java.util.Map;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.yaml.snakeyaml.Yaml;

/**
 * Transform *.yml file with tests to collection of {@link TgTest}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1
 */
public final class YmlTagsOf implements YmlTags {

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
    public YmlTagsOf(final TextFile yml) {
        this.tree = new StickyScalar<>(
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
        );
    }

    /**
     * Load string with tests as yml tree.
     * @return The YML tree.
     * @throws IllegalYmlFormatException in case if YML file has
     *  wrong/corrupted/unsupported format.
     * @checkstyle IllegalCatchCheck (10 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public String raw() throws IllegalYmlFormatException {
        try {
            return this.tree.value();
        } catch (final Exception exp) {
            throw new IllegalYmlFormatException(exp);
        }
    }

    @Override
    public TgVersion version() throws IllegalYmlFormatException {
        return new TgVersion(this.raw());
    }

    @Override
    public TgSetup setup() throws IllegalYmlFormatException {
        return new TgSetup(this.raw());
    }

    @Override
    public Collection<TgTest> tests() throws IllegalYmlFormatException {
        return new TgTests(this.raw()).value();
    }

}
