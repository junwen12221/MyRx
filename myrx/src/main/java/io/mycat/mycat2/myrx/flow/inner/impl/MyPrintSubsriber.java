package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;

/**
 * Created by jamie on 2017/10/16.
 */
public class MyPrintSubsriber implements MySubsriberInterface<Object> {
    @Override
    public void onNext(Object item) {
        System.out.println(item);
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
