package org.dgroup.dockertest.docker;

/**
 * .
 *
 * @author  Yurii Dubinka (dgroup@ex.ua)
 * @since   0.1.0
 **/
public final class CmdOutput {

    private final Process outcome;

    public CmdOutput(Process outcome) {
        this.outcome = outcome;
    }

    public String text() {
        return outcome.toString();
    }
}
