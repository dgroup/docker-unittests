package org.dgroup.dockertest.test.output;

import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class StdOutput implements Output {

    @Override
    public void print(String scenario, boolean failed, String expectedCmdOutput, String actualCmdOutput) {
        System.out.println(message(scenario, failed, expectedCmdOutput, actualCmdOutput));
    }

    String message(String scenario, boolean failed, String expectedCmdOutput, String actualCmdOutput) {
        return new UncheckedText(
                new FormattedText(
                        "%s \"%s\"%nER: %s%nAR: %s",
                        failed ? "Fail" : "OK",
                        scenario,
                        expectedCmdOutput,
                        actualCmdOutput
                )
        ).asString();
    }
}