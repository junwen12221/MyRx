package io.mycat.mycat2.myrx.visitor;

import io.mycat.mycat2.myrx.element.*;
import io.mycat.mycat2.myrx.flow.inner.MyProcessorInterface;
import io.mycat.mycat2.myrx.flow.inner.MyPublisherInterface;
import io.mycat.mycat2.myrx.flow.inner.MySubsriberInterface;
import io.mycat.mycat2.myrx.flow.inner.impl.MyFilterProcessor;
import io.mycat.mycat2.myrx.flow.inner.impl.MyJoinProcessor;
import io.mycat.mycat2.myrx.flow.inner.impl.MyPublisher;

import java.util.*;


/**
 * Created by jamie on 2017/10/15.
 */
public class GeneratorVisitor {
    List<MyPublisherInterface> publisherList = new ArrayList<>();
    List<INQuery> inQueryList = new ArrayList<>();
    List<Runnable> actions = new ArrayList<>();
    Map<INQuery, Map<String, String>> asMap = new HashMap<>();
    Map<Join, List<MyPublisherInterface>> join2PublisherListMap = new HashMap<>();
    Map<Join, List<MyProcessorInterface>> join2ProcessorListMap = new HashMap<>();
    Map<Join, List<MySubsriberInterface>> join2SubsriberListMap = new HashMap<>();
    Map<Join, List<MyJoinProcessor>> join2JoinProcessorListMap = new HashMap<>();
    Map<Join, MyJoinProcessor> join2JoinProcessorMap = new HashMap<>();
    Map<Distribute, List<Object>> distributedMap = new HashMap<>();
    ;
    Object arg;
    private Out out;

    private static void setConditionHelper(Object th, GeneratorVisitor visitor, Condition up) {
        if (th instanceof String) {
//            debug(th);
        } else {
            Condition condition1 = (Condition) th;
            condition1.accept(visitor);
        }
    }

    private static void debug(Object... args) {
        System.out.println(Arrays.toString(args));
    }

    public void visit(Condition condition) {
        setConditionHelper(condition.left, this, condition);
        setConditionHelper(condition.right, this, condition);

    }

    public void visit(Element element) {
        debug(element);
    }

    public void visit(INQuery inQuery) {
//        debug("执行" + inQuery.sql);
//        debug("别名" + inQuery.aliasTable);
        arg = new MyPublisher();
    }

    public void visit(Join join) {
        if (arg instanceof MyJoinProcessor) {
            join2JoinProcessorListMap.compute(join, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add((MyJoinProcessor) arg);
                return v;
            });
        } else if (arg instanceof MyProcessorInterface) {
            join2ProcessorListMap.compute(join, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add((MyProcessorInterface) arg);
                return v;
            });
        } else if (arg instanceof MyPublisherInterface) {
            join2PublisherListMap.compute(join, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add((MyPublisherInterface) arg);
                return v;
            });
        } else if (arg instanceof MySubsriberInterface) {
            join2SubsriberListMap.compute(join, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add((MySubsriberInterface) arg);
                return v;
            });
        } else {
            System.out.println("sss");
        }

        if (!join2JoinProcessorMap.containsKey(join)) {
            MyJoinProcessor joinProcessor = new MyJoinProcessor();
            join2JoinProcessorMap.put(join, joinProcessor);
        }
        arg = join2JoinProcessorMap.get(join);
    }

    public void visit(Where where) {
        MyProcessorInterface processorInterface = new MyFilterProcessor(where.predicate);
        if (arg instanceof MyPublisher) {
            ((MyPublisher) arg).subscribe(processorInterface);
        }

//        debug(where);
    }

    public void visit(Which which) {
//        debug(which);
//        debug("需要实现改写字段 别名"+which.alias+":"+which.table);
    }

    public List<MyPublisherInterface> getPublisherList() {
        return publisherList;
    }

    public List<INQuery> getInQueryList() {
        return inQueryList;
    }

    public List<Runnable> getActions() {
        return actions;
    }

    public Map<INQuery, Map<String, String>> getAsMap() {
        return asMap;
    }

    public Map<Join, List<MyPublisherInterface>> getJoin2PublisherListMap() {
        return join2PublisherListMap;
    }

    public Map<Join, List<MyProcessorInterface>> getJoin2ProcessorListMap() {
        return join2ProcessorListMap;
    }

    public Map<Join, List<MySubsriberInterface>> getJoin2SubsriberListMap() {
        return join2SubsriberListMap;
    }

    public Map<Join, MyJoinProcessor> getJoin2JoinProcessorMap() {
        return join2JoinProcessorMap;
    }

    public void visit(Out out) {
        this.out=out;
    }

    public Out getOut() {
        return out;
    }
}
