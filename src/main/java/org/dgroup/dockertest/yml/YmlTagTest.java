/*
 * MIT License
 *
 * Copyright (c) 2017 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.dgroup.dockertest.yml;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 **/
@SuppressWarnings("PMD")
public final class YmlTagTest {

    private final YmlTag tag;

    public YmlTagTest(Map<String, Object> yml) {
        this(
                new YmlTag(yml.get("test"), "test")
        );
    }

    private YmlTagTest(YmlTag tag) {
        this.tag = tag;
    }

    public String assume() {
        return tag.map().get("assume").toString();
    }

    public String cmd() {
        return tag.map().get("cmd").toString();
    }

    public String[] cmdAsArray(){
        return cmd().split(" ");
    }

    public List<YmlTagOutputPredicate> output() {
        return new YmlTagOutput(
                (List<Map<String, String>>) tag.map().get("output")
        ).conditions();
    }

    @Override
    public String toString() {
        return "YmlTagTest{" +
                "tag=" + tag +
                ", assume='" + assume() + '\'' +
                ", cmd='" + cmd() + '\'' +
                ", output=" + output().size() +
                '}';
    }
}
