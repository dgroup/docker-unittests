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
package org.dgroup.dockertest.yml;

import java.util.List;
import java.util.Map;
import org.dgroup.dockertest.yml.tag.test.YmlTagTest;
import org.dgroup.dockertest.yml.tag.test.YmlTagTestOf;
import org.dgroup.dockertest.yml.tag.tests.YmlTagTests;
import org.dgroup.dockertest.yml.tag.version.YmlTagVersion;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.scanner.ScannerException;

/**
 * Transform *.yml file with tests to collection of {@link YmlTagTestOf }.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class YmlString {

    /**
     * All tags defined in yml file with tests.
     */
    private final String yml;

    /**
     * Ctor.
     * @param yml Tags defined in file with tests as string.
     */
    public YmlString(final String yml) {
        this.yml = yml;
    }

    /**
     * Parsed yml tests as list.
     * @return Yml tests.
     * @throws IllegalYmlFileFormatException in case if YML file has
     *  wrong/corrupted/unsupported format.
     * @todo #72:15m Remove unnecessary variable.
     */
    public List<YmlTagTest> asTests() throws IllegalYmlFileFormatException {
        final Map<String, Object> tree = this.loadYmlTree();
        final YmlTagVersion version = new YmlTagVersion(tree);
        version.verify();
        return new YmlTagTests(tree).asList();
    }

    /**
     * Load string with tests as yml tree.
     * @return Tree.
     * @throws IllegalYmlFileFormatException in case if YML file has
     *  wrong/corrupted/unsupported format.
     */
    private Map<String, Object> loadYmlTree()
        throws IllegalYmlFileFormatException {
        try {
            return new Yaml().load(this.yml);
        } catch (final ScannerException ex) {
            throw new IllegalYmlFileFormatException(ex.getMessage(), ex);
        }
    }

}
