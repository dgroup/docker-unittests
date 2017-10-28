package org.dgroup.dockertest.test;

import org.dgroup.dockertest.test.output.Output;

/**
 * Represents single unit test with docker image
 *
 * @author  Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since   0.1.0
 **/
public interface Test {
    void execute();
    boolean failed();
    void print(Output output);
}