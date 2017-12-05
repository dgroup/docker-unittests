/*
 * MIT License
 *
 * Copyright (c) 2017 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.dgroup.dockertest.text;

import org.cactoos.list.ListOf;
import org.cactoos.text.UncheckedText;
import java.util.Collection;


public final class PlainFormattedText {

    private final String pattern;
    private final Collection<Object> args;

    public PlainFormattedText(String pattern, Object... args) {
        this(pattern, new ListOf<>(args));
    }

    public PlainFormattedText(String pattern, Collection<Object> args){
        this.pattern = pattern;
        this.args = args;
    }

    public String asString() {
        if (new StringOccurrences(pattern, "%s").nonEqualTo(args.size()))
            throw new IllegalArgumentException(
                    "Wrong amount of arguments("+args.size()+") for pattern '"+pattern+"'."
            );

        return new UncheckedText(new org.cactoos.text.FormattedText(pattern, args))
                .asString();
    }

    @Override
    public String toString() {
        return asString();
    }
}
