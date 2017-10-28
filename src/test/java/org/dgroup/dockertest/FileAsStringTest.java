package org.dgroup.dockertest;

import org.cactoos.list.ListOf;
import org.dgroup.dockertest.text.FileAsString;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public class FileAsStringTest {

    @Test
    public void content() {
        assertThat(
                "File content was readed to asString",
                new FileAsString(
                        new ListOf<>(
                                "-f", ".gitignore"
                        )
                ).content(),
                containsString(
                        "/.idea/"
                )
        );
    }
}