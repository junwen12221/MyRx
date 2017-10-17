package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MyProcessorInterface;
import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;

import java.util.function.BinaryOperator;

/**
 * Created by jamie on 2017/10/17.
 */
public class MyReduceProcessor<T> implements MyProcessorInterface<T, T> {
    BinaryOperator<T> accumulator;
    MySubsriberInterface<? super T> subscriber;
    T identity;

    public MyReduceProcessor(T identity, BinaryOperator<T> accumulator) {
        this.identity = identity;
        this.accumulator = accumulator;
    }

    @Override
    public void subscribe(MySubsriberInterface<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onNext(T item) {
        identity = accumulator.apply(identity, item);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
