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
package org.dgroup.dockertest.docker.process;

import java.time.Instant;
import org.dgroup.dockertest.docker.output.TimedCmdOutput;

/**
 * Elapsed time for docker process.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Timed extends DockerProcessEnvelope {

    /**
     * Ctor.
     * @param origin Docker process where we need a timing.
     * @checkstyle LineLengthCheck (20 lines)
     */
    public Timed(final DockerProcess origin) {
        super(() -> {
            // @checkstyle MethodBodyCommentsCheck (20 lines)
            // @checkstyle RegexpSinglelineCheck (20 lines)
            // @checkstyle CascadeIndentationCheck (20 lines)
            /*
             JLS 1.8, section 15.7.4, Argument Lists are Evaluated Left-to-Right:
             -------------------------------------------------------------------
             In a method or constructor invocation or class instance creation
             expression, argument expressions may appear within the parentheses,
             separated by commas. Each argument expression appears to be fully
             evaluated before any part of any argument expression to its right.
            */
            return new TimedCmdOutput(
                Instant.now(), origin.execute()
            );
        });
    }

}
