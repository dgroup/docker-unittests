package org.dgroup.dockertest.text;


import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class StringOccurrencesTest {

    @Test
    public void equalTo() {
        assertTrue(
                new StringOccurrences("Hey %s. My name is %s", "%s")
                        .equalTo(2)
        );
    }

}