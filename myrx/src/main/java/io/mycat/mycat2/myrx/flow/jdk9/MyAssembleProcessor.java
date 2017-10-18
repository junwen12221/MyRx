//package io.mycat.mycat2.myrx.flow.jdk9;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.SubmissionPublisher;
//import java.util.function.BiConsumer;
//import java.util.function.BinaryOperator;
//import java.util.function.Function;
//import java.util.function.Supplier;
//import java.util.stream.Collector;
//
///**
// * Created by jamie on 2017/10/14.
// */
//public class MyAssembleProcessor<IN, MUTABLE_ACCUMULATION, FINAL_RESULT> {
//    List<MySubscriber> subscriberList = new ArrayList<>();
//    long limit = Long.MAX_VALUE;
//    long min;
//    SubmissionPublisher publisher;
//    Collector collector;
//    BiConsumer<MUTABLE_ACCUMULATION, IN> accumulator;
//    Set<Collector.Characteristics> characteristics;
//    BinaryOperator<MUTABLE_ACCUMULATION> combiner;
//    Function<MUTABLE_ACCUMULATION, FINAL_RESULT> finisher;
//    Supplier<MUTABLE_ACCUMULATION> supplier;
//
//    public MyAssembleProcessor(SubmissionPublisher publisher, Collector<IN, MUTABLE_ACCUMULATION, FINAL_RESULT> collector) {
//        this.publisher = publisher;
//        this.collector = collector;
//        this.accumulator = collector.accumulator();
//        this.characteristics = collector.characteristics();
//        this.combiner = collector.combiner();
//        this.finisher = collector.finisher();
//        this.supplier = collector.supplier();
//    }
//
//    public void collect(MySubscriber subscriber, IN item) {
//        if (!subscriber.isFinished()) {
//            MUTABLE_ACCUMULATION mutableAccumulation = (MUTABLE_ACCUMULATION)subscriber.getMutableAccumulation();
//            accumulator.accept(mutableAccumulation, item);
//        } else {
//            subscriber.getSubscription().cancel();
//            endCollect(subscriber);
//        }
//    }
//
//    public void endCollect(MySubscriber subscriber) {
//        subscriber.setFinished(true);
//        if (isAllFinished()) {
//            MUTABLE_ACCUMULATION mutable_accumulation = (MUTABLE_ACCUMULATION) subscriberList.get(0).getMutableAccumulation();
//            for (int i = 1; i < subscriberList.size(); i++) {
//                mutable_accumulation=  combiner.apply(mutable_accumulation,(MUTABLE_ACCUMULATION) subscriberList.get(i).getMutableAccumulation());
//            }
//            publisher.submit(finisher.apply(mutable_accumulation));
//        }
//    }
//
//    public long getLimit() {
//        return limit;
//    }
//
//    public void setLimit(long limit) {
//        this.limit = limit;
//    }
//
//    private long getMinCount() {
//        long count = 0;
//        for (int i = 1; i < subscriberList.size(); i++) {
//            count = Math.min(subscriberList.get(i).getCount(), count);
//        }
//        return min = count;
//    }
//
//    private boolean isAllFinished() {
//        for (int i = 0; i < subscriberList.size(); i++) {
//            if (!subscriberList.get(i).isFinished()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public Supplier<MUTABLE_ACCUMULATION> getSupplier() {
//        return supplier;
//    }
//}