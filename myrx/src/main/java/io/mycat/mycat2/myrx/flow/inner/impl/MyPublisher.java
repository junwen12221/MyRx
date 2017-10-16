package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MyProcessorInterface;
import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;

import java.util.stream.IntStream;

/**
 * Created by jamie on 2017/10/16.
 */
public class MyPublisher<T> implements MyProcessorInterface<T, T>, AutoCloseable {
    MySubsriberInterface subscriber;

    public static void main(String[] args) {
        MyPublisher<String> myPublisher = new MyPublisher();
        MyPublisher<String> myPublisher2 = new MyPublisher();
        MyPrintSubsriber printSubsriber = new MyPrintSubsriber();
        MyJoinProcessor<String> joinProcessor = new MyJoinProcessor<>();
        joinProcessor.put("p", myPublisher);
        joinProcessor.put("j", myPublisher2);
        joinProcessor.init();
        joinProcessor.subscribe(printSubsriber);

        asynTest(myPublisher, IntStream.range(0, 5), 30);
        asynTest(myPublisher2, IntStream.range(20, 30), 35);

    }

    private static void asynTest(MyPublisher<String> publisher, IntStream stream, long waittime) {
        new Thread(() -> {
            stream.boxed()
                    .map((i) -> Integer.valueOf(i).toString()).map((i) -> {
                try {
                    Thread.sleep(waittime);
                    System.out.println("waiting......");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return i;
            })
                    .forEach((i) -> publisher.submit(i));
            publisher.close();
        }).start();
    }

    @Override
    public void subscribe(MySubsriberInterface subscriber) {
        this.subscriber = subscriber;
    }

    public void close() {
        subscriber.onComplete();
    }

    public int submit(T item) {
        subscriber.onNext(item);
        return 0;
    }

    @Override
    public void onNext(Object item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
