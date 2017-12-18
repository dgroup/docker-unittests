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
package org.dgroup.dockertest.test.output;

import java.util.List;
import java.util.Map;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;

/**
 * Represents supported output formats for printing test results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class SupportedOutputs {

    /**
     * Supported formats.
     */
    private final Map<String, Output> out;

    /**
     * Ctor.
     */
    public SupportedOutputs() {
        this(
            new MapOf<>(
                new MapEntry<>("xml", new XmlOutput()),
                new MapEntry<>("html", new HtmlOutput()),
                new MapEntry<>("std", new StdOutput())
            )
        );
    }

    /**
     * Ctor.
     * @param out Supported output formats like standard output, xml, html.
     */
    public SupportedOutputs(final Map<String, Output> out) {
        this.out = out;
    }

    /**
     * Gives output type by format id.
     * @param type Format id
     * @return Output.
     */
    public Output find(final String type) {
        return this.out.get(type);
    }

    /**
     * Checking that output formats specified by user are supported.
     * @param specified By user output formats.
     * @return True in any of specified by user output formats is supported.
     */
    public boolean supports(final List<String> specified) {
        return this.out.keySet().stream().anyMatch(specified::contains);
    }

}
