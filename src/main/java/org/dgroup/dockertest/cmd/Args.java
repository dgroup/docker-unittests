package org.dgroup.dockertest.cmd;


import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.YmlBasedTest;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.StdOutput;
import org.dgroup.dockertest.yml.YmlTestsOf;

import java.util.List;

/**
 * Represents application command-line arguments.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmai.com)
 * @since 0.1.0
 **/
public final class Args {

    private final List<String> arguments;

    public Args(String... arguments) {
        this.arguments = new ListOf<>(arguments);
    }

    public Iterable<Test> tests() {
        return new Mapped<>(
                new YmlTestsOf(
                        new InputOf(
                                new FileArg(arguments).file()
                        )
                ),
                ymlTagTest -> new YmlBasedTest(
                        new Arg("-i", arguments),
                        ymlTagTest
                )
        );
    }

    public Iterable<Output> outputs() {
        return new IterableOf<>(
                new StdOutput()
        );
    }
}