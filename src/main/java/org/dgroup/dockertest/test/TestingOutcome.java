package org.dgroup.dockertest.test;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public interface TestingOutcome {
    boolean successful();
    String message();
}
