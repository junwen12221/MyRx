package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MyProcessorInterface;
import io.mycat.mycat2.myrx.flow.inner.Node;

import java.util.function.Function;

/**
 * Created by jamie on 2017/10/17.
 */
public class MyMapProcessor<T, R> implements MyProcessorInterface<T, R> {
    Function<T, R> function;
    Node<? super R> subscriber;


    @Override
    public void subscribe(Node<? super R> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onNext(T item) {
        subscriber.onNext(function.apply(item));
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
