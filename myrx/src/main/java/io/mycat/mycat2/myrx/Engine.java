package io.mycat.mycat2.myrx;

import io.mycat.mycat2.myrx.element.*;

import java.util.stream.Stream;

import static io.mycat.mycat2.myrx.element.Op.AND;
import static io.mycat.mycat2.myrx.element.Op.IN;

/**
 * Created by jamie on 2017/10/12.
 */
public class Engine {



    public static INQuery fromSQL(String sql, String aliasTable) {
        return new INQuery(sql, aliasTable);
    }


    public static Meng join(Meng... mengs) {
        return new Join(mengs);
    }

    public static Condition condition(Object left, Op op, Object right) {
        return new Condition(left, op, right);
    }


    public static void main(String[] args) {
        //select a.id,a.price,b.username where a.id in (111,222,333,444) and b.id=a.id
        Condition id_in_list = Engine.condition("a.id", IN, "(111,222,333,444)");
        Meng sql1 = Engine
                .fromSQL("select a.id,a.price", "a")
                .which("tableA", "a")
                .where(id_in_list);
        sql1.collect((c)->{
           System.out.println("=====");
        });
        Meng sql2 = Engine
                .fromSQL("select b.id,b.username from b", "b")
                .which("tableB", "b");
        Meng sql3 = Engine.join(sql1, sql2)
                .where(condition(
                        id_in_list,
                        AND,
                        condition("b.id", AND, "a.id")));
        sql3.collect((res) -> {
            System.out.println("=====2");
        });
        System.out.println("=====3");
        sql3.visit();
    }

}
