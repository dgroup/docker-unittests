package org.dgroup.dockertest.test.output;

/**
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class StdOutput implements Output {

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }
}