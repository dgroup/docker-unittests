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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.collection.SafeSet;
import com.github.dgroup.dockertest.exception.RootCauseOf;
import com.github.dgroup.dockertest.scalar.If;
import com.github.dgroup.dockertest.text.TextFile;
import com.github.dgroup.dockertest.yml.Tag;
import com.github.dgroup.dockertest.yml.Tags;
import com.github.dgroup.dockertest.yml.TgOutput;
import com.github.dgroup.dockertest.yml.TgSetup;
import com.github.dgroup.dockertest.yml.TgTest;
import com.github.dgroup.dockertest.yml.YmlFormatException;
import com.github.dgroup.dockertest.yml.tag.output.TgOutputOf;
import com.github.dgroup.dockertest.yml.tag.setup.TgSetupOf;
import com.github.dgroup.dockertest.yml.tag.test.TgTestOf;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.cactoos.collection.Mapped;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * Represents *.yml file with tests as an object.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1
 */
public final class TagsOf implements Tags {

    /**
     * Objects tree of yml file.
     */
    private final UncheckedScalar<YmlFile> tags;

    /**
     * Ctor.
     * @param src The source *.yml file with tests.
     * @checkstyle IndentationCheck (50 lines)
     * @checkstyle IllegalCatchCheck (50 lines)
     */
    @SuppressWarnings({
        "PMD.PreserveStackTrace", "PMD.AvoidCatchingGenericException"
    })
    public TagsOf(final TextFile src) {
        this.tags = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    try {
                        return new Yaml(new Constructor(YmlFile.class)).loadAs(
                            src.text(), YmlFile.class
                        );
                    } catch (final Exception ex) {
                        throw new YmlFormatException(
                            "YML file `%s` has unsupported format:%n %s",
                            src.path(),
                            new RootCauseOf(ex).exception().getMessage()
                        );
                    }
                }
            )
        );
    }

    @Override
    public Tag<String> version() throws YmlFormatException {
        return this.tags.value().tagVersion();
    }

    @Override
    public TgSetup setup() {
        return this.tags.value().tagSetup();
    }

    @Override
    public Collection<TgTest> tests() {
        return this.tags.value().tagTests();
    }

    /**
     * Represents raw YML file structure for {@code org.yaml:snakeyaml}.
     * Allows to parse the tags to domain YML objects structure.
     *
     * @checkstyle MemberNameCheck (200 lines)
     * @checkstyle JavadocTypeCheck (200 lines)
     * @checkstyle JavadocMethodCheck (200 lines)
     * @checkstyle JavadocVariableCheck (200 lines)
     * @checkstyle MethodBodyCommentsCheck (200 lines)
     * @checkstyle VisibilityModifierCheck (200 lines)
     */
    private static class YmlFile {
        public String version;
        public Set<String> setup;
        public Set<YmlTest> tests;

        public Tag<String> tagVersion() throws YmlFormatException {
            final TagOf<String> vrsn = new TagOf<>(this.version, "version");
            // @todo #/DEV Add mechanism for back-comparability and verification
            if (!"1.1".equals(vrsn.value())) {
                throw new YmlFormatException(
                    "Unsupported version: %s", vrsn.value()
                );
            }
            return vrsn;
        }

        public TgSetup tagSetup() {
            return new TgSetupOf(new SafeSet<>(this.setup));
        }

        public Collection<TgTest> tagTests() {
            return new Mapped<>(
                test -> new TgTestOf(
                    new TagOf<>(test.assume, "assume"),
                    new TagOf<>(test.cmd, "cmd"),
                    test.tagOutput()
                ),
                new If<Set<YmlTest>>(
                    () -> this.tests == null
                        || this.tests.iterator().next() == null,
                    Collections::emptySet,
                    () -> this.tests
                ).value()
            );
        }
    }

    private static class YmlTest {
        public String assume;
        public String cmd;
        public YmlTestOutput output;

        public TgOutput tagOutput() throws YmlFormatException {
            if (this.output == null) {
                // @checkstyle OperatorWrapCheck (5 lines)
                // @checkstyle StringLiteralsConcatenationCheck (5 lines)
                throw new YmlFormatException(
                    "Tag `output` has missing required child tag "
                        + "`contains|endsWith|equal|matches|startsWith`"
                );
            }
            return new TgOutputOf(
                new SafeSet<>(this.output.startsWith),
                new SafeSet<>(this.output.endsWith),
                new SafeSet<>(this.output.equals),
                new SafeSet<>(this.output.matches),
                new SafeSet<>(this.output.contains)
            );
        }
    }

    private static class YmlTestOutput {
        public String startsWith;
        public String endsWith;
        public String equals;
        public Set<String> contains;
        public Set<String> matches;
    }
}
