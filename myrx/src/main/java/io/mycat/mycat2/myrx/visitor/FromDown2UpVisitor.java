package io.mycat.mycat2.myrx.visitor;

import io.mycat.mycat2.myrx.element.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;

/**
 * Created by jamie on 2017/10/14.
 */
public class FromDown2UpVisitor {
    List<SubmissionPublisher> publisherList = new ArrayList<>();
    List<INQuery> inQueryList = new ArrayList<>();
    List<Runnable> actions = new ArrayList<>();
    Map<INQuery, Map<String, String>> asMap = new HashMap<>();
  public   GeneratorVisitor generatorVisitor=new GeneratorVisitor();
    public void visit(Condition condition) {
        visitConditionHelper(condition.left, this);
        visitConditionHelper(condition.right, this);
    }

    private static void visitConditionHelper(Object th, FromDown2UpVisitor visitor) {
        if (th instanceof String) {

        } else {
            Condition condition1 = (Condition) th;
            condition1.accept(visitor);
        }
    }

    public void visit(Element element) {

    }

    public void visit(INQuery inQuery) {
        inQueryList.add(inQuery);
        /////////////////////////////
        Element element = inQuery;
        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        formDown2Join(element,(c)->{
       c.accept(generatorVisitor);

        });


        ////////////////////////////
    }
    static void formDown2Join(Element down,Consumer<Element> consumer){
        do {
            System.out.println("up=>"+down.toString());
            consumer.accept(down);
            if (down instanceof Join){
                break;
            }
            down = down.getUpNode();
        } while (down != null);
    }

    public void visit(Join join) {
        for (int i = 0; i < join.elements.length; i++) {
            INQuery query = join.elements[i].accept(this);
        }
        formDown2Join(join.getUpNode(),(c)->{
            c.accept(generatorVisitor);
        });
    }

    public void visit(Where where) {
        where.select.accept(this);

    }
    public void visit(Out out) {
        out.element.accept(this);
    }
    public void visit(Which which) {
        which.in.accept(this);
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
}
