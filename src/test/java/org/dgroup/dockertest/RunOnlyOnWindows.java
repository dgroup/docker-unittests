package org.dgroup.dockertest;


import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Allows to run particular tests only on Windows system
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public class RunOnlyOnWindows extends BlockJUnit4ClassRunner {

    public RunOnlyOnWindows(Class klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        if (windows())
            super.run(notifier);
    }

    public boolean windows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

}