package io.mycat.mycat2.myrx.visitor;

import io.mycat.mycat2.myrx.element.*;
import io.mycat.mycat2.myrx.flow.inner.ChainNode;
import io.mycat.mycat2.myrx.flow.inner.LeafNode;
import io.mycat.mycat2.myrx.flow.inner.MutilNode;
import io.mycat.mycat2.myrx.flow.inner.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamie on 2017/10/14.
 */
public class CompileVisitor {
    List<Node> source = new ArrayList<>();

    private static void visitConditionHelper(Object th, CompileVisitor visitor) {
        if (th instanceof String) {

        } else {
            Condition condition1 = (Condition) th;
            condition1.accept(visitor);
        }
    }

    public void visit(Condition condition) {
        visitConditionHelper(condition.left, this);
        visitConditionHelper(condition.right, this);
    }

    public void visit(Element element) {
        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+element);
    }

    public LeafNode visit(INQuery inQuery) {
        LeafNode left = new LeafNode();
        /////////////////////////////
        Element element = inQuery;
        left.setName(inQuery.sql);
        source.add(left);
        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return left;

    }


    public Node visit(Join join) {
        MutilNode mutilNode = new MutilNode();
        for (int i = 0; i < join.elements.length; i++) {
            Node node = join.elements[i].accept(this);
            mutilNode.addNode(node);
            node.setTopNode(mutilNode);
        }
        return mutilNode;
    }

    public Node visit(Where where) {
        ChainNode chainNode = new ChainNode();
        Node node = where.select.accept(this);
        node.setTopNode(chainNode);
        chainNode.setNextNode(node);
        return chainNode;
    }

    public Node visit(Out out) {
        ChainNode chainNode = new ChainNode();
        Node node = out.element.accept(this);
        node.setTopNode(chainNode);
        chainNode.setNextNode(node);
        return chainNode;
    }

    public Node visit(Which which) {
        ChainNode chainNode = new ChainNode();
        Node node = which.in.accept(this);
        node.setTopNode(chainNode);
        chainNode.setNextNode(node);
        return chainNode;
    }

    public List<Node> getSource() {
        return source;
    }
}
