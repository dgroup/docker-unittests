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
package com.github.dgroup.dockertest;

import com.github.dgroup.dockertest.text.TextFile;
import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import com.github.dgroup.dockertest.yml.TgTest;
import com.github.dgroup.dockertest.yml.YmlString;
import com.github.dgroup.dockertest.yml.tag.TgSetup;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Represents an yml resource available in `src/test/resources/yml/test` dir.
 * Allows to simplify the access to testing resources during unit testing.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class YmlResource {

    /**
     * Path to yml file.
     */
    private final Scalar<String> location;
    /**
     * YML file as string.
     */
    private final TextFile text;
    /**
     * The tree of YML objects.
     */
    private final YmlString yml;

    /**
     * Ctor.
     * @param name Yml file with tests which was placed to YML resource dir
     *  {@code src/test/resources/yml/tests}.
     */
    public YmlResource(final String name) {
        this(
            () -> Paths.get("src", "test", "resources", "yml", "tests", name)
                .toFile()
        );
    }

    /**
     * Ctor.
     * @param path Yml file with tests.
     */
    public YmlResource(final Scalar<File> path) {
        this(
            new StickyScalar<>(() -> path.value().getAbsolutePath()),
            new TextFile(path, StandardCharsets.UTF_8)
        );
    }

    /**
     * Ctor.
     * @param path Yml file with tests.
     * @param txt Yml text with tests.
     */
    public YmlResource(final Scalar<String> path, final TextFile txt) {
        this(path, txt, new YmlString(txt));
    }

    /**
     * Ctor.
     * @param path Yml file with tests.
     * @param txt Yml text with tests.
     * @param yml The YML tree.
     */
    public YmlResource(
        final Scalar<String> path, final TextFile txt, final YmlString yml
    ) {
        this.location = path;
        this.text = txt;
        this.yml = yml;
    }

    /**
     * Path to *.yml file with tests.
     * @return Path to file.
     */
    public String path() {
        return new UncheckedScalar<>(this.location).value();
    }

    /**
     * Return yml file as is.
     * @return YML file with tests.
     */
    public TextFile file() {
        return this.text;
    }

    /**
     * Return all tests within yml resource.
     * @return Testing scenarios.
     * @throws IllegalYmlFormatException in case if YML file has
     *  wrong/corrupted/unsupported format.
     */
    public List<TgTest> scenarios() throws IllegalYmlFormatException {
        return this.yml.asTests();
    }

    /**
     * Fetch particular scenario by its number starting from {@code 1...n}.
     *
     * @param pos Scenario number.
     * @return One testing scenario.
     * @throws IllegalArgumentException in case if we can't find the scenarios
     *  due to empty/corrupted YML file.
     */
    public TgTest scenario(final int pos) throws IllegalArgumentException {
        try {
            if (pos < 1 || pos > this.scenarios().size()) {
                throw new IllegalArgumentException(
                    new TextOf("Scenario with '%s' position not found", pos)
                        .text()
                );
            }
            return this.scenarios().get(pos - 1);
        } catch (final IllegalYmlFormatException cause) {
            throw new IllegalArgumentException(cause);
        }
    }

    /**
     * Find the {@code setup} tag within YML file.
     * @return The <em>setup</em> tag.
     * @throws IllegalYmlFormatException in case if YML file has
     *  wrong/corrupted/unsupported format.
     */
    public TgSetup setup() throws IllegalYmlFormatException {
        return this.yml.setupTag();
    }
}
