/**
 * MIT License
 *
 * Copyright (c) 2017-2019 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.dgroup.dockertest.concurrent.func;

import com.github.dgroup.dockertest.concurrent.ExcsrvFake;
import com.github.dgroup.dockertest.concurrent.SimplifiedFuture;
import com.github.dgroup.dockertest.concurrent.Timeout;
import com.github.dgroup.dockertest.concurrent.TimeoutOf;
import com.github.dgroup.dockertest.test.TestOutcome;
import java.util.concurrent.TimeUnit;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link TimingOut}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class TimingOutTest {

    @Test(timeout = 2 * 1000)
    public void mapCallableToFuture() throws Exception {
        MatcherAssert.assertThat(
            new TimingOut<Integer>(Timeout.No::new)
                .apply(
                    new SimplifiedFuture.Fake<Integer>().apply(
                        () -> 2 * 2
                    )
                ),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void applyFunctionToTheFutureFromExecutorService() throws Exception {
        MatcherAssert.assertThat(
            new TimingOut<TestOutcome>(
                () -> new TimeoutOf(3, TimeUnit.SECONDS)
            ).apply(
                new ExcsrvFake<>().submit(
                    () -> new com.github.dgroup.dockertest.test.Test.Sleeping(
                        new TimeoutOf(2, TimeUnit.SECONDS)
                    ).execute()
                )
            ).scenario(),
            Matchers.containsString("Slept 2 SECONDS")
        );
    }
}
