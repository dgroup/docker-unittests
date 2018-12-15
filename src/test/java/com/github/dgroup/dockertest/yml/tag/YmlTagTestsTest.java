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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.Assert;
import com.github.dgroup.dockertest.YmlResource;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests for class {@link YmlTagTests}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @todo #/DEV tagTestsIsMissing: Unstable OS dependent test.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class YmlTagTestsTest {

    @Test @Ignore("OS dependent test")
    public void tagTestsIsMissing() {
        new Assert().thatThrowableMessageEndingWith(
            () -> new YmlResource("tag-tests-is-missing.yml").scenarios(),
            "tag-tests-is-missing.yml` has the wrong format:\n" +
                " mapping values are not allowed here\n" +
                " in 'string', line 3, column 9:\n" +
                "      - test:\n" +
                "            ^\n"
        );
    }

    @Test
    public void tagTestsHasNoDefinedChildren() {
        new Assert().thatThrowableMessageEndingWith(
            () -> new YmlResource("tag-tests-has-no-children.yml")
                .scenario(1)
                .assume(),
            "IllegalYmlFormatException: " +
                "`tests` tag has incorrect structure"
        );
    }

}
