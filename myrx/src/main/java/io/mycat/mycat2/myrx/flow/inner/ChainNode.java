package io.mycat.mycat2.myrx.flow.inner;

import java.util.function.Function;

public class ChainNode<T, R> extends Node<T> {

    Function<T, R> function;

    public ChainNode(Function<T, R> function) {
        this.function = function;
    }

    public ChainNode() {
        function = (i) -> {
            System.out.println("数据流转:" + i);
            return (R) i;
        };
    }


    @Override
    public void onNext(T item) {
        topNode.onNext(function.apply(item));
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
