package org.dgroup.dockertest.yml;

import org.cactoos.list.Mapped;
import org.dgroup.dockertest.text.PlainFormattedText;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class YmlTagOutput {

    private final List<Map<String, String>> tag;

    public YmlTagOutput(List<Map<String, String>> yml) {
        this.tag = yml;
    }

    public List<YmlTagOutputPredicate> conditions() {
        return new Mapped<>(
                tag,
                conditions -> {
                    String condition = conditions.keySet().iterator().next();
                    String expectedText = conditions.values().iterator().next();

                    if ("contains".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("contains", expectedText, actual -> actual.contains(expectedText));

                    if ("equal".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("equal", expectedText, actual -> actual.equals(expectedText));

                    if ("startWith".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("startWith", expectedText, actual -> actual.startsWith(expectedText));

                    if ("endWith".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("endWith", expectedText, actual -> actual.endsWith(expectedText));

                    throw new IllegalYmlFormatException(
                            new PlainFormattedText(
                                    "Tag `output` has unsupported condition: `%s`. Supported values `contains`, `equal`, `startWith`, `endWith`",
                                    condition
                            )
                    );
                }
        );
    }
}