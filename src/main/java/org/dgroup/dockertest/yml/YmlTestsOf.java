package org.dgroup.dockertest.yml;

import lombok.extern.slf4j.Slf4j;
import org.cactoos.Input;
import org.cactoos.io.InputOf;
import org.cactoos.iterator.Mapped;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * .
 *
 * @author Yurii Dubinka (dgroup@ex.ua)
 * @since 0.1.0
 **/
@Slf4j
public final class YmlTestsOf implements Iterable<YmlTagTest> {

    private final String testsAsText;

    public YmlTestsOf(File file) {
        this(new InputOf(file));
    }

    public YmlTestsOf(Input src) {
        this(
                new UncheckedText(
                        new TextOf(src)
                ).asString()
        );
    }

    public YmlTestsOf(String testsAsText) {
        this.testsAsText = testsAsText;
    }

    @Override
    public Iterator<YmlTagTest> iterator() {
        Map<String, Object> ymlTree = new Yaml().load(testsAsText);

        YmlTag version = new YmlTag(
                ymlTree.get("version"), "version"
        );
        version.verifyExistence();
        if (!"1".equals(version.asString()))
            throw new IllegalArgumentException(
                    new PlainFormattedText(
                            "Unsupported version: %s",
                            version.asString()
                    ).asString()
            );

        YmlTag tests = new YmlTag(
                ymlTree.get("tests"), "tests"
        );
        tests.verifyExistence();

        return new Mapped<>(
                new Mapped<>(
                        tests.list().iterator(),
                        test -> (Map<String, Object>) test
                ),
                YmlTagTest::new
        );
    }
}