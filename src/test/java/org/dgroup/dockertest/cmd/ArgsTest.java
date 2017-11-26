package org.dgroup.dockertest.cmd;

import org.dgroup.dockertest.YmlResource;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public class ArgsTest {

    @Test
    public void test() {
        assertThat(
                "Default java version on my system is `1.8`",
                new Args(
                        "-f",
                        new YmlResource(
                                "with-single-test.yml"
                        ).file().getAbsolutePath()
                ).tests().iterator().next().execute().message(),
                startsWith(
                        "Passed scenario `java version is 1.8`. Output for command `java -version` is `java version \"1.8"
                )
        );
    }
}