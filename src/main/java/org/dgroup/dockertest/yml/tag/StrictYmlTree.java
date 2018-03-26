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
package org.dgroup.dockertest.yml.tag;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import org.dgroup.dockertest.text.PlainText;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * Asserts which allows to validate yml tree.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @todo #/DEV Remove\propose another approach for this ugly YML tree check
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class StrictYmlTree {

    /**
     * Validate statement.
     */
    private final Callable check;

    /**
     * Ctor.
     * @param yml Tree as map.
     */
    StrictYmlTree(final Map<?, ?> yml) {
        this(
            () -> {
                if (yml == null) {
                    throw new IllegalYmlFormatException();
                }
                return yml;
            }
        );
    }

    /**
     * Ctor.
     * @param tag Particular YML tag to be verified.
     * @param yml Tree as map.
     */
    StrictYmlTree(final String tag, final Map<?, ?> yml) {
        this(
            () -> {
                if (yml == null || yml.isEmpty()) {
                    throw new IllegalYmlFormatException(
                        new PlainText(
                            "`%s` tag is missing or has incorrect structure",
                            tag
                        )
                    );
                }
                return yml;
            }
        );
    }

    /**
     * Ctor.
     * @param tag Particular YML tag to be verified.
     * @param yml Tags as collection.
     */
    StrictYmlTree(final String tag, final Collection<Object> yml) {
        this(
            () -> {
                if (yml == null || yml.isEmpty()) {
                    throw new IllegalYmlFormatException(
                        new PlainText(
                            "`%s` tag is missing or has incorrect structure",
                            tag
                        )
                    );
                }
                return yml;
            }
        );
    }

    /**
     * Ctor.
     * @param parent YML tag to be verified.
     * @param child YML tag to be verified.
     * @param yml Tree.
     */
    StrictYmlTree(
        final String parent,
        final String child,
        final Map<String, Object> yml
    ) {
        this(
            () -> {
                if (yml == null || yml.isEmpty()) {
                    throw new IllegalYmlFormatException(parent, child);
                }
                return yml;
            }
        );
    }

    /**
     * Ctor.
     * @param check Particular check-operation.
     */
    StrictYmlTree(final Callable check) {
        this.check = check;
    }

    /**
     * Verify the structure of YML document.
     * @throws IllegalYmlFormatException in case if YML file is corrupted
     *  or has wrong/invalid format.
     * @checkstyle IllegalCatchCheck (10 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void verify() throws IllegalYmlFormatException {
        try {
            this.check.call();
        } catch (final Exception exp) {
            throw new IllegalYmlFormatException(exp);
        }
    }
}
