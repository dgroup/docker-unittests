package org.dgroup.dockertest.text;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlainFormattedTextWithRepeatableArgumentsTest {

    @Test
    public void asString() {
        assertThat(
                new FormattedTextWithRepeatableArguments(
                        "{0}{1}test{1}resources{1}testng.xml",
                        "home", File.separator
                ).asString(),
                equalTo("home" + File.separator + "test" + File.separator + "resources" + File.separator + "testng.xml")
        );
    }
}