//package io.mycat.mycat2.myrx.flow.jdk9;
//
//import java.util.concurrent.Flow;
//
///**
// * Created by jamie on 2017/10/14.
// */
//public class PrintSubscriber implements Flow.Subscriber<Object>{
//
//    private Flow.Subscription subscription;
//
//
//    @Override
//    public void onSubscribe(Flow.Subscription subscription) {
//        this.subscription = subscription;
//        subscription.request(1); //a value of  Long.MAX_VALUE may be considered as effectively unbounded
//    }
//
//    @Override
//    public void onNext(Object item) {
//        System.out.println("=> " + item);
//        subscription.request(1); //a value of  Long.MAX_VALUE may be considered as effectively unbounded
//    }
//
//    @Override
//    public void onError(Throwable t) {
//        t.printStackTrace();
//    }
//
//    @Override
//    public void onComplete() {
//        System.out.println("Done");
//    }
//
//
//    public Flow.Subscription getSubscription() {
//        return subscription;
//    }
//}
