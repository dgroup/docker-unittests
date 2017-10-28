package org.dgroup.dockertest.cmd;

import java.util.List;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class Arg {

    private final String name;
    private final List<String> args;

    public Arg(String name, List<String> args){
        this.name = name;
        this.args = args;
    }

    public String value() {
        return args.get(args.indexOf(name) + 1);
    }
}