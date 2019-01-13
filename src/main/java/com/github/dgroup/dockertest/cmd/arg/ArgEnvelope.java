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
package com.github.dgroup.dockertest.cmd.arg;

import com.github.dgroup.dockertest.cmd.Arg;
import com.github.dgroup.dockertest.cmd.scalar.ArgAt;
import com.github.dgroup.dockertest.cmd.scalar.ArgIn;
import com.github.dgroup.dockertest.scalar.Mapped;
import com.github.dgroup.dockertest.text.Text;
import com.github.dgroup.dockertest.text.TextOf;
import java.util.List;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Envelope for {@link Arg}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <X> Type of item.
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
class ArgEnvelope<X> implements Arg<X> {

    /**
     * Origin.
     */
    private final Scalar<Arg<X>> origin;

    /**
     * Ctor.
     * @param label Name of command-line argument.
     * @param args All command-line arguments.
     * @param fnc To convert string argument value to instance of {@code <X>}.
     */
    ArgEnvelope(final String label, final List<String> args,
        final Func<String, X> fnc) {
        this(
            label, args, fnc,
            () -> new TextOf("Argument `%s` wasn't specified", label)
        );
    }

    /**
     * Ctor.
     * @param label Name of command-line argument.
     * @param args All command-line arguments.
     * @param fnc To convert string argument value to instance of {@code <X>}.
     * @param err Error message in case if argument is absent/missing.
     * @checkstyle AnonInnerLengthCheck (30 lines)
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    ArgEnvelope(final String label, final List<String> args,
        final Func<String, X> fnc, final Scalar<Text> err) {
        this(
            new StickyScalar<>(
                () -> new Arg<X>() {
                    @Override
                    public String name() {
                        return label;
                    }

                    @Override
                    public X value() throws CmdArgNotFoundException {
                        if (!this.specifiedByUser()) {
                            throw new CmdArgNotFoundException(
                                new UncheckedScalar<>(err).value()
                            );
                        }
                        return new UncheckedScalar<>(
                            new Mapped<>(fnc, new ArgAt(label, args))
                        ).value();
                    }

                    @Override
                    public boolean specifiedByUser() {
                        return new ArgIn(label, args).value();
                    }
                }
            )
        );
    }

    /**
     * Ctor.
     * @param origin Origin.
     */
    ArgEnvelope(final Scalar<Arg<X>> origin) {
        this.origin = origin;
    }

    @Override
    public String name() {
        return new UncheckedScalar<>(this.origin).value().name();
    }

    @Override
    public X value() throws CmdArgNotFoundException {
        return new UncheckedScalar<>(this.origin).value().value();
    }

    @Override
    public boolean specifiedByUser() {
        return new UncheckedScalar<>(this.origin).value().specifiedByUser();
    }

}
