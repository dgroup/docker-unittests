package org.dgroup.dockertest.cmd;

import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

import java.io.File;
import java.util.List;

/**
 * Represents a commandline cmd for the file with tests.
 *
 * @author  Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since   0.1.0
 **/
public final class FileArg {

    private static final String SRC_FILE_ARG = "-f";
    private final List<String> args;

    public FileArg(List<String> args) {
        this.args = args;
    }

    public File file() {
        if (!args.contains(SRC_FILE_ARG))
            throw new CmdArgNotFoundException(
                    new UncheckedText(
                            new FormattedText(
                                    "Can't detect the file with tests due to missing '%s' cmd.",
                                    SRC_FILE_ARG
                            )
                    ).asString()
            );

        return new File(
                args.get(
                        args.indexOf(SRC_FILE_ARG) + 1
                )
        );
    }
}