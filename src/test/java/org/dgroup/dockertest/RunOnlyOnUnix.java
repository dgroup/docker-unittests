package org.dgroup.dockertest;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Allows to run particular tests only on Linux system
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class RunOnlyOnUnix extends BlockJUnit4ClassRunner {

    public RunOnlyOnUnix(Class klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        String os = System.getProperty("os.name");
        if (os.endsWith("nux") || os.endsWith("nix") || os.endsWith("aix"))
            super.run(notifier);
    }

}
