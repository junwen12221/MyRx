package io.mycat.mycat2.myrx.flow.inner;

import java.util.function.Predicate;

public class FilterChainNode<T> extends Node<T> {

    Predicate<T> predicate;

    public FilterChainNode(Predicate<T> predicate) {
        this.predicate = predicate;
    }


    @Override
    public void onNext(T item) {
        if (predicate.test(item)) {
            topNode.onNext(item);
        }

    }

    @Override
    public void onError(Throwable throwable) {
        topNode.onError(throwable);
    }

    @Override
    public void onComplete() {
        topNode.onComplete();
    }
}
