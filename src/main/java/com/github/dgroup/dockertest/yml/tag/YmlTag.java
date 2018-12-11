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
package com.github.dgroup.dockertest.yml.tag;

import com.github.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * Represents single yml tag in *.yml file.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 * @todo #154/DEV The YmlTag should inherit Tag interface without any methods.
 *  It will allow indicating of all tags despite on their type/values.
 */
public interface YmlTag<T> {

    /**
     * Represent tag name as string.
     * @return Name.
     */
    String name();

    /**
     * Represent tag value as string.
     * @return Value.
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    T value() throws IllegalYmlFormatException;

    /**
     * Fake implementation for unit-testing purposes.
     * @checkstyle JavadocVariableCheck (10 lines)
     * @checkstyle JavadocMethodCheck (50 lines)
     */
    class Fake implements YmlTag<String> {

        private final String tag;
        private final String value;

        public Fake(final String tag, final String value) {
            this.tag = tag;
            this.value = value;
        }

        @Override
        public String name() {
            return this.tag;
        }

        @Override
        public String value() {
            return this.value;
        }
    }

}
