/**
 * MIT License
 *
 * Copyright (c) 2017 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.dgroup.dockertest.yml;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import org.cactoos.Input;
import org.cactoos.io.InputOf;
import org.cactoos.iterator.Mapped;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.yaml.snakeyaml.Yaml;

/**
 * Comment here].
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class YmlTestsOf implements Iterable<YmlTagTest> {
    /**
     * Comment here}.
     */
    private final String testsastext;

    /**
     * Comment here].
     * @param file Need to {comment here}.
     */
    public YmlTestsOf(final File file) {
        this(new InputOf(file));
    }

    /**
     * Comment here].
     * @param src Need to {comment here}
     */
    public YmlTestsOf(final Input src) {
        this(
                new UncheckedText(
                        new TextOf(src)
                ).asString()
        );
    }

    public YmlTestsOf(String testsastext) {
        this.testsastext = testsastext;
    }

    @Override
    public Iterator<YmlTagTest> iterator() {
        Map<String, Object> ymlTree = new Yaml().load(testsastext);

        YmlTag version = new YmlTag(
                ymlTree.get("version"), "version"
        );
        version.verifyExistence();
        if (!"1".equals(version.asString()))
            throw new IllegalArgumentException(
                    new PlainFormattedText(
                            "Unsupported version: %s",
                            version.asString()
                    ).asString()
            );

        YmlTag tests = new YmlTag(
                ymlTree.get("tests"), "tests"
        );
        tests.verifyExistence();

        return new Mapped<>(
                YmlTagTest::new,
                new Mapped<>(
                        test -> (Map<String, Object>) test,
                        tests.list().iterator()
                )
        );
    }
}
