//package io.mycat.mycat2.myrx.flow.jdk9;
//
//import java.util.concurrent.Flow;
//
//public class MySubscriber<T> implements Flow.Subscriber<T>{
//    private String name="";
//    private long count=0;
//    private Flow.Subscription subscription;
//    private MyAssembleProcessor processors;
//    private  boolean isFinished=false;
//    private long limit=-1;
//    public boolean isFinished(){
//       return count==limit||isFinished;
//    }
//    private Object mutableAccumulation;
//
//    public MySubscriber(String name, MyAssembleProcessor processors) {
//        this.name = name;
//        this.processors = processors;
//        processors.subscriberList.add(this);
//        mutableAccumulation=processors.supplier.get();
//    }
//
//    @Override
//    public void onSubscribe(Flow.Subscription subscription) {
//        this.subscription = subscription;
//        subscription.request(1); //a value of  Long.MAX_VALUE may be considered as effectively unbounded
//        System.out.println(subscription.toString());
//    }
//
//    @Override
//    public void onNext(T item) {
//        System.out.println("Got : " + item);
//        processors.collect(this,item);
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
//        processors.endCollect(this);
//        System.out.println("Done");
//    }
//
//    public long getCount() {
//        return count;
//    }
//
//    public void setCount(long count) {
//        this.count = count;
//    }
//
//    public Flow.Subscription getSubscription() {
//        return subscription;
//    }
//
//    public void setFinished(boolean finished) {
//        isFinished = finished;
//    }
//
//    public Object getMutableAccumulation() {
//        return mutableAccumulation;
//    }
//}