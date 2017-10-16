package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;

import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by jamie on 2017/10/16.
 */
public class BufferSubsriber<T> implements MySubsriberInterface<T> {
    LinkedTransferQueue<T> buffer = new LinkedTransferQueue<>();

    @Override
    public void onNext(T item) {
        buffer.offer(item);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    public LinkedTransferQueue<T> getBuffer() {
        return buffer;
    }
}
