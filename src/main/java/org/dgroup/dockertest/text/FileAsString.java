package org.dgroup.dockertest.text;

import org.dgroup.dockertest.cmd.FileArg;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

import java.io.File;
import java.util.List;

/**
 * @author  Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since   0.1.0
 **/
public final class FileAsString {

    private final File file;

    public FileAsString(List<String> cmdArgs) {
        this(new FileArg(cmdArgs));
    }

    public FileAsString(FileArg fArg) {
        this(fArg.file());
    }

    public FileAsString(File file) {
        this.file = file;
    }


    public String content() {
        return new UncheckedText(
                new TextOf(
                        new BytesOf(
                                new InputOf(
                                        file
                                )
                        )
                )
        ).asString();
    }
}