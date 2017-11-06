package org.dgroup.dockertest.yml;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
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
