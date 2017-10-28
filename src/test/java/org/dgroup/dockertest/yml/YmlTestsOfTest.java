package org.dgroup.dockertest.yml;

import lombok.extern.slf4j.Slf4j;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.text.FormattedTextWithRepeatableArguments;
import org.junit.Test;

import java.io.File;

import static org.dgroup.dockertest.AssertThrown.assertThrown;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Yurii Dubinka (dgroup@ex.ua)
 * @since 0.1.0
 **/
@Slf4j
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class YmlTestsOfTest {

    @Test
    public void versionTagIsMissing() {
        assertThrown(
                () -> new YmlTestsOf(
                        file("test_v1.0-with-missing-version-tag.yml")
                ).iterator(),
                new IllegalYmlFormatException("The `version` tag is missing")
        );
    }

    @Test
    public void iterator() {
        assertThat(
                "Tests from `test_v1.0.yml` were loaded as Iterable<YmlTest>",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ),
                hasSize(3)
        );
    }

    @Test
    public void assume() {
        assertThat(
                "Tag `tests/test[2]/assume` is equal to `\"node version is 8.5.1\"`",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).assume(),
                equalTo("node version is 8.5.1")
        );
    }

    @Test
    public void cmd() {
        assertThat(
                "Tag `tests/test[2]/cmd` is equal to `\"node -v\"`",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).cmd(),
                equalTo("node -v")
        );
    }

    @Test
    public void output() {
        assertThat(
                "Tag `tests/test[2]/output` has 4 statements",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).output(),
                hasSize(4)
        );
    }

    @Test
    public void outputContains() {
        assertThat(
                "Tag `tests/test[2]/output` has 1st statement `contains`",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).output().get(0).type(),
                equalTo("contains")
        );

        assertThat(
                "Tag `tests/test[2]/output` has 1st statement `contains` and expected value is `v8.5.0`",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).output().get(0).test("v8.5.0"),
                equalTo(true)
        );
    }

    @Test
    public void outputStartWith() {
        assertThat(
                "Tag `tests/test[2]/output` has 2nd statement `startWith`",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).output().get(1).type(),
                equalTo("startWith")
        );

        assertThat(
                "Tag `tests/test[2]/output` has 2nd statement `startWith` and expected value is `v8.`",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).output().get(1).test("v8."),
                equalTo(true)
        );
    }

    @Test
    public void outputEndWith() {
        assertThat(
                "Tag `tests/test[2]/output` has 3rd statement `endWith`",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).output().get(2).type(),
                equalTo("endWith")
        );

        assertThat(
                "Tag `tests/test[2]/output` has 3rd statement `endWith` and expected value is `.5.0`",
                new ListOf<>(
                        new YmlTestsOf(
                                file("test_v1.0.yml")
                        )
                ).get(1).output().get(2).test(".5.0"),
                equalTo(true)
        );
    }


    private File file(String name) {
        return new File(
                new FormattedTextWithRepeatableArguments(
                        "src{0}test{0}resources{0}yml{0}{1}",
                        File.separator, name
                ).asString()
        );
    }
}