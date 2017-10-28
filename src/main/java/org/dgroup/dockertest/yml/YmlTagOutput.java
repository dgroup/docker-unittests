package org.dgroup.dockertest.yml;

import org.cactoos.list.Mapped;
import org.dgroup.dockertest.text.PlainFormattedText;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author Yurii Dubinka (dgroup@ex.ua)
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
                        return new YmlTagOutputPredicate("contains",expectedText::contains);

                    if ("equal".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("equal",expectedText::equals);

                    if ("startWith".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("startWith",expectedText::startsWith);

                    if ("endWith".equalsIgnoreCase(condition))
                        return new YmlTagOutputPredicate("endWith",expectedText::endsWith);

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