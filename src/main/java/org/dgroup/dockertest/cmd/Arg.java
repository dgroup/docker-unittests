package org.dgroup.dockertest.cmd;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
@AllArgsConstructor
public final class Arg {

    private final String name;
    private final List<String> args;

    public String value() {
        return args.get(args.indexOf(name) + 1);
    }
}