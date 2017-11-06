package org.dgroup.dockertest.cmd;

import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class DefaultCmdOutput implements CmdOutput {

    private final Process outcome;

    public DefaultCmdOutput(Process outcome) {
        this.outcome = outcome;
    }

    public String text() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(outcome.getInputStream()))) {
            return new UncheckedText(
                    new TextOf(
                            new InputOf(in)
                    )
            ).asString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}