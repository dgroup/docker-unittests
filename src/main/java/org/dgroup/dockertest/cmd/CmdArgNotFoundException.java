package org.dgroup.dockertest.cmd;

/**
 * .
 *
 * @author Yurii Dubinka (dgroup@ex.ua)
 * @since 0.1.0
 **/
public class CmdArgNotFoundException extends RuntimeException {
    public CmdArgNotFoundException(String msg) {
        super(msg);
    }
}