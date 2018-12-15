/**
 * MIT License
 *
 * Copyright (c) 2017-2018 Yurii Dubinka
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
package com.github.dgroup.dockertest.concurrent;

import com.github.dgroup.dockertest.text.TextOf;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Timeout.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TimeoutOf implements Timeout {

    /**
     * Origin timeout.
     */
    private final Scalar<Long> tmt;
    /**
     * Measure unit.
     */
    private final Scalar<TimeUnit> unit;

    /**
     * Ctor.
     * @param tmt Timeout.
     * @param unit Unit of measurement.
     */
    public TimeoutOf(final String tmt, final TimeUnit unit) {
        this(() -> Long.valueOf(tmt), () -> unit);
    }

    /**
     * Ctor.
     * @param tmt Timeout.
     * @param unit Unit of measurement.
     */
    public TimeoutOf(final Integer tmt, final TimeUnit unit) {
        this(() -> Long.valueOf(tmt), () -> unit);
    }

    /**
     * Ctor.
     * @param tmt Timeout.
     * @param unit Unit of measurement.
     */
    public TimeoutOf(final Scalar<Long> tmt, final Scalar<TimeUnit> unit) {
        this.tmt = tmt;
        this.unit = unit;
    }

    @Override
    public Long timeout() {
        return new UncheckedScalar<>(this.tmt).value();
    }

    @Override
    public TimeUnit measure() {
        return new UncheckedScalar<>(this.unit).value();
    }

    @Override
    @SuppressWarnings("PMD.OnlyOneReturn")
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Timeout other = (Timeout) obj;
        return Objects.equals(this.timeout(), other.timeout())
            && Objects.equals(this.measure(), other.measure());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tmt, this.unit);
    }

    @Override
    public String toString() {
        return new TextOf("%s %s", this.timeout(), this.measure()).text();
    }
}
