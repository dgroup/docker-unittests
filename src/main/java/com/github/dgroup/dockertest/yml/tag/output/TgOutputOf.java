/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
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
package com.github.dgroup.dockertest.yml.tag.output;

import com.github.dgroup.dockertest.yml.TgOutput;
import com.github.dgroup.dockertest.yml.TgOutputPredicate;
import com.github.dgroup.dockertest.yml.tag.TgEnvelope;
import java.util.Collection;
import java.util.Set;
import org.cactoos.collection.Joined;
import org.cactoos.collection.Mapped;

/**
 * Represents yml tag {@code /tests/test/output}.
 * Tag can contain list of predicates contains, equals, startsWith, endsWith.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle RegexpSinglelineCheck (10 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class TgOutputOf extends TgEnvelope<Collection<TgOutputPredicate>>
    implements TgOutput {

    /**
     * Ctor.
     * @param starts The testing predicates for "startsWith" condition.
     * @param ends The testing predicates for "endsWith" condition.
     * @param eql The testing predicates for "equals" condition.
     * @param matches The testing predicates for "matches" condition.
     * @param contains The testing predicates for "contains" condition.
     * @checkstyle IndentationCheck (50 lines)
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public TgOutputOf(
        final Set<String> starts, final Set<String> ends, final Set<String> eql,
        final Set<String> matches, final Set<String> contains
    ) {
        super(
            () -> new Joined<>(
                new Mapped<>(
                    expected -> new TgOutputPredicateOf(
                        "startsWith", expected, String::startsWith
                    ),
                    starts
                ),
                new Mapped<>(
                    expected -> new TgOutputPredicateOf(
                        "endsWith", expected, String::endsWith
                    ),
                    ends
                ),
                new Mapped<>(
                    expected -> new TgOutputPredicateOf(
                        "equals", expected, String::equals
                    ),
                    eql
                ),
                new Mapped<>(
                    expected -> new TgOutputPredicateOf(
                        "matches", expected, String::matches
                    ),
                    matches
                ),
                new Mapped<>(
                    expected -> new TgOutputPredicateOf(
                        "contains", expected, String::contains
                    ),
                    contains
                )
            ),
            "output"
        );
    }
}
