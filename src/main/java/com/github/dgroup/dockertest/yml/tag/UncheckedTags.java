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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.yml.Tag;
import com.github.dgroup.dockertest.yml.Tags;
import com.github.dgroup.dockertest.yml.TgSetup;
import com.github.dgroup.dockertest.yml.TgTest;
import com.github.dgroup.dockertest.yml.UncheckedYmlFormatException;
import com.github.dgroup.dockertest.yml.YmlFormatException;
import java.util.Collection;

/**
 * Tags that doesn't throw checked {@link YmlFormatException}.
 *
 * In case of YML format error the {@link UncheckedYmlFormatException} will be
 *  thrown.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.1
 */
public final class UncheckedTags implements Tags {

    /**
     * Origin.
     */
    private final Tags tags;

    /**
     * Ctor.
     * @param tags Origin.
     */
    public UncheckedTags(final Tags tags) {
        this.tags = tags;
    }

    @Override
    public Tag<String> version() {
        try {
            return this.tags.version();
        } catch (final YmlFormatException cause) {
            throw new UncheckedYmlFormatException(cause);
        }
    }

    @Override
    public TgSetup setup() {
        try {
            return this.tags.setup();
        } catch (final YmlFormatException cause) {
            throw new UncheckedYmlFormatException(cause);
        }
    }

    @Override
    public Collection<TgTest> tests() {
        return this.tests();
    }
}
