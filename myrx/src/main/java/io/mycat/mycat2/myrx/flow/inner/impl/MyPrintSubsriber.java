package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.Node;

/**
 * Created by jamie on 2017/10/16.
 */
public class MyPrintSubsriber implements Node<Object> {
    @Override
    public void onNext(Object item) {
        System.out.println("MyPrintSubsriber=>" + item);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("complete!");
    }
}
