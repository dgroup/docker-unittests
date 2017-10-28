package org.dgroup.dockertest;

import org.assertj.core.api.ThrowableAssert;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class AssertThrown {

    public static void assertThrown(ThrowableAssert.ThrowingCallable operation, Exception exp) {
        assertThatThrownBy(operation)
                .isInstanceOf(exp.getClass())
                .hasMessage(exp.getMessage());
    }
}
