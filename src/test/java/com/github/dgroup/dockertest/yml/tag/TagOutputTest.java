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
import com.github.dgroup.dockertest.hamcrest.True;
import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import com.github.dgroup.dockertest.yml.TgOutput;
import com.github.dgroup.dockertest.yml.tag.output.TgPredicateFake;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;

/**
 * Unit tests for class {@link TgOutput}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LineLength (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyMethods"})
public final class TagOutputTest {

    @Test
    public void matchesRegexpSmoke() {
        final String version =
            "curl 7.57.0 (x86_64-pc-linux-gnu) libcurl/7.57.0 OpenSSL/1.0.2m " +
                "zlib/1.2.8 libidn2/2.0.4 libpsl/0.19.1 (+libidn2/2.0.4) " +
                "libssh2/1.8.0 nghttp2/1.28.0 librtmp/2.3\n" +
                "Release-Date: 2017-11-29\n" +
                "Protocols: dict file ftp ftps gopher http https imap imaps " +
                "ldap ldaps pop3 pop3s rtmp rtsp scp sftp smb smbs smtp smtps" +
                " telnet tftp \n" +
                "Features: AsynchDNS IDN IPv6 Largefile GSS-API Kerberos " +
                "SPNEGO NTLM NTLM_WB SSL libz TLS-SRP HTTP2 UnixSockets " +
                "HTTPS-proxy PSL \n";
        MatcherAssert.assertThat(
            version.matches(
                "^curl\\s7.*\\n.*\\nProtocols.+ftps.+https.+telnet.*\\n.*\\n$"
            ),
            new True()
        );
    }

    @Test
    public void tagOutputHasOneWrongChild() {
        new Assert().hasRootCause(
            () -> new YmlResource("tag-output-has-one-wrong-child.yml")
                .scenarios().iterator().next().output(),
            IllegalYmlFormatException.class,
            "Tag `output` has missing required " +
                "child tag `contains|endsWith|equal|matches|startsWith`"
        );
    }

    @Test
    public void tagOutputHasNoDefinedChildren() {
        new Assert().thatThrowableMessageEndingWith(
            () -> new YmlResource("tag-output-has-no-children.yml")
                .scenarios().iterator().next().output(),
            "IllegalYmlFormatException: Tag `output` has missing required " +
                "child tag `contains|endsWith|equal|matches|startsWith`"
        );
    }

    @Test
    public void tagOutputHasUnsupportedChild() {
        new Assert().hasRootCauseMatched(
            () -> new YmlResource("tag-output-has-unsupported-child.yml")
                .scenario(1),
            IllegalYmlFormatException.class,
            String.format(
                "^.*(tag-output-has-unsupported-child.yml).*%n.*" +
                    "(Unable\\sto\\sfind\\sproperty\\s'containsss').*$"
            )
        );
    }

    @Test
    public void tagOutputHasAllNecessaryYmlPredicates() throws IllegalYmlFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/test[2]/output` has 4 statements",
            new YmlResource("with-3-simple-tests.yml").scenario(2).output()
                .value(),
            IsCollectionWithSize.hasSize(4)
        );
    }

    @Test
    public void tagOutputHasValidPredicates() throws IllegalYmlFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/2/output` has valid structure",
            new YmlResource("with-3-simple-tests.yml").scenario(2).output(),
            new TgOutput.Is(
                new TgPredicateFake("startsWith", "v8."),
                new TgPredicateFake("endsWith", ".5.0"),
                new TgPredicateFake("contains", "v8.5.0"),
                new TgPredicateFake("contains", ".0")
            )
        );
    }

    @Test
    public void tagOutputMatches() throws IllegalYmlFormatException {
        MatcherAssert.assertThat(
            "Tag `tests/3/output` has valid structure",
            new YmlResource("with-3-simple-tests.yml").scenario(3).output(),
            new TgOutput.Is(
                new TgPredicateFake(
                    "matches",
                    "^\\W+|.*\n.*\nProtocols.+ftps.+https.+telnet.*\n.*$"
                )
            )
        );
    }

    @Test
    public void tagOutputMatchesHasExpectedValue() throws IllegalYmlFormatException {
        final String version = "curl 7.57.0 (x86_64-pc-linux-gnu) " +
            "libcurl/7.57.0 OpenSSL/1.0.2m zlib/1.2.8 libidn2/2.0.4 " +
            "libpsl/0.19.1 (+libidn2/2.0.4) libssh2/1.8.0 nghttp2/1.28.0 " +
            "librtmp/2.3\n" +
            "Release-Date: 2017-11-29\n" +
            "Protocols: dict file ftp ftps gopher http https imap imaps ldap " +
            "ldaps pop3 pop3s rtmp rtsp scp sftp smb smbs smtp smtps telnet " +
            "tftp \n" +
            "Features: AsynchDNS IDN IPv6 Largefile GSS-API Kerberos SPNEGO " +
            "NTLM NTLM_WB SSL libz TLS-SRP HTTP2 UnixSockets HTTPS-proxy PSL ";
        MatcherAssert.assertThat(
            "Tag `tests/test[3]/output` has 4th statement `matches`" +
                " and match regexp expression.",
            new YmlResource("with-3-simple-tests.yml").scenario(3).output()
                .value().iterator().next()
                .test(version),
            new True()
        );
    }
}
