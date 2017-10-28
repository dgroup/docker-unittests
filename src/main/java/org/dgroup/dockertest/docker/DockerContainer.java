package org.dgroup.dockertest.docker;

import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Represents single docker container.
 * Container will be removed after cmd execution.
 *
 * @author  Yurii Dubinka (dgroup@ex.ua)
 * @since   0.1.0
 **/
public final class DockerContainer {

    private final SystemProcess process;

    public DockerContainer(String image, String cmd) {
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

    public DockerContainer(SystemProcess process) {
        this.process = process;
    }


    public CmdOutput run() {
        return new CmdOutput(process.execute());
    }
}