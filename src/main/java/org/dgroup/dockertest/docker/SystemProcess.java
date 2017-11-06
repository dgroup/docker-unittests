package org.dgroup.dockertest.docker;

import java.io.IOException;

/**
 * .
 *
 * @author  Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since   0.1.0
 **/
public final class SystemProcess {

    private final ProcessBuilder process;

    public SystemProcess(String... cmd) {
        this(new ProcessBuilder(cmd).redirectErrorStream(true));
    }

    public SystemProcess(ProcessBuilder process) {
        this.process = process;
    }

    public Process execute() {
        try {
            return process.start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
