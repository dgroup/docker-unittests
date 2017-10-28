package org.dgroup.dockertest;

import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.test.Test;

/**
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class App {

    private final Args args;

    public App(Args args) {
        this.args = args;
    }


    public static void main(String[] args) {
        new App(new Args(args))
                .start();
    }


    public void start() {
        for (Test test : args.tests()) {
            test.execute();
            test.print(args.output());
        }
    }
}