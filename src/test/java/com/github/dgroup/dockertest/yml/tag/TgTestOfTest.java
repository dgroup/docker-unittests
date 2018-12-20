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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.Assert;
import com.github.dgroup.dockertest.YmlResource;
import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import com.github.dgroup.dockertest.yml.TgOutput;
import com.github.dgroup.dockertest.yml.tag.output.TgPredicateFake;
import com.github.dgroup.dockertest.yml.tag.test.TgTestOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link TgTestOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle RegexpMultilineCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class TgTestOfTest {

    @Test
    public void tagTestsHasOneWronglyDefinedChild()
        throws IllegalYmlFormatException {
        MatcherAssert.assertThat(
            new YmlResource("tag-tests-has-one-wrong-child.yml").scenarios(),
            Matchers.hasSize(0)
        );
    }

    @Test
    public void tagOutputStartsWith() throws IllegalYmlFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[1]/output` has 1st statement `startsWith`",
            new YmlResource("with-single-test.yml").scenario(1)
                .output().value().iterator().next().comparingType(),
            Matchers.equalTo("startsWith")
        );
    }

    @Test
    public void tagTestHasMissingAssumeTagIsolated() {
        new Assert().thatThrows(
            () -> new TgTestOf(
                new TagOf<>(null, "assume"), null, null
            ).assume(),
            new IllegalYmlFormatException(
                "The tag `assume` is missing or has incorrect structure"
            )
        );
    }

    @Test
    public void escapedSymbols() throws IllegalYmlFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/3/output` loaded despite on escaped symbols",
            new YmlResource("with-escaped-symbols-in-3-tests.yml").scenario(3)
                .output(),
            new TgOutput.Is(
                new TgPredicateFake(
                    "matches",
                    "^.*Protocols.+ftps.+https.+telnet.*$"
                ),
                new TgPredicateFake("contains", "Protocols: \\{")
            )
        );
    }

    @Test
    public void tagTestHasMissingAssumeTag() {
        new Assert().thatThrows(
            () -> new YmlResource("tag-test-has-missing-assume-tag.yml")
                .scenario(1).assume(),
            new IllegalYmlFormatException(
                "The tag `assume` is missing or has incorrect structure"
            )
        );
    }

    @Test
    public void tagTestHasMissingCmdTag() {
        new Assert().thatThrows(
            () -> new YmlResource("tag-test-has-missing-cmd-tag.yml")
                .scenario(1).cmd(),
            new IllegalYmlFormatException(
                "The tag `cmd` is missing or has incorrect structure"
            )
        );
    }

}
