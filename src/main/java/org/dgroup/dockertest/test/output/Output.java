package org.dgroup.dockertest.test.output;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public interface Output {
    void print(String scenario, boolean failed, String expectedCmdOutput, String output);
}
