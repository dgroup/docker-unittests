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
package com.github.dgroup.dockertest.io.file;

import com.github.dgroup.dockertest.collection.Flatly;
import com.github.dgroup.dockertest.scalar.If;
import java.io.File;
import org.cactoos.collection.CollectionEnvelope;
import org.cactoos.collection.CollectionOf;
import org.cactoos.collection.Mapped;
import org.cactoos.list.ListOf;

/**
 * Map hierarchical tree of files to the collection.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class Hierarchical extends CollectionEnvelope<File> {

    /**
     * Ctor.
     * @param root The root file of the tree.
     */
    public Hierarchical(final String root) {
        this(new File(root));
    }

    /**
     * Ctor.
     * @param roots The root/parent files of the trees.
     */
    public Hierarchical(final File... roots) {
        this(new ListOf<>(roots));
    }

    /**
     * Ctor.
     * @param roots The root/parent files of the trees.
     * @checkstyle IndentationCheck (20 lines)
     */
    public Hierarchical(final Iterable<File> roots) {
        super(
            () -> new Flatly<>(
                new Mapped<>(
                    root -> new If<>(
                        root::isFile,
                        () -> new CollectionOf<>(root),
                        () -> new Hierarchical(new Children(root))
                    ).value(),
                    roots
                )
            )
        );
    }

}
