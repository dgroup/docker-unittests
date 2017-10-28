package org.dgroup.dockertest.test;

import org.dgroup.dockertest.docker.CmdOutput;
import org.dgroup.dockertest.docker.DockerContainer;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.yml.YmlTagTest;

import java.util.function.Predicate;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class DefaultTest implements Test {

    private final String scenario;
    private final String dockerImage;
    private final String dockerCmd;
    private final String expectedCmdOutput;
    private final Predicate<String> match;
    private CmdOutput cmdOutput;

    public DefaultTest(
            String scenario, String expectedCmdOutput, Predicate<String> match,
            String dockerImage, String dockerCmd) {

        this.scenario = scenario;
        this.match = match;
        this.expectedCmdOutput = expectedCmdOutput;
        this.dockerImage = dockerImage;
        this.dockerCmd = dockerCmd;
    }

    public DefaultTest(String image, YmlTagTest yml) {
        this(
                yml.assume(),
                null,
                null,
                image,
                yml.cmd()
        );
    }

    @Override
    public void execute() {
        this.cmdOutput = new DockerContainer(
                dockerImage, dockerCmd
        ).run();
    }

    @Override
    public boolean failed() {
        return match.test(cmdOutput.text());
    }

    @Override
    public void print(Output output) {
        output.print(scenario, failed(), expectedCmdOutput, cmdOutput.text());
    }
}