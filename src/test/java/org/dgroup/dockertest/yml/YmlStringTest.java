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
package org.dgroup.dockertest.yml;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import org.dgroup.dockertest.AssertThrown;
import org.dgroup.dockertest.YmlResource;
import org.dgroup.dockertest.yml.tag.test.YmlTagTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Unit tests for class {@link YmlString}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle WhitespaceAroundCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle LineLengthCheck (500 lines)
 * @todo #1:2h Splitting is required because class with unit tests became too huge.
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.TooManyMethods" })
public final class YmlStringTest {

    @Test
    public void tagVersionIsMissing() {
        AssertThrown.assertThrown(
            () -> new YmlString(
                new YmlResource("with-missing-version-tag.yml").asString()
            ).asTests(),
            new IllegalYmlFileFormatException("`version` tag is missing or has incorrect structure")
        );
    }

    @Test
    public void iterator() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tests from file `with-3-simple-tests.yml` loaded as Iterable",
            this.loadTests("with-3-simple-tests.yml"),
            IsCollectionWithSize.hasSize(3)
        );
    }

    @Test
    public void tagAssume() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/assume` equal to `\"node version is 8.5.1\"`",
            this.loadTests("with-3-simple-tests.yml").get(1).assume(),
            IsEqual.equalTo("node version is 8.5.1")
        );
    }

    @Test
    public void tagCmd() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/cmd` is equal to `\"node -v\"`",
            this.loadTests("with-3-simple-tests.yml").get(1).cmd(),
            IsEqual.equalTo("node -v")
        );
    }

    @Test
    public void tagOutputHasAllNecessaryYmlPredicates() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 4 statements",
            this.loadTests("with-3-simple-tests.yml").get(1).output(),
            IsCollectionWithSize.hasSize(4)
        );
    }

    @Test
    public void tagOutputContains() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 1st statement `contains`",
            this.loadTests("with-3-simple-tests.yml").get(1).output().get(0).comparingType(),
            IsEqual.equalTo("contains")
        );
    }

    @Test
    public void tagOutputContainsHasExpectedValue() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 1st statement `contains`" +
                " and expected value is `v8.5.0`",
            this.loadTests("with-3-simple-tests.yml").get(1).output().get(0).test("v8.5.0"),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void tagOutputStartsWith() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 2nd statement `startsWith`",
            this.loadTests("with-3-simple-tests.yml").get(1).output().get(1).comparingType(),
            IsEqual.equalTo("startsWith")
        );
    }

    @Test
    public void tagOutputStartsWithHasExpectedValue() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 2nd statement `startsWith`" +
                " and expected value is `v8.`",
            this.loadTests("with-3-simple-tests.yml").get(1).output().get(1).test("v8."),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void tagOutputEndsWith() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 3rd statement `endsWith`",
            this.loadTests("with-3-simple-tests.yml").get(1).output().get(2).comparingType(),
            IsEqual.equalTo("endsWith")
        );
    }

    @Test
    public void tagOutputEndsWithHasExpectedValue() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 3rd statement `endsWith`" +
                " and expected value is `.5.0`",
            this.loadTests("with-3-simple-tests.yml").get(1).output().get(2).test(".5.0"),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void tagOutputMatches() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[3]/output` has 4th statement `matches`",
            this.loadTests("with-3-simple-tests.yml").get(2).output().get(2).comparingType(),
            IsEqual.equalTo("matches")
        );
    }

    @Test
    public void tagOutputMatchesHasExpectedValue() throws IllegalYmlFileFormatException {
        final String version = "curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57.0 OpenSSL/1.0.2m zlib/1.2.8 libidn2/2.0.4 libpsl/0.19.1 (+libidn2/2.0.4) libssh2/1.8.0 nghttp2/1.28.0 librtmp/2.3\n" +
            "Release-Date: 2017-11-29\n" +
            "Protocols: dict file ftp ftps gopher http https imap imaps ldap ldaps pop3 pop3s rtmp rtsp scp sftp smb smbs smtp smtps telnet tftp \n" +
            "Features: AsynchDNS IDN IPv6 Largefile GSS-API Kerberos SPNEGO NTLM NTLM_WB SSL libz TLS-SRP HTTP2 UnixSockets HTTPS-proxy PSL ";
        MatcherAssert.assertThat(
            "Tag `tests/test[3]/output` has 4th statement `matches`" +
                " and match regexp expression.",
            this.loadTests("with-3-simple-tests.yml")
                .get(2).output().get(2).test(version),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void tagTestsIsMissing() {
        AssertThrown.assertThrown(
            () -> this.loadTests("tag-tests-is-missing.yml"),
            new IllegalYmlFileFormatException(
                "mapping values are not allowed here\n" +
                    " in 'string', line 3, column 9:\n" +
                    "      - test:\n" +
                    "            ^\n"
            )
        );
    }

    @Test
    public void tagTestsHasNoDefinedChildren() {
        AssertThrown.assertThrown(
            () -> this.loadTests("tag-tests-has-no-children.yml"),
            new IllegalYmlFileFormatException(
                "`tests` tag is missing or has incorrect structure"
            )
        );
    }

    @Test
    public void tagTestsHasOneWronglyDefinedChild() throws IllegalYmlFileFormatException {
        MatcherAssert.assertThat(
            "Tag `tests` has 0 children",
            this.loadTests("tag-tests-has-one-wrong-child.yml"),
            Matchers.hasSize(0)
        );
    }

    @Test
    public void tagTestHasMissingAssumeTag() {
        AssertThrown.assertThrown(
            () -> this.loadTests("tag-test-has-missing-assume-tag.yml")
                .iterator().next().assume(),
            new IllegalYmlFileFormatException(
                "Tag `test` has missing required child tag `assume`"
            )
        );
    }

    @Test
    public void tagTestHasMissingCmdTag() {
        AssertThrown.assertThrown(
            () -> this.loadTests("tag-test-has-missing-cmd-tag.yml")
                .iterator().next().cmd(),
            new IllegalYmlFileFormatException(
                "Tag `test` has missing required child tag `cmd`"
            )
        );
    }

    @Test
    public void tagOutputHasOneWrongChild() {
        AssertThrown.assertThrown(
            () -> this.loadTests("tag-output-has-one-wrong-child.yml").iterator().next().output(),
            new UncheckedIOException(
                new IOException(
                    new IllegalYmlFileFormatException(
                        "Tag `output` has missing required child tag `contains|endsWith|equal|matches|startsWith`"
                    )
                )
            )
        );
    }

    @Test
    public void tagOutputHasNoDefinedChildren() {
        AssertThrown.assertThrown(
            () -> this.loadTests("tag-output-has-no-children.yml").iterator().next(),
            new UncheckedIOException(
                new IOException(
                    new IllegalYmlFileFormatException(
                        "Tag `test` has missing required child tag `output`"
                    )
                )
            )
        );
    }

    private List<YmlTagTest> loadTests(final String file) throws IllegalYmlFileFormatException {
        return new YmlString(
            new YmlResource(file).asString()
        ).asTests();
    }

}
