package org.dgroup.dockertest.cmd;

import org.cactoos.list.ListOf;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public class FileArgTest {

    @Test
    public void file() {
        assertThat(
                "File content was readed to asString",
                new FileArg(
                        new ListOf<>(
                                "-f", ".gitignore"
                        )
                ).file().getName(),
                equalTo(".gitignore")
        );
    }
}