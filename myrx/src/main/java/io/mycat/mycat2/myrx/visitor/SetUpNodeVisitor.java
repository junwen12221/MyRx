package io.mycat.mycat2.myrx.visitor;

import io.mycat.mycat2.myrx.element.*;

/**
 * Created by jamie on 2017/10/14.
 */
public class SetUpNodeVisitor {
    public void visit(Condition condition) {
        setConditionHelper(condition.left,this,condition);
        setConditionHelper(condition.right,this,condition);

    }
    private static void setConditionHelper(Object th,SetUpNodeVisitor visitor,Condition up){
        if (th instanceof String) {

        } else {
            Condition condition1=(Condition)th;
            condition1.setUpNode(up);
            condition1.accept(visitor);
        }
    }

    public void visit(Element element) {
        System.out.println("=>SetUpNodeVisitor异常");
    }

    public void visit(INQuery inQuery) {
        System.out.println(inQuery.toString());
    }

    public void visit(Join join) {
        for (int i = 0; i < join.elements.length; i++) {
            Element element= join.elements[i];
            element.setUpNode(join);
            element.accept(this);

        }
    }

    public void visit(Where where) {
        where.condition.setUpNode(where);
        where.condition.accept(this);
        where.select.setUpNode(where);
        where.select.accept(this);
    }

    public void visit(Which which) {
        which.in.setUpNode(which);
        which.in.accept(this);
    }
    public void visit(Out out) {
        out.element.setUpNode(out);
        out.element.accept(this);
    }
}
