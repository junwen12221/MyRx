package io.mycat.mycat2.myrx.flow;

import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainApp {
    public static void main(String... args) {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        SubmissionPublisher<String> joinPublisher = new SubmissionPublisher<>();
        SubmissionPublisher<String> collectPublisher = new SubmissionPublisher<>();
        PrintSubscriber printSubscriber = new PrintSubscriber();
        collectPublisher.subscribe(printSubscriber);

        MyAssembleProcessor myAssembleProcessor = new MyAssembleProcessor(collectPublisher, Collectors.toSet());
        MySubscriber<String> primarySubscriber = new MySubscriber("primary", myAssembleProcessor);
        MySubscriber<String> joinSubscriber = new MySubscriber("join", myAssembleProcessor);
        publisher.subscribe(primarySubscriber);
        joinPublisher.subscribe(joinSubscriber);
        asynTest(publisher,IntStream.range(0,5),30);
        asynTest(joinPublisher,IntStream.range(20,30),35);

        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void asynTest(SubmissionPublisher<String> publisher,IntStream stream,long waittime) {
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

}