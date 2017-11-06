package org.dgroup.dockertest;

import org.cactoos.scalar.Ternary;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class UncheckedTernary<T> {

    private final Ternary<T> origin;

    public UncheckedTernary(Ternary<T> origin) {
        this.origin = origin;
    }

    public T value(){
        try {
            return origin.value();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
