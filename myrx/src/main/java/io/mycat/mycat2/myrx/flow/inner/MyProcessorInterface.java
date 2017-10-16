package io.mycat.mycat2.myrx.flow.inner;

/**
 * Created by jamie on 2017/10/16.
 */
public interface MyProcessorInterface<T, R> extends MySubsriberInterface<T>, MyPublisherInterface<R> {
}
