package org.dgroup.dockertest.test.output;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * .
 *
 * @author Yurii Dubinka (dgroup@ex.ua)
 * @since 0.1.0
 **/
public class StdOutputTest {

    @Test
    public void failureMessage() {
        assertThat(
                "",
                new StdOutput().message(
                        "java version is 1.8+",
                        true,
                        "1.8.0_13",
                        "java version \"1.8.0_131\""
                ),
                equalTo(
                        String.format(
                                "Fail \"java version is 1.8+\"%nER: 1.8.0_13%nAR: java version \"1.8.0_131\""
                        )
                )
        );
    }

    @Test
    public void okMessage() {
        assertThat(
                "We have test status and name",
                new StdOutput().message("java version is 1.8+", false, "", ""),
                startsWith("OK \"java version is 1.8+\"")
        );
    }
}