package io.mycat.mycat2.myrx.flow.inner;

/**
 * Created by jamie on 2017/10/16.
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R accept(T t, U u, V v);
}
