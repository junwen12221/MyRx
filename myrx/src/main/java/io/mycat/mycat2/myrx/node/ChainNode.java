package io.mycat.mycat2.myrx.node;

import java.util.function.Function;

public class ChainNode<T, R> extends MyNode<T> {

    Function<T, R> function;

    public ChainNode(Function<T, R> function) {
        this.function = function;
    }

    public ChainNode() {
        function = (i) -> {
            // System.nextNode.println("数据流转:" + i);
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
