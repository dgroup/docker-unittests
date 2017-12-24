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
package org.dgroup.dockertest.yml.tag.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * YmlTagOutputPredicate that prints singe output predicate details to logs.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 * @todo #3 Move logback.xml to ${basedir}/target/classes, otherwise logs
 *  will be printed to std output (which isn't expected in cmd-line app).
 *  Looks like mvn-assembly-plugin or mvn-resource-plugin solves can do this.
 */
@SuppressWarnings("PMD.LoggerIsNotStaticFinal")
public final class LoggedYmlTagOutputPredicate
    implements YmlTagOutputPredicate {

    /**
     * Origin implementation.
     */
    private final YmlTagOutputPredicate origin;
    /**
     * Prints details to appender.
     */
    private final Logger log;

    /**
     * Ctor.
     * @param origin Single predicate from yml tag output.
     */
    public LoggedYmlTagOutputPredicate(final YmlTagOutputPredicate origin) {
        this(
            origin,
            LoggerFactory.getLogger(LoggedYmlTagOutputPredicate.class)
        );
    }

    /**
     * Ctor.
     * @param origin Single predicate from yml tag output.
     * @param log Logger where predicate details to be printed.
     */
    public LoggedYmlTagOutputPredicate(final YmlTagOutputPredicate origin,
        final Logger log) {
        this.origin = origin;
        this.log = log;
    }

    @Override
    public String comparingType() {
        return this.origin.comparingType();
    }

    @Override
    public boolean test(final String actual) {
        this.log.debug("Got `{}`.", actual);
        final boolean status = this.origin.test(actual);
        this.log.debug(
            "Predicate {} applied to `{}` gives `{}`.",
            this.origin, actual, status
        );
        return status;
    }

    @Override
    public String toString() {
        return this.origin.toString();
    }

}
