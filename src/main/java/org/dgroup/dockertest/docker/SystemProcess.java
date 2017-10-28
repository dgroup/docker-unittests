package org.dgroup.dockertest.docker;

import java.io.IOException;

/**
 * .
 *
 * @author  Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since   0.1.0
 **/
public final class SystemProcess {

    private final String cmd;

    public SystemProcess(String cmd) {
        this.cmd = cmd;
    }

    public Process execute() {
        try {
            return Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
