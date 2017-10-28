package org.dgroup.dockertest.cmd;


import org.dgroup.dockertest.test.DefaultTest;
import org.dgroup.dockertest.test.Test;
import org.dgroup.dockertest.test.output.Output;
import org.dgroup.dockertest.test.output.StdOutput;
import org.dgroup.dockertest.yml.YmlTestsOf;
import org.cactoos.io.InputOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.StickyList;

import java.util.List;

/**
 * Represents application command-line arguments.
 *
 * @author Yurii Dubinka (dgroup@ex.ua)
 * @since 0.1.0
 **/
public final class Args {

    private final List<String> arguments;

    public Args(String[] arguments) {
        this.arguments = new StickyList<>(arguments);
    }

    public Iterable<Test> tests() {
        return new Mapped<>(
                new YmlTestsOf(
                        new InputOf(
                                new FileArg(arguments).file()
                        )
                ),
                ymlTagTest -> new DefaultTest(
                        new ImageArg(arguments), ymlTagTest
                )
        );
    }

    public Output output() {
        return new StdOutput();
    }
}