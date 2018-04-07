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
package org.dgroup.dockertest.cmd;

import java.util.List;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.dockertest.cmd.scalar.ArgAt;
import org.dgroup.dockertest.cmd.scalar.ArgIn;
import org.dgroup.dockertest.scalar.ConditionNotSatisfiedException;
import org.dgroup.dockertest.scalar.Mapped;
import org.dgroup.dockertest.scalar.StrictIf;
import org.dgroup.dockertest.text.Text;
import org.dgroup.dockertest.text.TextOf;

/**
 * Envelope for {@link Arg}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (150 lines)
 */
class ArgEnvelope<T> implements Arg<T> {

    /**
     * Name of command-line argument.
     */
    private final String label;
    /**
     * Value of command-line argument.
     */
    private final Scalar<T> val;
    /**
     * Status of the argument(specified or not by user).
     */
    private final Scalar<Boolean> specified;
    /**
     * Error message in case if argument wasn't specified by the user, but app
     *  trying to evaluate the argument value.
     */
    private final Text error;

    /**
     * Ctor.
     * @param label Name of command-line argument.
     * @param args All command-line arguments.
     * @param fnc To convert string argument value to instance of {@code <T>}.
     */
    ArgEnvelope(final String label, final List<String> args,
        final Func<String, T> fnc) {
        this(
            label, args, fnc,
            new TextOf("Argument `%s` wasn't specified", label)
        );
    }

    /**
     * Ctor.
     * @param label Name of command-line argument.
     * @param args All command-line arguments.
     * @param fnc To convert string argument value to instance of {@code <T>}.
     * @param error Error message in case if argument wasn't specified by the
     *  user.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    ArgEnvelope(final String label, final List<String> args,
        final Func<String, T> fnc, final Text error) {
        this(
            label,
            new StickyScalar<>(new Mapped<>(fnc, new ArgAt(label, args))),
            new StickyScalar<>(new ArgIn(label, args)),
            error
        );
    }

    /**
     * Ctor.
     * @param label Name of command-line argument.
     * @param val Value of command-line argument.
     * @param specified By user or not.
     * @param error Error message in case if argument missing or not specified
     *  by the user.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    ArgEnvelope(
        final String label,
        final Scalar<T> val,
        final Scalar<Boolean> specified,
        final Text error
    ) {
        this.label = label;
        this.val = val;
        this.specified = specified;
        this.error = error;
    }

    @Override
    public String name() {
        return this.label;
    }

    @Override
    public T value() throws CmdArgNotFoundException {
        try {
            return new StrictIf<>(
                this.specified, this.val, this.error::text
            ).value();
        } catch (final ConditionNotSatisfiedException exp) {
            throw new CmdArgNotFoundException(exp);
        }
    }

    @Override
    public boolean specifiedByUser() {
        return new UncheckedScalar<>(this.specified).value();
    }

}
