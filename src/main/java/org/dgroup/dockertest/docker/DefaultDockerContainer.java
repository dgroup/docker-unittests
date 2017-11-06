package org.dgroup.dockertest.docker;

import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class DefaultDockerContainer implements DockerContainer {

    private final SystemProcess process;

    public DefaultDockerContainer(String image, String cmd) {
        this(
                new SystemProcess(
                        new UncheckedText(
                                new FormattedText(
                                        "docker run --rm %s %s",
                                        image, cmd
                                )
                        ).asString()
                )
        );
    }

    public DefaultDockerContainer(SystemProcess process) {
        this.process = process;
    }


    @Override
    public CmdOutput run() {
        return new DefaultCmdOutput(process.execute());
    }
}
