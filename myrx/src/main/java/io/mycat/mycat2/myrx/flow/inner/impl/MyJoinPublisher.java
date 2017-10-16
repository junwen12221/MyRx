package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MyPublisherInterface;
import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;

import java.util.List;

/**
 * Created by jamie on 2017/10/16.
 */
public class MyJoinPublisher<T, R> implements MyPublisherInterface<T>, AutoCloseable {
    List<MySubsriberInterface<? super T>> subsriberInterfaceList;
    MySubsriberInterface<? super T> subscriber;

    public MyJoinPublisher(List<MySubsriberInterface<? super T>> subsriberInterfaceList) {
        this.subsriberInterfaceList = subsriberInterfaceList;
    }

    @Override
    public void subscribe(MySubsriberInterface<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void close() throws Exception {

    }

    public int submit(T item) {
        return 0;
    }


    public void onError(Throwable throwable) {

    }
}
