package io.mycat.mycat2.myrx.flow.inner;

/**
 * Created by jamie on 2017/10/16.
 */
public interface MyPublisherInterface<T> {
    public void subscribe(MySubsriberInterface<? super T> subscriber);
}
