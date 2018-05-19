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
package com.github.dgroup.dockertest.docker.output;

import com.github.dgroup.dockertest.text.Joined;
import java.util.List;
import org.cactoos.list.ListOf;

/**
 * Represents docker command output.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface CmdOutput {

    /**
     * Represent cmd output as string.
     * @return Cmd output as string.
     */
    String asText();

    /**
     * Represent cmd output splitted by lines.
     * @return Cmd output as list.
     */
    List<String> byLines();

    /**
     * Fake implementation for unit testing purposes.
     * @checkstyle JavadocMethodCheck (20 lines)
     * @checkstyle JavadocVariableCheck (20 lines)
     */
    final class Fake implements CmdOutput {

        private final List<String> output;

        public Fake(final String... output) {
            this(new ListOf<>(output));
        }

        public Fake(final List<String> output) {
            this.output = output;
        }

        @Override
        public String asText() {
            return new Joined(this.output, " ").text();
        }

        @Override
        public List<String> byLines() {
            return this.output;
        }
    }
}
