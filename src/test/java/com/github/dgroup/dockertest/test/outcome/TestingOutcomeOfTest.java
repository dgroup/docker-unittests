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
package com.github.dgroup.dockertest.test.outcome;

import com.github.dgroup.dockertest.test.TestingFailedException;
import com.github.dgroup.dockertest.test.output.Output;
import com.github.dgroup.dockertest.yml.TgTest;
import com.github.dgroup.dockertest.yml.tag.output.TgOutputOf;
import com.github.dgroup.dockertest.yml.tag.output.TgPredicateFake;
import java.util.Collections;
import org.cactoos.list.ListOf;
import org.junit.Test;

/**
 * Unit tests for class {@link TestingOutcomeOf}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle LineLengthCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class TestingOutcomeOfTest {

    @Test(expected = TestingFailedException.class)
    public void reportTheResults() throws TestingFailedException {
        new TestingOutcomeOf(
            new TestOutcomeOf(
                new TgTest.Fake(
                    "", "",
                    new TgOutputOf(
                        Collections.emptySet(), Collections.emptySet(),
                        Collections.emptySet(), Collections.emptySet(),
                        Collections.emptySet()
                    )
                ),
                "curl 7.54.0 (x86_64-apple-darwin17.0) libcurl/7.54.0 LibreSSL/2.0.20 zlib/1.2.11 nghttp2/1.24.0\n" +
                    "Protocols: dict file ftp ftps gopher http https imap imaps ldap ldaps pop3 pop3s rtsp smb smbs smtp smtps telnet tftp \n" +
                    "Features: AsynchDNS IPv6 Largefile GSS-API Kerberos SPNEGO NTLM NTLM_WB SSL libz HTTP2 UnixSockets HTTPS-proxy \n",
                new ListOf<>(
                    new TgPredicateFake(
                        "startsWith", "curl 8000",
                        input -> input.startsWith("curl 8000")
                    )
                )
            )
        ).report(
            new ListOf<>(new Output.Fake())
        );
    }

}
