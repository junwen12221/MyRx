package io.mycat.mycat2.myrx.flow.inner;

public class LeafNode<T> extends Node<T> {
    @Override
    public void onNext(T item) {
        //  System.out.println("数据产生:" + item);
        topNode.onNext(item);
    }

    @Override
    public void onError(Throwable throwable) {
        topNode.onNext(throwable);
    }

    @Override
    public void onComplete() {
        topNode.onComplete();
    }
}
