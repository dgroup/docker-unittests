package org.dgroup.dockertest.test;

/**
 * Represents single unit execute with docker image
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public interface Test {
    TestingOutcome execute();
}