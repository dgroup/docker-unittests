package org.dgroup.dockertest.test;

import org.cactoos.collection.Filtered;
import org.dgroup.dockertest.text.PlainFormattedText;
import org.dgroup.dockertest.text.StringOf;
import org.dgroup.dockertest.yml.YmlTagOutputPredicate;

import java.util.List;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class TestingOutcomeByDefault implements TestingOutcome {

    private final String scenario;
    private final String cmdOutput;
    private final String cmd;
    private final List<YmlTagOutputPredicate> expectedConditions;

    public TestingOutcomeByDefault(String scenario, String cmd, String cmdOutput, List<YmlTagOutputPredicate> expectedConditions) {
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
        return successful() ? scenarioPassed() : scenarioFailed();
    }


    private String scenarioPassed() {
        return new PlainFormattedText(
                "Passed scenario `%s`. Output for command `%s` is `%s`",
                scenario, cmd, cmdOutput
        ).asString();
    }

    private String scenarioFailed() {
        return new PlainFormattedText(
                "Failed scenario `%s`. Output for command `%s` should %s, however received `%s`",
                scenario,
                cmd,
                new StringOf(expectedConditions, ", "),
                cmdOutput
        ).asString();
    }

}