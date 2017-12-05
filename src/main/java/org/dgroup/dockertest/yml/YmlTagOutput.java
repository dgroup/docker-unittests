/*
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
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
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

import org.cactoos.list.Mapped;
import org.dgroup.dockertest.text.PlainFormattedText;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 **/
public final class YmlTagOutput {

    private final List<Map<String, String>> tag;

    public YmlTagOutput(List<Map<String, String>> yml) {
        this.tag = yml;
    }

    public List<YmlTagOutputPredicate> conditions() {
        return new Mapped<>(
                tag,
                conditions -> {
                    String condition = conditions.keySet().iterator().next();
                    String expectedText = conditions.values().iterator().next();

                    if ("contains".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("contains", expectedText, actual -> actual.contains(expectedText));

                    if ("equal".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("equal", expectedText, actual -> actual.equals(expectedText));

                    if ("startWith".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("startWith", expectedText, actual -> actual.startsWith(expectedText));

                    if ("endWith".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("endWith", expectedText, actual -> actual.endsWith(expectedText));

                    throw new IllegalYmlFormatException(
                            new PlainFormattedText(
                                    "Tag `output` has unsupported condition: `%s`. Supported values `contains`, `equal`, `startWith`, `endWith`",
                                    condition
                            )
                    );
                }
        );
    }
}