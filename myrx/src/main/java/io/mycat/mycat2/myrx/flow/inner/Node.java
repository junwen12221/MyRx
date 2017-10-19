package io.mycat.mycat2.myrx.flow.inner;

/**
 * Created by jamie on 2017/10/16.
 */
public interface Node<T> {
    public void onNext(T item);

    public void onError(Throwable throwable);

    public void onComplete();
}
