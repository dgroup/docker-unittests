package org.dgroup.dockertest.test;

import org.cactoos.scalar.Ternary;
import org.dgroup.dockertest.UncheckedCallable;
import org.dgroup.dockertest.UncheckedTernary;
import org.dgroup.dockertest.cmd.Arg;
import org.dgroup.dockertest.docker.DefaultDockerContainer;
import org.dgroup.dockertest.docker.LocalSystemAsDockerContainer;
import org.dgroup.dockertest.yml.YmlTagOutputPredicate;
import org.dgroup.dockertest.yml.YmlTagTest;

import java.util.List;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class YmlBasedTest implements Test {

    private final String scenario;
    private final String cmd;
    private final List<YmlTagOutputPredicate> conditions;
    private final UncheckedCallable<String> cmdOutput;

    public YmlBasedTest(String scenario, List<YmlTagOutputPredicate> conditions, String cmd,
                        UncheckedCallable<String> cmdOutput) {
        this.scenario = scenario;
        this.conditions = conditions;
        this.cmd = cmd;
        this.cmdOutput = cmdOutput;
    }

    public YmlBasedTest(Arg image, YmlTagTest yml) {
        this(
                yml.assume(),
                yml.output(),
                yml.cmd(),
                new UncheckedTernary<>(
                        new Ternary<UncheckedCallable<String>>(
                                image.specified(),
                                () -> new DefaultDockerContainer(image.value(), yml.cmd())
                                        .run().text(),
                                () -> new LocalSystemAsDockerContainer(yml.cmd())
                                        .run().text()
                        )
                ).value()
        );
    }


    @Override
    public TestingOutcome execute() {
        return new DefaultTestingOutcome(
                scenario, cmd, cmdOutput.call(), conditions
        );
    }
}