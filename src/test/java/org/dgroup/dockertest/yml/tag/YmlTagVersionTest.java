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
package org.dgroup.dockertest.yml.tag;

import java.util.Collections;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.dgroup.dockertest.Assert;
import org.dgroup.dockertest.YmlResource;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;
import org.dgroup.dockertest.yml.YmlString;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link YmlTagVersion}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class YmlTagVersionTest {

    @Test(expected = IllegalYmlFormatException.class)
    public void noVersionSpecified() throws IllegalYmlFormatException {
        new YmlTagVersion(
            Collections.emptyMap()
        ).verify();
    }

    @Test(expected = IllegalYmlFormatException.class)
    public void unsupportedVersionSpecified() throws IllegalYmlFormatException {
        new YmlTagVersion(
            new MapOf<>(
                new MapEntry<>("version", "0.0-alpha")
            )
        ).verify();
    }

    @Test
    public void version() throws IllegalYmlFormatException {
        MatcherAssert.assertThat(
            new YmlTagVersion(
                new YmlString(
                    new YmlResource("with-single-test.yml").file()
                ).ymlTree()
            ).value(),
            Matchers.equalTo("1")
        );
    }

    @Test
    public void tagVersionIsMissing() {
        new Assert().thatThrows(
            () -> new YmlTagVersion(
                new YmlString(
                    new YmlResource("with-missing-version-tag.yml").file()
                ).ymlTree()
            ).verify(),
            new IllegalYmlFormatException(
                "`version` tag is missing or has incorrect structure"
            )
        );
    }

// {version=1, tests=[{test={assume=curl version is 7.xxx, cmd=curl
// --version, output=[{startsWith=curl 7.}, {contains=Protocols: },
// {contains=Features: }]}}]}

}
