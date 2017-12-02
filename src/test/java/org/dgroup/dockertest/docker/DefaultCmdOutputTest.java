package org.dgroup.dockertest.docker;

import org.dgroup.dockertest.RunOnlyOnWindows;
import org.dgroup.dockertest.cmd.DefaultCmdOutput;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
@RunWith(RunOnlyOnWindows.class)
public class DefaultCmdOutputTest {

    @Test(timeout = 1000 * 3)
    public void text() {
        assertThat(
                "Command `java -version` will have `1.8` version",
                new DefaultCmdOutput(
                        new SystemProcess("cmd", "/c", "java", "-version").execute()
                ).text(),
                startsWith("java version \"1.8")
        );
    }
}