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
package org.dgroup.dockertest.docker.output;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;

/**
 * Output for docker command with elapsed time.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TimedCmdOutput extends CmdOutputEnvelope {

    /**
     * Operation duration in seconds.
     */
    private final Double seconds;

    /**
     * Ctor.
     * @param start Time.
     * @param origin Operation output.
     */
    public TimedCmdOutput(final Instant start, final CmdOutput origin) {
        this(start, Instant.now(), origin);
    }

    /**
     * Ctor.
     * @param start Time.
     * @param end Time.
     * @param origin Operation output.
     */
    public TimedCmdOutput(final Instant start, final Instant end,
        final CmdOutput origin) {
        this(Duration.between(start, end), origin);
    }

    /**
     * Ctor.
     * @param spent Time.
     * @param origin Operation output.
     */
    public TimedCmdOutput(final Duration spent, final CmdOutput origin) {
        // @checkstyle MagicNumberCheck (1 lines)
        this(spent.toMillis() / 1000.0, origin);
    }

    /**
     * Ctor.
     * @param seconds Time spent in seconds.
     * @param origin Operation output.
     */
    public TimedCmdOutput(final Double seconds, final CmdOutput origin) {
        super(() -> origin);
        this.seconds = seconds;
    }

    @Override
    public List<String> byLines() {
        return new Joined<>(
            super.byLines(),
            new ListOf<>(String.format("Elapsed: %ss", this.seconds))
        );
    }

}
