package org.dgroup.dockertest.docker;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class FakeDockerContainer implements DockerContainer {

    private final String output;

    public FakeDockerContainer(String output) {
        this.output = output;
    }

    @Override
    public CmdOutput run() {
        return () -> output;
    }
}
