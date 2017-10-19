package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MyProcessorInterface;
import io.mycat.mycat2.myrx.flow.inner.Node;

import java.util.function.Predicate;

/**
 * Created by jamie on 2017/10/17.
 */
public class MyFilterProcessor<T> implements MyProcessorInterface<T, T> {
    Predicate<T> predicate;
    Node<? super T> subscriber;

    public MyFilterProcessor(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void subscribe(Node<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onNext(T item) {
        if (predicate.test(item)) {
            subscriber.onNext(item);
        }
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
