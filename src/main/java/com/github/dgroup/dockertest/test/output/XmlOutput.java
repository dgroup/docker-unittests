/**
 * MIT License
 *
 * Copyright (c) 2017-2018 Yurii Dubinka
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
package com.github.dgroup.dockertest.test.output;

import com.github.dgroup.dockertest.io.Property;
import com.github.dgroup.dockertest.scalar.If;
import com.github.dgroup.dockertest.test.outcome.TestOutcome;
import com.github.dgroup.dockertest.test.outcome.TestingOutcome;
import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import java.io.FileWriter;
import java.io.Writer;
import org.cactoos.Scalar;
import org.cactoos.io.BytesOf;
import org.cactoos.io.LengthOf;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.TextOf;
import org.xembly.Directives;
import org.xembly.Xembler;

/**
 * Print testing results to xml file.
 * The *.xml file with output will be placed to current directory.
 * Also directory can be exported from {@code -reports_home} cmd argument.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @todo #/DEV Do not print `failed` tag in case if the scenario passed.
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class XmlOutput implements Output {

    /**
     * The version of XML report format.
     */
    private final String vrsn;
    /**
     * The path of XML report.
     */
    private final UncheckedScalar<Writer> out;

    /**
     * Ctor.
     */
    public XmlOutput() {
        this(
            new UncheckedScalar<>(new Property("output.xml.version")).value(),
            new UncheckedScalar<>(new Property("output.xml.file")).value()
        );
    }

    /**
     * Ctor.
     * @param vrsn The version of XML report format.
     * @param path The path of XML report.
     */
    public XmlOutput(final String vrsn, final String path) {
        this(vrsn, () -> new FileWriter(path));
    }

    /**
     * Ctor.
     * @param vrsn The version of XML report format.
     * @param out The writer for XML report.
     */
    public XmlOutput(final String vrsn, final Scalar<Writer> out) {
        this.vrsn = vrsn;
        this.out = new UncheckedScalar<>(out);
    }

    @Override
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void print(final TestingOutcome outcome) {
        final Directives report = new Directives()
            .add("report")
            .attr("format-version", this.vrsn)
            .add("image").set("openjdk:9.0.1-11")
            .up()
            .add("tests")
            .attr("overall-status", this.decode(outcome.successful()));
        for (final TestOutcome test : outcome) {
            report.add("test")
                .add("scenario").set(test.scenario()).up()
                .add("status").set(this.decode(test.successful())).up()
                .add("expectedThatOutput");
            for (final TgOutputPredicate prd : test.expectedConditions()) {
                report.add(prd.comparingType())
                    .set(prd.expected())
                    .up();
            }
            report.up()
                .add("docker")
                .add("command").set(test.cmd()).up()
                .add("output").set(test.rawOutput()).up()
                .add("failed");
            for (final TgOutputPredicate prd : test.failedConditions()) {
                report.add(prd.comparingType())
                    .set(prd.expected())
                    .up();
            }
            report.up().up().up();
        }
        new UncheckedScalar<>(
            new LengthOf(
                new TeeInput(
                    new BytesOf(
                        new TextOf(
                            new Xembler(report).xmlQuietly()
                        )
                    ),
                    new OutputTo(this.out.value())
                )
            )
        ).value();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof XmlOutput;
    }

    /**
     * Decode the {@code true/false} to {@code passed/failed}.
     * @param status The origin.
     * @return The decoded text.
     * @checkstyle NonStaticMethodCheck (5 lines)
     */
    private String decode(final boolean status) {
        return new If<>(status, "passed", "failed").value();
    }
}
