package org.dgroup.dockertest.test;

import org.cactoos.collection.Filtered;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.text.StringOf;
import org.dgroup.dockertest.yml.YmlTagOutputPredicate;

import java.util.List; /**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
@SuppressWarnings("PMD") // TODO remove
public final class DefaultTestingOutcome implements TestingOutcome {

    private final String scenario;
    private final String cmdOutput;
    private final String cmd;
    private final List<YmlTagOutputPredicate> expectedConditions;

    public DefaultTestingOutcome(String scenario, String cmd, String cmdOutput, List<YmlTagOutputPredicate> expectedConditions) {
        this.scenario = scenario;
        this.cmd = cmd;
        this.cmdOutput = cmdOutput;
        this.expectedConditions = expectedConditions;
    }


    @Override
    public boolean successful() {
        return new Filtered<>(
                expectedConditions, t -> !t.test(cmdOutput)
        ).isEmpty();
    }

    @Override
    public String message() {
        return new PlainFormattedText(
                "%s scenario `%s`. Output for command `%s` should %s, however received `%s`",
                successful()? "Passed" : "Failed",
                scenario,
                cmd,
                new StringOf(expectedConditions, ", "),
                cmdOutput
        ).asString();
    }
}
