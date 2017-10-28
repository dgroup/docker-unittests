package org.dgroup.dockertest.cmd;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public class CmdArgNotFoundException extends RuntimeException {
    public CmdArgNotFoundException(String msg) {
        super(msg);
    }
}