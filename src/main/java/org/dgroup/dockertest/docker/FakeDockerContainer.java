package org.dgroup.dockertest.docker;

import org.dgroup.dockertest.cmd.CmdOutput;
import org.dgroup.dockertest.cmd.DefaultCmdOutput;

/**
 * Represents current local system as docker container.
 * It can be used for unit testings (in case when we didn't have the docker in the system.)
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class FakeDockerContainer implements DockerContainer {

    private final SystemProcess process;

    public FakeDockerContainer(String... cmd) {
        this(new SystemProcess(cmd));
    }

    public FakeDockerContainer(SystemProcess process) {
        this.process = process;
    }

    @Override
    public CmdOutput run() {
        return new DefaultCmdOutput(process.execute());
    }
}
