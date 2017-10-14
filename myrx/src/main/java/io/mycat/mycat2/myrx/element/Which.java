package io.mycat.mycat2.myrx.element;

public class Which implements Meng {
    String table;
    String alias;
    INQuery in;

    public Which(INQuery inQuery, String table, String alias) {
        this.table = table;
        this.alias = alias;
        this.in = inQuery;
    }
}