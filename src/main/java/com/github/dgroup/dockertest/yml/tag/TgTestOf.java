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

import com.github.dgroup.dockertest.text.Splitted;
import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.text.cutted.Between;
import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;
import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import com.github.dgroup.dockertest.yml.TgTest;
import com.github.dgroup.dockertest.yml.YmlTag;
import java.util.List;
import org.cactoos.Scalar;

/**
 * Represents yml tag {@code /tests/test}.
 * Tag can contain {@code assume}, {@code cmd} and {@link TgOutput}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @todo #/DEV Use pass type `TgTest` to envelope instead of String.
 */
@SuppressWarnings("PMD")
public final class TgTestOf extends TgEnvelope<String> implements TgTest {

    /**
     * Scenario name (assume YML tag).
     */
    private final YmlTag<String> scenario;
    /**
     * Container command (cmd YML tag).
     */
    private final YmlTag<String> command;
    /**
     * Container output (output YML tag).
     */
    private final YmlTag<String> out;

    /**
     * Ctor.
     * @param yml Presentation of tag `test` as string.
     */
    public TgTestOf(final String yml) {
        this(() -> yml);
    }

    /**
     * Ctor.
     * @param yml Presentation of tag `test` as string.
     */
    public TgTestOf(final Scalar<String> yml) {
        this(
            new TgText(
                () -> new Between(yml, "{test={assume=").first(", cmd="),
                "assume"
            ),
            new TgText(
                () -> new Between(yml, ", cmd=").first(", output=[{"),
                "cmd"
            ),
            new TgText(
                () -> new Between(yml, ", output=[{").last("]"),
                "output"
            )
        );
    }

    /**
     * Ctor.
     * @param assume Yml tag under `test`.
     * @param cmd Yml tag under `test`.
     * @param output Yml tag under `test`.
     * @checkstyle IndentationCheck (20 lines)
     */
    public TgTestOf(
        final YmlTag<String> assume,
        final YmlTag<String> cmd,
        final YmlTag<String> output
    ) {
        super(
            () -> new TextOf(
                "tag `test`, assume `%s`, cmd `%s`, output `%s`",
                assume.value(), cmd.value(), output.value()
            ).text(),
            "test"
        );
        this.scenario = assume;
        this.command = cmd;
        this.out = output;
    }

    @Override
    public String assume() throws IllegalYmlFormatException {
        return this.child(this.scenario);
    }

    @Override
    public String cmd() throws IllegalYmlFormatException {
        return this.child(this.command);
    }

    @Override
    public String[] containerCommandAsArray() throws IllegalYmlFormatException {
        return new Splitted(this.cmd()).toStringArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TgOutputPredicate> output()
        throws IllegalYmlFormatException {
        return new TgOutput(this.out).value();
    }

}
