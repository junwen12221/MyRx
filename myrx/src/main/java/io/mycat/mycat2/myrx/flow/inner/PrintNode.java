package io.mycat.mycat2.myrx.flow.inner;

public class PrintNode extends Node {


    @Override
    public void onNext(Object item) {
        System.out.println(item);
    }

    @Override
    public void onError(Throwable throwable) {
        nextNode.onError(throwable);
    }

    @Override
    public void onComplete() {
        nextNode.onComplete();
    }
}
