package io.mycat.mycat2.myrx.visitor;

import io.mycat.mycat2.myrx.element.*;
import io.mycat.mycat2.myrx.flow.MySubscriber;
import io.mycat.mycat2.myrx.flow.PrintSubscriber;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.SubmissionPublisher;

/**
 * Created by jamie on 2017/10/15.
 */
public class GeneratorVisitor {
    List<SubmissionPublisher> publisherList = new ArrayList<>();
    List<INQuery> inQueryList = new ArrayList<>();
    List<Runnable> actions = new ArrayList<>();
    Map<INQuery, Map<String, String>> asMap = new HashMap<>();
    Map<Join,List<Object>> joinMap = new HashMap<>();
    Map<Distribute,List<Object>> distributedMap = new HashMap<>();
    Object target;
    PrintSubscriber printSubscriber=new PrintSubscriber();
    private Join outJoin ;
    private Out out;

    public void visit(Condition condition) {
        setConditionHelper(condition.left, this, condition);
        setConditionHelper(condition.right, this, condition);

    }

    private static void setConditionHelper(Object th, GeneratorVisitor visitor, Condition up) {
        if (th instanceof String) {
//            debug(th);
        } else {
            Condition condition1 = (Condition) th;
            condition1.accept(visitor);
        }
    }

    public void visit(Element element) {
        debug(element);
    }

    public void visit(INQuery inQuery) {
//        debug("执行" + inQuery.sql);
//        debug("别名" + inQuery.aliasTable);
        target= new SubmissionPublisher<>();
    }

    public void visit(Join join) {
        joinMap.compute(join,(k,v)->{
           if (v==null){v=new ArrayList<>();}
           v.add(target);
           return v;
        });
        target=join;
        outJoin=join;
    }

    public void visit(Where where) {
//        debug(where);
    }

    public void visit(Which which) {
//        debug(which);
//        debug("需要实现改写字段 别名"+which.alias+":"+which.table);
    }
    private static void debug(Object... args) {
        System.out.println(Arrays.toString(args));
    }

    public Map<Join, List<Object>> getJoinMap() {
        return joinMap;
    }

    public List<SubmissionPublisher> getPublisherList() {
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

    public Join getOutJoin() {
        return outJoin;
    }
    public void visit(Out out) {
        this.out=out;
    }

    public Out getOut() {
        return out;
    }
}
