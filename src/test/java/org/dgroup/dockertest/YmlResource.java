package org.dgroup.dockertest;

import org.cactoos.list.ListOf;
import org.dgroup.dockertest.text.FormattedTextWithRepeatableArguments;

import java.io.File;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class YmlResource {

    private final FormattedTextWithRepeatableArguments path;

    public YmlResource(String name) {
        this("src{0}test{0}resources{0}yml{0}tests{0}{1}", File.separator, name);
    }

    private YmlResource(String pattern, Object... args) {
        this(new FormattedTextWithRepeatableArguments(pattern, new ListOf<>(args)));
    }

    private YmlResource(FormattedTextWithRepeatableArguments path) {
        this.path = path;
    }

    public File file() {
        return new File(path());
    }

    public String path() {
        return path.asString();
    }
}