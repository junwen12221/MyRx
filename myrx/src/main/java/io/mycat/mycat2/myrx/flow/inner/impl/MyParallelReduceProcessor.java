package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MyProcessorInterface;
import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * Created by jamie on 2017/10/17.
 */
public class MyParallelReduceProcessor<T, R> implements MyProcessorInterface<T, R> {
    BiFunction<R, ? super T, R> accumulator;
    MySubsriberInterface<? super R> subscriber;
    BinaryOperator<R> combiner;
    R identity;

    public MyParallelReduceProcessor(R identity, BiFunction<R, ? super T, R> accumulator,
                                     BinaryOperator<R> combiner) {
        this.identity = identity;
        this.accumulator = accumulator;
        this.combiner = combiner;
    }

    @Override
    public void subscribe(MySubsriberInterface<? super R> subscriber) {
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
