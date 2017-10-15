package io.mycat.mycat2.myrx;

import io.mycat.mycat2.myrx.element.*;
import io.mycat.mycat2.myrx.visitor.FromDown2UpVisitor;
import io.mycat.mycat2.myrx.visitor.SetUpNodeVisitor;

import static io.mycat.mycat2.myrx.element.Op.AND;
import static io.mycat.mycat2.myrx.element.Op.IN;

/**
 * Created by jamie on 2017/10/12.
 */
public class Engine {

    public static INQuery from(String sql, String aliasTable) {
        return new INQuery(sql, aliasTable);
    }

    public static INQuery fromSQL(String sql, String aliasTable) {
        return new INQuery(sql, aliasTable);
    }


    public static Meng join(Element... elements) {
        return new Join(elements);
    }

    public static Condition condition(Object left, Op op, Object right) {
        return new Condition(left, op, right);
    }


    public static void main(String[] args) throws Exception {
        //select a.id,a.price,b.username where a.id in (111,222,333,444) and b.id=a.id
        Condition id_in_list = Engine.condition("a.id", IN, "(111,222,333,444)");
        Out sql3 = fromSQL("select a.id,a.price", "a")
                .which("tableA", "a")
                .where(id_in_list)
                .join(
                        fromSQL("select b.id,b.username from b", "b")
                                .which("tableB", "b")
                )
                .where(
                        id_in_list,
                        AND,
                        condition("b.id", AND, "a.id"))
                .join(fromSQL("select a.id,a.price", "a"))
                .where("1", Op.IN, "(1,2)")
                .out("tableA.id", "tableA.price", "tableB.username");


        sql3.accept(new SetUpNodeVisitor());
        FromDown2UpVisitor visitor = new FromDown2UpVisitor();
        sql3.accept(visitor);
        System.out.println("=>>>>>>>>>>>>>>>>>> join <<<<<<<<<<<<<<<<<<<<<<<=");
        System.out.println(visitor.generatorVisitor.getJoinMap());
        System.out.println(visitor.generatorVisitor.getOutJoin());
        System.out.println(visitor.generatorVisitor.getOut());
    }

}
