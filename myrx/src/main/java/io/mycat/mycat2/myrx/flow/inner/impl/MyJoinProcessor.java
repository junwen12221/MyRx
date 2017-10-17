package io.mycat.mycat2.myrx.flow.inner.impl;

import io.mycat.mycat2.myrx.flow.inner.MyProcessorInterface;
import io.mycat.mycat2.myrx.flow.inner.MyPublisherInterface;
import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;
import io.mycat.mycat2.myrx.flow.inner.TriFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by jamie on 2017/10/16.
 */
public class MyJoinProcessor<T> implements MyPublisherInterface<T>, AutoCloseable {
    Map<String, MyProcessorInterface> map = new HashMap<>();
    MyJoinProcessorOut out;

    public <R> void put(String name, MyProcessorInterface<T, R> processorInterface) {
        map.put(name, processorInterface);
    }

    public void init() {
        List<MyJoinProcessorIn> ins = map.entrySet().stream().map((i) -> {
            MyJoinProcessorIn in = new MyJoinProcessorIn();
            in.setName(i.getKey());
            i.getValue().subscribe(in);
            return in;
        }).collect(Collectors.toList());
        out = new MyJoinProcessorOut(ins, () -> new HashMap<String, String>(), (item, collect, collectList) -> {
            System.out.println(item + "   " + collect + "   " + collectList);
            return item;
        });
    }

    @Override
    public void subscribe(MySubsriberInterface<? super T> subscriber) {
        out.subscribe(subscriber);
    }

    @Override
    public void close() throws Exception {
        out.close();
    }

    static class MyJoinProcessorIn<T, COLLECT, RESULT> implements MySubsriberInterface<T> {
        //MyJoinProcessorIn只负责缓冲,不进行处理条件的判断
        String name;
        MyJoinProcessorOut<T, COLLECT, RESULT> out;
        COLLECT collect;
        boolean isComplete = false;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void onNext(T item) {
            out.onNext(item, this);
        }

        public void onError(Throwable throwable) {
            out.onError(throwable);
        }

        public void onComplete() {
            this.isComplete = true;
            out.onComplete();
        }

        public boolean isComplete() {
            return isComplete;
        }

        public void setComplete(boolean complete) {
            isComplete = complete;
        }

        public MyJoinProcessorOut<T, COLLECT, RESULT> getOut() {
            return out;
        }

        public void setOut(MyJoinProcessorOut<T, COLLECT, RESULT> out) {
            this.out = out;
        }

        public COLLECT getCollect() {
            return collect;
        }

        public void setCollect(COLLECT collect) {
            this.collect = collect;
        }
    }

    static class MyJoinProcessorOut<T, COLLECT, RESULT> extends MyPublisher<RESULT> {
        MyJoinProcessorIn<T, COLLECT, RESULT>[] joinProcessorIn;
        COLLECT[] collects;
        TriFunction<T, MyJoinProcessorIn<T, COLLECT, RESULT>, MyJoinProcessorIn<T, COLLECT, RESULT>[], RESULT> triFunction;

        public MyJoinProcessorOut(List<MyJoinProcessorIn<T, COLLECT, RESULT>> joinProcessorIn,
                                  Supplier<COLLECT> supplier,
                                  TriFunction<T, MyJoinProcessorIn<T, COLLECT, RESULT>, MyJoinProcessorIn<T, COLLECT, RESULT>[], RESULT> triFunction) {
            this.joinProcessorIn = joinProcessorIn.toArray(new MyJoinProcessorIn[joinProcessorIn.size()]);
            this.collects = (COLLECT[]) new Object[joinProcessorIn.size()];
            for (int i = 0; i < joinProcessorIn.size(); i++) {
                MyJoinProcessorIn in = joinProcessorIn.get(i);
                in.collect = collects[i] = supplier.get();
                in.setOut(this);
            }
            this.triFunction = triFunction;
        }

        public void onNext(T item, MyJoinProcessorIn<T, COLLECT, RESULT> collect) {
            super.submit(triFunction.accept(item, collect, joinProcessorIn));
        }

        public void onError(Throwable throwable) {
            throwable.printStackTrace();
            try {
                super.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onComplete() {
            boolean res = this.joinProcessorIn[1].isComplete();
            for (int i = 1; i < this.joinProcessorIn.length; i++) {
                res = res && this.joinProcessorIn[i].isComplete();
            }
            if (res == true) {
                try {
                    super.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
