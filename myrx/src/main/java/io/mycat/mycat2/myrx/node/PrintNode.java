package io.mycat.mycat2.myrx.node;

public class PrintNode extends Node {


    @Override
    public void onNext(Object item) {
        System.out.println("print=>" + item);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
        System.out.println("print=>Complete!");
    }
}
