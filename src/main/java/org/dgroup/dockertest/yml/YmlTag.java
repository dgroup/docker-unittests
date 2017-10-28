package org.dgroup.dockertest.yml;

import org.dgroup.dockertest.text.PlainFormattedText;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class YmlTag {

    private final Object tag;
    private final String name;

    public YmlTag(Object tag, String name) {
        this.tag = tag;
        this.name = name;
    }


    public void verifyExistence() {
        if (tag == null)
            throw new IllegalYmlFormatException(
                    new PlainFormattedText(
                            "The `%s` tag is missing", name
                    )
            );
    }

    public String asString() {
        return tag == null ? "" : tag.toString();
    }

    public List<Object> list() {
        return (List<Object>) tag;
    }

    public Map<Object, Object> map() {
        return (Map<Object, Object>) tag;
    }
}