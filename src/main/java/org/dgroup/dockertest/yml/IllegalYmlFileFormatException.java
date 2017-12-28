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
package org.dgroup.dockertest.yml;

import java.util.List;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.text.PlainFormattedText;

/**
 * Notify that *.yml file has a wrong structure.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class IllegalYmlFileFormatException extends RuntimeException {

    /**
     * Ctor.
     * @param pattern Template.
     * @param args Arguments for template above.
     */
    public IllegalYmlFileFormatException(final String pattern,
        final Object... args) {
        this(new PlainFormattedText(pattern, args));
    }

    /**
     * Ctor.
     * @param msg Describes what exactly tag is wrong.
     */
    public IllegalYmlFileFormatException(final PlainFormattedText msg) {
        this(msg.toString());
    }

    /**
     * Ctor.
     * @param msg Describes what exactly tag is wrong.
     */
    public IllegalYmlFileFormatException(final String msg) {
        super(msg);
    }

    /**
     * Ctor.
     * @param msg Describes what exactly tag is wrong.
     * @param cause Original cause.
     */
    public IllegalYmlFileFormatException(final String msg,
        final Throwable cause) {
        super(msg, cause);
    }

    /**
     * Split message about wrong format to list of lines.
     * @return Messages.
     */
    public List<String> detailsSplittedByLines() {
        return new ListOf<>(this.getMessage().split("\n"));
    }
}
