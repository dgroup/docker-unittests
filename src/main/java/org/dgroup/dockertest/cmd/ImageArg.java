package org.dgroup.dockertest.cmd;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * .
 *
 * @author Yurii Dubinka (dgroup@ex.ua)
 * @since 0.1.0
 **/
@AllArgsConstructor
public final class ImageArg {

    private final List<String> args;

    public String name() {
        return args.get(args.indexOf("-i") + 1);
    }
}
