package io.mycat.mycat2.myrx.flow.inner;

/**
 * Created by jamie on 2017/10/16.
 */
public interface MySubscriptionInterface {
    public void request(long n);

    public void cancel();
}
