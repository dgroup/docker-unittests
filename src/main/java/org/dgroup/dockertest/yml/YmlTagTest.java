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

import java.util.List;
import java.util.Map;
import org.dgroup.dockertest.text.PlainFormattedText;

/**
 * Represents yml tag {@code /tests/test}.
 * Tag can contain {@code assume}, {@code cmd} and {@link YmlTagOutput}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
@SuppressWarnings("PMD")
public final class YmlTagTest {

    /**
     * Yml tag transformed to object.
     */
    private final YmlTag tag;

    /**
     * Ctor.
     *
     * @param yml Tag transformed to object.
     */
    public YmlTagTest(Map<String, Object> yml) {
        this(
            new YmlTag(yml.get("test"), "test")
        );
    }

    /**
     * Ctor.
     *
     * @param tag Yml tag transformed to object.
     */
    private YmlTagTest(YmlTag tag) {
        this.tag = tag;
    }

    /**
     * Yml tag {@code /tests/test/assume}.
     *
     * @return Value for tag {@code assume}
     */
    public String assume() {
        return this.tag.map().get("assume").toString();
    }

    /**
     * Yml tag {@code /tests/test/cmd}.
     *
     * @return Value for tag {@code cmd}
     */
    public String cmd() {
        return this.tag.map().get("cmd").toString();
    }

    /**
     * Yml tag {@code /tests/test/cmd}.
     * For example "java -version" became new String[]{"java", "-version"}.
     *
     * @return Slitted command by spaces.
     */
    public String[] dockerCmdAsArray() {
        return this.cmd().split(" ");
    }

    /**
     * Yml tag {@code /tests/test/output} may have several values.
     *
     * @return All specified values for tag {@code output}
     */
    public List<YmlTagOutputPredicate> output() {
        return new YmlTagOutput(
            (List<Map<String, String>>) this.tag.map().get("output")
        ).conditions();
    }

    @Override
    public String toString() {
        return new PlainFormattedText(
            "tag `%s`, assume `%s`, cmd `%s`, output `%s`",
            this.tag,
            this.assume(),
            this.cmd(),
            this.output().size()
        ).asString();
    }
}
