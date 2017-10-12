package io.mycat.mycat2.myrx;

import java.util.function.BiFunction;

import static io.mycat.mycat2.myrx.Engine.Op.AND;
import static io.mycat.mycat2.myrx.Engine.Op.IN;

/**
 * Created by jamie on 2017/10/12.
 */
public class Engine {
    static  class INQuery implements Meng {
        String sql;
        String aliasTable;

        public INQuery(String sql, String aliasTable) {
            this.sql = sql;
            this.aliasTable = aliasTable;
        }

        public Meng which(String table, String alias) {
            return new Which(this,table,alias);
        }
    }
    static  class Which implements Meng {
        String table; String alias;
        INQuery in;
        public Which(INQuery inQuery,String table, String alias) {
            this.table = table;
            this.alias = alias;
            this.in=inQuery;
        }
    }
    protected static abstract class Element implements Meng {

    }

    public static INQuery fromSQL(String sql, String aliasTable) {
        return new INQuery(sql, aliasTable);
    }

    protected static class Where extends Element {
        Condition condition;
        Meng select;
        public Where where(Condition condition) {
            return null;
        }

        public Where(Condition condition, Meng select) {
            this.condition = condition;
            this.select = select;
        }
    }

    protected static class Condition extends Element {
    }


    public static Where join(Meng... mengs) {
        return null;
    }

    public static Condition condition(Condition left, Op op, Condition right) {
        return null;
    }

    public static Condition condition(String left, Op op, String right) {
        return null;
    }

    public static enum Op implements BiFunction<Object, Object, Object> {
        IN {
            @Override
            public Object apply(Object o, Object o2) {
                return null;
            }
        }, OR {
            @Override
            public Object apply(Object o, Object o2) {
                return null;
            }
        }, AND {
            @Override
            public Object apply(Object o, Object o2) {
                return null;
            }
        }

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
//        Meng sql2 = Engine
//                .fromSQL("select b.id,b.username from b", "b")
//                .which("tableB", "b");
//        Engine.join(sql1, sql2)
//                .where(condition(
//                        id_in_list,
//                        AND,
//                        condition("b.id", AND, "a.id")))
//                .collect((res) -> {
//
//                });
    }

}
