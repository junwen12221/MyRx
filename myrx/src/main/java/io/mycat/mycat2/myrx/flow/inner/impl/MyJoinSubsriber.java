package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;

/**
 * Created by jamie on 2017/10/16.
 */
public class MyJoinSubsriber<T> implements MySubsriberInterface<T> {
    private String name = "";
    private long count = 0;
    private MyJoinPublisher processors;
    private boolean isFinished = false;
    private long limit = -1;
    private Object mutableAccumulation;

    public MyJoinSubsriber(String name, MyJoinPublisher processors) {
        this.name = name;
        this.processors = processors;
        processors.subsriberInterfaceList.add(this);
    }

    public boolean isFinished() {
        return count == limit || isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public void onNext(T item) {
        System.out.println("Got : " + item);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        try {
            processors.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getMutableAccumulation() {
        return mutableAccumulation;
    }
}
