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
package org.dgroup.dockertest.classpath;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.collection.CollectionEnvelope;
import org.cactoos.scalar.StickyScalar;

/**
 * Represents collection of root folders within the class loaders.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Resources extends CollectionEnvelope<URL> {

    /**
     * Ctor.
     */
    public Resources() {
        this(
            () -> Thread.currentThread()
                .getContextClassLoader()
                .getResources("")
        );
    }

    /**
     * Ctor.
     * @param src The root folders with class loaders.
     */
    public Resources(final Scalar<Enumeration<URL>> src) {
        super(
            new StickyScalar<>(
                () -> {
                    final Enumeration<URL> cpath = src.value();
                    final List<URL> origin = new ArrayList<>(5);
                    while (cpath.hasMoreElements()) {
                        origin.add(cpath.nextElement());
                    }
                    return Collections.unmodifiableCollection(origin);
                }
            )
        );
    }

}
