package org.dgroup.dockertest;

import org.cactoos.list.Mapped;
import org.dgroup.dockertest.cmd.Args;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.TestingOutcome;
import org.dgroup.dockertest.test.output.Output;

import java.util.List;

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
        List<String> tests = new Mapped<>(
                new Mapped<>(args.tests(), Test::execute),
                TestingOutcome::message
        );

        for(Output output : args.outputs())
            for(String test : tests)
                output.print(test);
    }
}