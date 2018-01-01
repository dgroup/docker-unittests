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
package org.dgroup.dockertest.cmd;

import java.util.List;

/**
 * Command-line argument that doesn't throw checked
 *  {@link CmdArgNotFoundException}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class UncheckedArg implements Arg {

    /**
     * Command line argument.
     */
    private final Arg origin;

    /**
     * Ctor.
     * @param origin Command line argument.
     */
    public UncheckedArg(final Arg origin) {
        this.origin = origin;
    }

    /**
     * Ctor.
     * @param name Cmd argument name.
     * @param args All cmd arguments.
     */
    public UncheckedArg(final String name, final List<String> args) {
        this(new DefaultArg(name, args));
    }

    @Override
    public String name() {
        return this.origin.name();
    }

    // @checkstyle ReturnCountCheck (10 lines)
    @SuppressWarnings("PMD.OnlyOneReturn")
    @Override
    public String value() {
        try {
            return this.origin.value();
        } catch (final CmdArgNotFoundException exp) {
            return "";
        }
    }

    @Override
    public boolean specifiedByUser() {
        return this.origin.specifiedByUser();
    }
}
