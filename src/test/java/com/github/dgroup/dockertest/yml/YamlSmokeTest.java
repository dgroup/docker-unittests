/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
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
package com.github.dgroup.dockertest.yml;

import java.util.List;
import java.util.Set;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * This is an example how to avoid raw parsing of YML file.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1
 * @checkstyle MemberNameCheck (200 lines)
 * @checkstyle JavadocTypeCheck (200 lines)
 * @checkstyle JavadocTagsCheck (200 lines)
 * @checkstyle JavadocMethodCheck (200 lines)
 * @checkstyle JavadocVariableCheck (200 lines)
 * @checkstyle VisibilityModifierCheck (200 lines)
 */
public final class YamlSmokeTest {
    /**
     * The test is verifying that we can easily parse the YML file based on
     * structure of private <em>domain</em> classes.
     *
     * These classes should be private and not be available to use anyone
     * outside of the parser because they are against to EO development
     * principles:
     * - no null
     * - no getters/setters
     * - no mutable classes
     * More about them you may find here https://www.elegantobjects.org.
     *
     * @throws Exception In case of I/O errors.
     */
    @Test
    public void smoke() throws Exception {
        final YmlFile file = new Yaml(new Constructor(YmlFile.class)).load(
            new ResourceOf("yml/3rd-party-tests/with-single-test.yml").stream()
        );
        MatcherAssert.assertThat(
            "The tag <version> has value 1.1",
            file.version,
            new IsEqual<>("1.1")
        );
        MatcherAssert.assertThat(
            "The tag <setup> contains 2 instructions",
            file.setup,
            Matchers.hasItems("apt-get install curl", "apt-get install tree")
        );
        MatcherAssert.assertThat(
            "The tag <tests> contains 2 tests",
            file.tests,
            Matchers.hasSize(2)
        );
        MatcherAssert.assertThat(
            "The 1st test has expected <tests/1st/assume>",
            file.tests.iterator().next().assume,
            new IsEqual<>("curl version is 7.xxx")
        );
        MatcherAssert.assertThat(
            "The 1st test has expected <tests/1st/output/startsWith> tag",
            file.tests.iterator().next().output.startsWith,
            new IsEqual<>("curl 7.")
        );
        MatcherAssert.assertThat(
            "The 1st test has expected <tests/1st/output/contains> tag",
            file.tests.iterator().next().output.contains,
            Matchers.hasItems("Protocols: ", "Features")
        );
    }

    private static class YmlFile {
        public String version;
        public Set<String> setup;
        public List<YmlTest> tests;
    }

    private static class YmlTest {
        public String assume;
        public String cmd;
        public YmlTestOutput output;
    }

    private static class YmlTestOutput {
        public String startsWith;
        public String endsWith;
        public Set<String> contains;
        public Set<String> matches;
    }
}
