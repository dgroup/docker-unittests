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
package org.dgroup.dockertest.test.output.std;

import java.util.List;
import org.dgroup.dockertest.docker.output.CmdOutput;
import org.dgroup.dockertest.scalar.UncheckedTernary;
import org.dgroup.dockertest.test.TestingOutcome;
import org.dgroup.dockertest.yml.IllegalYmlFileFormatException;

/**
 * Fake instance of {@link StdOutput} for unit-testing purposes.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class FakeStdOutput implements StdOutput {

    /**
     * Message storage.
     */
    private final List<String> lines;

    /**
     * Ctor.
     * @param lines Output details.
     */
    public FakeStdOutput(final List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void print(final String msg) {
        this.lines.add(msg);
    }

    @Override
    public void print(final CmdOutput output) {
        this.lines.addAll(output.byLines());
    }

    @Override
    public void print(final String file,
        final IllegalYmlFileFormatException exp) {
        this.lines.add(file);
        this.lines.addAll(exp.detailsSplittedByLines());
    }

    @Override
    public void print(final TestingOutcome outcome) {
        this.lines.add(
            new UncheckedTernary<>(
                outcome.successful(),
                "Testing successfully completed.",
                "Testing failed."
            ).value()
        );
    }

    /**
     * App progress details.
     * @return Output.
     */
    public List<String> details() {
        return this.lines;
    }

}
