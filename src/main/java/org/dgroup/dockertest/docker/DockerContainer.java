package org.dgroup.dockertest.docker;

import org.dgroup.dockertest.cmd.CmdOutput;

/**
 * Represents single docker container.
 * Container will be removed after cmd execution.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public interface DockerContainer {
    CmdOutput run();
}