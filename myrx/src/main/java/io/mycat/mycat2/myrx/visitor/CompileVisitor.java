package io.mycat.mycat2.myrx.visitor;

import io.mycat.mycat2.myrx.element.*;
import io.mycat.mycat2.myrx.flow.inner.LeafNode;
import io.mycat.mycat2.myrx.flow.inner.MutilNode;
import io.mycat.mycat2.myrx.flow.inner.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;

/**
 * Created by jamie on 2017/10/14.
 */
public class CompileVisitor {
    public void visit(Condition condition) {
        visitConditionHelper(condition.left, this);
        visitConditionHelper(condition.right, this);
    }

    private static void visitConditionHelper(Object th, CompileVisitor visitor) {
        if (th instanceof String) {

        } else {
            Condition condition1 = (Condition) th;
            condition1.accept(visitor);
        }
    }

    public void visit(Element element) {
        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+element);
    }

    public LeafNode visit(INQuery inQuery) {
        LeafNode left = new LeafNode();
        /////////////////////////////
        Element element = inQuery;
        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return left;

    }


    public Node visit(Join join) {
        MutilNode mutilNode = new MutilNode();
        for (int i = 0; i < join.elements.length; i++) {
            Node node = join.elements[i].accept(this);
            mutilNode.addNode(node);
        }
        return mutilNode;
    }

    public Node visit(Where where) {
        Node node = where.select.accept(this);
        return node;
    }

    public Node visit(Out out) {
        return out.element.accept(this);
    }

    public Node visit(Which which) {
        return which.in.accept(this);
    }

}
