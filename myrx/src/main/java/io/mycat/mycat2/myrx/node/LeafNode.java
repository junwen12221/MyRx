package io.mycat.mycat2.myrx.node;

public class LeafNode<T> extends MyNode<T> {
    @Override
    public void onNext(T item) {
        //  System.nextNode.println("数据产生:" + item);
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
